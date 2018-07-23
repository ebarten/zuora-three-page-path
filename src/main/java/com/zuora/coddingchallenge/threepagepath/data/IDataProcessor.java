package com.zuora.coddingchallenge.threepagepath.data;

import java.util.Map;

/**
 * The interface defines a data processor's behaviour
 */
public interface IDataProcessor {

    /**
     * The method processes the passed data and stores it internally.
     *
     * @param data The data to process.
     */
    void process(String data);

    /**
     * The method returns the processed data.
     *
     * @return The processed data.
     */
    Map<String, String> getProcessedData();
}
