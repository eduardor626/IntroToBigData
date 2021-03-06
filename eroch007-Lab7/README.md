# Lab 7

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* (Q1) Look at the dashboard on the City of Chicago to explore how the data can be used. What are the top five crime types?
    - The top 5 crimes: Theft, Battery, Criminal Damage, Narcotics, Assault

* (Q2) Compare the sizes of the CSV file and the resulting Parquet file? What do you notice? Explain.
    - The CSV file is 1.68 GB however the Parquet file is broken down into a folder of files that range between 18-37MB. The total size of the Parquet folder is 456.9 MB. I believe this is because the Parquet column format causes less overhead than the CSV file. Organizing by column allows for better compression. The space savings are very noticeable. I/O will be reduced as we can efficiently scan only a subset of the columns while reading the data.

* (Q3) How many times do you see the schema the output? How does this relate to the number of files in the output directory? What do you make of that?
    - We see the output schema displayed 13 times on the output. This relates to the amount of times the file is partitioned in columnar format within the newly created `.parquet` directory.

* (Q4) How does the output look like? How many files were generated?
    - It contains a partitioned `.parquet` directory with subdirectories in it. Each subdirectory has a District #, for each district. It seems as if these files
    were generated by the `partitionBy` method we use in the Scala program and give it the parameter District. The partitioned `.parquet` directory is also smaller than the non-partitioned directory.

* (Q5) Explain an efficient way to run this query on a column store.
    -  Because we are doing a find operation we can easily find the Case Number because we have the data stored as a column. This causes less overhead than reading a whole record and all its fields in order to find one value. Here we know which field/column to look at which makes it much faster to find the certain case number. Once we find the case number we need the whole record though. So I think because the RDBMs run in parallel, that once we know the position of one value in a column store we can find its whole record based on the position of the found case number in the file.

* (Q6) Which of the three input files you think will be processed faster using this operation?
    - I believe the partitioned `.parquet` file will be processed faster because of the specification of the district. Since the partioned `.parquet` file is partitioned by District ID, it can grab the value of the stats of a certain district much faster. 




    
| Command  |  Time on CSV | Time on non-partitioned Parquet	  |  Time on partitioned Parquet |  
|---|---|---|---|
| top-crime-types  |  15.24318  | 4.3951 |  5.7970|
| find |    35.0493 | 7.70168 |  9.1019| 
| stats|  29.4561|  7.97491 |  7.9634 | 
| stats-district   | 17.1362  | 3.9770  |  3.4019 |