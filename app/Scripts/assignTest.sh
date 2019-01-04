#!/bin/sh -e
TEST="$1"
cd ..
sed -i -e 's|VladTest|'"${TEST}"'|g' build.gradle
echo "Running ${TEST} test "

#changing VladTest in build.gradle to given word to this script
