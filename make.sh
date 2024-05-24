#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -ne 4 ]; then
    echo "Usage: $0 <source_file> <arg1> <arg2> <arg3>"
    exit 1
fi

# Compile the Java code
javac Salsa20.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Run the compiled Java program with the provided arguments
    java Salsa20 "$1" "$2" "$3" "$4"
else
    echo "Compilation failed."
fi
