package cs3500.animator.model;

import java.util.ArrayList;
import java.util.NavigableMap;

import cs3500.animator.model.shape.Shape;

/**
 * This Interface consists of all the operations for previewing the current state of the
 * AnimatorModel.
 */
public interface AnimatorModelState {

  /**
   * Returns the shapes implemented in the AnimatorModel in a list.
   *
   * @return a list of Shapes
   */
  ArrayList<Shape> getShapes();

  /**
   * Returns all the Shapes in the given timeframe.
   *
   * @param frame a  frame that is contained in the model itself
   * @param s     a shape that is contained in the model itself
   * @return Return the "same" shape that is in the frame
   * @throws IllegalArgumentException if shape is not part of the model
   */
  Shape getShapeInFrame(int frame, Shape s);

  Shape getShapeInSelectFrame(int frame, Shape s);

  /**
   * Returns the list of timeframes listed in ascending order.
   *
   * @param s a shape that is contained in the model itself
   * @return a list of timeframes where the shape is included
   * @throws IllegalArgumentException if shape is not part of the model
   */
  ArrayList<Integer> getFramesContainingShape(Shape s);

  /**
   * Returns the tempo at which the frames will be refreshed.
   * @return  the tempo
   */
  int getTempo();

  int getTempo(int tick);

  ArrayList<Integer> getTempoFrame();

  /**
   * Returns the width of the canvas on which the animation will be using.
   * @return the width of the animation
   */
  int getBoundsWidth();

  /**
   * Returns the height of the canvas on which the animation will be using.
   * @return  the height of the animation
   */
  int getBoundsHeight();

  /**
   * Returns the total number of frames the model holds.
   * @return  the total number of ticks it takes to complete the animation in the model
   */
  int getTotalFramesInTimeFrame();

  /**
   * Returns the total number of frames (accounting the start and end of the motions) the model
   * holds.
   *
   * @return the total number of ticks it takes to complete the animation in the model
   */
  int getTotalSelectFramesInTimeFrame();

  /**
   * Returns a copy of the current timeframes stored in the model.
   */
  NavigableMap<Integer, ArrayList<Shape>> getACopyOfTimeframe();

}
