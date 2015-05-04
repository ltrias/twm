package br.com.lptrias.twm.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/twm/{meshName}")
public class MeshController {
	
	@RequestMapping(value="/", method=RequestMethod.POST, consumes="text/plain")
	public void insert(@PathVariable String meshName, @RequestBody String mesh, HttpServletResponse response){
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT, consumes="text/plain")
	public void update(@PathVariable String meshName, @RequestBody String mesh, HttpServletResponse response){
	}
}