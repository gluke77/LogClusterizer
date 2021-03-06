# LogClusterizer 

Reads log messages from file and groups together similar sentences (sentences where only a single word has
changed) and extracts the changes.

If the input looks like
```
01-01-2012 19:45:00 Naomi is getting into the car
01-01-2012 20:12:39 Naomi is eating at a restaurant
02-01-2012 09:13:15 George is getting into the car
02-01-2012 10:14:00 George is eating a diner
03-01-2012 10:14:00 Naomi is eating a diner
```
...then output looks like:
```
=====
01-01-2012 19:45:00 Naomi is getting into the car
02-01-2012 09:13:15 George is getting into the car
The changing word was: Naomi, George
02-01-2012 10:14:00 George is eating a diner
03-01-2012 10:14:00 Naomi is eating a diner
The changing word was: Naomi, George
=====
```
Usage:
```
java -jar LogClusterizer-<VERSION>.jar <FILENAME>
```
or 
```
java -jar LogClusterizer-<VERSION>.jar <FILENAME> > <OUTPUT_FILE_NAME>
```
