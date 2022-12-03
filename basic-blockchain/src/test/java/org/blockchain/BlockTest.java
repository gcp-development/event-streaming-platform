package org.blockchain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class BlockTest extends TestCase {
    public BlockTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(BlockTest.class);
    }

    public void testApp() {
        assertTrue(true);
    }
}
