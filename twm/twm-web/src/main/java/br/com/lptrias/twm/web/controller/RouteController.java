package br.com.lptrias.twm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lptrias.twm.model.Route;
import br.com.lptrias.twm.service.RouteService;

@RestController
@RequestMapping("/twm/route/{meshName}")
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Route findCheapestRoute(@RequestParam(value="o", required=true) String origin, 
								   @RequestParam(value="d", required=true) String destination,
								   @RequestParam(value="fe", required=true) float fuelEficiency,
								   @RequestParam(value="fc", required=true) float fuelCost,
								   @PathVariable String meshName){
		
		Route r = routeService.findCheapestRoute(origin, destination, meshName);
		
		float finalCost = r.getCost() * fuelCost / fuelEficiency;
				
		r.setCost(finalCost);
		
		return r;
	}
}
