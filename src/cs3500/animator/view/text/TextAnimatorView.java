package cs3500.animator.view.text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.ViewType;

/**
 * This class extends the SimpleAnimatorView to output a list of motion depending on tempo set by
 * the user.
 */
public class TextAnimatorView implements AnimatorView {
  private final AnimatorModel model;
  private final int height;
  private final int width;
  private final double tempo;

  /**
   * This is the Constructor for SimpleAnimatorView.
   *
   * @param m Represents the Animation Model.
   */
  public TextAnimatorView(AnimatorModel m) {
    if (m == null) {
      throw new IllegalArgumentException("AnimatorModel cannot be null.");
    }

    this.model = m;
    this.height = m.getBoundsHeight();
    this.width = m.getBoundsWidth();
    this.tempo = m.getTempo();
  }

  /**
   * A helper function for toString. It prints out the animation text form.
   *
   * @param tempo tempo of the animation
   * @return the string output of the animation
   */
  private String toStringHelper(double tempo) {
    StringBuilder result = new StringBuilder();

    DecimalFormat df = new DecimalFormat("0.00");

    int countShape = 0;
    ArrayList<Shape> shapes = model.getShapes();
    for (Shape shape : shapes) {
      result.append("Shape ")
          .append(shape.getId())
          .append(" ")
          .append(shape.getType())
          .append("\n");

      ArrayList<Integer> frames = model.getFramesContainingShape(shape);
      int count = 1;
      for (Integer f : frames) {

        if (model.getShapeInFrame(f, shape) != null && count == 1) {
          result.append("motion").append("\t").append(shape.getId()).append("\t")
              .append(df.format(f / tempo)).append(
                  "\t")
              .append(model.getShapeInFrame(f,
                  shape)).append("\t\t");
          count++;
        } else if (model.getShapeInFrame(f, shape) != null && count >= 2
            && count != frames.size()) {
          result.append(shape.getId()).append("\t").append(df.format(f / tempo)).append("\t")
              .append(model.getShapeInFrame(f, shape)).append("\n")
              .append("motion").append("\t").append(shape.getId()).append("\t")
              .append(df.format(f / tempo)).append("\t")
              .append(model.getShapeInFrame(f, shape)).append("\t\t");
          count++;
        } else if (count == frames.size()) {
          result.append(shape.getId()).append("\t").append(df.format(f / tempo)).append("\t")
              .append(model.getShapeInFrame(f, shape));
        }
      }

      if (shapes.size() - 1 != countShape) {
        result.append("\n");
      }
      countShape++;
    }

    return result.toString();
  }

  @Override
  public String toString() {
    return "Canvas " + width + " " + height + "\n" + toStringHelper(tempo);
  }

  @Override
  public ViewType getViewType() {
    return ViewType.TEXT;
  }

}
