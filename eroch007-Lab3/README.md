# Lab 3

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* (Q1) What do you think the line `job.setJarByClass(Filter.class);` does?
   -  To help Hadoop figure out which jar it should send to nodes to perform Map and Reduce tasks. To make sure that both Mapper and Reducer are part of the same jar. In this case it will be our Filter class as the parameter. 
   [Link to source](https://stackoverflow.com/questions/3912267/hadoop-query-regarding-setjarbyclass-method-of-job-class)

* (Q2) What is the effect of the line `job.setNumReduceTasks(0);`?
   - This line implies that no reducer will execute and no aggregation will take place. This is typically called a “Map-only job” in Hadoop. Map Only Job means the map does all the tasks with its InputSplit and the reducer does no job. Mapper output is the final output. As we see in lecture in some examples that do not need the Reducer task.

* (Q3) Where does the main function run? (Driver node, Master node, or a slave node).
   - The main function I believe acts as the client or driver node. Since it is not a part of our cluster which means it acts
   as a remote node. 

* (Q4) How many lines do you see in the output?
   - 27972 lines are in the output file. All have a response code of 200. 

* (Q5) How many files are produced in the output? 
   - I see a total of 4 files. 2 of them are copies with a .crc extension. 

* (Q6) Explain this number based on the input file size and default block size.
   - The input file size is 2.3MB. A default block size is 128MB. So the reason we see 2 copies is because we are running the program locally so that means one of them is local and doesn't need to be replicated. Therefore the 2 replicas were copied that were not on the local machine. 

* (Q7) How many files are produced in the output?
   - I see a total of 2 files. 

* (Q8) Explain this number based on the input file size and default block size.
   - This can be due to the fact that in HDFS we are working directly with the datanode. So it does not require any overhead of copying.
   The file is 2.3MB which clearly fits in a 128MB block.

* (Q9) How many files are produced in the output directory and how many lines are there in each file? 
   - In the output directory I now see a total of 6 files. Three files with a copy of each. The output of the file `part-r-00000`:
```
200   481974462
302   26005
304   0
404   0
```   

* (Q10) Explain these numbers based on the number of reducers and number of response codes in the input file.
   - I believe that the Filter class has 0 reducers because of the line `job.setNumReduceTasks(0);`. Also we do everything in the map function. For the Aggregation class I believe we have one reducer since we have a reduce function. From the output file we see that there are only four types of response codes and we see the sum of all the bytes for that response code on the right hand column.

* (Q11) How many files are produced in the output directory and how many lines are there in each file?
   - There are 6 files that are produced in total in the output directory. Three of them are copies of another with a .crc extension. In the output file now there are 5 lines.`part-r-00000`:
```
200   37585778
302   3682049
304   0
404   0
500   0
```
   - In the file `part-r-00001` there are two lines:
```
403   0
501   0
```
* (Q12) Explain these numbers based on the number of reducers and number of response codes in the input file.
   - The new file is larger than the last file. So the number of total bytes for response codes are large. We also see that we have some new response codes in this file. I notice that now the output directory has the extra 2 lines. Could that be because of the reduce job? We see that there are no duplicate response numbers on either file. I remember this being talked about in lecture. The map to reducer process sorts the key value pair in its appropriate file. Making sure of no duplicates. So maybe this has two reducers since we see two different outputs like this.

* (Q13) How many files are produced in the output directory and how many lines are there in each file? 
   - There are 3 files that are created. One of the ` part-r-00000` files has this output.
```
200   37551809
```

* (Q14) Explain these numbers based on the number of reducers and number of response codes in the input file.
   - The Filter class only produces records with a response code of 200. So the output of Aggregate class makes sense because it only has files of 200 response code to look at. Therefore we only see one summation, which is displayed on the output of the Aggregate class.