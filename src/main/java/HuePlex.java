import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.deadlyplatypus.hueplex.HuePlexRunner;

/**
 * 
 */

/**
 * @author Derrick
 * 
 */
public class HuePlex {
  public static final String huePlexProperties = "hueplex.properties";
  public static final int serverPort = 34800;
  public static final String serverLocation = "http://localhost";

  /**
   * @param args
   */
  public static void main(String[] args) {
    try {
      Properties props = getConfiguration(args);

      HuePlexRunner main =
          new HuePlexRunner(props.getProperty("server") + ":" + props.getProperty("port"),
              props.getProperty("user"), props.getProperty("pass"));
      Thread mainThread = new Thread(main, "Main Thread");
      mainThread.run();
      mainThread.join();
      System.exit(0);
    } catch (Exception e) {
      System.exit(1);
    }
  }

  private static Properties getConfiguration(String[] inputs) throws Exception {
    Options options = getOptions();
    if (inputs.length == 0) {
      usage(0);
      return null;
    }

    CommandLineParser cmdParser = new DefaultParser();
    CommandLine cmd;
    try {
      cmd = cmdParser.parse(options, inputs);
      if (cmd.hasOption("h")) {
        usage(0);
        return null;
      }
    } catch (ParseException e) {
      usage(1);
      return null;
    }

    String configLocation = cmd.getOptionValue("config");
    Properties properties = new Properties();
    boolean usingDefaultConfig = false;
    if (configLocation == null || configLocation.isEmpty()) {
      usingDefaultConfig = true;
      configLocation = HuePlex.huePlexProperties;
    }

    InputStream in = null;
    try {
      in = new FileInputStream(configLocation);
      properties.load(in);
    } catch (IOException e) {
      // Only care if the specified file cannot be read
      if (!usingDefaultConfig) {
        System.err.println("Could not open file " + configLocation);
        System.exit(1);
      }
    }

    // Overwrite the config file parameters with the specified values
    if (cmd.hasOption("s")) {
      properties.setProperty("server", cmd.getOptionValue("s"));
    }
    if (cmd.hasOption("p")) {
      properties.setProperty("port", cmd.getOptionValue("p"));
    }
    if (cmd.hasOption("u")) {
      properties.setProperty("user", cmd.getOptionValue("u"));
    }
    if (cmd.hasOption("p")) {
      properties.setProperty("pass", cmd.getOptionValue("p"));
    }

    if (!properties.containsKey("server")) {
      properties.setProperty("server", serverLocation);
    }
    if (!properties.containsKey("port")) {
      properties.setProperty("port", Integer.toString(serverPort));
    }
    if (!properties.containsKey("user")) {
      System.err.println("Missing username.");
      System.exit(1);
    }
    if (!properties.containsKey("pass")) {
      System.err.println("Missing password.");
      System.exit(1);
    }

    return properties;
  }

  private static Options getOptions() {
    Option configuration =
        new Option("c", "config", true, "The location of the configuration file.");
    Option serverLocation =
        new Option("s", "server", true, "The IP Address or DNS name of the server. Defaults to "
            + HuePlex.serverLocation + ".");
    Option port =
        new Option("p", "port", true, "The port that the server uses. Defaults to " + serverPort
            + ".");
    Option username = new Option("u", "user", true, "Authorized username.");
    Option password = new Option("p", "password", true, "Password for user.");
    Option help = new Option("h", "help", false, "Prints a help message.");

    Options options = new Options();
    options.addOption(configuration);
    options.addOption(serverLocation);
    options.addOption(port);
    options.addOption(username);
    options.addOption(password);
    options.addOption(help);

    return options;
  }

  private static void usage(int exitCode) {
    Options options = getOptions();
    HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp("java " + HuePlex.class.getCanonicalName(), options);
    System.exit(exitCode);
  }
}
