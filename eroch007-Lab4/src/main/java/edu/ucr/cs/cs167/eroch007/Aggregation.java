package edu.ucr.cs.cs167.eroch007;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.util.Map;


public class Aggregation {


    public static void main( String[] args ) throws IOException {
        final String inputfile = args[0];

        final String outputFile = args[1];


        //How we write to the output
        FileWriter output = new FileWriter(outputFile);

        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("lab4");
        JavaSparkContext spark = new JavaSparkContext(conf);

        try {
            JavaRDD<String> logFile = spark.textFile(inputfile).cache();
            JavaPairRDD<String,Integer> linesByCode = logFile.mapToPair(new PairFunction<String, String, Integer>() {
                @Override
                public Tuple2<String, Integer> call(String s){
                    String code = s.split("\t")[5];
                    return new Tuple2<String,Integer>(code,1);
                }
            });

            //Getting the total number of counts and storing into map
            Map<String, Long> countByCode = linesByCode.countByKey();
            String codeEntries = "";
            //iteration through the map
            for(Map.Entry<String,Long> entry : countByCode.entrySet()){
                codeEntries = "Code " + "'"+ entry.getKey() + "'" + " : number of entries " + entry.getValue() ;
                System.out.println(codeEntries);
                output.write(codeEntries+'\n');

            }
            output.close(); //closing the file

        } finally {
            spark.close();
        }
    }
}
