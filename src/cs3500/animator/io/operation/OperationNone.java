package cs3500.animator.io.operation;

import cs3500.animator.model.shape.Shape;

/**
 * This class represents the instance where no operation is performed.
 */
public class OperationNone extends OperationSimple implements Operation {
  private final Shape shape;

  /**
   * Constructor.
   * @param shape the shape
   * @param name  the name of the shape
   * @param type  the type of the shape
   */
  public OperationNone(Shape shape, String name, String type) {
    super(name, type);
    this.shape = shape;
  }

  @Override
  public OperationName getOperationType() {
    return OperationName.NO_OPERATION;
  }

  @Override
  public Shape retrieveShape() {
    return this.shape;
  }

  @Override
  public String toString() {
    return super.toString() + " ";
  }
}
