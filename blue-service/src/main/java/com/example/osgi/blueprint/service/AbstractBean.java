package com.example.osgi.blueprint.service;

import com.example.osgi.blueprint.api.IBeanStatus;

/**
 * bean lifecycle implementation class, used to provide common behavior for
 * initialization and destruction
 * 
 * @author mike
 *
 */
public abstract class AbstractBean implements IBeanStatus
{
	/**
	 * flag used by subclasses to set initialization status
	 */
	protected boolean initialized = false;
	
	/**
	 * flag used by subclasses to set destruction status
	 */
	protected boolean destroyed = false;
	
	/**
	 * returns initialization state of bean
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * returns destruction state of bean
	 */
	public boolean isDestroyed() {
		return destroyed;
	}
}
