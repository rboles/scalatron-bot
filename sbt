#!/bin/sh

java $SBTOPTS -jar "$(dirname "$0")/sbt-launch.jar" "$@"
