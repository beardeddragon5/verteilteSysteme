
#include "client.h"

#define CHECK(x) (x == NULL ? 0 : *x)

#define WRAPPER_STRING(func)                \
  int func(CLIENT* handle, char* word) {    \
    int* ret = func ## _1(&word, handle);   \
    return CHECK(ret);                      \
  }

WRAPPER_STRING(insertw)
WRAPPER_STRING(deletew)
WRAPPER_STRING(lookupw)

int initw(CLIENT* handle) {
  int* ret = initw_1(NULL, handle);
  return CHECK(ret);
}

int updatew(CLIENT* handle, char* word1, char* word2) {
  upd u = { word1, word2 };
  int* ret = updatew_1(&u, handle);
  return CHECK(ret);
}

int countw(CLIENT* handle) {
  int* ret = countw_1(NULL, handle);
  return ret == NULL ? -1 : *ret;
}

char* selectw(CLIENT* handle) {
  char** ret = selectw_1(NULL, handle);
  return CHECK(ret);
}

manywords select2w(CLIENT* handle) {
  manywords* ret = select2w_1(NULL, handle);
  if ( ret == NULL ) {
    manywords out = { { 0, NULL } };
    return out;
  }
  return *ret;
}
