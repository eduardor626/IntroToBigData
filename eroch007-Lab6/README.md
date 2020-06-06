# Lab 6

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* (Q1) What is the type of the attributes time and bytes this time? Why?
   - This time we see that the type of attributes for time and bytes get changed to type `String` instead of type `int`. This is due to the fact that we have not told Spark to infer the schema based on the values of the file. Therefore everything gets the type String by default. Since we comment out the `.option("inferSchema","true")` line.
