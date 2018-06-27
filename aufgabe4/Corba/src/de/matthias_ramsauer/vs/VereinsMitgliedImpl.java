/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.matthias_ramsauer.vs;

import Verein.VereinsmitgliedPackage.verein;
import Verein._VereinsmitgliedImplBase;
import java.util.List;

/**
 *
 * @author matthias
 */
public class VereinsMitgliedImpl extends _VereinsmitgliedImplBase {
    
    private final String mname;
    private final List<verein> vereine;

    public VereinsMitgliedImpl(String mname, List<verein> vereine) {
        this.vereine = vereine;
        this.mname = mname;
    }

    @Override
    public String mname() {
        return mname;
    }

    @Override
    public verein[] mvereine() {
        return vereine.toArray(new verein[vereine.size()]);
    }

    @Override
    public short erhoeheBeitrag(String verein, short erhoehung) {
        final verein ver = vereine.stream().filter(v -> v.vname.equals(verein)).findAny().orElse(null);
        ver.vbeitrag += erhoehung;
        return ver.vbeitrag;
    }
    
    
}
