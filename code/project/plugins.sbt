resolvers ++= Seq(
  "Era7 maven releases" at "http://releases.era7.com.s3.amazonaws.com",
  "Era7 maven snapshot" at "http://snapshots.era7.com.s3.amazonaws.com"
)

addSbtPlugin("ohnosequences" % "nice-sbt-settings" % "0.5.0-RC1")