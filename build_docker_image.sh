#!/bin/bash

./gradlew build
docker build --tag kakawi/hleb_codebattle2020 .
