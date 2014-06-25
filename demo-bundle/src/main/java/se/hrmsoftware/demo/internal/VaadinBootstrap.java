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
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.hrmsoftware.bundles.db.migration.liquibase.MigratedDataSource;
import se.hrmsoftware.demo.internal.tabs.ComponentFactory;
import se.hrmsoftware.demo.internal.tabs.ComponentManager;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;

@Component(immediate = true, service = { Servlet.class }, property = { "alias=/demo", "servlet-name=demo-app" })
public class VaadinBootstrap extends VaadinServlet implements SessionInitListener {

	private final static Logger LOG = LoggerFactory.getLogger(VaadinBootstrap.class);
	private final ComponentManager componentManager = new ComponentManager();
	private final AppUiProvider appUiProvider = new AppUiProvider();
	private DataSource dataSource;

	@Reference(target = "(osgi.jdbc.datasource.id=h2)")
	void setDatasource(MigratedDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.OPTIONAL)
	void addComponentFactory(ComponentFactory componentFactory) {
		componentManager.addComponentFactory(componentFactory);
	}

	void removeComponentFactory(ComponentFactory componentFactory) {
		componentManager.removeComponentFactory(componentFactory);
	}

	@Activate
	void onActivate(BundleContext ctx) {
		LOG.info("Bootstrapping Vaadin");
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		getService().addSessionInitListener(this);
	}

	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException {
		event.getSession().addUIProvider(appUiProvider);
	}

	public class AppUiProvider extends UIProvider {
		@Override
		public UI createInstance(UICreateEvent event) {
			return new App(dataSource, componentManager);
		}

		@Override
		public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
			return App.class;
		}
	}
}

