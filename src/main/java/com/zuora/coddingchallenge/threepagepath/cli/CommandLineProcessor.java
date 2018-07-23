package com.zuora.coddingchallenge.threepagepath.cli;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.Optional;

/**
 * The class processed the program's command line arguments.
 */
public class CommandLineProcessor {

    private int requestedTopUsage = 0;
    private boolean helpRequested = false;
    private Optional<File> dataFile = Optional.empty();
    private CommandLine commandLine;

    private static class OptionNames {
        static String HELP = "help";
        static String TOP = "top";
    }

    /**
     * The constructor of this class.
     *
     * @param args The program arguments.
     * @throws ParseException In case of a failure to process the program arguments and flags.
     */
    public CommandLineProcessor(String[] args) throws ParseException {
        parseCommandLine(args);

        processTopUsageOption();

        processProgramArguments();
    }

    /**
     * The method returns the user pages usage data file supplied by the user.
     *
     * @return The data file supplied by the user.
     */
    public Optional<File> getPagesUsageDataFile() {
        return dataFile;
    }

    /**
     * The method returns true if help was requested by the user. false - otherwise.
     *
     * @return true if help was requested by the user. false - otherwise.
     */
    public boolean isHelpRequested() {
        return helpRequested;
    }

    /**
     * The method returns true if top usage was requested by the user.
     *
     * @return true if top usage was requested by the user.
     */
    public boolean isTopUsageRequested() {
        return requestedTopUsage > 0;
    }

    /**
     * The method returns the requested top usage.
     *
     * @return The requested top usage.
     */
    public int getRequestedTopUsage() {
        return requestedTopUsage;
    }

    private void parseCommandLine(String[] args) throws ParseException {
        Options options = buildCliOptions();

        CommandLineParser parser = new DefaultParser();
        commandLine = parser.parse(options, args);
    }

    private Options buildCliOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Present program usage");
        options.addOption("t", "top", true, "Supply the number of top most used paths you are interested in");
        return options;
    }

    private void processTopUsageOption() {
        String topUsageOption = commandLine.getOptionValue(OptionNames.TOP);
        if (topUsageOption != null) {
            requestedTopUsage = Integer.parseInt(topUsageOption);
        }
    }

    private void processProgramArguments() throws ParseException {
        if (commandLine.hasOption(OptionNames.HELP)) {
            helpRequested = true;
            return;
        }

        processSuppliedPagesUsageFile();
    }

    /**
     * The method processed the user supplied pages usage data file if exists and stores it internally if found to be valid.
     *
     * @throws ParseException In case of a failure to find the specified data file.
     */
    private void processSuppliedPagesUsageFile() throws ParseException {

        String[] args = commandLine.getArgs();
        if (args.length > 0) {
            File dataFile = new File(args[0]);
            if (!dataFile.exists()) {
                throw new ParseException("Invalid data file path!");
            }

            this.dataFile = Optional.of(dataFile);
        }
    }

}
