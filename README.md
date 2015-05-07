# Project TWM

TWM is a simple set of web services to find cheapest route on road meshes. These meshes can be provided by users using a REST interface.

## System Architecture

TWM is meant to be robust, highly available and scalable from its start. To meet these requirements a set of good tools must be chosen. Graphs are the tool of choice for path finding and [Tinkerpop Blueprints](https://github.com/tinkerpop/blueprints/wiki) provides a specifications of a rich framework for graph operations.

[Titan](http://thinkaurelius.github.io/titan/) is an ACID distributed graph database that can operate over many storage backends(such as Cassandra, HBase and BerkeleyDB), many indexing backends(such as Solr, Lucene and ElasticSearch) and integrates with Hadoop for offline analytics and with the Tinkerpop Stack. All those features turn Titan into the best graph database to achieve robustness, availability and scalability and the storage layer of TWM.

TWM itself is a web application that can be installed concurrently on as much servers as needed.

## How to Run
To run TWM you must first install Titan Graph DB, after that you can use maven to start the project with the following versions:

* java 7
* maven 3

###Installing Titan DB
Although storage and indexing backends could be installed on its own clusters, and it would be good for a production scenario, Titan DB provides a [localhost working package](http://s3.thinkaurelius.com/downloads/titan/titan-0.5.4-hadoop2.zip) with pre configured Cassandra and ElasticSearch that is enough to prove TWM's concept.

After expanding the zip file you can start Titan DB with the titan script as follows

```
lucas@lucas-VirtualBox ~ $ ~/titan-0.5.4-hadoop2/bin/titan.sh start
Forking Cassandra...
Running `nodetool statusthrift`. OK (returned exit status 0 and printed string "running").
Forking Elasticsearch...
Connecting to Elasticsearch (127.0.0.1:9300). OK (connected to 127.0.0.1:9300).
Forking Titan + Rexster...
Connecting to Titan + Rexster (127.0.0.1:8184).............. OK (connected to 127.0.0.1:8184).
Run rexster-console.sh to connect.
```
It will start also Rexter Graph Server Console on http://localhost:8182/

###Starting TWM
After pulling the project source code you can compile and run it as follows:

```
lucas@lucas-VirtualBox ~/git/twm/twm $ mvn clean install
...
lucas@lucas-VirtualBox ~/git/twm/twm $ cd twm-web/
lucas@lucas-VirtualBox ~/git/twm/twm/twm-web $ mvn jetty:run
```
It will start the webapp and log to /tmp/twm-web.log

## How to use TWM
TWM usage cycle has two main steps, uploading data and querying paths.

###Uploading data

You can POST specially formatted data to TWM with curl as follows:

```
curl -i -XPOST  -H"Content-Type:text/plain" --data-binary @mesh_data http://localhost:8080/twm/mesh/test_mesh/
```
On this example the mesh data is stored in the file **./mesh_data** and a road mesh called **test_mesh** as the last portion of URI states. The data file must be composed of individual lines describing a transition between vertices and its cost each. A sample file is show below

```
A B 10
B D 15
A C 20
C D 30
B E 50
D E 30
```

###Querying Paths

## Remarks
### Index limitations
### Linux paths and TWM hosts
### Memory Footprint
### Why not [Neo4j](http://neo4j.com/)
