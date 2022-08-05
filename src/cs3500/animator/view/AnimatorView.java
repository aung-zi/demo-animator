package cs3500.animator.view;


import java.awt.event.ActionListener;
import java.util.List;

import cs3500.animator.controller.KeyboardListener;
import cs3500.animator.model.shape.Shape;

/**
 * This is the Interface to support view for Animator.
 */
public interface AnimatorView {

  /**
   * Renders the SVG Format of the Animation.
   *
   * @param fileName of the output
   */
  default void renderSVG(String fileName) {
    throw new UnsupportedOperationException("The View does not support rendering the animation " +
        "into SVG.");
  }

  /**
   * Make the view visible. This is usually called after the view is constructed.
   */
  default void makeVisible() {
    throw new UnsupportedOperationException("The View does not support methods used for rendering" +
        " animation graphically");
  }

  /**
   * Provide the view with the shapes to be drawn.
   *
   * @param shapes provided shapes
   */
  default void setShapes(List<Shape> shapes) {
    throw new UnsupportedOperationException("The View does not support methods used for rendering" +
        " animation graphically");
  }

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error error message wanted to show
   */
  default void showErrorMessage(String error) {
    throw new UnsupportedOperationException("The View does not support methods used for rendering" +
        " animation graphically");
  }

  /**
   * Signal the view to draw itself.
   */
  default void refresh() {
    throw new UnsupportedOperationException("The View does not support methods used for rendering" +
        " animation graphically");
  }

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing. Thus our
   * Swing-based implementation of this interface will already have such a method.
   */
  default void addKeyListener(KeyboardListener keyboardListener) {
    throw new UnsupportedOperationException("The View does not support adding any runnable " +
        "keyboard shortcuts.");
  }


  /**
   * this is to force the view to have a method to set up the button.
   *
   * @param listener the action
   */
  default void addActionListener(ActionListener listener) {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
        "elements.");
  }

  /**
   * Returns the type of view.
   */
  default ViewType getViewType() {
    return ViewType.GENERIC;
  }

  /**
   * Plays or pauses an animation.
   */
  default void playOrPause() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
        "elements.");
  }

  /**
   * Loops an animation.
   */
  default void loop() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
        "elements.");
  }

  /**
   * Restart an animation.
   */
  default void restart() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
        "elements.");
  }

  /**
   * Speed up an animation.
   */
  default void speedUp() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
        "elements.");
  }

  /**
   * Slows down an animation.
   */
  default void speedDown() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
        "elements.");
  }

  default void perform() {
    throw new UnsupportedOperationException("The View does not support timer.");
  }

  default void outlineOrFill() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
            "elements.");
  }

  default void discrete() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
            "elements.");
  }

  default void edit() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
            "elements.");
  }

  default int getDuration() {
    throw new UnsupportedOperationException("The View does not support adding any runnable UI " +
            "elements.");
  }
}
