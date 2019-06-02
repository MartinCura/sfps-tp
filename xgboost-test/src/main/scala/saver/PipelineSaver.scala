package saver

import java.io.File

import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.DataFrame
import org.jpmml.sparkml.PMMLBuilder
import pipelines.GeneralPipeline

class PipelineSaver {

  def saveToJpmml(pipeline: PipelineModel, schema: DataFrame, path: String ): Unit = {

    val pmml = new PMMLBuilder(schema.schema, pipeline).build
    new PMMLBuilder(schema.schema, pipeline).buildFile(new File(path))

  }

}
