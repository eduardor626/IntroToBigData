package edu.ucr.cs.cs167.eroch007

/**
 * @author ${Eduardo Rocha}
 *
 *         Objectives
 *
 *         *Run analytic queries on Parquet files.
 *         *Understand the performance gains of working with Parquet files.
 *         *Run SQL queries using SparkSQL
 *
 *
 */

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.SparkConf



object App {
  def main(args: Array[String]) {
    val operation: String = args(0)
    val inputfile: String = args(1)


    //initialize  project with SparkSession to access SparkSQL and DataFrame
    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab7")
      .config(conf)
      .getOrCreate()

    try {
    import spark.implicits._
    // TODO Load input

    //parsing the extension
    var input = spark.emptyDataFrame
    val lastDot = inputfile.lastIndexOf('.')
    val extension = inputfile.substring(lastDot + 1)

    if (extension == "csv") {

      //since Parquet cannot deal with attribute names with spaces in them,
      // you will need to rename all attibutes with spaces.
      //println(extension)
      //println("Recieving CSV file")
      input = spark.read.format("csv")
        .option("sep",",")
        .option("inferSchema", "true")
        .option("header", "true")
        .load(inputfile)
        .withColumnRenamed("Case Number", "Case_Number")
        .withColumnRenamed("Primary Type", "Primary_Type")
        .withColumnRenamed("Location Description","Location_Description")
        .withColumnRenamed("Community Area", "Community_Area")
        .withColumnRenamed("FBI Code","FBI_Code")
        .withColumnRenamed("X Coordinate","X_Coordinate")
        .withColumnRenamed("Y Coordinate","Y_Coordinate")
        .withColumnRenamed("Updated On","Updated_On")

    } else {
      //println(extension)
      //println("Receiving PARQUET file")
      input = spark.read.parquet(inputfile)
    }


    //input.show()
    //input.printSchema()

    val t1 = System.nanoTime
    operation match {
      case "write-parquet" =>
      // Write the input dataset to a parquet file. The file name is passed in args(2)
        val outputParquetFile = args(2).toString()
        input.write.parquet(outputParquetFile)

      case "write-parquet-partitioned" =>
      // Write the input dataset to a partitioned parquet file by District. The file name is passed in args(2)
       val outputParquetFilePartition = args(2).toString()
        input.write.partitionBy("District").parquet(outputParquetFilePartition)

      case "top-crime-types" =>
      // DO Print out the top five crime types by count "Primary_Type"
        //For this lab, you need to implement all analysis queries using SQL. To do so,
        // you first need to create a temporary view out of your input and run the SQL query on it.

        val tempView = "input_temp_view"
        // Register the DataFrame as a SQL temporary view
        input.createTempView(tempView)
        val columnName = "Primary_Type"

        val sqlDF = spark.sql(s"SELECT ${columnName}, COUNT(*) AS count FROM ${tempView} GROUP BY " +
          s"${columnName} " +
          s"ORDER BY count DESC LIMIT 5")
        sqlDF.show()


      case "find" =>
      // Find a record by Case_Number in args(2)
        val caseNum = args(2).toString()


        val tempView = "temp_view"
        input.createTempView(tempView)
        val columnName = "Case_Number"


        //Add a operation filter that locates a crime record by Case-Number and writes the entire record.
        val df = spark.sql(s"SELECT * FROM ${tempView} WHERE ${columnName} = '${caseNum}'")
        df.show()


      case "stats" =>
      // Compute the number of arrests, domestic crimes, and average beat per district.
      // Save the output to a new parquet file named "stats.parquet"

        val tempView = "temp_view"
        input.createTempView(tempView)
        val arrest = "Arrest"
        val domestic = "Domestic"
        val beat = "Beat"

        val dataF = spark.sql(s"SELECT District, COUNT(case when ${arrest} = true then 1 else null end) AS Arrests, " +
          s"COUNT(case when ${domestic} = true then 1 else null end) as Domestics, " +
          s"AVG(${beat}) AS Avg_Beats FROM ${tempView} GROUP BY District ORDER BY District ASC ")
        dataF.show()

          //input.write.mode("overwrite").parquet("stats.parquet")

      case "stats-district"  =>
      // Compute number of arrests, domestic crimes, and average beat for one district (args(2))
      // Write the result to the standard output
        val tempView = "crimes"
        input.createTempView(tempView)
        val certainDistrict = args(2).toString()
      val dataF = spark.sql(s"SELECT District," +
        s"COUNT(case when Arrest = true then 1 else null end) AS Arrests," +
        s" COUNT(case when Domestic = true then 1 else null end) AS Domestics, " +
          s"AVG(Beat) AS AVG_Beats FROM ${tempView}  WHERE District = '${certainDistrict}' GROUP BY District")
        dataF.show()

    }

    val t2 = System.nanoTime
    println(s"Operation $operation on file '$inputfile' finished in ${(t2-t1)*1E-9} seconds")
  } finally {
    spark.stop
  }
}
}
