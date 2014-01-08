package org.dpolivaev.dsl.tsgen.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class StrategyDslPlugin extends Plugin {
	public static final String PLUGIN_ID= "org.dpolivaev.dsl.tsgen";

	private static StrategyDslPlugin plugin;
	private BundleContext bundleContext;

	public StrategyDslPlugin() {
		super();
		plugin = this;
	}

	public static StrategyDslPlugin getDefault() {
		return plugin;
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		bundleContext= context;
	}

	/**
	 * @see Plugin#stop(BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		bundleContext= null;
	}
	public Object getService(String serviceName) {
		ServiceReference<?> reference= bundleContext.getServiceReference(serviceName);
		if (reference == null)
			return null;
		return bundleContext.getService(reference);
	}
	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, getPluginId(), IStatus.ERROR, "Error", e)); //$NON-NLS-1$
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
	public static String getPluginId() {
		return PLUGIN_ID;
	}
}
