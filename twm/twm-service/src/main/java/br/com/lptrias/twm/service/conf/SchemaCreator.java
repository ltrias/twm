package br.com.lptrias.twm.service.conf;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.ConsistencyModifier;
import com.thinkaurelius.titan.core.schema.TitanGraphIndex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

@Component
public class SchemaCreator implements InitializingBean{
	private static final Logger LOGGER = Logger.getLogger(SchemaCreator.class);
	
	@Autowired
	@Qualifier("graph")
	Graph graph;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.debug("Verifying DB schema existence");
		
		TitanGraph tg = (TitanGraph) graph;
		TitanManagement m = tg.getManagementSystem();
		
		PropertyKey name = null;
		if( !m.containsPropertyKey(LOCATION_NAME) ){
			LOGGER.debug("Creating property key " + LOCATION_NAME);
			name = m.makePropertyKey(LOCATION_NAME).dataType(String.class).make();
			TitanGraphIndex namei = m.buildIndex(LOCATION_NAME,Vertex.class).addKey(name).unique().buildCompositeIndex();
			m.setConsistency(namei, ConsistencyModifier.LOCK);
		}
		
//		if( !m.containsPropertyKey(TRANSITION_COST)) {
//			PropertyKey distance = m.makePropertyKey(TRANSITION_COST).dataType(Integer.class).make();
//			TitanGraphIndex distancei = m.buildIndex(TRANSITION_COST, Edge.class).addKey(distance).buildMixedIndex("search");
//		}
		
		m.commit();	
		LOGGER.debug("done");
	}
	
	
	
}
