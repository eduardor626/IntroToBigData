package edu.ucr.cs.cs167.eroch007;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.Configuration;
import java.lang.System;
import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Hello world!
 *
 */


public class App
{
    public static void checkArguments(String[] args){
        if(args.length != 2){
            throw new IllegalArgumentException("Invalid Number of Arguments passed to program.\n");
        }
    }

    public static void main( String[] args ) throws IOException {
        /*
         should take two command-line arguments;
         1st = input
         2nd = output
         */
        checkArguments(args);

        /*
        store the two arguments in local variables of type org.apache.hadoop.fs.Path
         */
        Path input = new Path(args[0]);
        Path output = new Path(args[1]);

        /*
        Retrieve the correct file system for the two files and store in a variable
        of type org.apache.hadoop.fs.FileSystem
         */
        Configuration conf = new Configuration();

        FileSystem fs_input = input.getFileSystem(conf);
        FileSystem fs_output = output.getFileSystem(conf);

        /*
            Check whether the input file exists or not. If it does not exist, write an error message and exit.
            Similarly, check whether the output file exists or not. If it already exists, write an error message and exit.

         */
        if(!fs_input.exists(input)){
            throw new FileNotFoundException("No file named"+input+"\n");
        }
        if(fs_output.exists(output)){
            throw new FileAlreadyExistsException("output file exists already\n");
        }

        /*
        FileSystem API to open the input file and copy all its contents to the output file.
        Measure the total time that it takes to do this step. Hint: Use the method System#nanoTime()
         */
        FSDataInputStream input_stream = fs_input.open(input);
        FSDataOutputStream out_stream = fs_output.create(output);


        byte[] buffer = new byte[1024];


        long start = System.nanoTime();
        int i = 0;
        long byteCount = 0;
        while((i = input_stream.read(buffer)) != -1){
            out_stream.write(buffer,0,i);
            byteCount+=i;
        }
        long totalTime = System.nanoTime() - start;
        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;

        System.out.println("Copied "+byteCount+" bytes from  "+input.toString()+" to "+output.toString()+" in "+
                elapsedTimeInSecond+" seconds\n");

        /*
          close the file streams
         */
        input_stream.close();
        out_stream.close();


    }
}
