package evaluator

import java.io.InputStream

import org.dmg.pmml.FieldName
import org.jpmml.evaluator.{InputField, LoadingModelEvaluatorBuilder, ModelEvaluator}

import scala.collection.JavaConverters._


object Eval {

  def openFile(path: String) : Option[InputStream] = {
    Some(this.getClass.getClassLoader.getResourceAsStream(path))
  }

  def evaluator(pmmlIs: InputStream) : ModelEvaluator[_] = {
    new LoadingModelEvaluatorBuilder()
      .load(pmmlIs)
      .build()
  }


  def evaluateSetOfFeatures(features: List[Double], evaluator: ModelEvaluator[_]) : Either[String, Map[FieldName,_]] = {
    val activeFields: List[InputField] = evaluator.getActiveFields.asScala.toList

    val zippedArgsWithIndex = activeFields.zipWithIndex
    val arguments = zippedArgsWithIndex.map{ argWithIndex =>
      (argWithIndex._1.getFieldName, features(argWithIndex._2))
    }.toMap.asJava

    try {
      Right(evaluator.evaluate(arguments).asScala.toMap)
    } catch {
      case e : Exception => Left(e.getMessage)
    }

  }

}
