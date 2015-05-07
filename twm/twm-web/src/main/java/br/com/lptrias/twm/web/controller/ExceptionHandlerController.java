package br.com.lptrias.twm.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController {
	private static final Logger LOGGER = Logger.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler(Exception.class)
	public void handle(Exception e, HttpServletResponse response){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		LOGGER.error(sw.toString());
		response.setStatus(500);
	}
}
