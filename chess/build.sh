#!/bin/bash

mvn clean install -DskipTests
java -jar target/chess.jar