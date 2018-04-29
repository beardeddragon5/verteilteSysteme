// Schnittstellendefinition ComplexAdder
package complex.common;

import java.rmi.*;

public interface ComplexAdder extends Remote {
  Complex add(Complex a, Complex b) throws RemoteException, ComplexException;
}
