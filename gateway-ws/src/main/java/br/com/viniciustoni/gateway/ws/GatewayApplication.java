package br.com.viniciustoni.gateway.ws;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.servlet.GuiceFilter;

import br.com.viniciustoni.gateway.ws.config.servlet.GuiceServletConfigListener;

public class GatewayApplication {

	public static void main(String[] args) throws Exception {

		// ---------------------------
		// Recupera os argumentos da linha de comando
		// ---------------------------
		final Options options = new Options();
		options.addOption("port", true, "service port");

		final CommandLineParser parser = new DefaultParser();
		final CommandLine cmd = parser.parse(options, args);

		// ---------------------------
		// Valida os argumentos
		// ---------------------------
		checkArgument(cmd.hasOption("port") && NumberUtils.isNumber(cmd.getOptionValue("port")), "Invalid port");

		// ---------------------------
		// Configura o servidor Jetty
		// ---------------------------
		
        final Server jettyServer = new Server(Integer.parseInt(cmd.getOptionValue("port")));
        
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.<DispatcherType>of(DispatcherType.REQUEST, DispatcherType.ASYNC));
        
        // Listeners
        context.addEventListener(new GuiceServletConfigListener());
        
        // Servlets
        context.addServlet(DefaultServlet.class, "/*");
        
        jettyServer.setHandler(context);
		
		// ---------------------------
		// Inicia o servidor
		// ---------------------------
		try {
			jettyServer.start();
			
			// ---------------------------
			// Inicia as filas
			// ---------------------------
			
			jettyServer.join();

		} finally {
			jettyServer.destroy();
		}

	}
}
