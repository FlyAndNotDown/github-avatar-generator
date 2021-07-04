package pers.kindem.gh.avatar;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("o").longOpt("output").hasArg().required().type(String.class).desc("output file").build());
        options.addOption(Option.builder("s").longOpt("seed").hasArg().required().type(String.class).desc("seed string").build());

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("bad command line arguments");
        }
        if (commandLine == null) {
            return;
        }

        Generator generator = new Generator(commandLine.getOptionValue("s"));
        Saver.saveImage(generator.nextAvatar(), commandLine.getOptionValue("o"));
    }
}
