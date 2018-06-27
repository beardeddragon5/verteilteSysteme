package Verein;


/**
* Verein/_VereinsmitgliedImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verein.idl
* Montag, 25. Juni 2018 11:14 Uhr MESZ
*/

public abstract class _VereinsmitgliedImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements Verein.Vereinsmitglied, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _VereinsmitgliedImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_mname", new java.lang.Integer (0));
    _methods.put ("_get_mvereine", new java.lang.Integer (1));
    _methods.put ("erhoeheBeitrag", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Verein/Vereinsmitglied/_get_mname
       {
         String $result = null;
         $result = this.mname ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // Verein/Vereinsmitglied/_get_mvereine
       {
         Verein.VereinsmitgliedPackage.verein $result[] = null;
         $result = this.mvereine ();
         out = $rh.createReply();
         Verein.VereinsmitgliedPackage.VereinSeqHelper.write (out, $result);
         break;
       }

       case 2:  // Verein/Vereinsmitglied/erhoeheBeitrag
       {
         String verein = in.read_string ();
         short erhoehung = in.read_short ();
         short $result = (short)0;
         $result = this.erhoeheBeitrag (verein, erhoehung);
         out = $rh.createReply();
         out.write_short ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Verein/Vereinsmitglied:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _VereinsmitgliedImplBase