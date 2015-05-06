package br.com.lptrias.twm.service;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.service.exception.GraphModificationException;

/**
 * Operations with {@link RoadMesh} instances
 * 
 * @author ltrias
 *
 */
public interface RoadMeshService {
	
	/**
	 * Stores a new RoadMesh according to its attributes to prevent data duplication such as same city in two different road meshes
	 * 
	 * @param mesh
	 * @throws GraphModificationException 
	 */
	void saveMesh(RoadMesh mesh) throws GraphModificationException;
	
	/**
	 * Updates an existing RoadMesh
	 * 
	 * @param mesh
	 * @throws GraphModificationException 
	 */
	void updateMesh(RoadMesh mesh) throws GraphModificationException;
}
