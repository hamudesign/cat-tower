package design.hamu.cat.tower

import sbt._

object CatTowerPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements

  object autoImport extends CatTowerKeys {
    implicit def toCatTowerGroupId(groupId: String): CatTowerGroupId = {
      require(groupId.nonEmpty, "Group ID may not be empty")
      new CatTowerGroupId(groupId)
    }
  }
}
