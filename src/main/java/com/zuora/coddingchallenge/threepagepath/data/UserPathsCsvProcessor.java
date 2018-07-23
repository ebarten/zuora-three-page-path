package com.zuora.coddingchallenge.threepagepath.data;

import java.util.HashMap;
import java.util.Map;

import static com.zuora.coddingchallenge.threepagepath.Constants.PAGE_PATH_SEPARATOR;

public class UserPathsCsvProcessor implements IDataProcessor {

    private HashMap<String, String> rawUserPaths = new HashMap<>();

    @Override
    public void process(String dataLine) {
        addPageToUserPath(dataLine);
    }

    private void addPageToUserPath(String dataLine) {
        String[] lineData = dataLine.split(",");
        String user = lineData[0].trim();
        String page = lineData[1].trim();
        String userPath = addPageToPath(rawUserPaths, user, page);

        rawUserPaths.put(user, userPath);
    }

    private String addPageToPath(Map<String, String> rawUserPaths, String user, String page) {
        String userPath = rawUserPaths.get(user);

        return (userPath == null) ?
                page :
                userPath + PAGE_PATH_SEPARATOR + page;
    }

    @Override
    public Map<String, String> getProcessedData() {
        return rawUserPaths;
    }
}
