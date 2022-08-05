package cs3500.animator.io.operation;

import java.awt.Color;

import cs3500.animator.model.Posn;
import cs3500.animator.model.shape.Shape;

/**
 * This is an abstract class for a family of Operation.
 */
abstract class OperationSimple implements Operation {
  private final String name;
  private final String type;

  /**
   * Constructor.
   * @param name  the name of the shape
   * @param type  the type of the shape
   */
  public OperationSimple(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "name: " + this.name + " & type: " + this.type;
  }

  @Override
  public Color getColor() {
    throw new UnsupportedOperationException("Color not available");
  }

  @Override
  public float getHeight() {
    throw new UnsupportedOperationException("Height not available");
  }

  @Override
  public float getWidth() {
    throw new UnsupportedOperationException("Width not available");
  }

  @Override
  public Posn getCenter() {
    throw new UnsupportedOperationException("Center not available");
  }

  @Override
  public Shape retrieveShape() {
    throw new UnsupportedOperationException("Shape not available");
  }
}
