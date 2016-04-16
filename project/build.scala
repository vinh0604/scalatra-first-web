import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt._
import Keys._
import org.scalatra.sbt._
import com.earldouglas.xwp.JettyPlugin
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._
import org.flywaydb.sbt.FlywayPlugin.autoImport._

object ScalatraFirstWebBuild extends Build {
  val Organization = "vinhbachsy"
  val Name = "Scalatra First Web"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.7"
  val ScalatraVersion = "2.4.0"
  var JettyVersion = "9.2.14.v20151106"

  lazy val project = Project (
    "scalatra-first-web",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/",
      flywayUrl := "jdbc:postgresql://127.0.0.1:5432/websosanh",
      flywayUser := "vinhbachsy",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.1.3" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % JettyVersion % "container;compile",
        "org.eclipse.jetty" % "jetty-plus" % JettyVersion % "container;compile",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
        "org.yaml" % "snakeyaml" % "1.16",
        "org.jsoup" % "jsoup" % "1.8.3",
        "com.rockymadden.stringmetric" %% "stringmetric-core" % "0.27.4",
        "org.joda" % "joda-money" % "0.10.0",
        "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
        "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
        "org.mock-server" % "mockserver-netty" % "3.10.2" % "test",
        "com.typesafe" % "config" % "1.3.0",
        "com.netaporter" %% "scala-uri" % "0.4.12",
        "org.scalatra" %% "scalatra-json" % "2.4.0",
        "org.json4s"   %% "json4s-jackson" % "3.3.0",
        "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
        "com.github.dnvriend" %% "akka-persistence-jdbc" % "2.2.17"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      },
      javaOptions ++= Seq(
        "-Xdebug",
        "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
      )
    )
  ).enablePlugins(JavaAppPackaging).enablePlugins(JettyPlugin)
}
