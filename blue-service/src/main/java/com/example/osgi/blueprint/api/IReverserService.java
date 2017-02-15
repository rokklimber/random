package com.example.osgi.blueprint.api;

import java.io.Serializable;

/**
 * interface describing the reverser service contract, this service will return any string
 * that is presented to this service backwards
 * 
 * @author mike
 *
 */
public interface IReverserService extends Serializable, IBeanStatus
{
	/**
	 * service method that returns input string backwards to client
	 * @param input string containing input text
	 * @return reversed input string
	 */
	String reverse(String input);
}
