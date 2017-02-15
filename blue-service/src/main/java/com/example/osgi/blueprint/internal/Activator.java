package com.example.osgi.blueprint.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * generic activation class, this bundle did not require any special activation,
 * so all of the methods are do nothing print statements
 * 
 * @author mike
 *
 */
public class Activator implements BundleActivator{

	/**
	 * do nothing bundle start method
	 * @see org.osgi.framework.BundleActivator#start(BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		System.out.println("Blueprint Service Bundle Started");
	}

	/**
	 * do nothing bundle stop method
	 * @see org.osgi.framework.BundleActivator#stop(BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		System.out.println("Blueprint Service Bundle Stopped");
	}

}
