package se.hrmsoftware.demo.internal.tabs;

import com.vaadin.ui.Component;

/**
 * Interface used to create dynamic components for the UI.
 */
public interface ComponentFactory {
	String getLabel();

	Component create();
}
