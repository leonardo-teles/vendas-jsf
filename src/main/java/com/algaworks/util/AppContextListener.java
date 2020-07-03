package com.algaworks.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
