package se.hrmsoftware.demo.internal.tabs;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

@org.osgi.service.component.annotations.Component
public class DemoComponentFactory implements ComponentFactory {
	@Override
	public String getLabel() {
		return "Demo";
	}

	@Override
	public Component create() {
		return new Label("Foo Demo");
	}
}
