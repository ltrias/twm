package br.com.lptrias.twm.service;

import br.com.lptrias.twm.model.Route;

/**
 * 
 * Responsible for calculating routes over stored road meshes
 * 
 * @author ltrias
 *
 */
public interface RouteService {
	Route findCheapestRoute(String origin, String destination, String meshName);
}
