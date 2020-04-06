scalaVersion := "2.11.8"
//scalaVersion := "2.10.0"
name := "hello-world"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"

//libraryDependencies += "org.apache.kafka" % "kafka-streams" % "2.2.0"
//libraryDependencies += "log4j" % "log4j" % "1.2.17"
//libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.12.1"
//libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.12.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.0"


// https://mvnrepository.com/artifact/org.apache.kafka/kafka
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.1"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.4.4"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.4.4"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.4.4"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.10.3"
// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.3"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "javax.mail" % "mail" % "1.4.1"
