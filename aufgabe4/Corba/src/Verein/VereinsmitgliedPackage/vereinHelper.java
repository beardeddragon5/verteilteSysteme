package Verein.VereinsmitgliedPackage;


/**
* Verein/VereinsmitgliedPackage/vereinHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verein.idl
* Montag, 25. Juni 2018 11:14 Uhr MESZ
*/

abstract public class vereinHelper
{
  private static String  _id = "IDL:Verein/Vereinsmitglied/verein:1.0";

  public static void insert (org.omg.CORBA.Any a, Verein.VereinsmitgliedPackage.verein that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Verein.VereinsmitgliedPackage.verein extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "vname",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_short);
          _members0[1] = new org.omg.CORBA.StructMember (
            "vbeitrag",
            _tcOf_members0,
            null);
          _tcOf_members0 = Verein.TypeHelper.type ();
          _members0[2] = new org.omg.CORBA.StructMember (
            "vtype",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "city",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (Verein.VereinsmitgliedPackage.vereinHelper.id (), "verein", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Verein.VereinsmitgliedPackage.verein read (org.omg.CORBA.portable.InputStream istream)
  {
    Verein.VereinsmitgliedPackage.verein value = new Verein.VereinsmitgliedPackage.verein ();
    value.vname = istream.read_string ();
    value.vbeitrag = istream.read_short ();
    value.vtype = Verein.TypeHelper.read (istream);
    value.city = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Verein.VereinsmitgliedPackage.verein value)
  {
    ostream.write_string (value.vname);
    ostream.write_short (value.vbeitrag);
    Verein.TypeHelper.write (ostream, value.vtype);
    ostream.write_string (value.city);
  }

}
