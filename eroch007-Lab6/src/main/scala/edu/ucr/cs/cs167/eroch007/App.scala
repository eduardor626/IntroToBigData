package edu.ucr.cs.cs167.eroch007

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

/**
 * @author $Eduardo Rocha
 *
 *         Understand SparkSQL and the DataFrame API
 *         Write a Scala program that uses SparkSQL
 */


object App {

  def main(args : Array[String]) {

    //Reading in the command line arguments
    val command: String = args(0)
    val inputfile: String = args(1)


    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab6")
      .config(conf)
      .getOrCreate()

    val input = spark.read.format("csv")
      .option("sep", "\t")
      .option("inferSchema", "true")
      .option("header", "true")
      .load(inputfile)


    import spark.implicits._

    //to check the inferred schema, add the following statement
    //input.printSchema()

    //to display the first 20 lines to make sure you parse correctly
    //input.show()

    try {
      val t1 = System.nanoTime
      command match {
        case "count-all" =>
        // count total number of records in the file
          val countRecords = input.count()
          println(s"Total count for file '${inputfile}' is ${countRecords}")

        case "code-filter" =>
        // Filter the file by response code, args(2), and print the total number of matching lines
          val responseCode = args(2).toString()
          val counter = input.filter(s"response=${responseCode}").count()
          println(s"Total count for file '${inputfile}' with response code ${responseCode} is ${counter}")

        case "time-filter" =>
        // Filter by time range [from = args(2), to = args(3)], and print the total number of matching lines
          val start = args(2)
          val end = args(3)
          val matchingLines = input.filter(s"time BETWEEN ${start} AND ${end}").count()
          println(s"Total count for file '${inputfile}' in time range [${start}, ${end}] is ${matchingLines}")

        case "count-by-code" =>
        // Group the lines by response code and count the number of records per group
          val groupRecords = input.groupBy("response")
            .count()
          println(s"Number of lines per code for the file ${inputfile}")
          groupRecords.show()

        case "sum-bytes-by-code" =>
        // Group the lines by response code and sum the total bytes per group
          val sumBytes = input.groupBy("response")
            .sum("bytes")
          println(s"Total bytes per code for the file ${inputfile}")
          sumBytes.show()


        case "avg-bytes-by-code" =>
        // Group the lines by response code and calculate the average bytes per group
          val avgBytes = input.groupBy("response").avg("bytes")
          println(s"Average bytes per code for the file ${inputfile}")
          avgBytes.show()

        case "top-host" =>
        // print the host the largest number of lines and print the number of lines

          val hostRecords = input.groupBy("host").count()
          val highestToLowest = hostRecords.orderBy($"count".desc) //order by descending
          val topHost = highestToLowest.first()
          println(s"Top host in the file ${inputfile} by number of entries")
          println(s"Host: ${topHost.getAs("host")}")
          println(s"Number of entries: ${topHost.getAs(1)}")


        case "comparison" =>
        // Given a specific time, calculate the number of lines per response code for the
        // entries that happened before that time, and once more for the lines that happened at or after
        // that time. Print them side-by-side in a tabular form.

          val timeStamp = args(2).toString
          //creating 2 dataframes by filtering the input twice
          val before = input.filter(s"time < ${timeStamp}")
            .groupBy("response")
            .count()
            .withColumnRenamed("count","count_before")

          val after = input.filter(s"time > ${timeStamp}")
            .groupBy("response")
            .count()
            .withColumnRenamed("count","count_after")

          val merge = before.join(after,"response")
          println(s"Comparison of the number of lines per code before and after ${timeStamp} on file ${inputfile}")
          merge.show()
      }
      val t2 = System.nanoTime
      println(s"Command '${command}' on file '${inputfile}' finished in ${(t2-t1)*1E-9} seconds")


    } finally {
      spark.stop
    }
  }

}
