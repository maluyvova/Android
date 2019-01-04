#!/bin/sh -e
TEST="$1"
sed -i -e 's|testClassRegex="VladTest"|'"${TEST}"'|g' build.gradle
echo "Running all tests "
rm -R build.gradle-e

#changing VladTest in build.gradle to given word to this script
