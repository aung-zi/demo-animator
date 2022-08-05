package cs3500.animator.view.svg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.ViewType;

/**
 * This class represents the view that outputs an SVG.
 */
public class SVGAnimatorView implements AnimatorView {
  private final AnimatorModel model;

  /**
   * Default constructor for SVGAnimatorView.
   *
   * @param model input model that going to transfer.
   */
  public SVGAnimatorView(AnimatorModel model) {
    this.model = model;
  }

  @Override
  public void renderSVG(String fileName) {
    try {
      FileWriter myWriter = new FileWriter("./" + fileName + ".svg");
      myWriter.write(this.format());
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Formats the model.
   *
   * @return SVG format
   */
  private String format() {
    StringBuilder result = new StringBuilder();
    result.append("<svg width=\"")
        .append(model.getBoundsWidth())
        .append("\" height=\"")
        .append(model.getBoundsHeight())
        .append("\" ")
        .append("version=\"1.1\" ")
        .append("xmlns=\"http://www.w3")
        .append(".org/2000/svg\">\n\n");

    model.getShapes().forEach(shape -> {
      ArrayList<Integer> frames = model.getFramesContainingShape(shape);
      Shape s = model.getShapeInFrame(frames.get(0), shape);
      if (shape.getType().equals("rect")) {
        result.append("\t").append("<").append(s.getType()).append(" ")
            .append("id=\"").append(s.getId()).append("\" ")
            .append("x=\"").append(s.getCenter().getX()).append("\" ")
            .append("y=\"").append(s.getCenter().getY()).append("\" ")
            .append("width=\"").append(s.getWidth()).append("\" ")
            .append("height=\"").append(s.getHeight()).append("\" ")
            .append("fill=\"rgb(")
            .append(s.getColor().getRed()).append(",")
            .append(s.getColor().getGreen()).append(",")
            .append(s.getColor().getBlue()).append(")\" ")
            .append("visibility=\"visible\" >\n");
      } else if (shape.getType().equals("plus")) {
        result.append("\t").append("<").append("polygon").append(" ")
            .append("id=\"").append(s.getId()).append("\" ")
            .append("points=\"")

            .append(" ")
            .append(s.getCenter().getX())
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() / 3)

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() / 3)
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() / 3)

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() / 3)
            .append(",")
            .append(s.getCenter().getY())

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() * 2 / 3)
            .append(",")
            .append(s.getCenter().getY())

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() * 2 / 3)
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() / 3)

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth())
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() / 3)

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth())
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() * 2 / 3)

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() * 2 / 3)
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() * 2 / 3)

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() * 2 / 3)
            .append(",")
            .append(s.getCenter().getY() + s.getHeight())

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() / 3)
            .append(",")
            .append(s.getCenter().getY() + s.getHeight())

            .append(" ")
            .append(s.getCenter().getX() + s.getWidth() * 1 / 3)
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() * 2 / 3)

            .append(" ")
            .append(s.getCenter().getX())
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() * 2 / 3)

            .append(" ")
            .append(s.getCenter().getX())
            .append(",")
            .append(s.getCenter().getY() + s.getHeight() * 1 / 3)

            .append("\" ")
            .append("fill=\"rgb(")
            .append(s.getColor().getRed()).append(",")
            .append(s.getColor().getGreen()).append(",")
            .append(s.getColor().getBlue()).append(")\" ")
            .append("visibility=\"visible\" >\n");
      } else {
        result.append("\t").append("<").append(s.getType()).append(" ")
            .append("id=\"").append(s.getId()).append("\" ")
            .append("cx=\"").append(s.getCenter().getX()).append("\" ")
            .append("cy=\"").append(s.getCenter().getY()).append("\" ")
            .append("rx=\"").append(s.getWidth()).append("\" ")
            .append("ry=\"").append(s.getHeight()).append("\" ")
            .append("fill=\"rgb(")
            .append(s.getColor().getRed()).append(",")
            .append(s.getColor().getGreen()).append(",")
            .append(s.getColor().getBlue()).append(")\" ")
            .append("visibility=\"visible\" >\n");
      }

      if (shape.getType().equals("plus")) {
        for (int frame = 0; frame < frames.size() - 1; frame++) {
          int duration = (frames.get(frame + 1) - frames.get(frame)) * 10;
          ArrayList<String> attributes = findAttribute(model.getShapeInFrame(frames.get(frame),
                  shape),
              model.getShapeInFrame(frames.get(frame + 1), shape));

          System.out.println(attributes);

          for (String attribute : attributes) {
            result.append("\t\t").append("<animate attributeType=\"xml\" ")
                .append("begin=\"").append(frames.get(frame)).append("ms\" ")
                .append("dur=\"").append(duration).append("ms\" ");

            if (attribute.equals("fill")) {
              result.append("attributeName=\"").append(attribute).append("\" ");
            } else {
              result.append("attributeName=\"").append("points").append("\" ");
            }


            switch (attribute) {
              case "fill":
                result.append("values=\"rgb(")
                    .append(model.getShapeInFrame(frames.get(frame), s).getColor().getRed())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame), s).getColor().getGreen())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame), s).getColor().getBlue())
                    .append(")")
                    .append(";")
                    .append("rgb(")
                    .append(model.getShapeInFrame(frames.get(frame + 1), s).getColor().getRed())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame + 1), s).getColor().getGreen())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame + 1), s).getColor().getBlue())
                    .append(")\" ")
                    .append("fill=\"freeze\"").append(" />\n");

                break;
              case "width":
              case "height":
              case "x":
              case "y":
                Shape s1 = model.getShapeInFrame(frames.get(frame), s);
                Shape s2 = model.getShapeInFrame(frames.get(frame + 1), s);

                result.append("from=\"")
                    .append(" ")
                    .append(s1.getCenter().getX())
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() / 3)
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() / 3)
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() / 3)
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() / 3)
                    .append(",")
                    .append(s1.getCenter().getY())
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() * 2 / 3)
                    .append(",")
                    .append(s1.getCenter().getY())
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() * 2 / 3)
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() / 3)
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth())
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() / 3)
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth())
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() * 2 / 3)
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() * 2 / 3)
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight())
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() / 3)
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight())
                    .append(" ")
                    .append(s1.getCenter().getX() + s1.getWidth() * 1 / 3)
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s1.getCenter().getX())
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s1.getCenter().getX())
                    .append(",")
                    .append(s1.getCenter().getY() + s1.getHeight() * 1 / 3)
                    .append("\" ")
                    .append("to=\"")
                    .append(" ")
                    .append(s2.getCenter().getX())
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() / 3)
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() / 3)
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() / 3)
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() / 3)
                    .append(",")
                    .append(s2.getCenter().getY())
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() * 2 / 3)
                    .append(",")
                    .append(s2.getCenter().getY())
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() * 2 / 3)
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() / 3)
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth())
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() / 3)
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth())
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() * 2 / 3)
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() * 2 / 3)
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight())
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() / 3)
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight())
                    .append(" ")
                    .append(s2.getCenter().getX() + s2.getWidth() * 1 / 3)
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s2.getCenter().getX())
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() * 2 / 3)
                    .append(" ")
                    .append(s2.getCenter().getX())
                    .append(",")
                    .append(s2.getCenter().getY() + s2.getHeight() * 1 / 3)
                    .append("\" ")
                    .append("fill=\"freeze\"").append(" />\n");
                break;
              default:
                break;
            }
          }
        }
      } else {
        for (int frame = 0; frame < frames.size() - 1; frame++) {
          int duration = (frames.get(frame + 1) - frames.get(frame)) * 10;
          ArrayList<String> attributes = findAttribute(model.getShapeInFrame(frames.get(frame),
                  shape),
              model.getShapeInFrame(frames.get(frame + 1), shape));

          for (String attribute : attributes) {
            result.append("\t\t").append("<animate attributeType=\"xml\" ")
                .append("begin=\"").append(frames.get(frame)).append("ms\" ")
                .append("dur=\"").append(duration).append("ms\" ")
                .append("attributeName=\"").append(attribute).append("\" ");

            switch (attribute) {
              case "fill":
                result.append("values=\"rgb(")
                    .append(model.getShapeInFrame(frames.get(frame), s).getColor().getRed())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame), s).getColor().getGreen())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame), s).getColor().getBlue())
                    .append(")")
                    .append(";")
                    .append("rgb(")
                    .append(model.getShapeInFrame(frames.get(frame + 1), s).getColor().getRed())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame + 1), s).getColor().getGreen())
                    .append(",")
                    .append(model.getShapeInFrame(frames.get(frame + 1), s).getColor().getBlue())
                    .append(")\" ")
                    .append("fill=\"freeze\"").append(" />\n");

                break;
              case "width":
                result.append("from=\"")
                    .append(model.getShapeInFrame(frames.get(frame), s).getWidth()).append("\" ")
                    .append("to=\"").append(model.getShapeInFrame(frames.get(frame + 1), s)
                        .getWidth()).append("\" ")
                    .append("fill=\"freeze\"").append(" />\n");

                break;
              case "height":
                result.append("from=\"")
                    .append(model.getShapeInFrame(frames.get(frame), s).getHeight()).append("\" ")
                    .append("to=\"").append(model.getShapeInFrame(frames.get(frame + 1), s)
                        .getHeight()).append("\" ")
                    .append("fill=\"freeze\"").append(" />\n");

                break;
              case "cx":
              case "x":
                result.append("from=\"")
                    .append(model.getShapeInFrame(frames.get(frame), s).getCenter().getX())
                    .append("\" ")
                    .append("to=\"").append(model.getShapeInFrame(frames.get(frame + 1), s)
                        .getCenter().getX()).append("\" ")
                    .append("fill=\"freeze\"").append(" />\n");
                break;
              case "cy":
              case "y":
                result.append("from=\"")
                    .append(model.getShapeInFrame(frames.get(frame), s).getCenter().getY())
                    .append("\" ")
                    .append("to=\"").append(model.getShapeInFrame(frames.get(frame + 1), s)
                        .getCenter().getY()).append("\" ")
                    .append("fill=\"freeze\"").append(" />\n");
                break;

              default:
                break;
            }
          }
        }
      }
      if (s.getType().equals("plus")) {
        result.append("\t").append("</").append("polygon").append(">").append("\n");
      } else {
        result.append("\t").append("</").append(s.getType()).append(">").append("\n");
      }
    });


    result.append("\n</svg>");

    return result.toString();
  }

  /**
   * Determines if there is any change in attribute.
   *
   * @param s1 Shape 1
   * @param s2 Shape 2
   * @return the change
   */
  private ArrayList<String> findAttribute(Shape s1, Shape s2) {
    ArrayList<String> changes = new ArrayList<>();

    switch (s1.getType()) {
      case ("rect"):
      case ("plus"):
        if (s1.getCenter().getX() != s2.getCenter().getX()) {
          changes.add("x");
        }
        if (s1.getCenter().getY() != s2.getCenter().getY()) {
          changes.add("y");
        }
        break;
      case ("ellipse"):
        if (s1.getCenter().getX() != s2.getCenter().getX()) {
          changes.add("cx");
        }
        if (s1.getCenter().getY() != s2.getCenter().getY()) {
          changes.add("cy");
        }
        break;
      default:
        break;
    }

    if (!s1.getColor().equals(s2.getColor())) {
      changes.add("fill");
    }
    if (s1.getWidth() != s2.getWidth()) {
      changes.add("width");
    }
    if (s1.getHeight() != s2.getHeight()) {
      changes.add("height");
    }

    return changes;
  }

  @Override
  public ViewType getViewType() {
    return ViewType.SVG;
  }

}
