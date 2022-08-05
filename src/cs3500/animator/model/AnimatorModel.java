package cs3500.animator.model;

import cs3500.animator.model.shape.Shape;

/**
 * This Interface consists of the simplest operation of an Animator software that can be used to
 * build more useful methods.
 */
public interface AnimatorModel extends AnimatorModelState {

  /**
   * Adds shape to the Animator. This is the first method to be used as the first motion for the
   * Shape. Subsequent motions can be created using addMotion().
   *
   * @param s    Represents the Shape
   * @param from Represents the starting tick in the timeframe
   * @param to   Represents the ending tick in the timeframe
   * @throws IllegalArgumentException if the range is either negative or zero
   */
  void addShape(Shape s, int from, int to);

  /**
   * Adds motion of the Shape to the Animator. Will not work before there are no shapes that
   * exist in the model.
   *
   * @param s     Represents the Shape
   * @param frame Represents the ending tick where the Shape will be placed. It will combine with
   *              the last tick to form a range.
   * @throws IllegalArgumentException if the frame is negative or is overlappig with the existing
   *                                  frames
   */
  void addMotion(Shape s, int frame);

  /**
   * Remove the frame of the Shape, hence the motion in the Animator.
   *
   * @param s     Represents the Shape
   * @param frame Represents the tick where the end state of the motion is taking place.
   */
  void removeMotion(Shape s, int frame);

  /**
   * Delete the shape from the Animator, which in turns deletes all the motion defined thus far.
   *
   * @param s Represents the Shape
   * @throws IllegalArgumentException if the Shape does not exist in the Animator
   */
  void deleteShape(Shape s);

  /**
   * Set the tempo at which the frame will be refreshed.
   *
   * @param tick the speed of the tick where 1 represents 1 second
   */
  void setTempo(int tick);

  void setTweenTempo(int tick, int startTime, int endTime);

  /**
   * Set the canvas where the shapes will be drawn.
   *
   * @param width  the width of the animation.
   * @param height the height of the animation.
   */
  void setBounds(int width, int height);
}
