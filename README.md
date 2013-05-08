scalatron-bot
=============

http://scalatron.github.io/

# Getting Started

Download Scalatron and unzip it beside the scalatron bot.

Once the bot is build (SBT or Maven, see below), copy the jar into the Scalatron/bots directory.

See ~/bin/update.sh - a shell script that copies the packaged bot into the Scalatron bots directory.

# Developing with SBT

I pulled the SBT scalatron-template from https://github.com/dwins/scalatron-template

* Build (produces target/scala-2.9.0-1/ScalatronBot.jar)

    $ ./sbt package 

* Continuous build (handy to leave running while you edit)

    $ ./sbt ~compile
       
* Load code in interactive Scala interpreter

    $ ./sbt console

* Delete compiled output

    $ ./sbt clean

# Developing with Maven

The bot project includes a pom.xml

* Compile the bot

    $ mvn compile

* Package the bot

    $ mvn install
