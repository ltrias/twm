package br.com.lptrias.twm.service.conf;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.thinkaurelius.titan.core.PropertyKey;
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
		
		TitanGraph tg = (TitanGraph) graph;
		TitanManagement m = tg.getManagementSystem();
		
		PropertyKey name = null;
		if( !m.containsPropertyKey(NAME) ){
			name = m.makePropertyKey(NAME).dataType(String.class).make();
			TitanGraphIndex namei = m.buildIndex(NAME,Vertex.class).addKey(name).unique().buildCompositeIndex();
			m.setConsistency(namei, ConsistencyModifier.LOCK);
		}
		
//		if( !m.containsVertexLabel(LOCATION) ){
//			m.makeVertexLabel(LOCATION).make();
//		}
		
		if( !m.containsPropertyKey(DISTANCE)) {
			PropertyKey distance = m.makePropertyKey(DISTANCE).dataType(Integer.class).make();
			TitanGraphIndex distancei = m.buildIndex(DISTANCE, Edge.class).addKey(distance).buildMixedIndex("search");
		}
		
//		if( !m.containsEdgeLabel("acessa") ){
//			m.makeEdgeLabel("acessa").directed().signature(m.getPropertyKey("d")).make();
//		}
		
		m.commit();	
	}
	
}
