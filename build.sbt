import buildinfo.BuildInfo._

lazy val interplay = (project in file("."))
  .enablePlugins(PlaySbtPlugin && PlayReleaseBase)
  .settings(playCrossReleasePlugins := false)

description := "Base build plugin for all Play modules"

addSbtPlugin("com.github.gseitz" % "sbt-release" % sbtReleaseVersion)
addSbtPlugin("com.jsuereth" % "sbt-pgp" % sbtPgpVersion)
addSbtPlugin("org.foundweekends" % "sbt-bintray" % sbtBintrayVersion)
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % sbtSonatypeVersion)
addSbtPlugin("com.lightbend" % "sbt-whitesource" % sbtWhitesourceVersion)

libraryDependencies ++= Seq(
  "org.scala-sbt" %% "scripted-plugin" % scriptedSbt.value,
  "com.typesafe" % "config" % "1.3.2"
)

playBuildExtraTests := {
  scripted.toTask("").value
}

playBuildRepoName in ThisBuild := "interplay"

sbtPlugin := true

sbtVersion := "1.0.2"

crossSbtVersions := Seq("1.0.2")

scalaVersion := "2.12.4"

addCommandAlias("validate", ";clean;test;scripted")