package se.hrmsoftware.demo.internal;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import se.hrmsoftware.demo.internal.tabs.ComponentFactory;
import se.hrmsoftware.demo.internal.tabs.ComponentManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The vaadin application. It builds a UI based on a tabsheet and a set of "component factories" that
 * are supplied dynamically.
 */
@Theme(Reindeer.THEME_NAME)
public class App extends UI implements ComponentManager.Listener {

	private final TabSheet tabs = new TabSheet();
	private final DataSource dataSource;
	private final ComponentManager componentManager;

	public App(DataSource dataSource, ComponentManager componentManager) {
		this.dataSource = dataSource;
		this.componentManager = componentManager;
	}

	@Override
	protected void init(VaadinRequest request) {
		// Main UI is a TabSheet.
		tabs.setWidth(50f, Unit.PERCENTAGE);
		tabs.setHeight(100f, Unit.PERCENTAGE);
		tabs.setImmediate(true);

		// Add the server-time component.
		tabs.addTab(createServerTimeContent(), "Server Time");

		// Add an empty component
		tabs.addTab(createDataAccessContent(), "Vaadin Data Access");

		// Set the panel as the page's content.
		setContent(tabs);

		ComponentFactory labelTab = new ComponentFactory() {
			@Override
			public String getLabel() {
				return "Server Time";
			}

			@Override
			public Component create() {
				return createServerTimeContent();
			}
		};

		ComponentFactory sqlTab = new ComponentFactory() {
			@Override
			public String getLabel() {
				return "Vaadin Data Access";
			}

			@Override
			public Component create() {
				return createDataAccessContent();
			}
		};
		List<ComponentFactory> factories = new ArrayList<ComponentFactory>(Arrays.asList(labelTab, sqlTab));
		factories.addAll(componentManager.getComponentFactories());
		updateTabs(factories);
		componentManager.addListener(this);
	}

	@Override
	public void onUpdate(final Collection<ComponentFactory> factories) {
		if (getSession() != null) {
			access(new Runnable() {
				@Override
				public void run() {
					updateTabs(factories);
					//					push();
				}
			});
		}
	}

	@Override
	public void detach() {
		componentManager.removeListener(this);
		super.detach();
	}

	public void updateTabs(final Collection<ComponentFactory> componentFactories) {
		tabs.removeAllComponents();
		for (ComponentFactory factory : componentFactories) {
			tabs.addTab(factory.create(), factory.getLabel());
		}
	}

	private Component createDataAccessContent() {
		try {
			SQLContainer sqlContainer = new SQLContainer(new TableQuery("COUNTRIES", new J2EEConnectionPool(dataSource)));
			Table table = new Table("COUNTRIES Table", sqlContainer);
			table.setWidth(100f, Unit.PERCENTAGE);
			return table;
		}
		catch (SQLException e) {
			throw new RuntimeException("Wtf", e);
		}
	}

	private Component createServerTimeContent() {

		// A property that holds the server-time.
		final ObjectProperty<String> serverTime = new ObjectProperty<String>(currentTime(), String.class);

		// Label that shows the server time.
		Label serverTimeLabel = new Label(serverTime, ContentMode.TEXT);

		// A button that can be used to refresh the server time.
		Button serverTimeRefreshBtn = new Button("Refresh", new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				serverTime.setValue(currentTime());
			}
		});

		// Add stuff to the panel.
		VerticalLayout layout = new VerticalLayout(serverTimeLabel, serverTimeRefreshBtn);
		layout.setMargin(true);
		layout.setSpacing(true);
		return layout;

	}

	private String currentTime() {
		return "" + new Date();
	}

}
