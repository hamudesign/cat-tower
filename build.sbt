import design.hamu.Dependencies

ThisBuild / versionScheme := Some("semver-spec")

lazy val commonSettings = Seq(
  scalaVersion := Dependencies.Scala.v12,
  organization := "design.hamu",
  version := "0.0.1",
  scalacOptions := Seq("-Xlint", "-Ywarn-unused", "-deprecation", "-Ymacro-annotations")
)

lazy val publishSettings = Seq(
  coverageMinimum := 75,
  coverageFailOnMinimum := true,
  publishMavenStyle := true,
  crossSbtVersions := Seq("1.5.1"),
  crossScalaVersions := Seq(Dependencies.Scala.v12),
  scalacOptions := {
    scalaBinaryVersion.value match {
      case v if v.startsWith("2.12") => Seq("-Ypartial-unification", "-deprecation")
      case v if v.startsWith("2.13") => Seq("-Xlint", "-Ywarn-unused", "-deprecation")
      case _ => Seq()
    }
  },
  homepage := Some(url("https://hamuhouse.github.io/cat-tower/")),
  licenses := List("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  developers := List(
    Developer(id = "matsudaskai", name = "Kai Matsuda", email = "", url = url("https://vangogh500.github.io/"))
  ),
  scmInfo := Some(
    ScmInfo(url("https://github.com/hamuhouse/cat-tower"), "scm:git@github.com:hamuhouse/cat-tower.git")
  ),
  publishTo := sonatypePublishTo.value
)

lazy val root = project
  .in(file("."))
  .aggregate(
    plugin
  )

lazy val plugin = project
  .in(file("plugin"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "cat-tower",
    sbtPlugin := true,
    sbtTestDirectory := sourceDirectory.value / "sbt-test",
    commonSettings,
    publishSettings,
    libraryDependencies ++= Seq(
      Dependencies.Scalatest.core
    ).map(_ % "test")
  )

lazy val docs = project
  .in(file("docs"))
  .enablePlugins(MicrositesPlugin)
  .settings(
    name := "Cat Tower",
    micrositeName := "Cat Tower",
    micrositeBaseUrl := "cat-tower",
    micrositeHomepage := "https://hamudesign.github.io/sphcat-towerynx/",
    micrositeHighlightTheme := "atom-one-light",
    git.remoteRepo := "https://github.com/hamudesign/cat-tower.git",
    mdocIn := baseDirectory.value / "src" / "main" / "mdoc",
  )
