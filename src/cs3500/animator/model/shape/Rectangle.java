package cs3500.animator.model.shape;

import java.awt.Color;

import cs3500.animator.model.Posn;

/**
 * This class represents Rectangle, a child of SimpleShape.
 */
public class Rectangle extends SimpleShape implements Shape {

  /**
   * Constructor of the model.shape.Shape.
   *
   * @param c Represents the color of the model.shape.SimpleShape
   * @param p Represents the position of the model.shape.SimpleShape
   */
  public Rectangle(Color c, Posn p, String i, float w, float h) {
    super(c, p, i, w, h);
  }

  @Override
  public String getType() {
    return "rect";
  }

  @Override
  public Shape move(Posn position) {
    return new Rectangle(this.getColor(), position, this.getId(), this.getWidth(),
        this.getHeight());
  }

  @Override
  public Shape reColor(Color color) {
    return new Rectangle(color, this.getCenter(), this.getId(), this.getWidth(),
        this.getHeight());
  }

  @Override
  public Shape reSize(float width, float height) {
    return new Rectangle(this.getColor(), this.getCenter(), this.getId(), width,
        height);
  }


}
