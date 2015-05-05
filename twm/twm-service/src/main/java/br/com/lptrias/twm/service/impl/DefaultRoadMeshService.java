package br.com.lptrias.twm.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.service.RoadMeshService;

/**
 * @author ltrias
 *
 */
@Service
public class DefaultRoadMeshService implements RoadMeshService {
	
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void saveMesh(RoadMesh mesh) {
		
		LOGGER.debug("saveMesh called with: " + mesh);
	}

	@Override
	public void updateMesh(RoadMesh mesh) {
		LOGGER.debug("updateMesh called with: " + mesh);
	}

}
