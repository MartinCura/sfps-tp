name := "xgboost-trainer"

version := "1.0"

organization := "sfps.tp"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.0" % "provided"
libraryDependencies += "ml.dmlc" % "xgboost4j" % "0.80" 
libraryDependencies += "ml.dmlc" % "xgboost4j-spark" % "0.80"

libraryDependencies += "org.jpmml" % "jpmml-sparkml" % "1.5.3"

libraryDependencies += "org.jpmml" % "pmml-evaluator" % "1.4.9"
libraryDependencies += "org.jpmml" % "pmml-evaluator-extension" % "1.4.9"

// Doobie
lazy val doobieVersion = "0.7.0"
libraryDependencies += "org.tpolecat" %% "doobie-core"      % doobieVersion
libraryDependencies += "org.tpolecat" %% "doobie-postgres"  % doobieVersion
libraryDependencies += "org.tpolecat" %% "doobie-specs2"    % doobieVersion

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.5"

assemblyShadeRules in assembly := Seq(
    ShadeRule.rename("org.jpmml.model.**" -> "org.shaded.jpmml.model.@1").inAll,
    ShadeRule.rename("org.dmg.pmml.**" -> "org.shaded.dmg.pmml.@1").inAll,
    ShadeRule.rename("org.jpmml.schema.**" -> "org.shaded.jpmml.schema.@1").inAll
)

assemblyMergeStrategy in assembly := {
    case x => MergeStrategy.first
}

      

