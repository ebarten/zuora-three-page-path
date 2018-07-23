package com.zuora.coddingchallenge.threepagepath.core;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PagePathStatisticsTest {

    @Test
    public void whenSingleUserVisitsMoreThanThreePagesThenPass() {
        Map<String, String> userPaths = new HashMap<>();
        userPaths.put("U1", "/ -> login -> subscribers -> /");
        PagePathStatistics pathStatistics = new PagePathStatistics(userPaths);

        List<PathUsage> actualPathUsageMap = pathStatistics.getPathsUsage();
        List<PathUsage> expectedPathUsageMap = new ArrayList<>();
        expectedPathUsageMap.add(new PathUsage("/ -> login -> subscribers"));
        expectedPathUsageMap.add(new PathUsage("login -> subscribers -> /"));

        assertThat("Unexpected used paths!", actualPathUsageMap, equalTo(expectedPathUsageMap));
    }

    @Test
    public void whenMultiUsersVisitsMoreThanThreePagesThenPathCountPass() {
        Map<String, String> userPaths = new HashMap<>();
        userPaths.put("U1", "/ -> login -> subscribers -> /");
        userPaths.put("U2", "/ -> login -> subscribers");
        PagePathStatistics pathStatistics = new PagePathStatistics(userPaths);

        List<PathUsage> actualPathUsageMap = pathStatistics.getPathsUsage();
        List<PathUsage> expectedPathUsageMap = new ArrayList<>();
        expectedPathUsageMap.add(new PathUsage("/ -> login -> subscribers", 2));
        expectedPathUsageMap.add(new PathUsage("login -> subscribers -> /"));

        assertThat("Unexpected used paths!", actualPathUsageMap, equalTo(expectedPathUsageMap));
    }

    @Test
    public void whenMultiUsersVisitsMoreThanThreePagesThenTopMostPass() {
        Map<String, String> userPaths = new HashMap<>();
        userPaths.put("U1", "/ -> login -> subscribers -> /");
        userPaths.put("U2", "/ -> login -> subscribers");
        PagePathStatistics pathStatistics = new PagePathStatistics(userPaths);

        List<PathUsage> actualTopMostUsed = pathStatistics.getTopUsed(1);
        List<PathUsage> expectedTopMostUsed = Arrays.asList(new PathUsage("/ -> login -> subscribers", 2));

        assertThat("Unexpected top most used paths!", actualTopMostUsed, equalTo(expectedTopMostUsed));
    }

    @Test
    public void whenThreeUsersVisitsMoreThanThreePagesThen3TopMostPass() {
        Map<String, String> userPaths = new HashMap<>();
        userPaths.put("U1", "/ -> login -> subscriber -> /");
        userPaths.put("U2", "/ -> login -> subscriber");
        userPaths.put("U3", "/ -> login -> subscriber");
        userPaths.put("U4", "/ -> login -> product");
        userPaths.put("U5", "/ -> login -> product");
        PagePathStatistics pathStatistics = new PagePathStatistics(userPaths);

        List<PathUsage> actualTopMostUsed = pathStatistics.getTopUsed(2);
        PathUsage path1Usage = new PathUsage("/ -> login -> subscriber", 3);
        PathUsage path2Usage = new PathUsage("/ -> login -> product", 2);
        List<PathUsage> expectedTopMostUsed = Arrays.asList(path1Usage, path2Usage);

        assertThat("Unexpected top most used paths!", actualTopMostUsed, equalTo(expectedTopMostUsed));
    }

}
