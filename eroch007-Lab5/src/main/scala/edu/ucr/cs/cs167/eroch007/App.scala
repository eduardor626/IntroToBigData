package edu.ucr.cs.cs167.eroch007

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author ${Eduardo Rocha}
 */
object App {

  def main(args : Array[String]) {
    //Reading program parameters
    val command: String = args(0)
    val inputfile: String = args(1)

    val ResponseCode: Int = 5
    val BytesCode: Int = 6;

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    //println(s"Using Spark master '${conf.get("spark.master")}'")
    conf.setAppName("lab5")
    val sparkContext = new SparkContext(conf)
    try {
      //creating our RDD and including all the records of the input File
      val inputRDD: RDD[String] = sparkContext.textFile(inputfile)

      // TODO Parse the input file using the tab separator and skip the first line
      val filteredRDD: RDD[Array[String]] = inputRDD.map(line => line.split("\t")).filter(parts => !parts(0).equals("host"))

      val t1 = System.nanoTime
      command match {
        case "count-all" =>
          // TODO count total number of records in the file
          val countLines = filteredRDD.count();
          println("Total count for file "+"'"+inputfile+"'"+" is "+countLines)

        case "code-filter" =>
          // TODO Filter the file by response code, args(2), and print the total number of matching lines
          val desiredResponseCode: String = args(2)
          val matchingLines: RDD[Array[String]]  = filteredRDD.filter(parts => parts(ResponseCode).equals(desiredResponseCode))
          val countCodes = matchingLines.count();
          println("Total count for file "+"'"+inputfile+"'"+ " with response code "+desiredResponseCode+" is "+countCodes)

        case "time-filter" =>
          // TODO Filter by time range [from = args(2), to = args(3)], and print the total number of matching lines
          val startStr : String = args(2)
          val endStr : String = args(3)
          val start : Long = startStr.toLong
          val end : Long = endStr.toLong
          //Here we use the time column of the log file thats comining in
          //we filter if that time code is between the two time intervals specified
          //at the end we run a count operation on the matching rdd
          val TimeCode : Int = 2;
          val timeBetween : RDD[Array[String]] = filteredRDD.filter(parts => parts(TimeCode).toLong >= start && parts(TimeCode).toLong <= end)
          val count = timeBetween.count()
          println("Total count for file "+"'"+inputfile+"'"+" in time range ["+startStr+", "+endStr+"]" + " is "+count)

        case "count-by-code" =>
          // TODO Group the lines by response code and count the number of records per group
          val countByCode = filteredRDD.map(line => (line(ResponseCode), 1)).countByKey()
          println("Number of lines per code for the file "+inputfile)
          println("Code,Count")
          for ((k,v) <- countByCode) println(s"$k,$v")

        case "sum-bytes-by-code" =>
          // TODO Group the lines by response code and sum the total bytes per group
          val sumBytes = filteredRDD.map(line => (line(ResponseCode),line(BytesCode).toLong)).reduceByKey((accum, n) => (accum + n))
          sumBytes.collect()
          println("Total bytes per code for the file "+inputfile)
          println("Code,Sum(bytes)")
          sumBytes.foreach(x => println(x._1 + ","+x._2))

        case "avg-bytes-by-code" =>
          // TODO Group the liens by response code and calculate the average bytes per group

          //getting the number of response codes for count
          val count = filteredRDD.map(line => (line(ResponseCode), 1)).countByKey()

          //getting the sum of all the bytes for each response code
          val sumBytes = filteredRDD.map(line => (line(ResponseCode),line(BytesCode).toLong)).reduceByKey((accum, n) => (accum + n))
          sumBytes.collect()

          //computing the avg
          println("Average bytes per code for the file "+inputfile)
          println("Code,Avg(bytes)")
          sumBytes.foreach(x => println(x._1 + ","+x._2.toDouble / count(x._1).toDouble))

        case "top-host" =>
        // TODO print the host the largest number of lines and print the number of lines
          val HostCode : Int = 0
          val host = filteredRDD.map(line => (line(HostCode),1)).reduceByKey((accum, n) => (accum + n))

          //sorting in descending order by value , Hi to Low
          val sorted = host.sortBy(-_._2)
          sorted.collect()

          println("Top host in the file "+inputfile+" by number of entries")
          println("Host: "+sorted.first()._1)
          println("Number of entries: "+sorted.first()._2)
          //sorted.foreach(x => println(x._1 + ","+x._2))


        case "comparison" =>
        // TODO Given a specific time, calculate the number of lines per response code for the
        // entries that happened before that time, and once more for the lines that happened at or after
        // that time. Print them side-by-side in a tabular form.

          //geting the time input
          val time: Long = args(2).toLong

          //getting the files with time before and after specified time input
          val before = filteredRDD.filter(line => (line(2).toLong < time))
          val after = filteredRDD.filter(line => (line(2).toLong > time))

          //combining all the files of before and after by response code
          val beforeAfter = before.map(line => (line(5), 1)).countByKey().toSeq.union(after.map(line => (line(5), 1)).countByKey().toSeq)

          //group by first value which is the response code
          val merge = beforeAfter.groupBy(_._1)


          //mapValues operates on the value only (the second part of the tuple),
          // while map operates on the entire record (tuple of key and value).
          val cleanOutput = merge.mapValues(_.map(_._2).toList)
          println(s"Comparison of the number of lines per code before and after ${time} on file ${inputfile}")
          println("Code,Count before,Count after")
          cleanOutput.foreach(line => println(s"${line._1},${line._2(0)},${line._2(1)}"))



      }
      val t2 = System.nanoTime
      println(s"Command '${command}' on file '${inputfile}' finished in ${(t2-t1)*1E-9} seconds")
    } finally {
      sparkContext.stop
    }
  }
  }
