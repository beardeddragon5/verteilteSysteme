
/* rdbase.x */

/* RPC declarations for dictionary program */

const MAXWORD = 50;
const DICTSIZ= 100;
const MAXRESULT = 10;
const MAXSUCHERG = 100;

struct upd {
   string upd_old <MAXWORD>;
   string upd_new <MAXWORD>;
};

struct oneword {
   string word <MAXWORD>;
};

struct manywords {
   oneword words <DICTSIZ>;
};


program RDBASEPROG{
   version RDBASEVERS {

      int  INITW (void)     = 1;
      int  INSERTW (string) = 2;
      int  DELETEW (string) = 3;
      int  LOOKUPW (string) = 4;
      int  UPDATEW (upd)    = 5;

      int COUNTW (void) = 6;
      string SELECTW (void) = 7;
      manywords SELECT2W (void) = 8;

   } = 1;
} = 0x30090949;
