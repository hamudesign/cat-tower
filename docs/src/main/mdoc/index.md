---
layout: home
title:  "Cat Tower"
section: "home"
---
# Cat Tower
![img](https://img.shields.io/badge/maven-0.0.1-brightgreen)

Cat tower is a SBT plugin which aims to make dependency management easier. 
Compatible dependency versions are organized into sets called cat towers.
These dependencies can then be upgraded together without compatibility issues by simply upgrading the cat tower version.
The tool is inspired by [The Haskell Tool Stack](https://docs.haskellstack.org/en/stable/README/).

# Getting Started

Cat tower is available from Sonatype (and Maven Central) and currently only supports JVM.
Add the following to your project/plugins.sbt:
```scala
addSbtPlugin("design.hamu" % "cat-tower" % version)
```

In your `build.sbt` enable the plugin and specify your cat-tower version:
```scala
enablePlugins(CatTowerPlugin)
catTowerVersion := "2.6"
```

In your `build.sbt` use `%!` or `%%!` to then add dependencies to your project without specifying a version.
The version for each dependency is automatically set to a version that is compatible with the rest of the cat tower.
```scala
libraryDependencies ++= Seq(
  "org.http4s" %%! "http4s-core",
  "org.typelevel" %%! "cats-core",
  "org.slf4j" %! "slf4j-api",
)
```
In the above example `http4s-core`, `cats-core`, and `slf4j-api` dependency versions used are guaranteed to be compatible.
To upgrade the set of dependencies just upgrade the `catTowerVersion`!