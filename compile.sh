#!/bin/bash
set -e

echo "Compilando o projeto..."
mvn clean package

echo "Executando o projeto..."
java -javaagent:target/notation-exercise-1.0-SNAPSHOT-agent.jar -cp target/notation-exercise-1.0-SNAPSHOT.jar notation.Main
