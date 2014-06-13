package se.hrmsoftware.demo.internal;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;

@Component(immediate = true, service = { Servlet.class }, property = { "alias=/demo", "servlet-name=demo-app" })
@VaadinServletConfiguration(productionMode = false, ui = App.class)
public class VaadinBootstrap extends VaadinServlet {

	private final static Logger LOG = LoggerFactory.getLogger(VaadinBootstrap.class);

	@Activate
	void onActivate(BundleContext ctx) {
		LOG.info("Bootstrapping Vaadin");
	}
}

