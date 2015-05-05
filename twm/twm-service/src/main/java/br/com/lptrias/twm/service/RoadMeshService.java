package br.com.lptrias.twm.service;

import br.com.lptrias.twm.model.RoadMesh;

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
	 */
	void saveMesh(RoadMesh mesh);
	
	/**
	 * Updates an existing RoadMesh
	 * 
	 * @param mesh
	 */
	void updateMesh(RoadMesh mesh);
}
