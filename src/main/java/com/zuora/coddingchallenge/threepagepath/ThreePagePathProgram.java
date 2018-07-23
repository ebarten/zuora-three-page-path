package com.zuora.coddingchallenge.threepagepath;

import com.zuora.coddingchallenge.threepagepath.cli.CommandLineProcessor;
import com.zuora.coddingchallenge.threepagepath.core.PagePathStatistics;
import com.zuora.coddingchallenge.threepagepath.core.PathUsage;
import com.zuora.coddingchallenge.threepagepath.data.DataInputReader;
import com.zuora.coddingchallenge.threepagepath.data.UserPathsCsvProcessor;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The class is the heart of the program. It manages the flow of the program from input reading to the resultant path usage statistics display.
 * Created by Eran Bartenstein
 */
public class ThreePagePathProgram {

    /**
     * The generates path usage statistics and display then to the user base on the choice he specified in the program command line.
     *
     * @param commandLineProcessor The processor of the program arguments.
     */
    public void generatePathStatistics(CommandLineProcessor commandLineProcessor) {
        try {
            PagePathStatistics pagePathStatistics = calculatePagePathStatistics(commandLineProcessor);

            List<PathUsage> pathUsageList = commandLineProcessor.isTopUsageRequested() ?
                    pagePathStatistics.getTopUsed(commandLineProcessor.getRequestedTopUsage()) :
                    pagePathStatistics.getPathsUsage();

            displayStatistics(pathUsageList);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read user input. If you've supplied a data file, maybe the file path is incorrect.");
        } catch (IOException e) {
            System.out.println("Failed to read supplied data.");
        }
    }

    private PagePathStatistics calculatePagePathStatistics(CommandLineProcessor commandLineProcessor) throws IOException {
        Optional<File> dataFile = commandLineProcessor.getPagesUsageDataFile();
        return dataFile.isPresent() ?
                calculatePathStatisticsFromFile(dataFile.get()) :
                calculatePathStatisticsFromStdin();
    }

    private PagePathStatistics calculatePathStatisticsFromStdin() throws IOException {

        System.out.println("Please input user pages in the following format per line: user, page. To terminate your input type: 'q'/'Q' as a terminating number.");
        try (InputStream userDataInputStream = System.in) {
            return calculatePagePathStatisticsFromInputStream(userDataInputStream);
        }
    }

    private PagePathStatistics calculatePathStatisticsFromFile(File currFile) throws IOException {
        try (InputStream userPathsInputStream = new FileInputStream(currFile)) {
            return calculatePagePathStatisticsFromInputStream(userPathsInputStream);
        }
    }

    private PagePathStatistics calculatePagePathStatisticsFromInputStream(InputStream userPathsInputStream) throws IOException {
        Map<String, String> pageUsages = readUserPageUsage(userPathsInputStream);

        return new PagePathStatistics(pageUsages);
    }

    private Map<String, String> readUserPageUsage(InputStream dataInputStream) throws IOException {
        DataInputReader dataInputReader = new DataInputReader(dataInputStream);
        UserPathsCsvProcessor userPathsCsvProcessor = new UserPathsCsvProcessor();
        dataInputReader.read(userPathsCsvProcessor);

        return userPathsCsvProcessor.getProcessedData();
    }

    private void displayStatistics(List<PathUsage> pathUsageList) {
        pathUsageList.forEach(pathUsage -> {
            System.out.println(pathUsage.getPath() + " : " + pathUsage.getUsage());
        });
    }
}
