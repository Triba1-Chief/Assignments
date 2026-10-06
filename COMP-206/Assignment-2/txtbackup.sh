#!/bin/bash

# Q1.1
# Name: Samuel Kinyua
# Student ID removed

pwd
ls *.txt

mkdir backup
cd backup
echo 'Moved to backup directory'
pwd

cp ../*.txt ../backup
echo 'Copied all text files to backup directory'

echo 'Current backup:' >> date.txt
date >> date.txt
cat date.txt

tar -zcvf txtarchive.tgz *.txt
echo "Created archive txtarchive.tgz"
ls -l
