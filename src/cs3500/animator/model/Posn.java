package cs3500.animator.model;

import java.util.Objects;

/**
 * This class represents the point (position) on the Canvas used in the Animator Model. This is a
 * value object.
 */
public class Posn {
  private final float x;
  private final float y;

  /**
   * Constructor of the class.
   *
   * @param x Represents the x coordinate
   * @param y Represents the y coordinate
   * @throws IllegalArgumentException if the coordinates are negative
   */
  public Posn(float x, float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x coordinate of the position.
   *
   * @return x coordinate
   */
  public float getX() {
    return x;
  }

  /**
   * Returns the y coordinate of the position.
   *
   * @return y coordinate
   */
  public float getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Posn)) {
      return false;
    }

    return x == ((Posn) o).getX() &&
        y == ((Posn) o).getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
