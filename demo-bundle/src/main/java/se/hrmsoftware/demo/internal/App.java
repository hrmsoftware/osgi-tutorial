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

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

/**
 * The vaadin application.
 */
@Theme(Reindeer.THEME_NAME)
public class App extends UI {
	private final DataSource dataSource;

	public App(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected void init(VaadinRequest request) {
		// Main UI is a TabSheet.
		TabSheet tabs = new TabSheet();
		tabs.setWidth(50f, Unit.PERCENTAGE);
		tabs.setHeight(100f, Unit.PERCENTAGE);

		// Add the server-time component.
		tabs.addTab(createServerTimeContent(), "Server Time");

		// Add an empty component
		tabs.addTab(createDataAccessContent(), "Vaadin Data Access");

		// Set the panel as the page's content.
		setContent(tabs);
	}

	private Component createDataAccessContent() {
		try {
			SQLContainer sqlContainer = new SQLContainer(new TableQuery("TEST", new J2EEConnectionPool(dataSource)));
			Table table = new Table("TEST Table", sqlContainer);
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
