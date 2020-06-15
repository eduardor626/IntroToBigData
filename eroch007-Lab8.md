# Lab 8

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* Note: For all the questions below, write down your query and the answer to any questions. Include all the queries that you ran along with the answers in a README file.


* (Q1) Insert the sample JSON file into a new collection named contacts.
   - db.createCollection("contacts")
   - db.contacts.insert()
   - db.contacts.find()
   - db.getCollectionNames()
   		> ["contacts"]
   - db.contacts.find()
   - db.contacts.insert(JSONcontents)
   - db.contacts.find()

* (Q2) Retrieve all the users sorted by name.
	- db.contacts.find().sort({Name:1})


* (Q3) List only the `id` and `name`s sorted in reverse alphabetical order by `name` (Z-to-A).
   - db.contacts.find({},{_id:1,Name:1}).sort({Name:-1})


* (Q4) Is the comparison of the attribute `name` case-sensitive? Show how you try this with the previous query and 
    include your answer.
   - The comparison of the attribute 'Name' is case-sensitive. I tried this by changing the `Name:1` field 
   inside the `.find()` function to be lowercase(`name:1`) and did not receive any output for the `name` attribute
   only the `_id` field. Which means that the comparison is case sensitive.
   - db.contacts.find({},{_id:1,name:1}).sort({Name:-1})

* (Q5) Repeat Q3 above but do not show the _id field.
    - db.contacts.find({},{_id:0,Name:1}).sort({Name:-1}) 
    - the `_id:0` makes the find() not show the `_id` field. 

* (Q6) Insert the following document to the collection.
```text
{Name: {First: “David”, Last: “Bark”}}
```
Does MongoDB accept this document while the `name` field has a different type than other records? 
   - db.contacts.insert({Name: {First: “David”, Last: “Bark”}})
   - Yes MongoDB accepts this document while the `name` field had a different type than other records.
   - db.contacts.find({},{_id:1,Name:1}).sort({Name:-1})

* (Q7)Rerun Q3, which lists the records sorted by `name`.  Where do you expect the new record to be located in the sort order? 
Verify the answer and explain. 
   - After re-running this command we see that if we do `Name:-1` the newly inserted document into name comes first. If we
     do `Name:1` then the newly inserted document into name comes last. I believe this is due to precedence as discussed in lecture.
     
     
Insert the following document into the users collection.
```text
{Name: [“David”, “Bark”]}
```
    
* (Q8) Repeat Q3. Where do you expect the new document to appear in the sort order. Verify your answer and explain after running 
the query. 
    - I expect the following new record to be located in order according to the first element in the array which is `David`.
    - db.contacts.find({},{_id:1,Name:1}).sort({Name:-1}) 
    - We see that it is sorted based on `David` maybe because it takes the max value of the array. 

* (Q9)Repeat Q3 again with all the objects that you inserted, but this time sort the name in *ascending* order.Where do 
you expect the last inserted record, `{Name: [“David”, “Bark”]}` to appear this time? Does it appear in the same position 
relative to the other records? Explain why or why not. 
    - Now in the ascending order.
    - db.contacts.find({},{_id:1,Name:1}).sort({Name:1}) 
    - This time however, I notice that `[David, Bark]` appears right after the A names. So it seems that the sort must be 
      taking the minimum value of the array according to the Name. Therefore it picks up Bark. It does not appear in a position
      relative to the other records.
          
* (Q10) Build an index on the Name field for the users collection. 
Is MongoDB able to build the index on that field with the different value types stored in the Name field?
    - db.contacts.createIndex({Name:1})
    - db.contacts.getIndexes()
    - I do think that MongoDB is able to build the index on the field with different value types stored in the Name field
    because after the first command we see a successful build output.
    - Output: "createdCollectionAutomatically":false
    "numIndexesBefore":1,
    "numIndexesBefore":1,
    "numIndexesAfter":2,
    "ok":1