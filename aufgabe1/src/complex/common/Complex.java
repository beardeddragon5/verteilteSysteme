// serialisierbarer Datentyp complex
// gehoert zur Schnittstelle
package complex.common;

public class Complex implements java.io.Serializable {
  public int real;
  public int imag;

  public Complex () {
    this.real = 0;
    this.imag = 0;
  }

  public Complex (int r, int i) {
    this.real = r;
    this.imag = i;
  }
}
