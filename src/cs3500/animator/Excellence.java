package cs3500.animator;

import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.controller.IController;
import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.ViewFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Excellence is a class that contains a main method that is the entry point of this project. The
 * main method takes in inputs as command-line arguments, and attempts to generate a model,
 * controller, and view with those arguments.
 */
public class Excellence {


  /**
   * Takes in command line arguments, parses them, and attempts to play a view with the given values
   * (by generating a model, controller, and view). The given command line arguments
   * <strong>must</strong> provide an input file (via "-in"
   * <i>fileName</i>) and a view type (via "-view" <i>viewType</i>). View types must be one of
   * "text", "svg", or "visual". Additionally, an output file and/or a speed can be specified
   * optionally (via "-out" <i>fileName</i> and "-speed" <i>tickRate</i>, respectively).
   *
   * <p>If an error occurs for whatever reason, a JOptionPane error dialog will popup describing
   * what happened.</p>
   *
   * <p>See {@link Excellence.ArgsProcessor} for more details on what constitutes a valid command
   * line argument, and how they are parsed.</p>
   *
   * @param args is a String[] of the command line arguments to be parsed into a view.
   */
  public static void main(String[] args) {

    if (!ArgsProcessor.processArgs(args)) {
      // If the above call fails, an error occurred, so returns automatically to halt processing
      return;
    }

    if (ArgsProcessor.in == null) {
      Excellence.errorPopup("Error: Input file not provided.");
      return;
    }
    if (ArgsProcessor.view == null) {
      Excellence.errorPopup("Error: View type not provided.");
      return;
    }

    // Builds the model based on the file specified by the command line
    IModel model;
    try {
      AnimationBuilder<IModelImpl> builder = IModelImpl.builder();
      model = AnimationReader.parseFile(ArgsProcessor.in, builder);
    } catch (Exception e) {
      // If an exception is thrown while parseFile is called (for whatever reason), an error
      // popup will occur
      Excellence.errorPopup("Error: Model was unable to be built.\n"
          + "Error message:\n"
          + e.getMessage());
      return;
    }

    // Constructs the view based on the given view type
    IView newView;

    try {
      newView = ViewFactory.makeView(ArgsProcessor.view);
    } catch (Exception e) {
      // Catches any exceptions thrown by any of the view or controller constructors, and prints
      // an appropriate message. Examples of exceptions that might occur are negative tick rates
      // (speed), or null arguments.
      Excellence.errorPopup("Error: View and/or controller were unable to be constructed.\n"
          + "Error message:\n"
          + e.getMessage());
      return;
    }
    IController controller = new ControllerImpl(newView, model,  ArgsProcessor.speed,
            ArgsProcessor.out);

    if ((ArgsProcessor.view.equals("text") || ArgsProcessor.view.equals("svg"))) {
      try {
        controller.outputText();
        if (ArgsProcessor.outFlag) {
          ((Closeable) (ArgsProcessor.out)).close();
        }
      } catch (IOException e) {
        Excellence.errorPopup(
            "Error occurred upon attempting to close the file writer for text based views.");
      }
    }
    else {
      try {
        controller.renderAnimation();
      } catch (UnsupportedOperationException e) {
        Excellence.errorPopup(e.getMessage());
      }

    }
  }

  /**
   * ArgsProcessor is a nested static class whose sole purpose is to parse the arguments from a
   * String array. As it parses arguments from the array, it mutates the fields in this class to
   * their appropriate value (based on the String given from the array). See method descriptions for
   * details: {@link Excellence.ArgsProcessor#processArgs(String[])}.
   */
  private static class ArgsProcessor {

    private static String view = null;
    private static Readable in = null;
    // Default output is System.out
    private static Appendable out = System.out;
    // Default speed is 1 tick/s
    private static int speed = 1;

    // Each flag represents whether or not it's corresponding field has been assigned once
    // already (not including defaults). true indicates that it has, false indicates that it hasn't.
    private static boolean inFlag = false;
    private static boolean viewFlag = false;
    private static boolean outFlag = false;
    private static boolean speedFlag = false;

    /**
     * Processes the values of a given String[] into data to be stored into the class. Returns
     * whether or not the processing was successful. Details of valid values are described in {@link
     * Excellence.ArgsProcessor#storeArgs(String, String)}
     *
     * @param args is the String[] of arguments to be parsed into data
     * @return true if processArgs successfully parsed args into data (i.e. no error message popped
     *     up), false otherwise.
     */
    private static boolean processArgs(String[] args) {
      // Increments by 2 because options and arguments come in pairs
      for (int i = 0; i < args.length; i = i + 2) {
        try {
          if (!storeArgs(args[i], args[i + 1])) {
            // If storeArgs failed, returns false to indicate the this method also failed
            return false;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          // Creates a popup in the case that an option is called without an argument directly
          // after it
          Excellence.errorPopup("Error: Attempted to call " + args[i]
              + " without an argument.");
          return false;

        }
      }
      return true;
    }

    /**
     * Attempts to convert a String {@code option} and a String {@code param} into data to be stored
     * in the class. Values for {@code option} can be one of "-in", "-view", "-out", or "-speed"
     *
     * <p>A JOptionPane error dialog will be generated, and the method will return false if:
     * <ul>
     * <li>The given String {@code option} is not one of the above, or</li>
     * <li>The given String {@code option} has already been processed at an earlier point in the
     * program, or</li>
     * <li>The given {@code option} is "-in", and a file with the given name of {@code param}
     * cannot be found, or</li>
     * <li>The given {@code option} is "-out", and a file with the given name of {@code param}
     * cannot be generated (for whatever reason), or</li>
     * <li>The given {@code option} is "-speed", and the given {@code param} cannot be parsed as
     * an Integer.</li>
     * </ul></p>
     *
     * @param option is the command line option that determines what {@code param} should represent
     * @param param  is the command line parameter that represents what will be stored in the class
     * @return true if the method is successfully able to convert the given String {@code option}
     *     and a String {@code param} into data stored in the class (i.e. no error popups are
     *     generated), false otherwise.
     */
    private static boolean storeArgs(String option, String param) {
      try {
        switch (option) {
          case "-in":
            if (!inFlag) {
              ArgsProcessor.in = new BufferedReader(new FileReader(param));
              ArgsProcessor.inFlag = true;
              return true;
            }
            break;
          case "-view":
            if (!viewFlag) {
              ArgsProcessor.view = param;
              ArgsProcessor.viewFlag = true;
              return true;
            }
            break;
          case "-out":
            if (!outFlag) {
              ArgsProcessor.out = new BufferedWriter(new FileWriter(param));
              ArgsProcessor.outFlag = true;
              return true;
            }
            break;
          case "-speed":
            if (!speedFlag) {
              ArgsProcessor.speed = Integer.parseInt(param);
              ArgsProcessor.speedFlag = true;
              return true;
            }
            break;
          default:
            Excellence.errorPopup("Error: Attempted to call invalid command-line option, "
                + option + ".");
            // Returns false to exit the method
            return false;
        }

        // If you get here, then option satisfied one of the cases, but the respective flag was
        // already true (you went in a case, but nothing happened), meaning that you called the
        // same option multiple times.
        Excellence.errorPopup("Error: Attempted to call option " + option + " after it has "
            + "already been called.");

      } catch (FileNotFoundException e) {
        // Creates a popup in the case that creating a FileReader throws a FileNotFoundException
        Excellence.errorPopup("Error: Attempted to call " + option
            + " with unknown file, " + param + ".");
      } catch (IOException e) {
        // Creates a popup in the case that creating a FileWriter throws a IOException
        Excellence.errorPopup("Error: Attempted to call: " + option +
            " with file name, " + param + ", that could not be created.");

      } catch (NumberFormatException e) {
        // Creates a popup in the case that creating a parsing the speed as an Integer throws a
        // NumberFormatException
        Excellence.errorPopup("Error: Attempted to call: " + option
            + " with invalid integer, " + param);
      }

      // If you get here, an error was thrown, and/or a error popup was generated, meaning that
      // storeArgs failed
      return false;
    }

  }

  /**
   * Creates a popup error message with the given String in it.
   *
   * @param errorMessage is the error message to be displayed.
   */
  private static void errorPopup(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }
}