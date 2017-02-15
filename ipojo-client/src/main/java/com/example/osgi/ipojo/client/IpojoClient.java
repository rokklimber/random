package com.example.osgi.ipojo.client;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import com.example.osgi.blueprint.api.IDateService;
import com.example.osgi.blueprint.api.IReverserService;
import com.example.osgi.ipojo.api.IIpojoClient;

/**
 * client component that pulls blueprint services from service bundle and exposes
 * client to testing users as client interface
 * 
 * @author mike
 *
 */
@SuppressWarnings("serial")
@Component
@Instantiate
@Provides
public class IpojoClient implements IIpojoClient
{
	/**
	 * injects date service from service bundle using ipojo
	 */
	@Requires
	private IDateService dateSrv;
	
	/**
	 * injects reverser service from service bundle using ipojo
	 */
	@Requires
	private IReverserService revSrv;
	
	/**
	 * lifecycle capture using ipojo to show activation to client interface users
	 */
	@Validate
	public void start()
	{
		System.out.println("====== Initializing iPOJOClient =========");
		String dtSrvStatus =
				( dateSrv != null && dateSrv.isInitialized() && !dateSrv.isDestroyed()) ? ("AVAILABLE") : ("UNAVAILABLE");
				
		String rvSrvStatus =
				( revSrv != null && revSrv.isInitialized() && !revSrv.isDestroyed()) ? ("AVAILABLE") : ("UNAVAILABLE");
		
		System.out.println("date service ref: " + dateSrv + " is: " + dtSrvStatus);
		System.out.println("revr service ref: " + revSrv + " is: " + rvSrvStatus);
	}

	/**
	 * lifecycle capture using ipojo to show deactivation to interface users
	 */
	@Invalidate
	public void stop()
	{
		System.out.println("====== Destroying iPOJOClient ===========");
	}
	
	/**
	 * proxy method for client service bundle date service
	 */
	public String getDate() {
		return dateSrv.getDate();
	}

	/**
	 * proxy method for client service bundle reverse service
	 */
	public String reverse(String input)
	{
		return revSrv.reverse(input);
	}
	
	/**
	 * checks service initialization status using lifecycle capture
	 * 
	 * @return bean initialization status
	 */
	public boolean isInitialized()
	{
		if(revSrv!= null && !revSrv.isDestroyed() && revSrv.isInitialized() &&
				dateSrv != null && !dateSrv.isDestroyed() && dateSrv.isInitialized())
			return true;
		return false;
	}

	/**
	 * checks service destruction status using lifecycle capture
	 * 
	 * @return bean destruction status
	 */
	public boolean isDestroyed()
	{
		if(revSrv!= null && revSrv.isDestroyed() && dateSrv != null && dateSrv.isDestroyed())
			return true;
		return false;
	}


}
