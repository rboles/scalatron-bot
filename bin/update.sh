#!/bin/sh

SRC=../target/ScalatronBot-1.0.jar
DST=../../bots/sboles/ScalatronBot.jar

cp $SRC $DST && chmod 644 $DST && ls -l $DST

