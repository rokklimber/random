package com.example.osgi.paxexam.it;

import static com.example.osgi.paxexam.it.PaxExamProvisioningSupport.blueprintBundles;
import static com.example.osgi.paxexam.it.PaxExamProvisioningSupport.gogoBundles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import com.example.osgi.blueprint.api.IDateService;
import com.example.osgi.blueprint.api.IReverserService;

/**
 * pax-exam integration test for blueprint service bundle
 * 
 * @author mike
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class PaxExamBlueprintIntTst {
    
    @Inject
    IReverserService reverser;
    
    @Inject
    IDateService date;

    /**
     * integration test configuration that starts a felix container with the
     * required bundles for blueprint to run, and installs bundle under test
     * from local maven repo, requires <code>mvn install</code>
     * 
     * @return correct options for blueprint framework setup
     */
    @Configuration
    public Option[] configureTest() {

        return CoreOptions.options(
            CoreOptions.cleanCaches(),
            gogoBundles(),
            blueprintBundles(true, false),
            CoreOptions.junitBundles(),
        	mavenBundle("com.example.osgi.blueprint","bp-service","1.0.0.b"));
    }
    
    /**
     * used to test the hello service from the service bundle
     */
    @Test
    public void myHelloTest()
    {
    	assertTrue(reverser.isInitialized() && !reverser.isDestroyed());
    	String revResp = reverser.reverse("hello");
    	System.out.println("hello from reverser: " + revResp);
    	assertEquals("olleh",revResp);
    }
    
    /**
     * used to test the date service from the service bundle
     */
    @Test
    public void myDateTest()
    {
    	assertTrue(date.isInitialized() && !date.isDestroyed());
    	String dateResp = date.getDate();
    	System.out.println("current date from date service: " + dateResp);
    	assertNotNull(dateResp);
    }
}

