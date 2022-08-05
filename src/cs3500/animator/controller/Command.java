package cs3500.animator.controller;

import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.ViewType;

/**
 * This class represents the Controller that controls any Animator View. It communicates directly
 * with the main method, and acts as a delegate between the view and model.
 */
public class Command implements AnimatorController {
  private final AnimatorView view;
  private final String output;
  private Map<ViewType, Runnable> viewTypes;
  private Timer timer;

  /**
   * This is the constructor.
   *
   * @param view AnimatorView
   */
  public Command(AnimatorModel model, AnimatorView view, String output) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("The view cannot be null.");
    }
    this.output = output;
    this.view = view;
    if (view.getViewType() == ViewType.VISUAL
        || view.getViewType() == ViewType.SVG
        || view.getViewType() == ViewType.TEXT
        || view.getViewType() == ViewType.GENERIC) {
      this.timer = new Timer(0, e -> {
        view.perform();
        timer.setDelay(1000 / model.getTempo());
      });
    } else {
      this.timer = new Timer(0, e -> {
        view.perform();
        timer.setDelay(1000 / model.getTempo(view.getDuration()));
      });
    }
  }

  /**
   * Configures listeners according to the type of view. If additional views are added, please set
   * up the configurations here.
   */
  private void configureViewListener() {
    viewTypes = new HashMap<>();

    viewTypes.put(ViewType.INTERACTIVE, () -> {
      configureKeyBoardListener();
      configureButtonListener();
      startTimer();

    });
    viewTypes.put(ViewType.VISUAL, this::startTimer);
    viewTypes.put(ViewType.SVG, () -> view.renderSVG(output));
    viewTypes.put(ViewType.TEXT, () -> {
      if (output != null) {
        try {
          FileWriter myWriter = new FileWriter("./" + output + ".txt");
          myWriter.write(view.toString());
          myWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println(view.toString());
      }
    });
  }

  /**
   * Starts the timer.
   */
  private void startTimer() {
    timer.start();
  }

  /**
   * Configures keyboards. If additional keystrokes are added, please set it up here.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_R, view::restart);
    keyPresses.put(KeyEvent.VK_L, view::loop);
    keyPresses.put(KeyEvent.VK_S, view::playOrPause);
    keyPresses.put(KeyEvent.VK_EQUALS, view::speedUp);
    keyPresses.put(KeyEvent.VK_MINUS, view::speedDown);
    keyPresses.put(KeyEvent.VK_O, view::outlineOrFill);
    keyPresses.put(KeyEvent.VK_D, view::discrete);

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

  /**
   * Configures buttons. If additional buttons are added, please set it up here.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("PlayOrPause", view::playOrPause);
    buttonClickedMap.put("Loop", view::loop);
    buttonClickedMap.put("Restart", view::restart);
    buttonClickedMap.put("SpeedUp", view::speedUp);
    buttonClickedMap.put("SpeedDown", view::speedDown);
    buttonClickedMap.put("OutlineOrFill", view::outlineOrFill);
    buttonClickedMap.put("Discrete", view::discrete);
    buttonClickedMap.put("editAnimation", view::edit);

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  @Override
  public void run() {
    configureViewListener();
    if (!viewTypes.containsKey(view.getViewType())) {
      throw new IllegalArgumentException("The view does not exist.");
    }
    viewTypes.get(view.getViewType()).run();
  }
}