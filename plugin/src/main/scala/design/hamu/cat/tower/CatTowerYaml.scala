package design.hamu.cat.tower

import sbt.File

import java.nio.file.{FileSystems, Files, Path, Paths}
import java.util.Collections
import scala.collection.convert.DecorateAsScala
import scala.io.Source

case class CatTowerYaml(map: Map[String, String]) extends CatTower {
  def get(groupId: String, artifactId: String): String =
    map(s"${groupId}:${artifactId}")
}

object CatTowerYaml extends DecorateAsScala {

  val towerDirectory = "/META-INF/resources/cat/tower"

  def loadAll: Map[String, CatTower] = {
    val uri = getClass.getResource(towerDirectory).toURI()
    val path = if (uri.getScheme.equals("jar")) {
      val fs = FileSystems.newFileSystem(uri, Collections.emptyMap[String, AnyRef]())
      fs.getPath(towerDirectory)
    } else {
      Paths.get(uri)
    }
    val stream = Files.walk(path, 1)
    stream
      .iterator()
      .asScala
      .drop(1)
      .foldLeft(Map.empty[String, CatTower]) { case (acc, path) =>
        acc + (s"${path.getFileName}" -> load(s"${towerDirectory}/${path.getFileName}"))
      }
  }

  def load(resource: String): CatTower = {
    parse(Source.fromInputStream(getClass.getResourceAsStream(resource)).getLines())
  }

  def parse(lines: Iterator[String]): CatTower = {
    case class State(map: Map[String, String], org: Option[String])
    val map = lines
      .filter(!_.isEmpty)
      .foldLeft(State(Map.empty, None)) {
        case (state, line) if line.head.isWhitespace =>
          val (artifact, version) = splitArtifactVersion(line)
          state.copy(map = state.map + (s"${state.org.get}:${artifact}" -> version))
        case (state, line) =>
          val org = getOrg(line)
          state.copy(org = Some(org))
      }
      .map
    CatTowerYaml(map)
  }

  def getOrg(line: String): String =
    line.trim.takeWhile(_ != ':')

  def splitArtifactVersion(line: String): (String, String) =
    line.split(":") match {
      case Array(artifact, version) =>
        (artifact.trim, version.trim)
    }
}
