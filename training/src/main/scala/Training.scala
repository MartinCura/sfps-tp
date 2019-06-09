package sfps.training

import java.io.FileOutputStream
import org.apache.spark.sql.SparkSession
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.jpmml.model.MetroJAXBUtil

import pipelines.StringLabeledPipeline
import saver.PipelineSaver
import evaluator.Eval

import sfps.types._
import sfps.etl._


object Training {

    val MODEL_FILE_PATH = "target/xgboostModel.pmml"

    def main() = {
      val sparkSession = SparkSession.builder()
        .appName("SFPS training")
        .config("spark.master", "local")
        .getOrCreate()

      import sparkSession.implicits._

      val data = ETL.getMicroData(10)
      val df = data.toDF()

      df.show() //

      assert(df.count() > 0, "No rows")
      val features = data(0).productIterator.toList.map(_.toString) // Do note it takes them from the Row members, NOT the case classes

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
        println(sparkSession.sparkContext.hadoopConfiguration)
        val fs = FileSystem.get(sparkSession.sparkContext.hadoopConfiguration)
        val output = fs.create(new Path(MODEL_FILE_PATH))
        MetroJAXBUtil.marshalPMML(pmml.get, output)
        output.flush()
        output.close()
      })

      sparkSession.stop()
    }

}
