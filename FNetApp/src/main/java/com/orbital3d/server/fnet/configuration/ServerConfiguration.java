package com.orbital3d.server.fnet.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ServerConfiguration {
	@Value("${server.port:8080}")
	private int serverPort;

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		JettyServletWebServerFactory factory = new JettyServletWebServerFactory();

		factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error/403.html"));
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "404.html"));
		factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/505.html"));
		factory.setPort(serverPort);
		return factory;
	}
}
