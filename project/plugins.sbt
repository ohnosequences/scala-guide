resolvers ++= Seq(
  "Era7 maven releases" at "http://releases.era7.com.s3.amazonaws.com",
  "Era7 maven snapshots" at "http://snapshots.era7.com.s3.amazonaws.com"
)

addSbtPlugin("ohnosequences" % "nice-sbt-settings" % "0.5.1")

addSbtPlugin("laughedelic" % "literator-plugin" % "0.7.0-SNAPSHOT")
