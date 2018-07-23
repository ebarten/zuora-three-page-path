package com.zuora.coddingchallenge.threepagepath;

import com.zuora.coddingchallenge.threepagepath.core.PagePathStatisticsTest;
import com.zuora.coddingchallenge.threepagepath.data.UserPathsCsvProcessorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The class uses as a test suite to run all tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PagePathStatisticsTest.class,
        UserPathsCsvProcessorTest.class
})
public class ThreePagePathTestSuite {
}
