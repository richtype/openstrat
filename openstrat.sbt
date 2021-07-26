/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.2.2"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.0"
val scalaMinor = "1"
//lazy val jarVersion = "_" + scalaMajor + "-" + versionStr + ".jar"
//ThisBuild/scalaVersion := scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

//scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
// "-feature", "-language:implicitConversions", "UTF-8", "-deprecation", "-explaintypes"),// "-Xsource:3"),//, "-Ywarn-value-discard", "-Xlint"),

lazy val root = (project in file(".")).aggregate(UtilJvm, GraphicsJvm, TilingJvm, EarthJvm, DevJvm)
lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value

def baseProj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(
  moduleDir := baseDir.value / srcsStr,  
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  resourceDirectory := moduleDir.value / "res",
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/resourceDirectory :=  moduleDir.value / "testRes",
)

def sett2 = List(
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-encoding", "UTF-8"), // "-Xsource:3"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value withSources(),
)

def sett3 = List(
  scalaVersion := "3.0.2-RC1",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def jvm3Proj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm").settings(sett3).settings(
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "src3", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
)

def js2Proj(name: String) = baseProj(name, name + "Js2").enablePlugins(ScalaJSPlugin).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src2", "srcExs").map(moduleDir.value / _),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0" withSources(),
)

def js3Proj(name: String) = baseProj(name, name + "Js3").enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src3", "srcExs").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13) withSources(),
)

def nat2Proj(name: String) = baseProj(name, name + "Nat2").enablePlugins(ScalaNativePlugin).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "src2", "srcNat", "srcExs").map(moduleDir.value / _),
)

lazy val MacrosJvm = jvm3Proj("Macros")
lazy val MacrosJs2 = js2Proj("Macros")
lazy val MacrosJs3 = js3Proj("Macros")
lazy val MacrosNat2 = nat2Proj("Macros")
lazy val UtilJvm = jvm3Proj("Util").dependsOn(MacrosJvm)
lazy val UtilJs = js2Proj("Util").dependsOn(MacrosJs2)
lazy val UtilNat2 = nat2Proj("Util").dependsOn(MacrosNat2)

lazy val GraphicsJvm = jvm3Proj("Graphics").dependsOn(UtilJvm).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources(),
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GraphicsJs = js2Proj("Graphics").dependsOn(UtilJs)
lazy val GraphicsNat2 = nat2Proj("Graphics").dependsOn(UtilNat2)
lazy val TilingJvm = jvm3Proj("Tiling").dependsOn(GraphicsJvm)
lazy val TilingJs = js2Proj("Tiling").dependsOn(GraphicsJs)
lazy val TilingNat2 = js2Proj("Tiling").dependsOn(GraphicsNat2)
lazy val EarthJvm = jvm3Proj("Earth").dependsOn(TilingJvm)
lazy val EarthJs = js2Proj("Earth").dependsOn(TilingJs)
lazy val EarthNat2 = js2Proj("Earth").dependsOn(TilingNat2)

lazy val DevJvm = jvm3Proj("Dev").dependsOn(EarthJvm).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

def js2App(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).dependsOn(EarthJs).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List((ThisBuild/baseDirectory).value / "Dev/src"),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0" withSources(),
)

lazy val DevJs2 = js2App("Dev").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/DevApp")
lazy val ZugJs = js2App("Zug").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/ZugApp")
lazy val WW2Js = js2App("WW2").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJSApps/WW2Js")

lazy val DevNat2 = nat2Proj("Dev").dependsOn(EarthNat2).settings(
  resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value),
)

val docDirs: List[String] = List("Util", "Graphics", "Tiling", "Earth", "Dev")

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid buiding ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := ("Macros" :: docDirs).flatMap(el => List(el + "/src", el + "/src3", el + "/srcJvm", el + "/srcExs", el + "srcFx")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15",
  Compile/doc/scalacOptions ++= Seq("-groups"),
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).dependsOn(MacrosJs2).settings(sett2).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).map(s => baseDir.value / s),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0" withSources(),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)