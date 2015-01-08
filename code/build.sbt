Nice.scalaProject

organization  := "ohnosequences"
name          := "scala-guide"
description   := ""

scalaVersion  := "2.11.4"

libraryDependencies += "org.scalaz"     %% "scalaz-core"  % "7.1.0"
libraryDependencies += "ohnosequences"  %% "cosas"        % "0.6.0-SNAPSHOT"  % "test"
libraryDependencies += "org.scalatest"  %% "scalatest"    % "2.1.3"           % "test"

bucketSuffix := "era7.com"