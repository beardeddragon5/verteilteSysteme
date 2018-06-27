/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.matthias_ramsauer.vs;

import Verein.Vereinsmitglied;
import Verein.VereinsmitgliedPackage.verein;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 *
 * @author matthias
 */
public class CorbaClient {
    
    public static Vereinsmitglied resolve(NamingContext ctx, String path) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
        final String[] parts = path.split("/");
        final NameComponent[] components = new NameComponent[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            final String kind = i == parts.length - 1 ? "text" : "directory";
            final String name = parts[i];
            
            components[i] = new NameComponent(name, kind);
        }
        
        return (Vereinsmitglied) ctx.resolve(components);
    }
    
    public static List<Vereinsmitglied> resolveGroup(NamingContext ctx, String path) throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
        final String[] parts = path.split("/");
        final NameComponent[] components = new NameComponent[parts.length];
        final List<Vereinsmitglied> members = new ArrayList<>();
        
        for (int i = 0; i < parts.length; i++) {
            components[i] = new NameComponent(parts[i], "directory");
        }
        
        final NamingContext group = (NamingContext) ctx.resolve(components);
        final BindingListHolder bl = new BindingListHolder();
        final BindingIteratorHolder bi = new BindingIteratorHolder();
        
        group.list(1000, bl, bi);
        
        final Binding[] bindings = bl.value;
        
        for (Binding binding : bindings) {
            members.add((Vereinsmitglied) group.resolve(binding.binding_name));
        }
        
        return members;
    }
    
    public static void get(Vereinsmitglied member) {
        System.out.println(member.mname());
        for (verein v : member.mvereine()) {
            System.out.printf("\t%s: %d €\n", v.vname, v.vbeitrag);
        }
    }
    
    public static void erhoehen(String param, Vereinsmitglied member) {
        final String verein = param.split("/", 3)[1];
        System.out.printf("neuer beitrag: %d\n", member.erhoeheBeitrag(verein, (short) 100));
    }
    
    public static void main(String[] args) throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
        final Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "1050");
        final ORB orb = ORB.init(args, props);
        
        final NamingContext nc = NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
        
        try(Scanner scan = new Scanner(System.in)) {
            String input;
            do {
                input = scan.nextLine();
                
                final String[] methodParam = input.split(" ", 2);
                final String method = methodParam[0];
                final String param = methodParam[1];
                
                switch(method) {
                    case "get":
                        get(resolve(nc, param));
                        break;
                    case "erhöhen":
                        erhoehen(param, resolve(nc, param));
                        break;
                    case "verein":
                        System.out.println(resolveGroup(nc, param)
                                .stream()
                                .map(m -> m.mname())
                                .collect(Collectors.joining("\n")));
                        break;
                }

            } while(!input.equals("exit"));
        }
        
    }
}
