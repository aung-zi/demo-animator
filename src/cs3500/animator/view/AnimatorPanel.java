package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.shape.Shape;

/**
 * This interface is for the Panel that is used to implement visualization of the animation in the
 * Animator.
 */
public interface AnimatorPanel {

  /**
   * Set the shapes to be rendered in each frame.
   *
   * @param shapes the list that contains all the shapes in a frame.
   */
  void setShapes(List<Shape> shapes);

  /**
   * Perform the accumulation of shapes from model and render them visually.
   */
  void perform();
}
