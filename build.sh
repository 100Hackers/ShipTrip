#!/bin/sh
rm -f *.class
javac *.java

if [ $? -eq 0 ]
then
    printf "Success\n"
else
    rm -f *.class
    printf "Error\n"
fi

