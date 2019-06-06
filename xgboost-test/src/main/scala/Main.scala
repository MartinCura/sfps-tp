import evaluator.Eval
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import pipelines.StringLabeledPipeline
import saver.PipelineSaver
import types._

object Main {
  
    val RESOURCES_FILE_PATH = "src/main/resources/iris.data"
    val SHORT_TRAIN_PATH = "src/main/resources/iris.data"
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



    def getSchema(features: List[MyRowElement]) {
        features.map(f => new StructField(f.toString(), DoubleType, true))
    }


    //Aca dejo un ejemplo de cómo habría que transformar la data de entrada para que quede como quiere spark
    case class Example(maiScore: Double, deviceMatch: Double, factorCodes: Double, firstEncounter: Double, apocrypha: Double)

    //Example of usage
    def main(args: Array[String]): Unit = {

        val spark = SparkSession .builder().appName("Spark SQL basic example")
        .config("spark.master", "local")
        .getOrCreate()

        import spark.implicits._

        
        val rowsExample = Seq(Example(1.0D, 2.0D, 3.0D, 4.0D, 3.0D),
                              Example(1.0D, 2.0D, 3.8D, 4.0D, 3.0D),
                              Example(1.0D, 2.0D, 78.0D, 4.0D, 4.0D),
                              Example(1.0D, 2.0D, 3.0D, 5.0D, 3.0D))

        val exampleDataframe = rowsExample.toDF

        exampleDataframe.show()

        // Creación del modelo y guardado a pmml:
        val modelCreator = new StringLabeledPipeline
        modelCreator.assemble(exampleDataframe.schema, exampleDataframe, "apocrypha", List("maiScore","deviceMatch","factorCodes","firstEncounter"))
            .foreach(assembledPipelineModel => {
              //SIDE EFFECTS!!!
              new PipelineSaver().saveToJpmml(assembledPipelineModel, exampleDataframe, MODEL_FILE_PATH)
          })
        

        //Dejo esto comentado que era lo del dataset de prueba:
        //STAGE 1: TRAINING AND SAVING
        //callModel()

        //mostrar resultado
        //JAXBUtil.marshalPMML(pmml, new StreamResult(System.out))

        // STAGE 2: LOAD AND EVAL JPMML
        //Example set of features.
        
        //callEvaluator()

        spark.stop()

      }

      def callModel() = {

        val spark = SparkSession .builder().appName("Spark SQL basic example")
        .config("spark.master", "local")
        .getOrCreate()

        val sch = getSchema()

        val rawInput = spark.read.schema(sch).csv(RESOURCES_FILE_PATH)

        val modelCreator = new StringLabeledPipeline
        modelCreator.preProccess(rawInput, "class").foreach(transformedDataFrame => {
          modelCreator.assemble(sch, transformedDataFrame, "class", List(FIELD_1, FIELD_2, FIELD_3, FIELD_4))
            .foreach(assembledPipelineModel => {
              //SIDE EFFECTS!!!
              new PipelineSaver().saveToJpmml(assembledPipelineModel, transformedDataFrame, MODEL_FILE_PATH)
          })
        })
        
        spark.stop()
      }

      def callEvaluator() = {

        val rawArgs = List(5.1, 3.5, 12.4, 7.2)

        Eval.openFile("xgboostModel.pmml").map(file => Eval.evaluateSetOfFeatures(rawArgs, Eval.evaluator(file)))
          .foreach {
            case Left(msg) => println("error: " + msg)
            case Right(evaluation) => println(evaluation.head)
          }

      }

}
