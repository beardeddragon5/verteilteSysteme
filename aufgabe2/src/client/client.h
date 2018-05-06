
#include <stdio.h>

#include "../rdbase/rdbase.h"

int initw(CLIENT* handle);

int insertw(CLIENT* handle, char* word);

int deletew(CLIENT* handle, char* word);

int lookupw(CLIENT* handle, char* word);

int updatew(CLIENT* handle, upd u);

int countw(CLIENT* handle);

char* selectw(CLIENT* handle);

manywords select2w(CLIENT* handle);
