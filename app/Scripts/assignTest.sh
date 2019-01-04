#!/bin/sh -e
TEST="$1"
sed -i -e 's|VladTest|'"${TEST}"'|g' build.gradle
echo "Running ${TEST} test "
rm -R build.gradle-e

#changing VladTest in build.gradle to given word to this script
