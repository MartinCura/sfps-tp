package saver

import java.io.{File, FilterOutputStream}

import javax.xml.transform.stream.StreamResult
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.DataFrame
import org.dmg.pmml.PMML
import org.jpmml.model.MetroJAXBUtil
import org.jpmml.sparkml.PMMLBuilder

class PipelineSaver {

  def toPmml(pipeline: PipelineModel, schema: DataFrame) : PMML = {
    return new PMMLBuilder(schema.schema, pipeline).build
  }

  def toPmml(pipeline: PipelineModel, schema: DataFrame, path: String ) = {
    new PMMLBuilder(schema.schema, pipeline).buildFile(new File(path))
  }

}
