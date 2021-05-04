package design.hamu.cat.tower

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import sbt.File

class CatTowerYamlSpec extends AnyWordSpec with Matchers {
  "CatTower" should {
    "get version for groupId and artifactId" in {
      CatTowerYaml(Map("org.test:test" -> "0.4.4"))
        .get("org.test", "test") must equal("0.4.4")
    }
    "getOrg from line with no white spaces" in {
      CatTowerYaml.getOrg("org.test:") must equal("org.test")
    }
    "getOrg from line with white spaces" in {
      CatTowerYaml.getOrg("   org.test:   ") must equal("org.test")
    }
    "splitArtifactVersion from line with no white space" in {
      CatTowerYaml.splitArtifactVersion("test:0.1.2") must equal(("test", "0.1.2"))
    }
    "splitArtifactVersion from line with white space" in {
      CatTowerYaml.splitArtifactVersion("  test:  0.1.2  ") must equal(("test", "0.1.2"))
    }
    "parse iterator of lines" in {
      val catTower = CatTowerYaml.parse(
        Iterator(
          "org.test:",
          "   foo: 0.1.1",
          " bar: 0.2.1",
          "org.test2:",
          "  foo: 2.2.2"
        )
      )
      catTower.get("org.test", "foo") must equal("0.1.1")
      catTower.get("org.test", "bar") must equal("0.2.1")
      catTower.get("org.test2", "foo") must equal("2.2.2")
    }
    "load and parse file" in {
      val catTower = CatTowerYaml.load(s"${CatTowerYaml.towerDirectory}/test.yaml")
      catTower.get("foo", "bar") must equal("0.1.2")
      catTower.get("bar", "foo") must equal("0.2.2")
    }
    "load files from resources" in {
      val catTowers = CatTowerYaml.loadAll
      println(catTowers)
      catTowers("test.yaml").get("foo", "bar") must equal("0.1.2")
      catTowers("test.yaml").get("bar", "foo") must equal("0.2.2")
    }
  }
}
