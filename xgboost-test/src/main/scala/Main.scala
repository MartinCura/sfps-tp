import evaluator.Eval
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import pipelines.StringLabeledPipeline
import saver.PipelineSaver

object Main {

    val RESOURCES_FILE_PATH = "src/main/resources/iris.data"
    val MODEL_FILE_PATH = "src/main/resources/xgboostModel.pmml"

    val FIELD_1 = "sepal_length"
    val FIELD_2 = "sepal_width"
    val FIELD_3 = "petal_length"
    val FIELD_4 = "petal_width"
    val FIELD_5 = "class"

    def getSchema() = {
      new StructType(Array(
        StructField(FIELD_1, DoubleType, true),
        StructField(FIELD_2, DoubleType, true),
        StructField(FIELD_3, DoubleType, true),
        StructField(FIELD_4, DoubleType, true),
        StructField(FIELD_5, StringType, true)))
    }

    def main(args: Array[String]): Unit = {

        val spark = SparkSession .builder().appName("Spark SQL basic example")
        .config("spark.master", "local")
        .getOrCreate()

        val sch = getSchema()

        val rawInput = spark.read.schema(sch).csv(RESOURCES_FILE_PATH)

        //STAGE 1: TRAINING AND SAVING

        val modelCreator = new StringLabeledPipeline
        modelCreator.preProccess(rawInput, "class").foreach(transformedDataFrame => {
          modelCreator.assemble(sch, transformedDataFrame, List(FIELD_1, FIELD_2, FIELD_3, FIELD_4))
            .foreach(assembledPipelineModel => {
              //SIDE EFFECTS!!!
              new PipelineSaver().saveToJpmml(assembledPipelineModel, transformedDataFrame, MODEL_FILE_PATH)
          })
        })


        //mostrar resultado
        //JAXBUtil.marshalPMML(pmml, new StreamResult(System.out))


        // STAGE 2: LOAD AND EVAL JPMML
        //Example set of features.
        val rawArgs = List(5.1, 3.5, 12.4, 7.2)

        Eval.openFile("xgboostModel.pmml").map(file => Eval.evaluateSetOfFeatures(rawArgs, Eval.evaluator(file)))
          .foreach {
            case Left(msg) => println("error: " + msg)
            case Right(evaluation) => println(evaluation.head)
          }

        spark.stop()

      }

}
