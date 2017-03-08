name := """Play-Practice"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.typesafe.play" %% "play-json" % "2.5.10",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.mindrot" % "jbcrypt" % "0.3m"

)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
