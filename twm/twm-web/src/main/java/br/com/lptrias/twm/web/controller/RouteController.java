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
								   @PathVariable String meshName){
		
		return routeService.findCheapestRoute(origin, destination, meshName);
	}
}
