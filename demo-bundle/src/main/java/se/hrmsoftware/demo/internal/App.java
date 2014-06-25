package se.hrmsoftware.demo.internal;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;
import se.hrmsoftware.demo.internal.tabs.ComponentFactory;
import se.hrmsoftware.demo.internal.tabs.ComponentManager;
import se.hrmsoftware.demo.internal.tabs.ServerTime;

import java.util.Collection;

/**
 * The vaadin application. It builds a UI based on a tabsheet and a set of "component factories" that
 * are supplied dynamically.
 */
@Theme(Reindeer.THEME_NAME)
public class App extends UI implements ComponentManager.Listener {

	private final TabSheet tabs = new TabSheet();
	private final ComponentManager componentManager;
	private final ComponentFactory FIXED_TAB_FACTORY = new ServerTime();

	public App(ComponentManager componentManager) {
		this.componentManager = componentManager;
	}

	@Override
	protected void init(VaadinRequest request) {
		// Main UI is a TabSheet.
		tabs.setWidth(50f, Unit.PERCENTAGE);
		tabs.setHeight(100f, Unit.PERCENTAGE);
		tabs.setImmediate(true);

		// Set the panel as the page's content.
		setContent(tabs);

		// Add the tabs.
		updateTabs(componentManager.getComponentFactories());
		componentManager.addListener(this);
	}

	@Override
	public void onUpdate(final Collection<ComponentFactory> factories) {
		if (getSession() != null) {
			access(new Runnable() {
				@Override
				public void run() {
					updateTabs(factories);
					Notification.show("Reloading tabs", Notification.Type.TRAY_NOTIFICATION);
					push();
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
		tabs.addTab(FIXED_TAB_FACTORY.create(), FIXED_TAB_FACTORY.getLabel()); // Always add the fixed tab first.
		for (ComponentFactory factory : componentFactories) {
			tabs.addTab(factory.create(), factory.getLabel());
		}
	}

}
