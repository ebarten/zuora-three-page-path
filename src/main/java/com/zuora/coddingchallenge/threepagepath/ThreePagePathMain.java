package com.zuora.coddingchallenge.threepagepath;

import com.zuora.coddingchallenge.threepagepath.cli.CommandLineProcessor;

public class ThreePagePathMain {

    public static void main(String[] args) {

        try {
            CommandLineProcessor commandLineProcessor = new CommandLineProcessor(args);

            // Check if usage help was requested.
            boolean helpRequested = commandLineProcessor.isHelpRequested();
            if (helpRequested) {
                displayUsage();
                System.exit(0);
            }

            // Translate the phone numbers given by the input.
            ThreePagePathProgram program = new ThreePagePathProgram();
            program.generatePathStatistics(commandLineProcessor);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * The method displays the command line expected/optional parameters to the user into the STDOUT.
     */
    private static void displayUsage() {
        System.out.println("Usage: {(O) -t [Number of top used paths to show. If not specified will show all paths]}" + System.lineSeparator() +
                "       {(O) [Data files list. If not specified will ask the user to type the data in]}");
    }


}
