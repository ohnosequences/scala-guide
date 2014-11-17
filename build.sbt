Nice.scalaProject

organization := "ohnosequences"

name := "scala-guide"

description := ""

bucketSuffix := "era7.com"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.0",
  "org.scalatest" %% "scalatest" % "2.1.3" % "test",
  "ohnosequences" %% "cosas" % "0.6.0-SNAPSHOT" % "test"
)

Literator.docsAddIndex := false
