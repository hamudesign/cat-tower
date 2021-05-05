package design.hamu

import sbt._

object Plugins {
  object ScalaFmt {
    private val version = "2.4.2"
    val core: ModuleID = "org.scalameta" % "sbt-scalafmt" % version
  }
  object Microsite {
    private val version = "1.3.4"
    val core: ModuleID = "com.47deg"  % "sbt-microsites" % version
  }
  object Sonatype {
    private val version = "3.9.7"
    val core: ModuleID = "org.xerial.sbt" % "sbt-sonatype" % version
  }
  object PGP {
    private val version = "2.1.2"
    val core: ModuleID = "com.jsuereth" % "sbt-pgp" % "2.0.0"
  }
}
