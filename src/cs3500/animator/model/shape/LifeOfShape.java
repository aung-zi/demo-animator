package cs3500.animator.model.shape;

import java.util.Objects;

/**
 * This class represents a life of the shape in the Animator, where it holds two numbers.
 */
public class LifeOfShape {
  private int startOfLife;
  private int endOfLife;

  /**
   * Constructor.
   *
   * @param startOfLife when the shape will first appear in the timeline of the Animator.
   * @param endOfLife   when the shape will disappear in the timeline of the Animator.
   * @throws IllegalArgumentException when the arguments are negative
   */
  public LifeOfShape(int startOfLife, int endOfLife) {
    if (startOfLife < 0) {
      throw new IllegalArgumentException("Life of Shape cannot start at a negative time");
    }

    if (endOfLife < 0) {
      throw new IllegalArgumentException("Life of Shape cannot end at a negative time");
    }

    if (startOfLife >= endOfLife) {
      throw new IllegalArgumentException("There needs to be some range in the life of shape");
    }

    this.startOfLife = startOfLife;
    this.endOfLife = endOfLife;
  }

  /**
   * Set the first appearance of a shape.
   */
  public void setStartOfLife(int startOfLife) {
    this.startOfLife = startOfLife;
  }

  /**
   * Returns the first appearance of a shape.
   *
   * @return first appearance of a shape
   */
  public int getStartOfLife() {
    return startOfLife;
  }

  /**
   * Set the last appearance of a shape.
   */
  public void setEndOfLife(int endOfLife) {
    this.endOfLife = endOfLife;
  }

  /**
   * Returns the last appearance of a shape.
   *
   * @return last appearance of a shape
   */
  public int getEndOfLife() {
    return endOfLife;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LifeOfShape)) {
      return false;
    }
    return this.startOfLife == ((LifeOfShape) o).startOfLife &&
        this.endOfLife == ((LifeOfShape) o).endOfLife;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startOfLife, endOfLife);
  }
}
