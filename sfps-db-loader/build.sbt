import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

// TODO: Check if necessary
/* The -Ypartial-unification compiler flag enables a bug fix that makes working
with functional code significantly easier. See the Cats Getting Started for more
info on this for more: https://github.com/typelevel/cats#getting-started. */
scalacOptions += "-Ypartial-unification" // 2.11.9+

lazy val doobieVersion = "0.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "sfps-db-loader",
    libraryDependencies += scalaTest % Test,
    // libraryDependencies += "org.tpolecat" %% "skunk-core" % "0.0.3",
    libraryDependencies += "org.tpolecat" %% "doobie-core"      % doobieVersion,
    libraryDependencies += "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
    libraryDependencies += "org.tpolecat" %% "doobie-specs2"    % doobieVersion,
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.5"
  )
