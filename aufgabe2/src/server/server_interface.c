
#include "server.h"

#define WRAPPER_PARA(rettype, func, param)                          \
  rettype * func ## _1_svc(param * v, struct svc_req* r) {          \
    static rettype ret;                                             \
    ret = func(*v);                                                 \
    return &ret;                                                    \
  }

#define WRAPPER_VOID(rettype, func)                                 \
  rettype * func ## _1_svc(void* v, struct svc_req* r) {            \
    static rettype ret;                                             \
    ret = func();                                                   \
    return &ret;                                                    \
  }

WRAPPER_VOID(int, initw)
WRAPPER_PARA(int, insertw, char*)
WRAPPER_PARA(int, deletew, char*)
WRAPPER_PARA(int, lookupw, char*)
WRAPPER_VOID(int, countw)
WRAPPER_VOID(char*, selectw)

int* updatew_1_svc(upd* u, struct svc_req*r) {
  static int ret;
  ret = updatew(u->upd_old, u->upd_new);
  return &ret;
}

manywords* select2w_1_svc(void*v, struct svc_req*r) {
  return select2w();
}
