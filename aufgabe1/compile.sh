#!/bin/bash

set -e

rm -rf build
mkdir -p build

javac -d build src/complex/common/*
javac -classpath build -d build src/complex/client/*
javac -classpath build -d build src/complex/server/*

javac -d build src/bank/common/*
javac -classpath build -d build src/bank/client/*
javac -classpath build -d build src/bank/server/*


cd build
jar cfe ../complex-client.jar complex.client.ComplexAdderClient complex/common/* complex/client/*
jar cfe ../complex-server.jar complex.server.ComplexAdderServer complex/common/* complex/server/*

jar cfe ../bank-customerclient.jar bank.client.CustomerClient bank/common/* bank/client/*
jar cfe ../bank-taxclient.jar bank.client.TaxOfficeClient bank/common/* bank/client/*
jar cfe ../bank-server.jar bank.server.BankServer bank/common/* bank/server/*
cd ..
