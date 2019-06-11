package sfps.training

import java.io.FileOutputStream
import org.apache.spark.sql.SparkSession
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.jpmml.model.MetroJAXBUtil

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import frameless.functions.aggregate._
import frameless.TypedDataset
import frameless._
import frameless.syntax._
import frameless.ml._
import frameless.ml.feature._
import frameless.ml.regression._
import org.apache.spark.ml.linalg.Vector

import pipelines.StringLabeledPipeline
import saver.PipelineSaver
import evaluator.Eval

import sfps.types._
import sfps.etl._
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.{Model, Pipeline, PipelineModel, PipelineStage}


object Training {

    val MODEL_FILE_PATH = "target/xgboostModel.pmml"

    case class SimpleClass(maiScore: Double, deviceMatch: Double, factorCodes: Double, firstEncounter: Double, apocrypha: Double)
  
    def main() = {
      val conf = new SparkConf().setMaster("local[*]").setAppName("Frameless repl").set("spark.ui.enabled", "false")
      implicit val spark = SparkSession.builder().config(conf).appName("REPL").getOrCreate()
      spark.sparkContext.setLogLevel("WARN")

      import spark.implicits._
  
      val trainingData = TypedDataset.create(Seq(
        SimpleClass(20.0, 2.1, 2.4, 100000, 1),
        SimpleClass(50.0, 7.1, 45, 200000, 1),
        SimpleClass(50.0, 2.2, 4, 250000, 0),
        SimpleClass(100.0, 4.5, 56, 500000,1)
      ))



      val columnNames = ETL.getMicroData(10)
      val data = ETL.getMicroData(10)
      val df = data.toDF()

    

      df.show() //

      assert(df.count() > 0, "No rows")
      val features = columnNames(0).productIterator.filter(_.toString().toLowerCase() != "").toList.map(_.toString) // Do note it takes them from the Row members, NOT the case classes

      // Creación del modelo y guardado a pmml
      val modelCreator = new StringLabeledPipeline
      val pmml = modelCreator
        .assemble(df.schema, df, Apocrypha.toString, features)
        .map(assembledPipelineModel => {
          val pmml = new PipelineSaver().toPmml(assembledPipelineModel, df)
          pmml
        })

      // Saving the model in a file
      pmml.foreach(p => {
        println(spark.sparkContext.hadoopConfiguration)
        val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
        val output = fs.create(new Path(MODEL_FILE_PATH))
        MetroJAXBUtil.marshalPMML(pmml.get, output)
        output.flush()
        output.close()
      })

      spark.stop()
    }

}
