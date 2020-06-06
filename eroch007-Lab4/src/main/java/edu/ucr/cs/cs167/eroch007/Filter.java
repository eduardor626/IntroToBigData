package edu.ucr.cs.cs167.eroch007;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Filter {


    public static void main( String[] args ) {

        //taking in the command line files/parameters
        final String inputfile = args[0];
        final String outputFile = args[1];
        final String desiredCode = args[2];

        //to run in Pseudo-distributed Mode
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("lab4");
        JavaSparkContext spark = new JavaSparkContext(conf);


        //logFile rDD
        //matchingLines rDD
        try {

            JavaRDD<String> logFile = spark.textFile(inputfile);
            logFile.cache();
            System.out.printf("Number of lines in the log file %d\n", logFile.count());
            JavaRDD<String> matchingLines = logFile.filter(line -> line.split("\t")[5].equals(desiredCode));
            System.out.printf("The file '%s' contains %d lines with response code %s\n", inputfile, matchingLines.count(), desiredCode);
            matchingLines.saveAsTextFile(outputFile);


        } finally {
            spark.close();
        }
    }
}
