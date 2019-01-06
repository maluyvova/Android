#!/bin/sh -e
TEST="$1"
cd ..
sed -i -e 's|VladTest|'"${TEST}"'|g' build.gradle
sed -i -e 's|650000|650000000|g' build.gradle
echo "Running ${TEST} test for 650000000 time "
rm -R build.gradle-e

#changing VladTest in build.gradle to given word to this script
