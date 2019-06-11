//import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.fiuba.sfps"
ThisBuild / organizationName := "fiuba"

// TODO: Check if necessary
/* The -Ypartial-unification compiler flag enables a bug fix that makes working
with functional code significantly easier. See the Cats Getting Started for more
info on this for more: https://github.com/typelevel/cats#getting-started. */
scalacOptions += "-Ypartial-unification" // 2.11.9+

lazy val doobieVersion = "0.7.0"
lazy val framelessVersion = "0.8.0" // for Spark 2.4.0

lazy val root = (project in file("."))
  .settings(
    name := "sfps-training",
    // libraryDependencies += scalaTest % Test,

    // CSVReader
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.5",

    // Doobie
    libraryDependencies += "org.tpolecat" %% "doobie-core"      % doobieVersion,
    libraryDependencies += "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
    libraryDependencies += "org.tpolecat" %% "doobie-specs2"    % doobieVersion,
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0",

    // Spark
    libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.0",
    libraryDependencies += "ml.dmlc" % "xgboost4j" % "0.80",
    libraryDependencies += "ml.dmlc" % "xgboost4j-spark" % "0.80",
    libraryDependencies += "org.jpmml" % "jpmml-sparkml" % "1.5.3",
    libraryDependencies += "org.jpmml" % "pmml-evaluator" % "1.4.9",
    libraryDependencies += "org.jpmml" % "pmml-evaluator-extension" % "1.4.9",

    libraryDependencies += "org.typelevel" %% "frameless-dataset" % framelessVersion,
    libraryDependencies += "org.typelevel" %% "frameless-ml"      % framelessVersion,
    libraryDependencies += "org.typelevel" %% "frameless-cats"    % framelessVersion
  
  )

