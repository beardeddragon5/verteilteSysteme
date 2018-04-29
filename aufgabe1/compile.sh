#!/bin/bash

set -e

rm -rf build
mkdir -p build

javac -d build src/complex/common/*
javac -classpath build -d build src/complex/client/*
javac -classpath build -d build src/complex/server/*

cd build
jar cfe ../client.jar complex.client.ComplexAdderClient complex/common/* complex/client/*
jar cfe ../server.jar complex.server.ComplexAdderServer complex/common/* complex/server/*
cd ..
