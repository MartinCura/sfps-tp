import java.io.FileOutputStream
import org.apache.spark.sql.SparkSession
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.jpmml.model.MetroJAXBUtil
import org.glassfish.jersey.spi.ExceptionMappers

import sfps.types._
import sfps.db.Schema
import sfps.etl.ETL

import pipelines.StringLabeledPipeline
import saver.PipelineSaver

object Training {

    val MODEL_FILE_PATH = "xgboostModel.pmml"

    def main(args: Array[String]) = {

      // Check table exists before anything
      if (!ETL.doesTableExist()) {
        println(" *** Table train does not exist, exiting. *** ")
        sys.exit
      }

      val sparkSession = SparkSession.builder()
        .appName("SFPS training")
        .config("spark.master", "local")
        .getOrCreate()

      import sparkSession.implicits._

      val LABEL = "apocrypha"
      val labels = Schema.labels.filter(_.toString().toLowerCase != LABEL.toLowerCase())

      //translate to double
      val data = ETL.getData(None).map(Mappers.doubleTypedMapper(_)).compile.toList.unsafeRunSync()

      println(data(20))
      val df = data.toDF()

      df.show() //

      assert(df.count() > 0, "No rows")


      // model creation and pmml translation
      val modelCreator = new StringLabeledPipeline
      val pmml = modelCreator
        .assemble(df.schema, df, Apocrypha.toString.toLowerCase(), labels)
        .map(assembledPipelineModel => {
          val pmml = new PipelineSaver().toPmml(assembledPipelineModel, df)
          pmml
        })

      //spark-friendly saving the model in a file
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
