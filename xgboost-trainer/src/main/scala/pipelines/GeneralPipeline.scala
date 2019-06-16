package pipelines

import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

trait GeneralPipeline {

  //def preProccess(rawDataFrame: DataFrame, strings: String*): Option[DataFrame]

  def assemble(schema: StructType, dataFrame: DataFrame, label: String, features: List[String]) : Option[PipelineModel]

}
