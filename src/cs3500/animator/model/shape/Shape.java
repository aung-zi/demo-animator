package cs3500.animator.model.shape;

import java.awt.Color;

import cs3500.animator.model.Posn;

/**
 * This interface represents the two-dimensional shape used in the Animator. It contains simple
 * operations to retrieve key information about the object itself. The Shape is equal to each
 * other if its id and the type are the same.
 */
public interface Shape {

  /**
   * Retrieves the height of the Shape.
   *
   * @return height of the Shape
   */
  float getHeight();

  /**
   * Retrieves the width of the Shape.
   *
   * @return width of the Shape
   */
  float getWidth();

  /**
   * Retrieves unique identifier of the Shape.
   *
   * @return id of the Shape
   */
  String getId();

  /**
   * Retrieves the current center position of the Shape.
   *
   * @return center position of the Shape.
   */
  Posn getCenter();

  /**
   * Retrieves the color of the position of the Shape.
   *
   * @return color of the Shape.
   */
  Color getColor();

  /**
   * Retrieves the type of Shape.
   *
   * @return type of the Shape.
   */
  String getType();

  /**
   * Modify the posn of shape by creating a new shape.
   *
   * @param p given posn
   * @return a new shape
   */
  Shape move(Posn p);

  /**
   * Modify the color of shape by creating a new shape.
   *
   * @param c given color
   * @return a new shape
   */
  Shape reColor(Color c);

  /**
   * Modify the size of shape by creating a new shape.
   *
   * @param width  given width
   * @param height given height
   * @return a new shape
   */
  Shape reSize(float width, float height);
}
