// Server
// Usage: java ComplexAdderServer
package complex.server;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ComplexAdderServer {
  public static void main (String args[]) {
    try {
      Registry reg = LocateRegistry.createRegistry (1099);

      ComplexAdderImpl ad = new ComplexAdderImpl ("myComplexAdder", reg);
      //AdderImpl ad = new AdderImpl ("rmi://localhost:1099/myComplexAdder");
      System.out.println ("ComplAdder Server ready.");
    } catch (Exception e) {
      System.out.println ("ServerException: " + e.getMessage());
    }
  }
}
