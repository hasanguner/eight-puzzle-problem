#!/usr/bin/env bash

./gradlew build

for problem in $(ls problems | grep puzzle); do
    java -jar build/libs/*.jar "problems/$problem" > "problems/solution${problem#puzzle}"
done;