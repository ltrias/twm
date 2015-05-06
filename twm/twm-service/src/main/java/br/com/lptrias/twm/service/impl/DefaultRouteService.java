package br.com.lptrias.twm.service.impl;

import org.springframework.stereotype.Service;

import br.com.lptrias.twm.model.Route;
import br.com.lptrias.twm.service.RouteService;

@Service
public class DefaultRouteService implements RouteService {

	@Override
	public Route findCheapestRoute(String origin, String destination, String meshName) {
		Route r = new Route();
		r.setMeshName(meshName);
		r.getSteps().add(origin);
		r.getSteps().add("intermediary step");
		r.getSteps().add(destination);
		
		return r;
	}

}
