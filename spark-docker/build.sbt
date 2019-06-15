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

lazy val evaluator = (project in file("xgboost-evaluator"))
  .settings(
     name := "evaluator",
     commonSettings,
     assemblySettings,

     libraryDependencies ++= Seq(
       "org.apache.spark" %% "spark-sql"    % sparkVersion,
       "org.apache.spark" %% "spark-mllib"  % sparkVersion,
       "ml.dmlc" % "xgboost4j"        % xgboostVersion,
       "ml.dmlc" % "xgboost4j-spark"  % xgboostVersion,
       "org.jpmml" % "jpmml-sparkml" % "1.5.3",
       "org.jpmml" % "pmml-evaluator" % "1.4.9",
       "org.jpmml" % "pmml-evaluator-extension" % "1.4.9"
     )
)


/** Server */

val Http4sVersion = "0.20.1"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"

lazy val server = (project in file("http4s-server"))
  .settings(
    name := "http4s-server",
    commonSettings,
    assemblySettings,

    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
    //  "io.circe"        %% "circe-generic"       % "0.6.1",
      "io.circe" %% "circe-literal" % CirceVersion//% "0.6.1"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.1"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.2.4")

  ).dependsOn(
    common,
    trainer,
    evaluator
  )


scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)


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
