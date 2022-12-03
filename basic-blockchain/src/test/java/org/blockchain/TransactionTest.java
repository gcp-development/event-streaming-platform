package org.blockchain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TransactionTest extends TestCase {

    public TransactionTest( String testName )
    {
        super( testName );
    }
    public static Test suite()
    {
        return new TestSuite( TransactionTest.class );
    }
    public void testApp()
    {
        assertTrue( true );
    }
}
