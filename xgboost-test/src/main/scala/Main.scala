import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import scala.io.Source
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import ml.dmlc.xgboost4j.scala.spark.XGBoostClassifier
import java.io.IOException
import java.io.File
import org.apache.spark.sql.types.StructType
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.RFormula
import org.jpmml.sparkml._
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature._
import org.apache.spark.ml.tuning._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.dmg.pmml.PMML
import org.apache.spark.ml.classification.DecisionTreeClassifier

object Main {

    def main(args: Array[String]): Unit = { 

      val spark = SparkSession .builder().appName("Spark SQL basic example")
      .config("spark.master", "local")
      .getOrCreate();

      import spark.implicits._

      val sch = new StructType(Array(
      StructField("sepal_length", DoubleType, true),
      StructField("sepal_width", DoubleType, true),
      StructField("petal_length", DoubleType, true),
      StructField("petal_width", DoubleType, true),
      StructField("class", StringType, true)))

      val rawInput = spark.read.schema(sch).csv("src/main/resources/iris.data")

      val Array(training, test) = rawInput.randomSplit(Array(0.8, 0.2), 123)

      //acá van las features a evaluar, todo tiene que ser DoubleType, así que si no es hay que correr un StringIndexer.
      val assembler = new VectorAssembler()
      .setInputCols(Array("sepal_length", "sepal_width", "petal_length", "petal_width"))
      .setHandleInvalid("keep")
      .setOutputCol("features")

      val labelIndexer = new StringIndexer()
        .setInputCol("class")
        .setOutputCol("label")
        .setHandleInvalid("keep")
        .fit(rawInput)

       val classifier = new DecisionTreeClassifier()
         .setLabelCol("label")
         .setFeaturesCol("features")
    
      //la transformación de la label la dejo afuera del pipeline porque si no jpmml no lo toma.
      val trSch = labelIndexer.transform(rawInput)
      val pipeline = new Pipeline().setStages(Array(assembler, classifier))
      val pipelineModel = pipeline.fit(trSch)

      //ejemplo de corrida batch
      val trTest = labelIndexer.transform(test)
      val prediction = pipelineModel.transform(trTest)
      prediction.show(false)


      //Guardar el modelo en formato JPMML
      val pmmlBytes = new PMMLBuilder(trSch.schema, pipelineModel).buildFile(new File("src/main/resources/xgboostModel"))
    
    }

    
}
