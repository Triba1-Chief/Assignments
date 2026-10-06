#!/bin/bash

echo "Test 1: not divisible & not increasing"
echo 10 5 7 | ./divisible
echo "Expected output message: Not divisible & Not increasing"

echo "Test 2: divisible & not increasing"
echo 5 20 10 | ./divisible
result=$?

if (($result == 2))
then
	echo "Exited with expected return code:   2"
else
	echo "Exited with unexpected return code:   $result (Expected 2)"
fi
