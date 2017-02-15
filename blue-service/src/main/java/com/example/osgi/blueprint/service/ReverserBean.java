package com.example.osgi.blueprint.service;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Destroy;
import org.apache.aries.blueprint.annotation.Init;
import org.apache.aries.blueprint.annotation.Service;

import com.example.osgi.blueprint.api.IReverserService;

/**
 * reverser bean that provides implementation of reverser service, service will be auto-injected
 * by the blueprint container at bundle startup
 * 
 * @author mike
 *
 */
@SuppressWarnings("serial")
@Bean(id="reverserBean", activation="eager")
@Service(interfaces={IReverserService.class})
public class ReverserBean extends AbstractBean implements IReverserService
{
	/**
	 * reverser service implementation method
	 * @see com.example.osgi.blueprint.api.IReverserService#reverse(String)
	 */
	public String reverse(String input) {
		StringBuffer buf = new StringBuffer(input);
		return buf.reverse().toString();
	}
	
	/**
	 * called by blueprint container when bean is instantiated, used to trap bean state
	 */
	@Init
    public void init() {
        System.out.println("====== Initializing ReverseBean ========");
        initialized = true;
    }

	/**
	 * called by blueprint container when bean is destroyed, used to trap bean state
	 */
    @Destroy
    public void destroy() {
        System.out.println("======= Destroying ReverseBean =========");
        destroyed = true;
    }
}
