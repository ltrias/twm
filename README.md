# Project TWM

TWM is a simple set of web services to find cheapest route on road meshes. These meshes can be provided by users using a REST interface.

## System Architecture

TWM is meant to be robust, highly available and scalable from its start. To meet these requirements a set of good tools must be chosen. Graphs are the tool of choice for path finding and [Tinkerpop Blueprints](https://github.com/tinkerpop/blueprints/wiki) provides a specifications of a rich framework for graph opeartions.

[Titan](http://thinkaurelius.github.io/titan/) is a distributed graph database that can operate over many storage backends(such as Cassandra, HBase and BerkeleyDB), many indexing backends(such as Solr, Lucene and ElasticSearch) and integrates with Hadoop for offline analytics and with the Tinkerpop Stack. All those features turn Titan into the best graph database to achieve robustness, availability and scalability and the storage layer of TWM

## How to Run
### Software Requirements
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
