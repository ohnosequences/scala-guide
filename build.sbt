Nice.scalaProject

organization  := "ohnosequences"
name          := "scala-guide"
description   := ""

scalaVersion  := "2.11.5"

libraryDependencies ++= Seq(
  "org.scalaz"    %% "scalaz-core" % "7.1.0",
  "ohnosequences" %% "cosas"       % "0.6.0-SNAPSHOT",
  "org.scalatest" %% "scalatest"  % "2.1.3"             % "test"
)

// shows time for each test:
testOptions in Test += Tests.Argument("-oD")

bucketSuffix := "era7.com"

Literator.docsAddIndex := false
