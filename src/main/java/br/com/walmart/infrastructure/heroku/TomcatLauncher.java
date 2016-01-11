package br.com.walmart.infrastructure.heroku;


import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class TomcatLauncher {
	private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";

	public static void main(String args[]) throws ServletException, LifecycleException {
		Tomcat tomcat = new Tomcat();

		tomcat.setPort(Integer.valueOf(getWebPort()));
		Context context = tomcat.addWebapp("/", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
		context.setAllowCasualMultipartParsing(true);

		tomcat.start();
		tomcat.getServer().await();
	}

	private static String getWebPort() {
		return "8080";
	}
}
