package Verein;

/**
* Verein/TypeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verein.idl
* Montag, 25. Juni 2018 11:14 Uhr MESZ
*/

public final class TypeHolder implements org.omg.CORBA.portable.Streamable
{
  public Verein.Type value = null;

  public TypeHolder ()
  {
  }

  public TypeHolder (Verein.Type initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Verein.TypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Verein.TypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Verein.TypeHelper.type ();
  }

}
