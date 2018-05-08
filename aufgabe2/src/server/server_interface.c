
#include "server.h"

int* initw_1_svc(void*v, struct svc_req*r) {
  static int ret;
  ret = initw();
  return &ret;
}

int* insertw_1_svc(char** word, struct svc_req*r) {
  static int ret;
  ret = insertw(*word);
  return &ret;
}

int* deletew_1_svc(char** word, struct svc_req*r) {
  static int ret;
  ret = deletew(*word);
  return &ret;
}

int* lookupw_1_svc(char** word, struct svc_req*r) {
  static int ret;
  ret = lookupw(*word);
  return &ret;
}

int* updatew_1_svc(upd* u, struct svc_req*r) {
  static int ret;
  ret = updatew(u->upd_old, u->upd_new);
  return &ret;
}

int* countw_1_svc(void*v, struct svc_req*r) {
  static int ret;
  ret = countw();
  return &ret;
}

char** selectw_1_svc(void*v, struct svc_req*r) {
  static char* ret;
  ret = selectw();
  return &ret;
}

manywords* select2w_1_svc(void*v, struct svc_req*r) {
  return select2w();
}
