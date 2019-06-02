package pipelines


import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.{Model, Pipeline, PipelineModel, PipelineStage}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

class StringLabeledPipeline extends GeneralPipeline {

  def assemble(schema: StructType,
               dataFrame: DataFrame,
               features: List[String]) : Option[PipelineModel] = {

    Some(pipeModel(List(assembler(features), RFclassifier)).fit(dataFrame))

  }

  //La versión de jpmml que usamos no soporta usar labels transformadas como etiquetas, hay que correr este preproceso
  //antes de meterlo en el pipeline -> https://github.com/jpmml/jpmml-sparkml/issues/35
  val LABEL = "label"
  def labelIndexerPreProcess(rawDataFrame: DataFrame, labelDataFieldName: String) : Model[_] =
    new StringIndexer()
    .setInputCol(labelDataFieldName)
    .setOutputCol(LABEL)
    .setHandleInvalid("keep")
    .fit(rawDataFrame)

  def pipeModel(stages: List[_ <: PipelineStage]) : Pipeline = {
    new Pipeline().setStages(stages.toArray)
  }

  //acá van las features a evaluar, todo tiene que ser DoubleType, así que si no es hay que correr un StringIndexer.
  def assembler(featureFields: List[String]) : VectorAssembler =
    new VectorAssembler()
    .setInputCols(featureFields.toArray)
    .setHandleInvalid("keep")
    .setOutputCol("features")

  def RFclassifier = new RandomForestClassifier()
    .setLabelCol("label")
    .setFeaturesCol("features")

  override def preProccess(rawDataFrame: DataFrame, strings: String*): Option[DataFrame] = {
    //TODO handle exceptions
    Some(labelIndexerPreProcess(rawDataFrame, strings.head).transform(rawDataFrame))
  }
}
