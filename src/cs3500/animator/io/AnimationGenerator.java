package cs3500.animator.io;


import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import cs3500.animator.model.Posn;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;

/**
 * This class is used to create manual algorithmic animations. The Generator here creates a simple
 * animation on BubbleSort of a given number of rectangles with a name, as well as the height and
 * width of the canvas in which it will be animated on.
 */
public class AnimationGenerator {
  private final ArrayList<Shape> shapes;
  private final ArrayList<Float> sort;
  private final int numOfShapes;
  private final String name;
  private final int width;
  private final int height;
  private final StringBuilder builder;
  Random rand = new Random();

  /**
   * Constructor for AnimationGenerator.
   *
   * @param numOfShapes number of shapes to be sorted
   * @param name        name of the file
   * @param width       width of the canvas
   * @param height      height of the canvas
   */
  public AnimationGenerator(int numOfShapes, String name, int width, int height) {
    this.numOfShapes = numOfShapes;
    this.name = name;
    this.width = width;
    this.height = height;

    this.shapes = new ArrayList<>();
    this.sort = new ArrayList<>();

    this.builder = new StringBuilder();

  }

  /**
   * This is the main method for AnimationGenerator.
   *
   * @param args args for main
   */
  public static void main(String[] args) {
    AnimationGenerator generator =
        new AnimationGenerator(10, "rect", 1000, 500);
    generator.generate();
  }

  /**
   * Generates the animation in a txt file.
   */
  public void generate() {
    generateShapes();
    bubbleSort();
    writeFile();
  }

  /**
   * Write to a file after creating one.
   */
  public void writeFile() {
    try {
      FileWriter myWriter = new FileWriter("./" + name + ".txt");
      myWriter.write(builder.toString());
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generates the shapes to be used for animation.
   */
  public void generateShapes() {
    builder.append("canvas ").append(width).append(" ").append(height).append("\n");
    for (int i = 1; i <= numOfShapes; i++) {

      float red = rand.nextFloat() * 1;
      float green = rand.nextFloat() * 1;
      float blue = rand.nextFloat() * 1;
      Color c = new Color(red, green, blue);

      Posn p = new Posn(100 + i * 10, 200);

      float height = rand.nextFloat() * 100;

      Rectangle s = new Rectangle(c, p, "R" + i, 10, height);

      shapes.add(s);
      sort.add(height);
      builder.append("rectangle name ").append(s.getId())
          .append(" min-x ").append(p.getX())
          .append(" min-y ").append(p.getY())
          .append(" width ").append(s.getWidth())
          .append(" height ").append(s.getHeight())
          .append(" color ")
          .append(red).append(" ")
          .append(green).append(" ")
          .append(blue).append(" from 1 to ").append(numOfShapes * 50).append("\n");
    }
  }

  /**
   * Checked if the list is sorted.
   *
   * @param l list of numbers represented by the height of the rectangle.
   * @return true if the list is sorted, false otherwise
   */
  public boolean isSorted(ArrayList<Float> l) {
    boolean result = true;
    for (int i = 0; i < l.size(); i++) {
      for (int j = i + 1; j < l.size(); j++) {
        if (l.get(i) >= l.get(j)) {
          result = false;
        }
      }
    }
    return result;
  }

  /**
   * Write the animation representing each step of recursion to file.
   *
   * @param r1    Shape 1
   * @param r2    Shape 2
   * @param count time
   */
  public void writeAnimationToFile(Shape r1, Shape r2, int count) {
    builder.append("move name ").append(r1.getId())
        .append(" moveto ")
        .append(r1.getCenter().getX()).append(" ")
        .append(r1.getCenter().getY()).append(" ")
        .append(r2.getCenter().getX()).append(" ")
        .append(r2.getCenter().getY()).append(" ")
        .append("from ").append(count).append(" to ")
        .append(count + 4).append("\n");
  }

  /**
   * Performs bubble sort and write to file.
   */
  public void bubbleSort() {
    int count = 1;
    while (!isSorted(sort)) {
      for (int i = 0; i < sort.size() - 1; i++) {
        if (sort.get(i) > sort.get(i + 1)) {
          Shape r1 = shapes.get(i);
          Shape r2 = shapes.get(i + 1);

          writeAnimationToFile(r1, r2, count);
          writeAnimationToFile(r2, r1, count);
          count += 4;
          float next = sort.get(i + 1);
          sort.set(i + 1, sort.get(i));
          sort.set(i, next);
          Posn p1 = shapes.get(i).getCenter();
          Posn p2 = shapes.get(i + 1).getCenter();
          Shape swap = shapes.get(i + 1).move(p1);
          shapes.set(i + 1, shapes.get(i).move(p2));
          shapes.set(i, swap);
        }
      }
    }
    for (int i = 0; i < sort.size(); i++) {
      Shape s = shapes.get(i);
      builder.append("change-color name ").append(s.getId())
          .append(" colorto ")
          .append((float) s.getColor().getRed() / 255).append(" ")
          .append((float) s.getColor().getGreen() / 255).append(" ")
          .append((float) s.getColor().getBlue() / 255).append(" ")
          .append("0 1 0 from ").append(count).append(" to ")
          .append(count + 4).append("\n");
      count += 4;
    }
  }
}
