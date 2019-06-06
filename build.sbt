import Dependencies._

// A good example to follow: https://github.com/pbassiner/sbt-multi-project-example/blob/master/build.sbt

lazy val commonSettings = Seq(
  organization := "com.fiuba.sfps",
  version      := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.8"
)

/* The -Ypartial-unification compiler flag enables a bug fix that makes working
with functional code significantly easier. See the Cats Getting Started for more
info on this for more: https://github.com/typelevel/cats#getting-started. */
// TODO: Check if necessary
scalacOptions += "-Ypartial-unification" // 2.11.9+

lazy val doobieVersion = "0.7.0"

lazy val etl = (project in file("sfps-etl"))
  .settings(
    name := "sfps-etl",
    commonSettings,
    libraryDependencies += scalaTest % Test,

    libraryDependencies += "org.tpolecat" %% "doobie-core"      % doobieVersion,
    libraryDependencies += "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
    libraryDependencies += "org.tpolecat" %% "doobie-specs2"    % doobieVersion,
  )

lazy val training = (project in file("xgboost-test"))
  .settings(
    name := "sfps-model-training",
    commonSettings,
    libraryDependencies += scalaTest % Test,

    libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0",
    libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.0",
    libraryDependencies += "ml.dmlc" % "xgboost4j" % "0.80",
    libraryDependencies += "ml.dmlc" % "xgboost4j-spark" % "0.80",

    libraryDependencies += "org.jpmml" % "jpmml-sparkml" % "1.5.3",

    libraryDependencies += "org.jpmml" % "pmml-evaluator" % "1.4.9",
    libraryDependencies += "org.jpmml" % "pmml-evaluator-extension" % "1.4.9"
  )
