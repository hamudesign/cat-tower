val testDependency = settingKey[ModuleID]("Test ModuleID that we test")
val check = taskKey[Unit]("Check that the testDependency was correctly constructed")

val testOrg = "org.http4s"
val testArtifact = "http4s-server"
val testVersion = "0.21.22"

def assertEquals(expected: Any, actual: Any): Unit =
  assert(actual == expected, s"Expected: $expected ; actual: $actual")

lazy val test = project
  .in(file("test"))
  .enablePlugins(CatTowerPlugin)
  .settings(
    version := "0.1",
    scalaVersion := "2.12.3",
    catTowerVersion := "2.6",
    testDependency := testOrg %%! testArtifact,
    check := {
      val moduleID = testDependency.value
      assertEquals(testOrg, moduleID.organization)
      assertEquals(testArtifact, moduleID.name)
      assertEquals(testVersion, moduleID.revision)
    }
  )