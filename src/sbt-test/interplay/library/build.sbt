lazy val `mock-library` = (project in file("."))
  .enablePlugins(PlayLibrary && PlayReleaseBase)
  .settings(common: _*)

playBuildRepoName in ThisBuild := "mock"

// Below this line is for facilitating tests
InputKey[Unit]("contains") := {
  val args = Def.spaceDelimited().parsed
  val contents = IO.read(file(args.head))
  val expected = args.tail.mkString(" ")
  if (!contents.contains(expected)) {
    throw sys.error(s"File ${args.head} does not contain '$expected':\n$contents")
  }
}

def common: Seq[Setting[_]] = Seq(
  PgpKeys.publishSigned := {
    IO.write(crossTarget.value / "publish-version", s"${publishTo.value.get.name}:${version.value}")
  },
  publish := { throw sys.error("Publish should not have been invoked") },
  credentials := Seq(Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", "sbt", "notcorrectpassword"))
)

commands in ThisBuild := {
  Command.command("sonatypeRelease") { state =>
    val extracted = Project.extract(state)
    IO.write(extracted.get(target) / "sonatype-release-version", extracted.get(version))
    state
  } +: (commands in ThisBuild).value
}


