// Client
// Usage: java ComplexAdderClient <hostname> <zahl1.real> <zahl1.imag> <zahl2.real> <zahl2.imag>
package complex.client;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import complex.common.*;

public class ComplexAdderClient {
  public static void main (String args[]) {
    try {
      Registry reg = LocateRegistry.getRegistry(1099);

      ComplexAdder ad = (ComplexAdder) reg.lookup ("myComplexAdder");
      //ComplexAdder ad = (ComplexAdder) reg.lookup ("rmi://" + args[0] + ":1099/" + "myComplexAdder");
      //ComplexAdder ad = (ComplexAdder) Naming.lookup ("rmi://" + args[0] + "/" + "myAdder");

      Complex s1 = new Complex();
      Complex s2 = new Complex();
      Complex s  = new Complex();
      s1.real = Integer.parseInt(args[1]);
      s1.imag = Integer.parseInt(args[2]);
      s2.real = Integer.parseInt(args[3]);
      s2.imag = Integer.parseInt(args[4]);
      s = ad.add(s1,s2);
      System.out.println ("Summe: (" + s.real + "," + s.imag + ")");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println ("ClientException: " + e.getMessage());
    }
  }
}
