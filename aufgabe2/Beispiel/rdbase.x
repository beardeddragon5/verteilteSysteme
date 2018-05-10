
/* rdbase.x */

/* RPC declarations for dictionary program */

const MAXWORD = 50;
const DICTSIZ= 100;

struct upd {
   string upd_old <MAXWORD>;
   string upd_new <MAXWORD>;
};

program RDBASEPROG{
   version RDBASEVERS {
   
      int  INITW (void)     = 1;
      int  INSERTW (string) = 2;
      int  DELETEW (string) = 3;
      int  LOOKUPW (string) = 4;
      int  UPDATEW (upd)    = 5;
      
   } = 1;
} = 0x30090949;

