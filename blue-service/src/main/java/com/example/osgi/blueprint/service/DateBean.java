package com.example.osgi.blueprint.service;

import java.util.Date;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Destroy;
import org.apache.aries.blueprint.annotation.Init;
import org.apache.aries.blueprint.annotation.Service;

import com.example.osgi.blueprint.api.IDateService;

/**
 * date bean that provides implementation of data service, service will be auto-injected
 * by the blueprint container at bundle startup
 * 
 * @author mike
 *
 */
@SuppressWarnings("serial")
@Bean(id="dateBean", activation="eager")
@Service(interfaces={IDateService.class})
public class DateBean extends AbstractBean implements IDateService 
{
	/**
	 * date service implementation method
	 * @see com.example.osgi.blueprint.api.IDateService#getDate()
	 */
	public String getDate()
	{
		return (new Date()).toString();
	}
	
	/**
	 * called by blueprint container when bean is instantiated, used to trap bean state
	 */
	@Init
    public void init() {
        System.out.println("====== Initializing DateBean ===========");
        initialized = true;
    }

	/**
	 * called by blueprint container when bean is destroyed, used to trap bean state
	 */
    @Destroy
    public void destroy() {
        System.out.println("======= Destroying DateBean ============");
        destroyed = true;
    }
}
