package cs3500.animator.io;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.animator.io.operation.Operation;
import cs3500.animator.io.operation.OperationName;
import cs3500.animator.io.operation.OperationColorChange;
import cs3500.animator.io.operation.OperationMove;
import cs3500.animator.io.operation.OperationNone;
import cs3500.animator.io.operation.OperationScaleChange;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Posn;
import cs3500.animator.model.SimpleAnimatorModel;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.LifeOfShape;
import cs3500.animator.model.shape.PlusSign;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Shape;

/**
 * This class represents a Builder for Animator Model. It contains several methods that allow the
 * user to manipulate the shapes easily (allow them to have multiple motions in the timeline before
 * it is actually built).
 *
 * @param <T> Animator Model
 */
public class AnimatorModelBuilder<T> implements TweenModelBuilder<AnimatorModel> {
  private final Map<Integer, ArrayList<Shape>> timeframe;
  private final Map<String, Integer> shapeCount;
  // INVARIANT: This list has the latest state of the shape when the model is built.
  private final Map<String, Shape> shapes;
  private final Map<String, LifeOfShape> lifeRecordOfShapes;
  private final Map<String, Set<Integer>> framesContainingShape;
  private final AnimatorModel model;
  private final Map<Integer, ArrayList<Operation>> operations;
  private int width;
  private int height;

  /**
   * Constructor.
   */
  public AnimatorModelBuilder() {
    this.timeframe = new LinkedHashMap<>();
    this.shapeCount = new LinkedHashMap<>();
    this.shapes = new LinkedHashMap<>();
    this.model = new SimpleAnimatorModel();
    this.lifeRecordOfShapes = new LinkedHashMap<>();
    this.framesContainingShape = new LinkedHashMap<>();

    this.operations = new LinkedHashMap<>();
  }

  @Override
  public TweenModelBuilder<AnimatorModel> setBounds(int width, int height) {
    this.width = width;
    this.height = height;

    return this;
  }

  /**
   * Validates the shape arguments before admitting.
   *
   * @param name       name of the shape
   * @param dimensionX dimension x of the shape
   * @param dimensionY dimension y of the shape
   * @param startFrame where to start in the timeline
   * @param endFrame   where to end in the timeline
   */
  private void validateShapeArguments(String name, float dimensionX, float dimensionY,
                                      int startFrame,
                                      int endFrame) {

    if (name == null) {
      throw new IllegalArgumentException("The name of the Shape cannot be null.");
    }
    if (dimensionX < 0 || dimensionY < 0) {
      throw new IllegalArgumentException("Radius cannot be negative");
    }
    if (startFrame < 0) {
      throw new IllegalArgumentException("Cannot add the shape in the frame less than 0.");
    }
    if (startFrame >= endFrame) {
      throw new IllegalArgumentException("There should be some range in start of life and end of " +
          "life.");
    }
  }

  /**
   * Add the given operation to the list of operations in the given frame in the timeline.
   *
   * @param frame frame in the timeline
   * @param op    the operation
   */
  private void addOperationsToFrame(int frame, Operation op) {
    if (operations.containsKey(frame)) {
      operations.get(frame).add(op);
    } else {
      operations.put(frame, new ArrayList<>(List.of(op)));
    }
  }

  /**
   * Add the given shape to the list of operations in the given frame in the timeline.
   *
   * @param frame frame in the timeline
   * @param shape the shape to be added to the timeline
   */
  private void addShapeToTimeframe(int frame, Shape shape) {
    if (timeframe.containsKey(frame)) {
      timeframe.get(frame).remove(shape);
      timeframe.get(frame).add(shape);
    } else {
      timeframe.put(frame, new ArrayList<>(List.of(shape)));
    }
  }

  /**
   * Register the given shape in the Builder temporarily.
   *
   * @param s the shape to be registered
   */
  private void registerShape(Shape s) {
    if (shapeCount.containsKey(s.getId())) {
      shapeCount.put(s.getId(), shapeCount.get(s.getId()) + 1);
    } else {
      shapeCount.put(s.getId(), 1);
    }
    shapes.put(s.getId(), s);
  }

  /**
   * Record the frames where the shape exists.
   *
   * @param name  name of the shape
   * @param frame frame to be recorded
   */
  private void recordFrameOfShape(String name, int frame) {
    if (framesContainingShape.containsKey(name)) {
      framesContainingShape.get(name).add(frame);
    } else {
      framesContainingShape.put(name, new HashSet<>(List.of(frame)));
    }
  }

  /**
   * Add the given shape to builder temporarily.
   *
   * @param shape       the shape
   * @param startOfLife when to begin in the timeline
   * @param endOfLife   when to end in the timeline
   */
  private void addShape(Shape shape, int startOfLife, int endOfLife) {
    // add oval to timeframe
    addOperationsToFrame(startOfLife, new OperationNone(shape, shape.getId(), shape.getType()));
    addOperationsToFrame(endOfLife, new OperationNone(shape, shape.getId(), shape.getType()));

    // register oval to the list of shapes
    registerShape(shape);

    // register the life of oval in the record
    lifeRecordOfShapes.put(shape.getId(), new LifeOfShape(startOfLife, endOfLife));

    // record the frame in which the shape contains
    recordFrameOfShape(shape.getId(), startOfLife);
    recordFrameOfShape(shape.getId(), endOfLife);

    // add the shape to the timeframe
    addShapeToTimeframe(startOfLife, shape);
    addShapeToTimeframe(endOfLife, shape);
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy,
                                                  float xRadius, float yRadius,
                                                  float red, float green, float blue,
                                                  int startOfLife, int endOfLife) {

    // validate the arguments if the shape to be created is proper.
    validateShapeArguments(name, xRadius, yRadius, startOfLife, endOfLife);

    // create a new oval to be added.
    Shape oval = new Ellipse(new Color(red, green, blue), new Posn(cx, cy), name, xRadius, yRadius);
    addShape(oval, startOfLife, endOfLife);

    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly,
                                                       float width, float height,
                                                       float red, float green, float blue,
                                                       int startOfLife, int endOfLife) {

    validateShapeArguments(name, width, height, startOfLife, endOfLife);

    // create a new rectangle to be added.
    Shape rect = new Rectangle(new Color(red, green, blue), new Posn(lx, ly), name, width, height);
    addShape(rect, startOfLife, endOfLife);

    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addPlusSign(String name, float lx, float ly,
                                          float width, float height,
                                          float red, float green, float blue,
                                          int startOfLife, int endOfLife) {

    validateShapeArguments(name, width, height, startOfLife, endOfLife);

    Shape plus = new PlusSign(new Color(red, green, blue), new Posn(lx, ly), name, width, height);
    addShape(plus, startOfLife, endOfLife);

    return this;
  }

  /**
   * Validates the arguments before translating motions to the model.
   *
   * @param name      name of the shape
   * @param startTime when to start in the timeline
   * @param endTime   when to end in the timeline
   * @throws IllegalArgumentException when arguments are null
   * @throws IllegalArgumentException when the shape does not exist
   * @throws IllegalArgumentException when the range is unreasonable.
   */
  private void validateAddMotionArguments(String name, int startTime, int endTime) {
    if (name == null) {
      throw new IllegalArgumentException("The name of the shape cannot be null");
    }

    if (!shapeCount.containsKey(name)) {
      throw new IllegalArgumentException("The shape to be moved does not exist");
    }

    if (startTime >= endTime) {
      throw new IllegalArgumentException("There needs to be some range in startTime and endTime");
    }
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY,
                                                  float moveToX, float moveToY,
                                                  int startTime, int endTime) {
    // validate the arguments if the shape to be created is proper.
    validateAddMotionArguments(name, startTime, endTime);

    addOperationsToFrame(startTime, new OperationMove(moveFromX, moveFromY, name,
        shapes.get(name).getType()));
    addOperationsToFrame(endTime, new OperationMove(moveToX, moveToY, name,
        shapes.get(name).getType()));

    // record the frame in which the shape contains
    recordFrameOfShape(name, startTime);
    recordFrameOfShape(name, endTime);

    addShapeToTimeframe(startTime, shapes.get(name));
    addShapeToTimeframe(endTime, shapes.get(name));

    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addColorChange(String name,
                                                         float oldR, float oldG, float oldB,
                                                         float newR, float newG, float newB,
                                                         int startTime, int endTime) {
    // validate the arguments if the shape to be created is proper.
    validateAddMotionArguments(name, startTime, endTime);

    if (startTime < lifeRecordOfShapes.get(name).getStartOfLife() ||
        endTime > lifeRecordOfShapes.get(name).getEndOfLife()) {
      throw new IllegalArgumentException("You may need to extend the life of the shape to " +
          "continue.");
    }

    addOperationsToFrame(startTime, new OperationColorChange(new Color(oldR, oldG, oldB), name,
        shapes.get(name).getType()));
    addOperationsToFrame(endTime, new OperationColorChange(new Color(newR, newG, newB), name,
        shapes.get(name).getType()));

    // record the frame in which the shape contains
    recordFrameOfShape(name, startTime);
    recordFrameOfShape(name, endTime);

    addShapeToTimeframe(startTime, shapes.get(name));
    addShapeToTimeframe(endTime, shapes.get(name));

    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addScaleToChange(String name,
                                                           float fromSx, float fromSy,
                                                           float toSx, float toSy,
                                                           int startTime, int endTime) {

    // validate the arguments if the shape to be created is proper.
    validateAddMotionArguments(name, startTime, endTime);

    if (startTime < lifeRecordOfShapes.get(name).getStartOfLife() ||
        endTime > lifeRecordOfShapes.get(name).getEndOfLife()) {
      throw new IllegalArgumentException("You may need to extend the life of the shape to " +
          "continue.");
    }

    addOperationsToFrame(startTime, new OperationScaleChange(fromSx, fromSy, name,
        shapes.get(name).getType()));
    addOperationsToFrame(endTime, new OperationScaleChange(toSx, toSy, name,
        shapes.get(name).getType()));

    // record the frame in which the shape contains
    recordFrameOfShape(name, startTime);
    recordFrameOfShape(name, endTime);

    addShapeToTimeframe(startTime, shapes.get(name));
    addShapeToTimeframe(endTime, shapes.get(name));

    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addSloMo(int tempo, int startTime, int endTime) {
    if (startTime >= endTime) {
      throw new IllegalArgumentException("Invalid time period.");
    }

    model.setTweenTempo(tempo, startTime, endTime);

    return this;
  }

  @Override
  public AnimatorModel build() {
    model.setBounds(width, height);

    ArrayList<Integer> frames = new ArrayList<>(operations.keySet());
    Collections.sort(frames);

    // go through all the operations in the frame
    for (Integer frame : frames) {
      ArrayList<Operation> listOfOps = new ArrayList<>(operations.get(frame));

      // apply all operations inside each frame
      for (Operation op : listOfOps) {
        String name = op.getName();
        OperationName operationType = op.getOperationType();
        Shape shape = shapes.get(name);

        switch (operationType) {
          case MOVE:
            shape = shape.move(op.getCenter());
            break;
          case COLOR_CHANGE:
            shape = shape.reColor(op.getColor());
            break;
          case SCALE_CHANGE:
            shape = shape.reSize(op.getWidth(), op.getHeight());
            break;
          default:
            break;
        }
        // update the shapes list with the latest changes
        shapes.replace(name, shape);
      }

      for (Shape s : timeframe.get(frame)) {
        Shape shape = shapes.get(s.getId());
        if (!model.getShapes().contains(s)) {
          model.addShape(shape, frame, frame + 1);
          model.removeMotion(shape, frame + 1);
        } else {
          model.addMotion(shape, frame);
        }
      }
    }

    return model;
  }
}
