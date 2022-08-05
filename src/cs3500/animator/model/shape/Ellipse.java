package cs3500.animator.model.shape;

import java.awt.Color;

import cs3500.animator.model.Posn;

/**
 * This class represents the geometry shape, Ellipse, a child of SimpleShape.
 */
public class Ellipse extends SimpleShape implements Shape {

  /**
   * Constructor of model.shape.Ellipse
   *
   * @param c Represents the color of Ellipse
   * @param p Represents the center position of Ellipse
   * @param w Represents the width of Ellipse
   * @param h Represents the height of Ellipse
   */
  public Ellipse(Color c, Posn p, String i, float w, float h) {
    super(c, p, i, w, h);
  }

  @Override
  public String getType() {
    return "ellipse";
  }

  @Override
  public Shape move(Posn position) {
    return new Ellipse(this.getColor(), position, this.getId(), this.getWidth(),
        this.getHeight());
  }

  @Override
  public Shape reColor(Color color) {
    return new Ellipse(color, this.getCenter(), this.getId(), this.getWidth(),
        this.getHeight());
  }

  @Override
  public Shape reSize(float width, float height) {
    return new Ellipse(this.getColor(), this.getCenter(), this.getId(), width,
        height);
  }
}
