name := "XGBoost Sample"

version := "1.0"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.0"
libraryDependencies += "ml.dmlc" % "xgboost4j" % "0.80" 
libraryDependencies += "ml.dmlc" % "xgboost4j-spark" % "0.80"

libraryDependencies += "org.scalaz" %% "scalaz-zio" % "1.0-RC4"
libraryDependencies += "org.jpmml" % "jpmml-sparkml" % "1.5.3"



