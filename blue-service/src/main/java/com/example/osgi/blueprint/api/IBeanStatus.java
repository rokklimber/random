package com.example.osgi.blueprint.api;

/**
 * Simple initialization interface for service beans that 
 * can be used to query the status of the beans, a bean is ready for use if it has
 * been initialized but not destroyed
 * 
 * @author mike
 *
 */
public interface IBeanStatus
{
	/**
	 * queries the state of the bean to determine if it is has been initialized
	 * @return true if the bean has been initialized
	 */
	public boolean isInitialized();
	
	/**
	 * queries the state of the bean to determine if it is has been destroyed
	 * @return true if the bean has been destroyed
	 */
	public boolean isDestroyed();
}
