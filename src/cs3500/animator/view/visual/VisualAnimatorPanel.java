package cs3500.animator.view.visual;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.Duration;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Posn;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.view.AnimatorPanel;

/**
 * This is the inherited Panel for Visual View. This is where the frames stored in the Animator
 * Model are rendered.
 */
public class VisualAnimatorPanel extends JPanel implements AnimatorPanel {
  private final AnimatorModel model;
  private final Duration duration;
  private ArrayList<Shape> shapes;

  /**
   * Constructor.
   *
   * @param model Animator Model
   */
  public VisualAnimatorPanel(AnimatorModel model, Duration duration) {
    this.model = model;
    this.duration = duration;
    shapes = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (Shape s : shapes) {
      Posn position = s.getCenter();

      switch (s.getType()) {
        case ("rect"):
          g2d.setColor(s.getColor());
          g2d.fillRect(
                  (int) position.getX(),
                  (int) position.getY(),
                  (int) s.getWidth(),
                  (int) s.getHeight());
          break;

        case ("ellipse"):
          g2d.setColor(s.getColor());
          g2d.fillOval(
                  (int) position.getX(),
                  (int) position.getY(),
                  (int) s.getWidth(),
                  (int) s.getHeight());
          break;

        default:
          break;
      }
    }
  }

  @Override
  public void setShapes(List<Shape> shapes) {
    this.shapes = new ArrayList<>(shapes);
  }

  /**
   * This method is a helper method for going through each frame and setting the shape.
   *
   * @param shapesAtEachTick list of shape at each tick.
   * @param model            the Animator model
   * @param duration         the inner timer
   */
  protected void accumulateShapes(ArrayList<Shape> shapesAtEachTick, AnimatorModel model,
                                  Duration duration) {
    for (Shape shape : model.getShapes()) {
      if (model.getShapeInFrame(duration.getDuration(), shape) != null) {
        shapesAtEachTick.add(model.getShapeInFrame(duration.getDuration(), shape));
      }
    }
    // set the list of shapes in the parent class.
    setShapes(shapesAtEachTick);
  }

  @Override
  public void perform() {
    accumulateShapes(new ArrayList<>(), model, duration);
    repaint();

    if (duration.getDuration() < model.getTotalFramesInTimeFrame()) {
      duration.addDuration();
    }
  }
}
