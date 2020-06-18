package edu.ucr.cs.cs167.eroch007
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, RegressionEvaluator}
import org.apache.spark.ml.feature.{HashingTF, StringIndexer, Tokenizer, VectorAssembler, Word2Vec}
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder, TrainValidationSplit}

/**
 * @author ${Eduardo Rocha}
 *
 *         Objectives:
 *         - Run machine learning algorithms on big data using MLlib.
 *         - Try out the different components of MLlib.
 */
object App {

  def main(args : Array[String]) {
    if (args.length < 2) {
      println("Usage <input file> <algorithm>")
      println("<input file> path to a CSV file input")
      println("<algorithm> is either regression or classification")
    }
    val inputfile = args(0)
    val method = args(1)
    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab9")
      .config(conf)
      .getOrCreate()

    val t1 = System.nanoTime
    try {
      if (method.equals("regression"))
      {
        /* In the first case study, we will run a regression model on the house prices dataset to build a model that
          estimates house prices based on some house features. */

        //read in file
        val input = spark.read.format("csv")
          .option("sep",",")
          .option("inferSchema", "true")
          .option("header", "true")
          .load(inputfile)

        input.printSchema()
        input.show()


        //TODO pply regression model on house prices
        val assembler = new VectorAssembler()
          .setInputCols(Array("bedrooms", "bathrooms", "sqft_living", "sqft_lot"))
          .setOutputCol("features")


        val lr = new LinearRegression()
          .setFeaturesCol("features")
          .setLabelCol("price")


        val model = lr.fit(assembler.transform(input))

        println(model.coefficients)


        val pipeline = new Pipeline()
          .setStages(Array(assembler, lr))

        val builder = new ParamGridBuilder()
          .addGrid(lr.regParam, Array(0.01, 0.1, 0.3))
          .addGrid(lr.elasticNetParam, Array(0.0, 0.3, 0.8, 1.0))


        val cv = new CrossValidator()
          .setEstimator(pipeline)
          .setEvaluator(new RegressionEvaluator().setLabelCol("price"))
          .setEstimatorParamMaps(builder.build())
          .setNumFolds(5)
          .setParallelism(2)

        //we will separate the input dataset into training and test sets
        val Array(trainingData, testData) = input.randomSplit(Array(0.8, 0.2))

        //cross validator run on the training set to search for the best mode
        val bestModel = cv.fit(trainingData)

        val predictions = bestModel.transform(testData)
        predictions.select("price", "prediction").show(5)

        val rmse = new RegressionEvaluator()
          .setLabelCol("price")
          .setPredictionCol("prediction")
          .setMetricName("rmse")
          .evaluate(predictions)
        println(s"RMSE on test set is $rmse")

        val corr = Correlation.corr(new VectorAssembler()
          .setInputCols(Array("prediction", "price"))
          .setOutputCol("features2")
          .transform(predictions), "features2").head
        println(s"Correlation is $corr")


      } else if (method.equals("classification")) {
        // TODO process the sentiment data

        //Read in file
        val input = spark.read.format("csv")
          .option("quote", "\"")
          .option("escape", "\"")
          .option("header", "true")
          .load(inputfile)

        input.printSchema()
        input.show()



        val token = new Tokenizer()
          .setInputCol("text")
          .setOutputCol("words")

        val TF = new HashingTF()
          .setInputCol("words")
          .setOutputCol("features")


        val str_indexer = new StringIndexer()
          .setInputCol("sentiment")
          .setOutputCol("label")
          .setHandleInvalid("skip")

        val logReg = new LogisticRegression()
        val pipeline = new Pipeline()
          .setStages(Array(token, TF, str_indexer, logReg))

        val paramGrid = new ParamGridBuilder()
          .addGrid(TF.numFeatures, Array(10, 100, 1000))
          .addGrid(logReg.regParam, Array(0.01, 0.1, 0.3, 0.8))

        val cv = new TrainValidationSplit()
          .setEstimator(pipeline)
          .setEvaluator(new BinaryClassificationEvaluator())
          .setEstimatorParamMaps(paramGrid.build())
          .setTrainRatio(0.8)
          .setParallelism(2)

        val Array(trainingData, testData) = input.randomSplit(Array(0.8, 0.2))
        val logisticModel = cv.fit(trainingData)

        val predictions = logisticModel.transform(testData)
        predictions.select("text", "label", "prediction", "probability").show()

        val binaryClassificationEvaluator = new BinaryClassificationEvaluator()
          .setLabelCol("label")
          .setRawPredictionCol("prediction")

        val accuracy = binaryClassificationEvaluator.evaluate(predictions)
        println(s"Accuracy of the test set is $accuracy")


      } else {
        println(s"Unknown algorithm ${method}")
        System.exit(1)
      }
      val t2 = System.nanoTime
      println(s"Applied algorithm $method on input $inputfile in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}
