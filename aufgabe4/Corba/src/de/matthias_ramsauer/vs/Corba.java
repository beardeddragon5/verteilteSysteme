/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.matthias_ramsauer.vs;

import Verein.Type;
import Verein.VereinsmitgliedPackage.verein;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 *
 * @author matthias
 */
public class Corba {

    /**
     * @param args the command line arguments
     * @throws org.omg.CORBA.ORBPackage.InvalidName
     * @throws org.omg.CosNaming.NamingContextPackage.NotFound
     * @throws org.omg.CosNaming.NamingContextPackage.AlreadyBound
     * @throws org.omg.CosNaming.NamingContextPackage.CannotProceed
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     */
    public static void main(String[] args) throws InvalidName, NotFound, AlreadyBound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "1050");
        ORB orb = ORB.init(args, props);
        
        final int HANSE_SPORT = 0;
        final int HANSE_CULTURE = 1;
        final int HANSE_SOZIAL = 2;
        final int WERDE_SPORT = 3;
        final int WERDE_THEATER = 4;
        
        final Map<Integer, Function<Integer, verein>> vereine = new HashMap<>();
        vereine.put(HANSE_SPORT, (b) -> new verein("Hansasport", b.shortValue(), Type.Sport, "Hamburg"));
        vereine.put(HANSE_CULTURE, (b) -> new verein("Hansakultur", b.shortValue(), Type.Culture, "Hamburg"));
        vereine.put(HANSE_SOZIAL, (b) -> new verein("Hansahilfe", b.shortValue(), Type.Sozial, "Hamburg"));
        vereine.put(WERDE_SPORT, (b) -> new verein("Werdersport", b.shortValue(), Type.Sport, "Bremen"));
        vereine.put(WERDE_THEATER, (b) -> new verein("Werdertheater", b.shortValue(), Type.Culture, "Bremen"));
        
        final BiFunction<Integer, Integer, verein> v = (ver, b) -> vereine.get(ver).apply(b);
        
        final VereinsMitgliedImpl[] members = new VereinsMitgliedImpl[]{
            new VereinsMitgliedImpl("A", Arrays.asList(v.apply(HANSE_SPORT, 100))),
            new VereinsMitgliedImpl("D", Arrays.asList(v.apply(HANSE_SPORT, 200), v.apply(HANSE_CULTURE, 500))),
            new VereinsMitgliedImpl("N", Arrays.asList(v.apply(HANSE_CULTURE, 350))),
            new VereinsMitgliedImpl("P", Arrays.asList(v.apply(HANSE_CULTURE, 150), v.apply(HANSE_SOZIAL, 500), v.apply(WERDE_SPORT, 600))),
            new VereinsMitgliedImpl("S", Arrays.asList(v.apply(WERDE_SPORT, 300), v.apply(WERDE_THEATER, 700))),
            new VereinsMitgliedImpl("V", Arrays.asList(v.apply(WERDE_THEATER, 120))),
        };
        
        final NamingContext ctx = NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
        
        final NameComponent hamburg = new NameComponent("Hamburg", "directory");
        final NameComponent bremen = new NameComponent("Bremen", "directory");
        final NamingContext hamburgCtx = ctx.bind_new_context(new NameComponent[]{ hamburg });
        final NamingContext bremenCtx = ctx.bind_new_context(new NameComponent[]{ bremen });
        
        final Map<String, NamingContext> vereinCtx = vereine.values().stream().map(f -> f.apply(0)).collect(Collectors.toMap((verein ver) -> ver.vname, (verein ver) -> {
            final NamingContext root;
            switch(ver.city) {
                case "Bremen":
                    root = bremenCtx;
                    break;
                case "Hamburg":
                    root = hamburgCtx;
                    break;
                default:
                    root = null;
                    break;
            }
            final NameComponent verName = new NameComponent(ver.vname, "directory");
            try {
                System.out.printf("add %s to %s\n", ver.vname, ver.city);
                return root.bind_new_context(new NameComponent[]{ verName });
            } catch (NotFound | AlreadyBound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName ex) {
                Logger.getLogger(Corba.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));
        
        for (VereinsMitgliedImpl member : members) {
            final NameComponent comp = new NameComponent(member.mname(), "text");
            for (verein ver : member.mvereine()) {
                vereinCtx.get(ver.vname).rebind(new NameComponent[]{comp}, member);
                System.out.printf("add %s to %s\n", member.mname(), ver.vname);
            }
        }
        orb.run();
    }
    
}
