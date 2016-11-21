package br.com.viniciustoni.gateway.ws.config.servlet;

import java.util.HashMap;

import javax.inject.Singleton;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.eclipse.jetty.servlet.DefaultServlet;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Configuração dos Servlets para o projeto de Ws.
 * 
 * @author Vinicius Antonio Gai
 *
 */
public class GatewayServletModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {

		// server
		bind(DefaultServlet.class).in(Singleton.class);

		HashMap<String, String> options = new HashMap<>();
		options.put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE.toString());
		serve("/*").with(GuiceContainer.class, options);

		// Jackson
		bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
		bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
		
		// Servlets
		final ResourceConfig resourceConfig = new PackagesResourceConfig("br.com.viniciustoni.gateway.ws");
		for (final Class<?> resource : resourceConfig.getClasses()) {
			bind(resource);
		}

	}

}
