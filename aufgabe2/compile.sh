#!/bin/bash

set -e

cd src

rm -rf rdbase
mkdir -p rdbase
cp rdbase.x rdbase/

cd rdbase
rpcgen rdbase.x
rm rdbase.x
cc -c *.c
cd ..

cd client
cc -c *.c
cd ..

cd server
cc -c *.c
cd ..

mkdir -p ../bin
cc -o ../bin/client client/*.o rdbase/rdbase_clnt.o rdbase/rdbase_xdr.o
cc -o ../bin/server server/*.o rdbase/rdbase_svc.o rdbase/rdbase_xdr.o

cd ..
