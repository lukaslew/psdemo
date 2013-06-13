import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "psdemo"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.33.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
