package cs3500.animator.io.operation;

import java.awt.Color;

import cs3500.animator.model.Posn;
import cs3500.animator.model.shape.Shape;

/**
 * This interface represents different types of motion performed on the registered shapes to the
 * Animator. The following method are used to retrieve the properties of these operations.
 */
public interface Operation {

  /**
   * Retrieves the Shape; May use to retrieve the shape in its original form.
   *
   * @return the shape
   */
  Shape retrieveShape();

  /**
   * Retrieves the new position after applying the operation.
   *
   * @return the position
   */
  Posn getCenter();

  /**
   * Retrieves the new width after applying the operation.
   *
   * @return the width
   */
  float getWidth();

  /**
   * Retrieves the new height after applying the operation.
   *
   * @return the height
   */
  float getHeight();

  /**
   * Retrieves the new color after applying the operation.
   *
   * @return the color
   */
  Color getColor();

  /**
   * Retrieves the name of the Shape.
   *
   * @return the id
   */
  String getName();

  /**
   * Retrieves the nature of the shape.
   *
   * @return the type
   */
  String getType();

  /**
   * Retrieves the type of operation.
   *
   * @return the name of the operation
   */
  OperationName getOperationType();
}
