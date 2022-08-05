package cs3500.animator.io.operation;

import java.awt.Color;

/**
 * This class represents the operation where the color of the shape is changed.
 */
public class OperationColorChange extends OperationSimple implements Operation {
  private final Color color;

  /**
   * Constructor.
   *
   * @param color new color of the shape
   * @param name  name of the shape
   * @param type  type of the shape
   */
  public OperationColorChange(Color color, String name, String type) {
    super(name, type);
    this.color = color;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public OperationName getOperationType() {
    return OperationName.COLOR_CHANGE;
  }

  @Override
  public String toString() {
    return super.toString() + " & color: (" +
        this.color.getRed() + ", " + this.color.getGreen() + ", " + this.color.getBlue() + ")";
  }
}
