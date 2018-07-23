package com.zuora.coddingchallenge.threepagepath.core;

public class PathUsage {
    private String path;
    private int usage = 1; // If the path exists - then it's used at least once

    PathUsage(String path) {
        this.path = path;
    }

    PathUsage(String path, int usage) {
        this.path = path;
        this.usage = usage;
    }

    public String getPath() {
        return path;
    }

    public int getUsage() {
        return usage;
    }

    void incrementUsage() {
        usage++;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        if (!(obj instanceof PathUsage)) {
            return false;
        }
        PathUsage otherPath = (PathUsage) obj;
        return (path.equals(otherPath.path) &&
                usage == otherPath.usage);
    }
}
