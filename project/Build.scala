import sbt._
import Keys._
import sbtrelease.ReleasePlugin._
import sbtprotobuf.ProtobufPlugin.protobufSettings

object Build extends Build {
  lazy val basicSettings = Seq(
    name                 := "akka-persistence-eventstore",
    organization         := "com.geteventstore",
    scalaVersion         := "2.11.0",
    crossScalaVersions   := Seq("2.10.4", "2.11.0"),
    licenses             := Seq("BSD 3-Clause" -> url("http://raw.github.com/EventStore/EventStore.Akka.Persistence/master/LICENSE")),
    homepage             := Some(new URL("http://github.com/EventStore/EventStore.Akka.Persistence")),
    organizationHomepage := Some(new URL("http://geteventstore.com")),
    description          := "Event Store JVM Client",
    startYear            := Some(2013),
    scalacOptions        := Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature"),
    resolvers            += "krasserm at bintray" at "http://dl.bintray.com/krasserm/maven",
    libraryDependencies ++= Seq(Akka.persistence, Akka.testkit, eventstoreClient, specs2, persistenceTestkit))

  object Akka {
    val actor       = apply("actor")
    val persistence = apply("persistence-experimental")
    val testkit     = apply("testkit") % "test"

    private def apply(x: String) = "com.typesafe.akka" %% "akka-%s".format(x) % "2.3.2"
  }

  val eventstoreClient = "com.geteventstore" %% "eventstore-client" % "0.4.0"
  val specs2 = "org.specs2" %% "specs2" % "2.3.11" % "test"
  val persistenceTestkit = "com.github.krasserm" %% "akka-persistence-testkit" % "0.3.1" % "test"

  def integrationFilter(name: String): Boolean = name endsWith "IntegrationSpec"
  def specFilter(name: String): Boolean = (name endsWith "Spec") && !integrationFilter(name)

  lazy val IntegrationTest = config("integration") extend Test

  lazy val root = Project(
    "root",
    file("."),
    settings = basicSettings ++ Defaults.defaultSettings ++ releaseSettings ++ protobufSettings ++ Scalariform.settings ++ Publish.settings)
    .configs(IntegrationTest)
    .settings(inConfig(IntegrationTest)(Defaults.testTasks): _*)
    .settings(
    testOptions       in Test            := Seq(Tests.Filter(specFilter)),
    testOptions       in IntegrationTest := Seq(Tests.Filter(integrationFilter)),
    parallelExecution in IntegrationTest := false)
}