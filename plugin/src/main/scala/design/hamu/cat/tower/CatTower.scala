package design.hamu.cat.tower

trait CatTower {
  def get(groupId: String, artifactId: String): String
}

object CatTower {

  def get(catTowerVersion: String, groupId: String, artifactId: String): String = {
    towers(s"${catTowerVersion}.yaml").get(groupId, artifactId)
  }

  val towers: Map[String, CatTower] = CatTowerYaml.loadAll
}
