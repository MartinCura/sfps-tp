name := "xgboost-evaluator"

version := "1.0"

organization := "sfps.tp"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0" 
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.0" 
libraryDependencies += "ml.dmlc" % "xgboost4j" % "0.80" 
libraryDependencies += "ml.dmlc" % "xgboost4j-spark" % "0.80"

libraryDependencies += "org.jpmml" % "jpmml-sparkml" % "1.5.3"

libraryDependencies += "org.jpmml" % "pmml-evaluator" % "1.4.9"
libraryDependencies += "org.jpmml" % "pmml-evaluator-extension" % "1.4.9"
