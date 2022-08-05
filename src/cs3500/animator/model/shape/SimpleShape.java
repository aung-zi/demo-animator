package cs3500.animator.model.shape;

import java.awt.Color;
import java.util.Objects;

import cs3500.animator.model.Posn;

/**
 * This abstract class represents the two-dimensional shapes used in the Animator.
 */
abstract class SimpleShape implements Shape {
  private final String id;
  private final Color color;
  private final Posn position;
  private final float width;
  private final float height;

  /**
   * Constructor of the model.shape.Shape.
   *
   * @param c  Represents the color of the shape
   * @param p  Represents the position of the shape
   * @param id Represents the id of the Shape
   * @param w  Represents the width of the shape
   * @param h  Represents the height of the shape
   */
  public SimpleShape(Color c, Posn p, String id, float w, float h) {
    this.color = c;
    this.position = p;
    this.width = w;
    this.height = h;
    this.id = id;
  }

  @Override
  public String getType() {
    return "Simple Shape";
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public float getWidth() {
    return width;
  }

  @Override
  public float getHeight() {
    return height;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public Posn getCenter() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleShape)) {
      return false;
    }

    return this.getId().equals(((SimpleShape) o).getId()) &&
        this.getType().equals(((SimpleShape) o).getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId(), this.getType());
  }

  @Override
  public String toString() {
    return
        this.getCenter().getX() + "\t" + this.getCenter().getY() + "\t" +
            this.getWidth() + "\t" + this.getHeight() + "\t" + this.getColor().getRed() + "\t" +
            this.getColor().getGreen() + "\t" + this.getColor().getBlue();
  }
}