# Lab 4

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* (Q1) Do you think it will use your local cluster? Why or why not?
   - Yes. Because in `main()` we have a `new JavaSparkContext("local[*]", "CS167-Lab4");` line which seems to be setting the context for my spark variable to be set on local machine. I also look at the web interface of the Spark Master and see that there are 4 total cores, but none are being used. Memory has 7GB avaliable and 0GB being used.

* (Q2) Does the application use the cluster that you started? How did you find out?
   - Yes the application does use the cluster that we started. On the web interface for spark we are able to see changes to the Applications categories. We switch between local and cluster mode.

* (Q3) What is the Spark master printed on the standard output on IntelliJ IDEA?
```
Using Spark master 'local[*]'
```

* (Q4) What is the Spark master printed on the standard output on the terminal?
```
Using Spark master 'local[*]'
```

* (Q5) For the previous command that prints the number of matching lines, list all the processed input splits.
```
20/04/24 13:07:52 INFO HadoopRDD: Input split: file:/Users/eduardorocha/workspace/eroch007_lab4/nasa_19950801.tsv:1169610+1169610

20/04/24 13:07:52 INFO HadoopRDD: Input split: file:/Users/eduardorocha/workspace/
eroch007_lab4/nasa_19950801.tsv:0+1169610
```
* (Q6) For the previous command that counts the lines and prints the output, how many splits were generated? 
   - There are 4 splits that are generated from the previous command.
```
20/05/31 12:50:09 INFO HadoopRDD: Input split: file:/Users/eduardorocha/workspace/eroch007_lab4/nasa_19950630.22-19950728.12.tsv:67108864+33554432

20/05/31 12:50:09 INFO HadoopRDD: Input split: file:/Users/eduardorocha/workspace/eroch007_lab4/nasa_19950630.22-19950728.12.tsv:0+33554432

20/05/31 12:50:09 INFO HadoopRDD: Input split: file:/Users/eduardorocha/workspace/eroch007_lab4/nasa_19950630.22-19950728.12.tsv:100663296+33554432

20/05/31 12:50:09 INFO HadoopRDD: Input split: file:/Users/eduardorocha/workspace/eroch007_lab4/nasa_19950630.22-19950728.12.tsv:33554432+33554432
```

* (Q7) Compare this number to the one you got earlier. 
   - The amount of splits doubles from 2 to 4.

* (Q8) Explain why we get these numbers.
   - I believe we get these numbers because the file is read twice. We can use the cache function in order for the file not to be read more than once. 
   

* (Q9) What can you do to the current code to ensure that the file is read only once?
   - We can use the Spark `cache()` to make sure that the file is read only once. We perform this on the RDD variable to ensure that `cache()` will cache the RDD into memory. "It is one mechanism to speed up applications that access the same RDD multiple times. An RDD that is not cached, nor checkpointed, is re-evaluated again each time an action is invoked on that RDD".[source](https://unraveldata.com/to-cache-or-not-to-cache/)
