package evaluator

import java.io.InputStream

import org.dmg.pmml.FieldName
import org.jpmml.evaluator.EvaluatorUtil
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


  def evaluateSetOfFeatures(label: String, features: List[Double], evaluator: ModelEvaluator[_]) : Either[String, Int] = {
    val activeFields: List[InputField] = evaluator.getActiveFields.asScala.toList
    val zippedArgsWithIndex = activeFields.zipWithIndex

    val arguments : java.util.Map[FieldName, Double] = zippedArgsWithIndex.map{ argWithIndex =>
      (argWithIndex._1.getFieldName, features(argWithIndex._2))
    }.toMap.asJava

    try {
      val results = EvaluatorUtil.decodeAll(evaluator.evaluate(arguments))
      Right(results.get(label).toString.toDouble.toInt)
    } catch {
      case e : Exception => Left(e.getMessage)
    }

  }

}
