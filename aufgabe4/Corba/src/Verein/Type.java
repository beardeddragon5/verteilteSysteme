package Verein;


/**
* Verein/Type.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verein.idl
* Montag, 25. Juni 2018 11:14 Uhr MESZ
*/

public class Type implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static Verein.Type[] __array = new Verein.Type [__size];

  public static final int _Sport = 0;
  public static final Verein.Type Sport = new Verein.Type(_Sport);
  public static final int _Sozial = 1;
  public static final Verein.Type Sozial = new Verein.Type(_Sozial);
  public static final int _Culture = 2;
  public static final Verein.Type Culture = new Verein.Type(_Culture);

  public int value ()
  {
    return __value;
  }

  public static Verein.Type from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected Type (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class Type
