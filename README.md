# Project TWM

TWM is a simple set of web services to find cheapest route on road meshes. These meshes can be provided by users using a REST interface.

## System Architecture

TWM is meant to be robust, highly available and scalable from its start. To meet these requirements a set of good tools must be chosen. Graphs are the tool of choice for path finding and [Tinkerpop Blueprints](https://github.com/tinkerpop/blueprints/wiki) provides a specifications of a rich framework for graph opeartions.

[Titan](http://thinkaurelius.github.io/titan/) is a distributed graph database that can operate over many storage backends(such as Cassandra, HBase and BerkeleyDB), many indexing backends(such as Solr, Lucene and ElasticSearch) and integrates with Hadoop for offline analytics and with the Tinkerpop Stack. All those features turn Titan into the best graph database to achieve robustness, availability and scalability and the storage layer of TWM.

## How to Run
To run TWM you must first install Titan Graph DB, after that you can use maven to start the project with the following versions

* java 7
* maven 3

###Installing Titan DB
Although storage and indexing backends could be installed on its own clusters, and it would be good for a production scenario, Titan DB provides a [localhost working package](http://s3.thinkaurelius.com/downloads/titan/titan-0.5.4-hadoop2.zip) with pre configured Cassandra and ElasticSearch that is enough to prove TWM's concept.

Aftes expanding the zip file you can start Titan DB with the titan script as follows

```

```


###althoughSoftware Requirements
* Java 1.7
* Maven 3
* Titan DB 0.5.4 (Altough most of the system will work with any Blueprints enabled graph database)
## Download Titan DB
## How to use
## Remarks
### Index limitations
### Linux paths and TWM hosts
### Memory Footprint
### Why not [Neo4j](http://neo4j.com/)
