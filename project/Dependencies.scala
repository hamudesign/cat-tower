package design.hamu

import sbt._

object Dependencies {

  object Scala {
    val v12 = "2.12.10"
    val v13 = "2.13.1"
  }

  object Scalatest {
    private val version = "3.2.8"
    val core: ModuleID = "org.scalatest" %% "scalatest" % version
  }
}
