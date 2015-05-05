package br.com.lptrias.twm.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.ConsistencyModifier;
import com.thinkaurelius.titan.core.schema.TitanGraphIndex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

@Component
public class SchemaCreator implements InitializingBean{
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	@Qualifier("graph")
	Graph graph;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		LOGGER.debug("Creating DB schema");
		
//		Graph g = TitanFactory.open(ClassLoader.getSystemClassLoader().getResource("titan-cassandra-es.properties").getPath());
//		TitanGraph tg = (TitanGraph) g;
//		TitanManagement m = tg.getManagementSystem();
		
		
		//LOGGER.debug("Graph: " + tg);
//
//		PropertyKey name = null;
//		if( !m.containsPropertyKey("name") ){
//			name = m.makePropertyKey("name").dataType(String.class).make();
//			TitanGraphIndex namei = m.buildIndex("name",Vertex.class).addKey(name).unique().buildCompositeIndex();
//			m.setConsistency(namei, ConsistencyModifier.LOCK);
//		}
//		
//		if( !m.containsVertexLabel(LOCATION) ){
//			m.makeVertexLabel(LOCATION).make();
//		}
//		
//		if( !m.containsPropertyKey("d")) {
//			PropertyKey distance = m.makePropertyKey("d").dataType(Integer.class).make();
//			TitanGraphIndex distancei = m.buildIndex("d", Edge.class).addKey(distance).buildMixedIndex("search");
//		}
//		
//		if( !m.containsEdgeLabel("acessa") ){
//			m.makeEdgeLabel("acessa").directed().signature(m.getPropertyKey("d")).make();
//		}
//		
//		m.commit();
		
	}
	
}
