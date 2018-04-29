// Schnittstellenimplementierung ComplexAdder
package complex.server;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import complex.common.*;

public class ComplexAdderImpl extends UnicastRemoteObject implements ComplexAdder {

  private Complex zahl;

  public ComplexAdderImpl (String name, Registry reg) throws RemoteException {
    //super();
    zahl = new Complex();
    zahl.real = 0;
    zahl.imag = 0;
    try {
      reg.rebind (name, this);
    } catch (Exception e) {
      System.out.println ("Exception: " + e.getMessage());
    }
  }

  public synchronized Complex add(Complex a, Complex b) throws RemoteException, ComplexException {
    zahl.real = a.real + b.real;
    zahl.imag = a.imag + b.imag;
    if ( zahl.real == 0 && zahl.imag == 0)
      throw new ComplexException ("Ergebnis ist Null!");
    return zahl;
  }
}
