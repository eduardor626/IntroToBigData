package edu.ucr.cs.cs167.eroch007;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.util.ByteArrayManager;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Filter log file by response code
 */
public class Filter {
    public static class TokenizerMapper
            extends Mapper<LongWritable, Text, NullWritable, Text> {

        private String responseNumber;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            // TODO add additional setup to your map task, if needed.
            //add a code that will read the desired response code from the job
            // configuration and store it in an instance variable in the class TokenizerMapper
            Configuration conf = context.getConfiguration();
            responseNumber = conf.get("response_code");

        }

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            if (key.get() == 0)
                return; // Skip header line
            String[] parts = value.toString().split("\t");
            String responseCode = parts[5];
            // TODO Filter by response code
            if (responseCode.equals(responseNumber))
                context.write(NullWritable.get(), value);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String desiredResponse = args[2];
        conf.set("response_code",desiredResponse);
        Job job = Job.getInstance(conf, "filter");
        job.setJarByClass(Filter.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setNumReduceTasks(0);
        job.setInputFormatClass(TextInputFormat.class);
        Path input = new Path(args[0]);
        FileInputFormat.addInputPath(job, input);
        Path output = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, output);

        //Add the desired respose code to the job configuration using the method Configuration#set


        // TODO pass the desiredResponse code to the MapReduce program
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}