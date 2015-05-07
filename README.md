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

To update the cost of transitions you can issue the same curl as above, but using PUT instead of POST. Only costs can be updated so far as a vertex may be part of more than onde road mesh.

###Querying Paths

To query for the cheapest path you must GET the system as follows:
```
lucas@lucas-VirtualBox ~ $ curl -i "http://localhost:8080/twm/route/test_mesh/?o=A&d=D&fe=10&fc=2.5"
```

This will ask for the cheapest route between **D** and **A** on the road mesh **test_mesh** and its total cost. The parameters mean:

* o: name of the origin
* d: name of the destination
* fe: fuel efficiency (in km/l)
* fc: fuel cost (in money/l)

The service will return an unformatted JSON like 

```
{
   "steps" : [
      "A",
      "B",
      "D"
   ],
   "cost" : 6.25,
   "meshName" : "test_mesh"
}
```

## Remarks
### Index limitations
Titan DB indexes are extemely sensitive and may lead to situations like removing an edge label but still being able to find it. This is not a problem in common TWM usage, but some unexpected behaviors may be found on debugging sessions or after manual changes on the graph. In general Titan DB is not capable of deindexing types so far.

Vertices and Edges may be removed using gremlin language, but the indexes will remain intact so, in case of unexpected behavior, I(and the Titan DB creators) suggest:

1. Stop TWM
2. Stop Titan DB
3. Delete data directory inside Titan DB folder
```
lucas@lucas-VirtualBox /tmp $ rm -rf ~/titan-0.5.4-hadoop2/db*
```
4. Start Titan Db
5. Start TWM
6. Feed TWM again

### Linux paths and TWM hosts
TWM has been developed on a single linux station. On that way all paths are folowwing linux structures(like log on /tmp). In the same way all hosts used inside TWM point to localhost.

No operational system or Titan DB(including indexers and storages) tunning has been made as it depends heavily on machine sizing and network topology. As any default probably the current TWM performance is not the best possible.

### Memory Footprint
As many (capable of being) heavy systems will run on the same machine maybe it's useful to limit application memory with:

```
lucas@lucas-VirtualBox ~/git/twm/twm/twm-web $ export MAVEN_OPTS=-Xmx50M; mvn jetty:run
```
50MB has been enough to run TWM with not so big meshes 


### Why not [Neo4j](http://neo4j.com/)

Neo4j is one of the most used graph dbs and, as Titan DB, can be performatic, transactional and has even a implementation o Dijkstra algorithm that is more tested and probably more performatic than TWM's one. On the other hand some features of Neo4J must be considered in order to choose a graph storage layer. Among others it's important to notice that:

* Neo4J community edition is supposed to run on a single machine, so it's hard to grow
* Some features work only on embbeded graphs, so it's hard to have availability
* Neo4J can be deployed in cluster for HA using master-slave replications, but it is paid
