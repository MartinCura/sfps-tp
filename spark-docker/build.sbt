name := "sfps-tp"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.8",
  organization := "com.fiuba.sfps",
  version      := "1.0",
  test in assembly := {}
)


/** Common */

lazy val doobieVersion = "0.7.0"

lazy val common = (project in file("common"))
  .settings(
    name := "common",
    commonSettings,

    libraryDependencies ++= Seq(
      "org.tpolecat" %% "doobie-core"      % doobieVersion,
      "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
      "org.tpolecat" %% "doobie-specs2"    % doobieVersion,
      "com.github.tototoshi" %% "scala-csv" % "1.3.5"
    )
  )


/** DB Loader */

lazy val dbloader = (project in file("db-loader"))
  .settings(
    name := "db-loader",
    commonSettings,
    mainClass in assembly := Some("sfps.db.DbLoader"),

    libraryDependencies ++= Seq(
      "org.tpolecat" %% "doobie-core"      % doobieVersion,
      "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
      "org.tpolecat" %% "doobie-specs2"    % doobieVersion,
      "com.github.tototoshi" %% "scala-csv" % "1.3.5"
    )
  )
  .dependsOn(
    common
  )


/** Trainer */

lazy val sparkVersion = "2.4.0"
lazy val xgboostVersion = "0.80"

lazy val trainer = (project in file("xgboost-trainer"))
  .settings(
    name := "trainer",
    commonSettings,
    assemblySettings,

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql"   % sparkVersion % "provided",
      "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
      "ml.dmlc" % "xgboost4j"       % xgboostVersion,
      "ml.dmlc" % "xgboost4j-spark" % xgboostVersion,

      "org.jpmml" % "jpmml-sparkml" % "1.5.3",

      "org.jpmml" % "pmml-evaluator"            % "1.4.9",
      "org.jpmml" % "pmml-evaluator-extension"  % "1.4.9"
    )
  )
  .dependsOn(
    common,
    dbloader
  )


/** Evaluator */

// lazy val evaluator = (project in file("xgboost-evaluator"))
//   .settings(
//     name := "evaluator",
//     commonSettings,

//     libraryDependencies ++= Seq( ... )
//   )


lazy val assemblySettings = Seq(
  assemblyShadeRules in assembly := Seq(
      ShadeRule.rename("org.jpmml.model.**" -> "org.shaded.jpmml.model.@1").inAll,
      ShadeRule.rename("org.dmg.pmml.**" -> "org.shaded.dmg.pmml.@1").inAll,
      ShadeRule.rename("org.jpmml.schema.**" -> "org.shaded.jpmml.schema.@1").inAll
  ),
  assemblyMergeStrategy in assembly := {
      case x => MergeStrategy.first
  }
)
