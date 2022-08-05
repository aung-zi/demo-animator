package cs3500.animator.io.operation;

/**
 * This class represents the operation where the dimensions of the shape are changed.
 */
public class OperationScaleChange extends OperationSimple implements Operation {
  private final float height;
  private final float width;

  /**
   * Constructor.
   * @param height  the height of the shape
   * @param width the width of the shape
   * @param name  the name of the shape
   * @param type  the type of the shape
   */
  public OperationScaleChange(float width, float height, String name, String type) {
    super(name, type);
    this.height = height;
    this.width = width;
  }

  @Override
  public float getHeight() {
    return height;
  }

  @Override
  public float getWidth() {
    return width;
  }

  @Override
  public OperationName getOperationType() {
    return OperationName.SCALE_CHANGE;
  }

  @Override
  public String toString() {
    return super.toString() + " & height: " + this.height;
  }
}
