#!/bin/bash

# Name: Samuel Kinyua
# Student ID removed

source_directory=$1
target_directory=$2

if [[ -z $target_directory ]]
then
	echo 'Error: Expected two input parameters.'
	echo 'Usage: ./sortedcopy.sh <sourcedirectory> <targetdirectory>'
	exit 1
fi

if ! [[ -d $source_directory ]]
then
	echo 'Error: Input parameter #1 $source_directory is not a directory.'
	echo 'Usage: ./sortedcopy.sh <sourcedirectory> <targetdirectory>'
	exit 2
fi

if [[ -d $target_directory ]]
then
	echo "Directory $target_directory already exists. Overwrite? (y/n)"
	read answer

	if [[ $answer = 'y' ]]
	then
		rm -r $target_directory
	else
		exit 3
	fi
fi

mkdir -p $target_directory

for file in $source_directory/*
do
	if [[ -f $file ]]
	then
		cp $file $target_directory
	else
		continue
	fi
done

cd $target_directory
sorted_list=$(ls | sort -r)

i=1
for file in $sorted_list
do
	cp -r $file $i'.'$file
	rm -r $file
	i=$(( $i+1 ))
done

exit 0
