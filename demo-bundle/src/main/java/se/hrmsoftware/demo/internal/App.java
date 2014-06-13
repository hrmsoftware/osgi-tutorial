package se.hrmsoftware.demo.internal;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import java.util.Date;

/**
 * The vaadin application.
 */
@Theme(Reindeer.THEME_NAME)
public class App extends UI {
	@Override
	protected void init(VaadinRequest request) {
		// The panel.
		Panel panel = new Panel("Vaadin Example");
		panel.setWidth(40f, Unit.PERCENTAGE);

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
		panel.setContent(layout);

		// Set the panel as the page's content.
		setContent(panel);
	}

	private String currentTime() {
		return "" + new Date();
	}
}
