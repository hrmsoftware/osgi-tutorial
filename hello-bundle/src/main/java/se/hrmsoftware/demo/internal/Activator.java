package se.hrmsoftware.demo.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A very simple Bundle Activator that will simply log using SLF4J
 * when started and stopped.
 */
public class Activator implements BundleActivator {

	private static final Logger LOG = LoggerFactory.getLogger(Activator.class);

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		LOG.info("Starting bundle: {}", bundleContext.getBundle().getSymbolicName());
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		LOG.info("Stopping bundle: {}", bundleContext.getBundle().getSymbolicName());
	}
}
