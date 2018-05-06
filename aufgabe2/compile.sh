#!/bin/bash

cd src
rm -rf rdbase
mkdir -p rdbase
cp rdbase.x rdbase/

cd rdbase
rpcgen rdbase.x
rm rdbase/rdbase.x
cc -c *.c
cd ..

cd client
cc -c *.c
cd ..

cd ..
