package com.zuora.coddingchallenge.threepagepath.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The class reads input from either STDIN or any other EOF terminated InputStream. In the case of STDIN, it expects
 * the user to terminate his input with a single line containing the 'q' / 'Q' character.
 */
public class DataInputReader {

    /**
     * The field is used to denote whether the input stream is STDIN or not.
     */
    private boolean stdin = false;

    private BufferedReader reader;

    /**
     * The constructor of this class.
     *
     * @param inputStream The input stream to read data from.
     */
    public DataInputReader(InputStream inputStream) {

        if (inputStream == System.in) {
            stdin = true;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);
    }

    /**
     * The method reads the input stream line after another and pushes each line to the data processor.
     *
     * @param dataProcessor The processor to process the read line.
     * @throws IOException In case of a failure to read from the input stream.
     */
    public void read(IDataProcessor dataProcessor) throws IOException {
        String readLine = reader.readLine();
        while (readLine != null) {
            dataProcessor.process(readLine);
            readLine = reader.readLine();
            if (stdin) {
                // Handle the case where by user input is terminated by the letter 'q'
                if (readLine.equalsIgnoreCase("q")) {
                    readLine = null;
                }
            }
        }
    }

}
