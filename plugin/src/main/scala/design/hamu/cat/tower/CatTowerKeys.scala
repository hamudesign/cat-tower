package design.hamu.cat.tower

import sbt._

trait CatTowerKeys {
  lazy val catTowerVersion = settingKey[String]("Cat tower version")
}

object CatTowerKeys extends CatTowerKeys
