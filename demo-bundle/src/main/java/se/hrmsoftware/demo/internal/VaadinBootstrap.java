package se.hrmsoftware.demo.internal;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;

@Component(immediate = true, service = { Servlet.class }, property = { "alias=/demo", "servlet-name=demo-app" })
public class VaadinBootstrap extends VaadinServlet {

	private final static Logger LOG = LoggerFactory.getLogger(VaadinBootstrap.class);
	private DataSource dataSource;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		getService().addSessionInitListener(new SessionInitListener() {
			@Override
			public void sessionInit(SessionInitEvent event) throws ServiceException {
				event.getSession().addUIProvider(new UIProvider() {
					@Override
					public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
						return App.class;
					}

					@Override
					public UI createInstance(UICreateEvent event) {
						return new App(dataSource);
					}
				});
			}
		});
	}


	@Reference(target = "(osgi.jdbc.datasource.id=h2)")
	void setDatasource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Activate
	void onActivate(BundleContext ctx) {
		LOG.info("Bootstrapping Vaadin");
	}
}

