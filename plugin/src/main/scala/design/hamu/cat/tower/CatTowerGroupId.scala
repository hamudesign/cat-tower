package design.hamu.cat.tower

import sbt._
import scala.language.experimental.macros

import scala.reflect.macros.{blackbox}

final class CatTowerGroupId(val groupId: String) extends AnyVal {
  def %!(artifactId: String): ModuleID =
    macro CatTowerGroupId.impl_%
  def %%!(artifactId: String): ModuleID =
    macro CatTowerGroupId.impl_%%
}

object CatTowerGroupId {
  def resolve_%(groupId: String, artifactId: String, catTowerVersion: String): ModuleID =
    groupId % artifactId % CatTower.get(catTowerVersion, groupId, artifactId)

  def resolve_%%(groupId: String, artifactId: String, catTowerVersion: String): ModuleID = {
    groupId %% artifactId % CatTower.get(catTowerVersion, groupId, artifactId)
  }

  def impl_%(
    c: blackbox.Context { type PrefixType = CatTowerGroupId }
  )(artifactId: c.Expr[String]): c.Expr[ModuleID] = {
    import c.universe._
    reify(resolve_%(c.prefix.splice.groupId, artifactId.splice, CatTowerKeys.catTowerVersion.value))
  }

  def impl_%%(
    c: blackbox.Context { type PrefixType = CatTowerGroupId }
  )(artifactId: c.Expr[String]): c.Expr[ModuleID] = {
    import c.universe._
    reify(resolve_%%(c.prefix.splice.groupId, artifactId.splice, CatTowerKeys.catTowerVersion.value))
  }
}
