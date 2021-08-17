name := "benchmark-akka-cluster"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.15"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.32"

libraryDependencies += "com.networknt" % "handler" % "2.0.29"

libraryDependencies += "io.jooby" % "jooby" % "2.10.0"

libraryDependencies += "io.jooby" % "jooby-utow" % "2.10.0"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion



libraryDependencies += "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion


dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.4"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-dataformat-cbor" % "2.11.4"
//libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.0-alpha4"
//libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.32" % Test


libraryDependencies ++= Seq(
  "io.aeron" % "aeron-driver" % "1.35.0",
  "io.aeron" % "aeron-client" % "1.35.0"
)



enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

