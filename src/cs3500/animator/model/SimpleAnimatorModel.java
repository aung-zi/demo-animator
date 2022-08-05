package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import cs3500.animator.model.shape.Shape;

/**
 * This class represents the model for Animator that implements the basic operations of an Animation
 * Software to develop more useful features (e.g. moving, resizing, etc..): initializing the shape,
 * removing the shape, and adding motion of the shape.
 */
public class SimpleAnimatorModel implements AnimatorModel {
  private final NavigableMap<Integer, ArrayList<Shape>> timeframe;
  private final Map<Shape, Integer> shapes;
  private final NavigableMap<Integer, Integer> tempoFrame;
  private int width;
  private int height;
  private int tempo;

  /**
   * Constructor for SimpleAnimatorModel.
   */
  public SimpleAnimatorModel() {
    this.timeframe = new TreeMap<>();
    this.shapes = new LinkedHashMap<>();
    this.width = 0;
    this.height = 0;
    this.tempo = 1;
    tempoFrame = new TreeMap<>();
  }


  @Override
  public void addShape(Shape s, int from, int to) {
    // makes sure the timeframe is non-negative
    if (from < 0 || to <= 0) {
      throw new IllegalArgumentException("Timeframe cannot be negative");
    }

    // make sure the range stays correct
    if (to < from) {
      throw new IllegalArgumentException("Adding an illegal time period.");
    }

    // cannot add the same shape twice
    if (shapes.containsKey(s)) {
      throw new IllegalArgumentException("The shape has already been added.");
    }

    // we do not want to override the arraylist
    if (timeframe.containsKey(from)) {
      timeframe.get(from).add(s);
    } else {
      timeframe.put(from, new ArrayList<>(List.of(s)));
    }

    // we do not want to override the arraylist
    if (timeframe.containsKey(to)) {
      timeframe.get(to).add(s);
    } else {
      timeframe.put(to, new ArrayList<>(List.of(s)));
    }

    // When adding new shape, the shape will be in two frames.
    shapes.put(s, 2);
  }

  @Override
  public void addMotion(Shape s, int frame) {
    // making sure that the frame is non-zero
    if (frame < 0) {
      throw new IllegalArgumentException("Timeframe cannot be negative");
    }

    if (!shapes.containsKey(s)) {
      throw new IllegalArgumentException("Shape does not exist.");
    }

    // making sure that the frames do not overlap
    ArrayList<Integer> list = new ArrayList<Integer>();
    for (int f : timeframe.keySet()) {
      if (timeframe.get(f).contains(s)) {
        list.add(f);
      }
    }

    int lastFrame = Collections.max(list);
    if (frame < lastFrame) {
      throw new IllegalArgumentException("Overriding motion is not supported");
    }

    if (timeframe.containsKey(frame)) {
      if (timeframe.get(frame).contains(s)) {
        throw new IllegalStateException("Overriding motion is not supported.");
      }
    }

    if (timeframe.containsKey(frame)) {
      timeframe.get(frame).add(s);
    } else {
      timeframe.put(frame, new ArrayList<>(List.of(s)));
    }

    shapes.put(s, shapes.get(s) + 1);

  }

  @Override
  public void deleteShape(Shape s) {
    if (!shapes.containsKey(s)) {
      throw new IllegalArgumentException("Shape does not exist.");
    }
    shapes.remove(s);
    for (ArrayList<Shape> l : timeframe.values()) {
      l.remove(s);
    }
  }

  @Override
  public void removeMotion(Shape s, int frame) {
    if (!shapes.containsKey(s)) {
      throw new IllegalArgumentException("Shape does not exist");
    }

    // removes the instance of shape in the frame.
    this.timeframe.get(frame).remove(s);
    // remove the frame if there are no shapes inside.
    if (this.timeframe.get(frame).isEmpty()) {
      this.timeframe.remove(frame);
    }

    // decrement the count of shapes
    shapes.put(s, shapes.get(s) - 1);
    // if there is no more shape to be removed, then remove the shape from the list of shapes.
    if (shapes.get(s) == 0) {
      shapes.remove(s);
    }
  }

  @Override
  public void setBounds(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public ArrayList<Shape> getShapes() {
    return new ArrayList<Shape>(shapes.keySet());
  }

  @Override
  public Shape getShapeInFrame(int frame, Shape s) {
    if (!shapes.containsKey(s)) {
      throw new IllegalArgumentException("The Shape does not exist.");
    }

    if (frame < 0) {
      throw new IllegalArgumentException("Frame cannot be negative");
    }

    // return the shape if the timeframe has it in the given frame.
    if (timeframe.containsKey(frame)) {
      if (timeframe.get(frame).contains(s)) {
        return timeframe.get(frame).get(timeframe.get(frame).indexOf(s));
      }
    }

    // calculate tween-ing
    NavigableMap<Integer, Integer> frames = new TreeMap<Integer, Integer>();

    for (Integer integer : this.getFramesContainingShape(s)) {
      frames.put(integer, integer);
    }

    if (frame < frames.firstKey() || frame > frames.lastKey()) {
      return null;
    }

    int f = frames.floorKey(frame);
    int t = frames.ceilingKey(frame);

    Shape from = timeframe.get(f).get(timeframe.get(f).indexOf(s));
    Shape to = timeframe.get(t).get(timeframe.get(t).indexOf(s));

    float multiplierFrom = ((float) (t - frame) / (t - f));
    float multiplierTo = ((float) (frame - f) / (t - f));

    float newWidth =
        Math.round((from.getWidth() * multiplierFrom) + (to.getWidth() * multiplierTo));
    float newHeight =
        Math.round((from.getHeight() * multiplierFrom) + (to.getHeight() * multiplierTo));

    float x =
        Math.round((from.getCenter().getX() * multiplierFrom) +
            (to.getCenter().getX() * multiplierTo));
    float y =
        Math.round((from.getCenter().getY() * multiplierFrom) +
            (to.getCenter().getY() * multiplierTo));

    Posn newCenter = new Posn(x, y);

    int newRed =
        (int) ((from.getColor().getRed() * multiplierFrom) +
            (to.getColor().getRed() * multiplierTo));
    int newGreen =
        (int) ((from.getColor().getGreen() * multiplierFrom) +
            (to.getColor().getGreen() * multiplierTo));
    int newBlue =
        (int) ((from.getColor().getBlue() * multiplierFrom) +
            (to.getColor().getBlue() * multiplierTo));

    return s.reSize(newWidth, newHeight).reColor(new Color(newRed, newGreen,
        newBlue)).move(newCenter);
  }

  @Override
  public Shape getShapeInSelectFrame(int increment, Shape s) {
    if (!timeframe.containsKey(increment)) {
      return null;
    }
    if (!timeframe.get(increment).contains(s)) {
      return getShapeInFrame(increment, s);
    }
    return timeframe.get(increment).get(timeframe.get(increment).indexOf(s));
  }

  @Override
  public ArrayList<Integer> getFramesContainingShape(Shape s) {
    if (!shapes.containsKey(s)) {
      throw new IllegalArgumentException("The shape does not exist.");
    }

    ArrayList<Integer> result = new ArrayList<>();
    for (Map.Entry<Integer, ArrayList<Shape>> entry : timeframe.entrySet()) {
      if (entry.getValue().contains(s)) {
        result.add(entry.getKey());
      }
    }

    // ensures the ordering is right
    Collections.sort(result);

    return result;
  }

  @Override
  public int getTempo() {
    return tempo;
  }

  @Override
  public int getTempo(int tick) {
    return tempoFrame.getOrDefault(tick, tempo);
  }

  @Override
  public void setTempo(int tick) {
    this.tempo = tick;
  }

  @Override
  public ArrayList<Integer> getTempoFrame() {
    return new ArrayList<>(tempoFrame.keySet());
  }

  @Override
  public void setTweenTempo(int tick, int startTime, int endTime) {
    for (int i = startTime; i <= endTime; i++) {
      if (tempoFrame.containsKey(i)) {
        tempoFrame.replace(i, tick);
      } else {
        tempoFrame.put(i, tick);
      }
    }
  }

  @Override
  public int getBoundsWidth() {
    return this.width;
  }

  @Override
  public int getBoundsHeight() {
    return this.height;
  }

  @Override
  public int getTotalFramesInTimeFrame() {
    return timeframe.lastKey();
  }

  @Override
  public int getTotalSelectFramesInTimeFrame() {
    return timeframe.size();
  }

  @Override
  public NavigableMap<Integer, ArrayList<Shape>> getACopyOfTimeframe() {
    return new TreeMap<>(timeframe);
  }

}
