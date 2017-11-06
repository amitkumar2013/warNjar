package com.api;

import org.springframework.web.bind.annotation.RestController;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private DataSource dataSource;

	@RequestMapping("/")
	public String index() throws NamingException {
		String msg0 = new InitialContext().lookup("java:comp/env/jdbc/myDataSource").toString();
		logger.debug("Injected DS(" + dataSource.toString() + ") --> " + msg0);
		return "DS from JNDI " + msg0;
	}

}
