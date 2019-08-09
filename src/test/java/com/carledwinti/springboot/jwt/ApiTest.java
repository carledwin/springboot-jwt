package com.carledwinti.springboot.jwt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Api.
 */
public class ApiTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ApiTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ApiTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApi()
    {
        assertTrue( true );
    }
}
