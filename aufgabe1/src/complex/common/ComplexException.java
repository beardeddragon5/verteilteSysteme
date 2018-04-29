// Exception fuer anwendungsdefinierte Fehlerfaelle
// gehoert zur Schnittstellendefinition
package complex.common;

public class ComplexException extends Exception {
  public ComplexException (String msg) {super(msg);}
}
