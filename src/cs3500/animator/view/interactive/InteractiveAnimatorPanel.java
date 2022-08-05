package cs3500.animator.view.interactive;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;

import javax.swing.JPanel;

import cs3500.animator.Duration;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Posn;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.view.AnimatorPanel;

/**
 * This is the class for GraphicsAnimator Panel that extends JPanel.
 */
public class InteractiveAnimatorPanel extends JPanel implements AnimatorPanel {
  private final AnimatorModel model;
  private final Duration duration;
  private boolean isPlay;
  private boolean loop;
  private boolean fill;
  private ArrayList<Shape> shapes;
  private boolean discreteMode;

  /**
   * Constructor for VisualAnimatorPanel.
   */
  public InteractiveAnimatorPanel(AnimatorModel model, Duration duration) {
    this.model = model;
    this.duration = duration;
    shapes = new ArrayList<>();
    isPlay = true;
    loop = false;
    fill = true;
    this.discreteMode = false;
  }

  public boolean getDiscreteMode() {
    return this.discreteMode;
  }

  public void setDiscreteMode(boolean mode) {
    this.discreteMode = mode;
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
      if (discreteMode) {
        if (model.getShapeInSelectFrame(duration.getDuration(), shape) != null) {
          shapesAtEachTick.add(model.getShapeInSelectFrame(duration.getDuration(), shape));
        }
      } else {
        if (model.getShapeInFrame(duration.getDuration(), shape) != null) {
          shapesAtEachTick.add(model.getShapeInFrame(duration.getDuration(), shape));
        }
      }
    }
    // set the list of shapes in the parent class.
    setShapes(shapesAtEachTick);
  }

  @Override
  public void perform() {
    if (isPlay) {
      performAnimation();
    }
    accumulateShapes(new ArrayList<>(), model, duration);
    repaint();
  }

  private void performAnimation() {
    if (discreteMode) {
      this.performDiscreteAnimation();
    } else {
      this.performContinuousAnimation();
    }
  }

  private void performContinuousAnimation() {
    if (duration.getDuration() < model.getTotalFramesInTimeFrame()) {
      duration.addDuration();
    }

    if (loop && duration.getDuration() >= model.getTotalFramesInTimeFrame()) {
      duration.restart();
    }
  }

  private void performDiscreteAnimation() {
    // retrieve a copy of the timeframe
    NavigableMap<Integer, ArrayList<Shape>> timeframe = model.getACopyOfTimeframe();
    // stop at the last key
    if (duration.getDuration() < timeframe.lastKey()) {
      // get the ceiling frame
      int theFrame = timeframe.ceilingKey(duration.getDuration() + 1);
      // set that frame as our duration
      duration.setDuration(theFrame);
    } else {
      duration.setDuration(timeframe.lastKey());
    }

    if (loop && duration.getDuration() >= timeframe.lastKey()) {
      duration.restart();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    if (fill) {
      for (Shape s : shapes) {
        Posn position = s.getCenter();

        switch (s.getType()) {
          case ("rect"): {
            g2d.setColor(s.getColor());
            g2d.fillRect(
                (int) position.getX(),
                (int) position.getY(),
                (int) s.getWidth(),
                (int) s.getHeight());
            break;
          }
          case ("ellipse"): {
            g2d.setColor(s.getColor());
            g2d.fillOval(
                (int) position.getX(),
                (int) position.getY(),
                (int) s.getWidth(),
                (int) s.getHeight());
            break;
          }
          case ("plus"): {
            g2d.setColor(s.getColor());
            g2d.fillRect(
                (int) (position.getX() + s.getWidth() / 4),
                (int) position.getY(),
                (int) s.getWidth() / 2,
                (int) s.getHeight());
            g2d.fillRect(
                (int) position.getX(),
                (int) (position.getY() + s.getHeight() / 4),
                (int) s.getWidth(),
                (int) s.getHeight() / 2);
            break;
          }
          default: {
            break;
          }
        }
      }
    }
    if (!fill) {
      for (Shape s : shapes) {
        Posn position = s.getCenter();

        switch (s.getType()) {
          case ("rect"): {
            g2d.setColor(s.getColor());
            g2d.drawRect(
                (int) position.getX(),
                (int) position.getY(),
                (int) s.getWidth(),
                (int) s.getHeight());
            break;
          }
          case ("ellipse"): {
            g2d.setColor(s.getColor());
            g2d.drawOval(
                (int) position.getX(),
                (int) position.getY(),
                (int) s.getWidth(),
                (int) s.getHeight());
            break;
          }
          case ("plus"): {
            g2d.setColor(s.getColor());
            //AB
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4),
                (int) position.getY(),
                (int) (position.getX() + s.getWidth() / 4 * 3),
                (int) position.getY());
            //BC
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4 * 3),
                (int) position.getY(),
                (int) (position.getX() + s.getWidth() / 4 * 3),
                (int) (position.getY() + s.getHeight() / 4));
            //CD
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4 * 3),
                (int) (position.getY() + s.getHeight() / 4),
                (int) (position.getX() + s.getWidth()),
                (int) (position.getY() + s.getHeight() / 4));
            //DE
            g2d.drawLine((int) (position.getX() + s.getWidth()),
                (int) (position.getY() + s.getHeight() / 4),
                (int) (position.getX() + s.getWidth()),
                (int) (position.getY() + s.getHeight() / 4 * 3));
            //FE
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4 * 3),
                (int) (position.getY() + s.getHeight() / 4 * 3),
                (int) (position.getX() + s.getWidth()),
                (int) (position.getY() + s.getHeight() / 4 * 3));
            //FG
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4 * 3),
                (int) (position.getY() + s.getHeight() / 4 * 3),
                (int) (position.getX() + s.getWidth() / 4 * 3),
                (int) (position.getY() + s.getHeight()));
            //HG
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4),
                (int) (position.getY() + s.getHeight()),
                (int) (position.getX() + s.getWidth() / 4 * 3),
                (int) (position.getY() + s.getHeight()));
            //IH
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4),
                (int) (position.getY() + s.getHeight() / 4 * 3),
                (int) (position.getX() + s.getWidth() / 4),
                (int) (position.getY() + s.getHeight()));
            //JI
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4),
                (int) (position.getY() + s.getHeight() / 4 * 3),
                (int) position.getX(),
                (int) (position.getY() + s.getHeight() / 4 * 3));
            //KJ
            g2d.drawLine((int) position.getX(),
                (int) (position.getY() + s.getHeight() / 4),
                (int) position.getX(),
                (int) (position.getY() + s.getHeight() / 4 * 3));
            //KL
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4),
                (int) (position.getY() + s.getHeight() / 4),
                (int) position.getX(),
                (int) (position.getY() + s.getHeight() / 4));
            //AL
            g2d.drawLine((int) (position.getX() + s.getWidth() / 4),
                (int) (position.getY() + s.getHeight() / 4),
                (int) (position.getX() + s.getWidth() / 4),
                (int) position.getY());
            break;
          }
          default: {
            break;
          }
        }
      }
    }
  }

  /**
   * Set whether to play or pause the animation.
   *
   * @param b toggle
   */
  public void playOrStop(boolean b) {
    isPlay = b;
  }

  /**
   * Set whether the repeat is on.
   *
   * @param b toggle
   */
  public void isLooping(boolean b) {
    loop = b;
  }

  /**
   * Resets the timer.
   */
  public void restart() {
    duration.restart();
  }

  /**
   * Speed up the tempo rendered in the View.
   */
  public void speedUp() {
    for (Integer i : model.getTempoFrame()) {
      if (model.getTempo(i) == 1) {
        model.setTweenTempo(10, i, i);
      } else {
        model.setTweenTempo(model.getTempo(i) + 10, i, i);
      }
    }
    if (model.getTempo() == 1) {
      model.setTempo(10);
    } else {
      model.setTempo(model.getTempo() + 10);
    }
  }

  /**
   * Slow down the tempo rendered in the View.
   */
  public void speedDown() {
    for (Integer i : model.getTempoFrame()) {
      if (model.getTempo(i) <= 10) {
        model.setTweenTempo(1, i, i);
      } else {
        model.setTweenTempo(model.getTempo(i) - 10, i, i);
      }
    }
    if (model.getTempo() <= 10) {
      model.setTempo(1);
    } else {
      model.setTempo(model.getTempo() - 10);
    }
  }

  public void fill() {
    fill = !fill;
  }

  public int getDuration() {
    return duration.getDuration();
  }
}
