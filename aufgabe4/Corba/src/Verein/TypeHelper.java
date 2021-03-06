package Verein;


/**
* Verein/TypeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verein.idl
* Montag, 25. Juni 2018 11:14 Uhr MESZ
*/

abstract public class TypeHelper
{
  private static String  _id = "IDL:Verein/Type:1.0";

  public static void insert (org.omg.CORBA.Any a, Verein.Type that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Verein.Type extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (Verein.TypeHelper.id (), "Type", new String[] { "Sport", "Sozial", "Culture"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Verein.Type read (org.omg.CORBA.portable.InputStream istream)
  {
    return Verein.Type.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Verein.Type value)
  {
    ostream.write_long (value.value ());
  }

}
