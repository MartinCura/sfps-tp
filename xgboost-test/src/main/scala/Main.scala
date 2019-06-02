import java.io.File

import javax.xml.transform.stream.StreamResult
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.jpmml.model.JAXBUtil
import org.jpmml.sparkml._
import evaluator.Eval

object Main {

    val MODEL_FILE_PATH = "src/main/resources/iris.data"

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

        val rawInput = spark.read.schema(sch).csv("src/main/resources/iris.data")

        //acá van las features a evaluar, todo tiene que ser DoubleType, así que si no es hay que correr un StringIndexer.
        val assembler = new VectorAssembler()
        .setInputCols(Array(FIELD_1, FIELD_2, FIELD_3, FIELD_4))
        .setHandleInvalid("keep")
        .setOutputCol("features")

        val labelIndexer = new StringIndexer()
          .setInputCol("class")
          .setOutputCol("label")
          .setHandleInvalid("keep")
          .fit(rawInput)

         val classifier = new RandomForestClassifier()
           .setLabelCol("label")
           .setFeaturesCol("features")

        //la transformación de la label la dejo afuera del pipeline porque si no jpmml no lo toma.
        val trSch = labelIndexer.transform(rawInput)
        val pipeline = new Pipeline().setStages(Array(assembler, classifier))
        val pipelineModel = pipeline.fit(trSch)


        //Guardar el modelo en formato JPMML
        val pmml = new PMMLBuilder(trSch.schema, pipelineModel).build
        new PMMLBuilder(trSch.schema, pipelineModel).buildFile(new File(MODEL_FILE_PATH))


        //mostrar resultado
        JAXBUtil.marshalPMML(pmml, new StreamResult(System.out))


        // Eval pipeline
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
