package com.example.osgi.paxexam.it;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.CoreOptions.*;

import java.util.ArrayList;
import java.util.List;

public class PaxExamProvisioningSupport {

    /**
     * Returns the bundle for the aop interface of the aopalliance needed e.g. by spring-aop.
     * @return bundle for aop domain
     */
    public static Option aopAllianceBundle() {
        return mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.aopalliance", "1.0_6");
    }

    /**
     * Returns the spring bundles used in this project.
     * @return spring 4 bundles
     */
    public static Option springBundles() {
        return CoreOptions.composite(
            // spring dependencies bundles
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-aop", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-beans", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-context", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-context-support", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-core", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-jdbc", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-orm", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-tx", "4.0.7.RELEASE_1"),
            mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.aopalliance","1.0_6"));
    }
    
    /**
     * returns the gogo shell bundles used as a shell for the felix container
     * this can bea really useful for testing,  add a test method that attaches to
     * the command processor and you can type commands while running the debugger
     * 
     * @return 3 gogo bundles
     */
    public static Option gogoBundles()
    {
    	return CoreOptions.composite(
    			mavenBundle("org.apache.felix", "org.apache.felix.gogo.runtime"),
    	        mavenBundle("org.apache.felix", "org.apache.felix.gogo.shell"),
    	        mavenBundle("org.apache.felix", "org.apache.felix.gogo.command")
    	);
    }
    
    /**
     * returns the iPOJO bundles used to implement iPOJO for the felix container
     * 
     * @return 2 iPOJO bundles
     */
    public static Option ipojoBundles()
    {
    	return CoreOptions.composite(
    			mavenBundle("org.apache.felix", "org.apache.felix.ipojo","1.12.1"),
    			mavenBundle("org.apache.felix", "org.apache.felix.ipojo.api","1.12.1")
    	);
    }
    
    /**
     * returns the bundles needed for the blueprint container to function using felix
     * be sure to annotate the bundle header under test with the correct header if your
     * code only has blueprint annotations and does not use an xml file
     * 
     * @param withAnnotationsAPI adds annotation classes if needed
     * @param addQuiesceSupport add quiesce support if desired
     * @return bundles needed to configure felix blueprint container correctly
     */
    public static Option blueprintBundles(boolean withAnnotationsAPI, boolean addQuiesceSupport)
    {
    	List<Option> optionList= new ArrayList<Option>();
    	
    	Option bpBaseBundleOptions[] = new Option[] {
				mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint", "1.1.0"),
				mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core"),
	            mavenBundle("org.apache.aries", "org.apache.aries.util", "1.1.0"),
    	        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.api", "1.0.1"),
    	        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.impl", "1.0.1"),
    	        mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm", "1.0.1")};
    	
    	Option bpAnnotationBundleOptions[] = new Option[] {
    			mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm", "1.0.7"),
    	        mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.annotation.api", "1.0.1"),
    	        mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.annotation.impl", "1.0.1"),
    	        mavenBundle("org.apache.xbean", "xbean-finder", "3.18"),
    	        mavenBundle("org.apache.xbean", "xbean-bundleutils", "3.18"),
    	        mavenBundle("org.ow2.asm", "asm-all", "5.2")};
    	
    	Option bpQuiesceOption = mavenBundle("org.apache.aries.quiesce", "org.apache.aries.quiesce.api", "1.0.0");
    	
    	for(Option o : bpBaseBundleOptions) optionList.add(o);
    	if(addQuiesceSupport) optionList.add(bpQuiesceOption);
    	if(withAnnotationsAPI) for(Option o : bpAnnotationBundleOptions) optionList.add(o);
    	
    	Option bpOptions = CoreOptions.composite(optionList.toArray(new Option[optionList.size()]));
    	
    	// add required slf4j bundles and return option set
    	return CoreOptions.composite(bpOptions, slf4jBundles());
    }
    
    /**
     * add slf4j bundles needed for blueprint container
     * @returns one bundle, and a jar file used as an OSGi fragment
     */
    public static Option slf4jBundles()
    {
    	return CoreOptions.composite(
    	mavenBundle("org.slf4j", "slf4j-api", "1.7.7"),
        // don't start fragment
        mavenBundle("org.slf4j", "slf4j-simple", "1.7.7").noStart());
    }
}