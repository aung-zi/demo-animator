package cs3500.animator.io.operation;

import cs3500.animator.model.Posn;

/**
 * This class represents the operation where the position of the shape is changed.
 */
public class OperationMove extends OperationSimple implements Operation {
  private final Posn center;

  /**
   * Constructor.
   * @param x the x coordinate of the shape
   * @param y the y coordinate of the shape
   * @param name  the name of the shape
   * @param type  the type of the shape
   */
  public OperationMove(float x, float y, String name, String type) {
    super(name, type);
    this.center = new Posn(x, y);
  }

  @Override
  public Posn getCenter() {
    return center;
  }

  @Override
  public OperationName getOperationType() {
    return OperationName.MOVE;
  }

  @Override
  public String toString() {
    return super.toString() + " & center: " + this.center.getX() + ", " + this.center.getY();
  }
}
