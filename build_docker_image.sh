#!/bin/bash

./gradlew build
docker build --tag hleb_codebattle2020 .
