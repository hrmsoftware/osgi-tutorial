package se.hrmsoftware.demo.internal.tabs;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.Date;

public class ServerTime implements ComponentFactory {

	@Override
	public String getLabel() {
		return "Server Time";
	}

	@Override
	public Component create() {
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
