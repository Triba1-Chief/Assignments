#!/bin/bash

# Q2.1
# Name: Samuel Kinyua
# Student ID removed

# Q2.2
echo 'Please enter your sentence:'
read sentence

# Q2.3
word_counter=$(echo $sentence | wc -w)
character_counter=$(echo $sentence | wc -c)

echo "Your input has $word_counter words and $character_counter characters"
