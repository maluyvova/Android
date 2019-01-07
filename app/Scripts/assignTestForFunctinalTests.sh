#!/bin/sh -e
TEST="$1"
cd ..
sed -i -e 's|testClassRegex="VladTest"|'"${TEST}"'|g' build.gradle

echo "Running all tests "

rm -R build.gradle-e
cd src/androidTest/java/com/tubitv/tubitv/Tests

sed -i -e 's|@Test|'"${TEST}"'|g' BlackScreenTest.kt
rm -R BlackScreenTest.kt-e
echo "Removing BlackScreen test"

#changing VladTest in build.gradle to given word to this script
