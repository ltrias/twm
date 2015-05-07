package br.com.lptrias.twm.web.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.service.RoadMeshService;
import br.com.lptrias.twm.service.exception.GraphModificationException;


@RestController
@RequestMapping("/twm/mesh/{meshName}")
public class MeshController {
	private static final Logger LOGGER = Logger.getLogger(MeshController.class);
	
	@Autowired
	private RoadMeshService roadMeshService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, consumes="text/plain")
	public void insert(@PathVariable String meshName, @RequestBody String meshData, HttpServletResponse response){
		handleRequest(meshName, meshData, response, RequestMethod.POST);
	}

	@RequestMapping(value="/", method=RequestMethod.PUT, consumes="text/plain")
	public void update(@PathVariable String meshName, @RequestBody String meshData, HttpServletResponse response){
		handleRequest(meshName, meshData, response, RequestMethod.PUT);
	}
	
	private void handleRequest(String meshName, String meshData, HttpServletResponse response, RequestMethod method) {
		LOGGER.info("Receiving mesh " + meshName);
		LOGGER.debug("Data: " + meshData);
		RoadMesh mesh = new RoadMesh(meshName);
		
		try{
			addDataTo(mesh, meshData);
		}catch(IllegalArgumentException e){
			try {
				LOGGER.debug("Bad request received: " + e.getMessage());
				response.sendError(400, e.getMessage());
			} catch (IOException e1) {
				LOGGER.error(e1);
			}
		}
		
		
		try{
			if( RequestMethod.POST == method ){
				roadMeshService.saveMesh(mesh);
			} else if( RequestMethod.PUT == method ){
				roadMeshService.updateMesh(mesh);
			}
		}catch(GraphModificationException e){
			try {
				response.getOutputStream().write(e.getMessage().getBytes());
			} catch (IOException e1) {
				LOGGER.warn("Error writing exception to client");
			}
			response.setStatus(400);
		}
	}

	void addDataTo(RoadMesh mesh, String meshData) {
		String[] lines = meshData.split("\n");
		
		for (String line : lines) {
			addLine(line, mesh);
		}
	}

	private void addLine(String line, RoadMesh mesh) {
		String[] tokens = line.split(" ");
		
		if( tokens.length != 3 ){
			throw new IllegalArgumentException(Arrays.toString(tokens) + " does not match accepted format: origin:String destination:String cost:int\n");
		}
		
		int cost = 0;
		
		try{
			cost = Integer.valueOf(tokens[2]);
		}catch(NumberFormatException e){
			throw new IllegalArgumentException("Cost must be integer on " + Arrays.toString(tokens));
		}
		
		mesh.addEntry(tokens[0], tokens[1], cost);
	}
}
