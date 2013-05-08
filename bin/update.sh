#!/bin/sh

SRC=../target/scala-2.9.0.1/ScalatronBot.jar
DST=../../bots/sboles/ScalatronBot.jar

cp $SRC $DST && chmod 644 $DST && ls -l $DST

