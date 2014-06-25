package se.hrmsoftware.demo.internal.tabs;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages a set of components.
 */
public class ComponentManager {

	private final List<ComponentFactory> componentFactories = new CopyOnWriteArrayList<ComponentFactory>();
	private final List<Listener> listeners = new LinkedList<Listener>();

	public Collection<ComponentFactory> getComponentFactories() {
		return componentFactories;
	}

	public void addComponentFactory(ComponentFactory componentFactory) {
		componentFactories.add(componentFactory);
		notifyListeners();
	}

	public void removeComponentFactory(ComponentFactory componentFactory) {
		componentFactories.remove(componentFactory);
		notifyListeners();
	}

	private synchronized void notifyListeners() {
		for (Listener listener : listeners) {
			listener.onUpdate(getComponentFactories());
		}
	}

	// Listeners management
	public synchronized void addListener(Listener listener) {
		listeners.add(listener);
	}

	public synchronized void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	public interface Listener {
		void onUpdate(Collection<ComponentFactory> factories);
	}


}
