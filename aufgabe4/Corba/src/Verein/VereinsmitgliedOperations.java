package Verein;


/**
* Verein/VereinsmitgliedOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verein.idl
* Montag, 25. Juni 2018 11:14 Uhr MESZ
*/

public interface VereinsmitgliedOperations 
{
  String mname ();
  Verein.VereinsmitgliedPackage.verein[] mvereine ();
  short erhoeheBeitrag (String verein, short erhoehung);
} // interface VereinsmitgliedOperations