package com.example.osgi.blueprint.api;

import java.io.Serializable;

/**
 * interface describing the date service contract, this service will return the current
 * date to the client program
 * 
 * @author mike
 *
 */
public interface IDateService extends Serializable,IBeanStatus
{
	/**
	 * returns the current date in string form to the client
	 * @return the current date
	 */
	public String getDate();
}
