package com.zuora.coddingchallenge.threepagepath.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.zuora.coddingchallenge.threepagepath.Constants.PAGE_PATH_LIMIT;
import static com.zuora.coddingchallenge.threepagepath.Constants.PAGE_PATH_SEPARATOR;

public class PagePathStatistics {

    private final Map<String, PathUsage> pathUsageMap = new HashMap<>();

    /**
     * The constructor of this class.
     */
    public PagePathStatistics(Map<String, String> pagesUsage) {
        calculatePathStatistics(pagesUsage);
    }

    /**
     * The method returns top n most used paths.
     *
     * @param n The number of top most used paths to return.
     * @return The top n most used paths.
     */
    public List<PathUsage> getTopUsed(int n) {
        return pathUsageMap.values().stream().
                sorted(Comparator.comparingInt(PathUsage::getUsage).reversed()).
                limit(n).
                collect(Collectors.toList());
    }

    /**
     * The method returns a list of path usages.
     *
     * @return The a list of path usages.
     */
    public List<PathUsage> getPathsUsage() {
        return pathUsageMap.values().stream().
                sorted(Comparator.comparingInt(PathUsage::getUsage).reversed()).
                collect(Collectors.toList());
    }

    private void calculatePathStatistics(Map<String, String> userPathUsages) {
        userPathUsages.forEach((user, path) -> {
            calculateSubPathsStatistics(path);
        });
    }

    private void calculateSubPathsStatistics(String path) {
        String[] pages = path.split(PAGE_PATH_SEPARATOR);
        int pagesNum = pages.length;
        if (pagesNum > PAGE_PATH_LIMIT - 1) {
            IntStream.range(0, numberOfValidSubPaths(pagesNum)).
                    forEach(pathStart -> {
                        String validPath = calculateSubPath(pages, pathStart);
                        incrementPathUsage(validPath);
                    });
        }
    }

    private String calculateSubPath(String[] pages, int pathStart) {
        return Stream.of(pages).
                skip(pathStart).
                limit(PAGE_PATH_LIMIT).
                collect(Collectors.joining(PAGE_PATH_SEPARATOR));
    }

    private int numberOfValidSubPaths(int pagesNum) {
        return (pagesNum - PAGE_PATH_LIMIT) + 1;
    }

    private void incrementPathUsage(String path) {
        PathUsage pathUsage = pathUsageMap.get(path);
        if (pathUsage == null) {
            pathUsage = new PathUsage(path);
        } else {
            pathUsage.incrementUsage();
        }
        pathUsageMap.put(path, pathUsage);
    }

}
