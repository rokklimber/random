package com.example.osgi.paxexam.it;

import static com.example.osgi.paxexam.it.PaxExamProvisioningSupport.blueprintBundles;
import static com.example.osgi.paxexam.it.PaxExamProvisioningSupport.ipojoBundles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import com.example.osgi.ipojo.api.IIpojoClient;

/**
 * pax-exam integration test for client ipojo bundle
 * 
 * @author mike
 *
 */

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class PaxExamIpojoTst {

    @Inject
    private IIpojoClient ipClient;

    /**
     * integration test configuration that starts a felix container with the
     * required bundles for blueprint and ipojo to run, and installs bundles under test
     * from local maven repo, requires <code>mvn install</code> first to make bundles
     * available in local repo
     * 
     * @return correct options for blueprint framework setup
     */
    @Configuration
    public Option[] configureTest() throws IOException {

        return CoreOptions.options(
            CoreOptions.cleanCaches(),
            blueprintBundles(true, false),
            CoreOptions.junitBundles(),
            ipojoBundles(),
        	mavenBundle("com.example.osgi.blueprint","bp-service","1.0.0.b"),
        	mavenBundle("com.example.osgi.ipojo","ipojo-client","1.0.0.b"));
    }
    
    /**
     * used to test the hello service from the client interface
     */
    @Test
    public void myHelloTest() throws InterruptedException
    {
    	String revResp = ipClient.reverse("hello");
    	System.out.println("hello from reverser: " + revResp);
    	assertEquals("olleh",revResp);
    }
    
    /**
     * used to test the date service from the client interface
     */
    @Test
    public void myDateTest() throws InterruptedException
    {
    	String dateResp = ipClient.getDate();
    	System.out.println("current date from date service: " + dateResp);
    	assertNotNull(dateResp);
    }
    
    /**
     * run test and print results
     * 
     * @param strings
     */
    public static void main(String ... strings)
	{
    	System.out.println("================ JUNIT TESTS STARTED AT: " + new Date() + " =====================") ;
		Result result = org.junit.runner.JUnitCore.runClasses(PaxExamIpojoTst.class);
		String resultData = "Failures: " + result.getFailureCount() + " Ignored: " +
		  result.getIgnoreCount() + " Successes: " + (result.getRunCount() - result.getFailureCount()) + " Out Of (Total): " + result.getRunCount();
		System.out.println("\nJUNIT RESULTS: (Execution Time: " + ((double)result.getRunTime())/1000.0 + " seconds)");
		System.out.println(resultData);
		System.out.println("=============== JUNIT TESTS FINISHED AT: " + new Date() + " =====================") ;
	}
}

