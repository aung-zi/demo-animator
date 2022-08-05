package cs3500.animator.model.shape;

import java.awt.Color;

import cs3500.animator.model.Posn;

/**
 * This class represents PlusSign, a child of SimpleShape.
 */
public class PlusSign extends SimpleShape implements Shape {

  /**
   * Constructor of the model.shape.Shape.
   *
   * @param c  Represents the color of the shape
   * @param p  Represents the position of the shape
   * @param id Represents the id of the Shape
   * @param w  Represents the width of the shape
   * @param h  Represents the height of the shape
   */
  public PlusSign(Color c, Posn p, String id, float w, float h) {
    super(c, p, id, w, h);
  }

  @Override
  public String getType() {
    return "plus";
  }

  @Override
  public Shape move(Posn p) {
    return new PlusSign(this.getColor(), p, this.getId(), this.getWidth(), this.getHeight());
  }

  @Override
  public Shape reColor(Color c) {
    return new PlusSign(c, this.getCenter(), this.getId(), this.getWidth(), this.getHeight());
  }

  @Override
  public Shape reSize(float width, float height) {
    return new PlusSign(this.getColor(), this.getCenter(), this.getId(), width, height);
  }
}
