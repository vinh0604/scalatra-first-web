addSbtPlugin("com.mojolly.scalate" % "xsbt-scalate-generator" % "0.5.0")

addSbtPlugin("org.scalatra.sbt" % "scalatra-sbt" % "0.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.0")

resolvers += "Flyway" at "https://flywaydb.org/repo"