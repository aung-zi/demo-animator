package cs3500.animator;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.animator.controller.Command;
import cs3500.animator.io.AnimationFileReader;
import cs3500.animator.io.AnimatorModelBuilder;
import cs3500.animator.io.TweenModelBuilder;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewFactory;

/**
 * This is the entry to the world of Animator.
 */
public class Animator {

  /**
   * This is the main method.
   *
   * @param args The arguments from the terminal
   * @throws FileNotFoundException when file is not found
   */
  public static void main(String[] args) throws FileNotFoundException {
    Map<String, String> config = new HashMap<>();
    JFrame frame = new JFrame();

    // cuts the program off if the number of arguments is invalid.
    if (args.length > 8) {
      JOptionPane.showMessageDialog(frame,
          "Invalid Number of Arguments Detected",
          "Insane Warning",
          JOptionPane.WARNING_MESSAGE);

      System.exit(1);
    }

    for (int i = 0; i < args.length - 1; i += 2) {
      if (args[i].charAt(0) != '-' || config.containsKey(args[i])) {
        JOptionPane.showMessageDialog(frame,
            "Invalid Arguments Detected",
            "Insane Warning",
            JOptionPane.WARNING_MESSAGE);

        System.exit(1);
      }
      config.put(args[i], args[i + 1]);
    }

    if (!config.containsKey("-in") || !config.containsKey("-view")) {
      throw new IllegalArgumentException("Invalid argument(s)");
    }

    // set the default speed to 1 in case the user does not define it
    config.putIfAbsent("-speed", "1");
    // set the default filename to "out" for any type view other than "text"
    // in case the user does not define it
    if (!config.get("-view").equals("text")) {
      config.putIfAbsent("-out", "out");
    }

    // find the path to the input file
    File file = new File(config.get("-in"));
    config.replace("-in", file.getAbsoluteFile().getAbsolutePath());

    TweenModelBuilder<AnimatorModel> modelBuilder = new AnimatorModelBuilder<>();
    AnimationFileReader reader = new AnimationFileReader();

    // takes in the file to create the model
    AnimatorModel model = reader.readFile(config.get("-in"), modelBuilder);
    // set the tempo depending on the speed
    int tempo = Integer.parseInt(config.get("-speed"));
    model.setTempo(tempo);
    // takes in the view type to output the correct view
    AnimatorView view = AnimatorViewFactory.getView(config.get("-view"), model);

    Command controller = new Command(model, view, config.get("-out"));
    controller.run();
  }
}
