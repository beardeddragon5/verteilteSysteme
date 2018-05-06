
#include "client.h"

#define CHECK(x) (x == 0 ? 0 : *x)

int initw(CLIENT* handle) {
  int* ret = initw_1(NULL, handle);
  return CHECK(ret);
}

int insertw(CLIENT* handle, char* word) {
  int* ret = insertw_1(&word, handle);
  return CHECK(ret);
}

int deletew(CLIENT* handle, char* word) {
  int* ret = deletew_1(&word, handle);
  return CHECK(ret);
}

int lookupw(CLIENT* handle, char* word) {
  int* ret = lookupw_1(&word, handle);
  return CHECK(ret);
}

int updatew(CLIENT* handle, upd u) {
  int* ret = updatew_1(&u, handle);
  return CHECK(ret);
}

int countw(CLIENT* handle) {
  int* ret = countw_1(NULL, handle);
  return CHECK(ret);
}

char* selectw(CLIENT* handle) {
  int* ret = selectw_1(NULL, handle);
  return CHECK(ret);
}

manywords select2w(CLIENT* handle) {
  manywords* ret = select2w(NULL, handle);
  if ( ret == NULL ) {
    return { { 0, NULL } };
  }
  return *ret;
}
