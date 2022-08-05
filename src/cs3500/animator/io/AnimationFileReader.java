package cs3500.animator.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents a file reader for the animation file. This reads in the file in the
 * prescribed file format, and relies on a model builder interface. The user of this class should
 * create a model builder that implements this interface.
 */

public class AnimationFileReader {

  /**
   * Read the animation file and use the builder to build a model.
   *
   * @param fileName the path of the file to be read
   * @param builder  the builder used to build the model
   * @param <T>      the type of model
   * @return the model
   * @throws FileNotFoundException  if the specified file cannot be read
   * @throws InputMismatchException if some data value is not of the expected type
   * @throws IllegalStateException  if an illegal token is read from the file
   */
  public <T> T readFile(String fileName, TweenModelBuilder<T> builder) throws
      FileNotFoundException, IllegalStateException, InputMismatchException {
    Scanner sc;

    sc = new Scanner(new FileInputStream(fileName));

    while (sc.hasNext()) {
      String command = sc.next();
      ShapeInfo shapeInfo;
      switch (command) {
        case "canvas":
          CanvasInfo canvasInfo = readCanvasInfo(sc);
          builder.setBounds(canvasInfo.getWidth(), canvasInfo.getHeight());
          break;
        case "rectangle":
          RectangleInfo rinfo = readRectangleInfo(sc);
          builder.addRectangle(
              rinfo.getName(),
              rinfo.getX(), rinfo.getY(),
              rinfo.getWidth(), rinfo.getHeight(),
              rinfo.getR(), rinfo.getG(), rinfo.getB(),
              rinfo.getStart(), rinfo.getEnd());
          break;
        case "oval":
          OvalInfo cinfo = readOvalInfo(sc);
          builder.addOval(
              cinfo.getName(),
              cinfo.getX(), cinfo.getY(),
              cinfo.getXRadius(), cinfo.getYRadius(),
              cinfo.getR(), cinfo.getG(), cinfo.getB(),
              cinfo.getStart(), cinfo.getEnd());
          break;
        case "plus":
          PlusInfo pinfo = readPlusInfo(sc);
          builder.addPlusSign(
                  pinfo.getName(),
                  pinfo.getX(), pinfo.getY(),
                  pinfo.getWidth(), pinfo.getHeight(),
                  pinfo.getR(), pinfo.getG(), pinfo.getB(),
                  pinfo.getStart(), pinfo.getEnd());
          break;
        case "move":
          MoveInfo minfo = readMoveInfo(sc);
          builder.addMove(
              minfo.getName(),
              minfo.getFromX(),
              minfo.getFromY(),
              minfo.getToX(),
              minfo.getToY(),
              minfo.getStart(),
              minfo.getEnd());
          break;
        case "change-color":
          ChangeColorInfo colorInfo = readChangeColorInfo(sc);
          builder.addColorChange(colorInfo.name,
              colorInfo.getFromR(),
              colorInfo.getFromG(),
              colorInfo.getFromB(),
              colorInfo.getToR(),
              colorInfo.getToG(),
              colorInfo.getToB(),
              colorInfo.getStart(),
              colorInfo.getEnd());
          break;
        case "scale":
          ScaleByInfo scaleByInfo = readScaleByInfo(sc);
          builder.addScaleToChange(scaleByInfo.name,
              scaleByInfo.getFromXScale(),
              scaleByInfo.getFromYScale(),
              scaleByInfo.getToXScale(),
              scaleByInfo.getToYScale(),
              scaleByInfo.getStart(),
              scaleByInfo.getEnd());
          break;
        case "slo-mo":
          SloInfo sloInfo = readSloInfo(sc);
          builder.addSloMo(
                  sloInfo.getTempo(),
                  sloInfo.getStart(), sloInfo.getEnd());
          break;
        default:
          throw new IllegalStateException("Unidentified token " + command + " "
              + "read from file");

      }
    }
    return builder.build();
  }

  private CanvasInfo readCanvasInfo(Scanner sc) {
    CanvasInfo info = new CanvasInfo();
    info.setWidth(sc.nextInt());
    info.setHeight(sc.nextInt());
    return info;
  }

  private RectangleInfo readRectangleInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    RectangleInfo info = new RectangleInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "min-x":
          info.setX(sc.nextFloat());
          break;
        case "min-y":
          info.setY(sc.nextFloat());
          break;
        case "width":
          info.setWidth(sc.nextFloat());
          break;
        case "height":
          info.setHeight(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "rectangle");
      }
    }

    return info;
  }

  private OvalInfo readOvalInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    OvalInfo info = new OvalInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "center-x":
          info.setX(sc.nextFloat());
          break;
        case "center-y":
          info.setY(sc.nextFloat());
          break;
        case "x-radius":
          info.setXRadius(sc.nextFloat());
          break;
        case "y-radius":
          info.setYRadius(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "oval");
      }
    }

    return info;
  }

  private PlusInfo readPlusInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    PlusInfo info = new PlusInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "min-x":
          info.setX(sc.nextFloat());
          break;
        case "min-y":
          info.setY(sc.nextFloat());
          break;
        case "width":
          info.setWidth(sc.nextFloat());
          break;
        case "height":
          info.setHeight(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "plus sign");
      }
    }

    return info;
  }

  private MoveInfo readMoveInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    MoveInfo info = new MoveInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "moveto":
          info.setFromX(sc.nextFloat());
          info.setFromY(sc.nextFloat());
          info.setToX(sc.nextFloat());
          info.setToY(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "move");
      }
    }

    return info;
  }

  private ChangeColorInfo readChangeColorInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    ChangeColorInfo info = new ChangeColorInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "colorto":
          info.setFromR(sc.nextFloat());
          info.setFromG(sc.nextFloat());
          info.setFromB(sc.nextFloat());
          info.setToR(sc.nextFloat());
          info.setToG(sc.nextFloat());
          info.setToB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "change-color");
      }
    }

    return info;
  }

  private ScaleByInfo readScaleByInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    ScaleByInfo info = new ScaleByInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "scaleto":
          info.setFromXScale(sc.nextFloat());
          info.setFromYScale(sc.nextFloat());
          info.setToXScale(sc.nextFloat());
          info.setToYScale(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "scale-to");
      }
    }

    return info;
  }

  private SloInfo readSloInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    SloInfo info = new SloInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "speed":
          info.setTempo(sc.nextInt());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "slo-mo");
      }
    }

    return info;
  }

  class CanvasInfo {
    private int width = 800;
    private int height = 600;

    int getWidth() {
      return width;
    }

    void setWidth(int w) {
      width = w;
    }

    int getHeight() {
      return height;
    }

    void setHeight(int h) {
      height = h;
    }
  }

  class Inputable {
    protected Map<String, Boolean> valueFlags;

    public Inputable() {
      valueFlags = new HashMap<String, Boolean>();

    }

    public boolean isAllInitialized() {
      for (Map.Entry<String, Boolean> entry : valueFlags.entrySet()) {
        if (!entry.getValue()) {
          return false;
        }
      }
      return true;
    }
  }

  class ShapeInfo extends Inputable {
    private String name;
    private float r;
    private float g;
    private float b;
    private int start;
    private int end;


    ShapeInfo() {
      super();
      valueFlags.put("name", false);
      valueFlags.put("r", false);
      valueFlags.put("g", false);
      valueFlags.put("b", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);
    }

    float getR() {
      return r;
    }

    void setR(float r) {
      this.r = r;
      valueFlags.replace("r", true);
    }

    float getG() {
      return g;
    }

    void setG(float g) {
      this.g = g;
      valueFlags.replace("g", true);
    }

    float getB() {
      return b;
    }

    void setB(float b) {
      this.b = b;
      valueFlags.replace("b", true);
    }

    String getName() {
      return name;
    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    public int getStart() {
      return start;
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    public int getEnd() {
      return end;
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }


  }

  class RectangleInfo extends ShapeInfo {
    private float x;
    private float y;
    private float width;
    private float height;

    RectangleInfo() {
      super();
      valueFlags.put("x", false);
      valueFlags.put("y", false);
      valueFlags.put("width", false);
      valueFlags.put("height", false);
    }

    float getX() {
      return x;
    }

    void setX(float x) {
      this.x = x;
      valueFlags.replace("x", true);
    }

    float getY() {
      return y;
    }

    void setY(float y) {
      this.y = y;
      valueFlags.replace("y", true);
    }

    float getWidth() {
      return width;
    }

    void setWidth(float width) {
      this.width = width;
      valueFlags.replace("width", true);
    }

    float getHeight() {
      return height;
    }

    void setHeight(float height) {
      this.height = height;
      valueFlags.replace("height", true);
    }
  }

  class OvalInfo extends ShapeInfo {
    private float cx;
    private float cy;
    private float xradius;
    private float yradius;

    OvalInfo() {
      super();
      valueFlags.put("cx", false);
      valueFlags.put("cy", false);
      valueFlags.put("xradius", false);
      valueFlags.put("yradius", false);
    }

    float getX() {
      return cx;
    }

    void setX(float x) {
      this.cx = x;
      valueFlags.replace("cx", true);
    }

    float getY() {
      return cy;
    }

    void setY(float y) {
      this.cy = y;
      valueFlags.replace("cy", true);
    }

    float getXRadius() {
      return xradius;
    }

    void setXRadius(float radius) {
      this.xradius = radius;
      valueFlags.replace("xradius", true);
    }

    float getYRadius() {
      return yradius;
    }

    void setYRadius(float radius) {
      this.yradius = radius;
      valueFlags.replace("yradius", true);
    }

  }

  class PlusInfo extends ShapeInfo {
    private float x;
    private float y;
    private float width;
    private float height;

    PlusInfo() {
      super();
      valueFlags.put("x", false);
      valueFlags.put("y", false);
      valueFlags.put("width", false);
      valueFlags.put("height", false);
    }

    float getX() {
      return x;
    }

    void setX(float x) {
      this.x = x;
      valueFlags.replace("x", true);
    }

    float getY() {
      return y;
    }

    void setY(float y) {
      this.y = y;
      valueFlags.replace("y", true);
    }

    float getWidth() {
      return width;
    }

    void setWidth(float width) {
      this.width = width;
      valueFlags.replace("width", true);
    }

    float getHeight() {
      return height;
    }

    void setHeight(float height) {
      this.height = height;
      valueFlags.replace("height", true);
    }
  }

  class MoveInfo extends Inputable {
    private String name;
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;
    private int start;
    private int end;

    MoveInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromx", false);
      valueFlags.put("fromy", false);
      valueFlags.put("tox", false);
      valueFlags.put("toy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    String getName() {
      return name;
    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    float getFromX() {
      return fromX;
    }

    void setFromX(float x) {
      this.fromX = x;
      valueFlags.replace("fromx", true);
    }

    float getFromY() {
      return fromY;
    }

    void setFromY(float y) {
      this.fromY = y;
      valueFlags.replace("fromy", true);
    }

    float getToX() {
      return toX;
    }

    void setToX(float x) {
      this.toX = x;
      valueFlags.replace("tox", true);
    }

    float getToY() {
      return toY;
    }

    void setToY(float y) {
      this.toY = y;
      valueFlags.replace("toy", true);
    }

    int getStart() {
      return start;
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    int getEnd() {
      return end;
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }
  }

  class ChangeColorInfo extends Inputable {
    private String name;
    private float fromR;
    private float fromG;
    private float fromB;
    private float toR;
    private float toG;
    private float toB;
    private int start;
    private int end;

    ChangeColorInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("tor", false);
      valueFlags.put("tog", false);
      valueFlags.put("tob", false);
      valueFlags.put("fromr", false);
      valueFlags.put("fromg", false);
      valueFlags.put("fromb", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    String getName() {
      return name;
    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    float getFromR() {
      return fromR;
    }

    void setFromR(float r) {
      this.fromR = r;
      valueFlags.replace("fromr", true);
    }

    float getFromG() {
      return fromG;
    }

    void setFromG(float g) {
      this.fromG = g;
      valueFlags.replace("fromg", true);
    }

    float getFromB() {
      return fromB;
    }

    void setFromB(float b) {
      this.fromB = b;
      valueFlags.replace("fromb", true);
    }

    float getToR() {
      return toR;
    }

    void setToR(float r) {
      this.toR = r;
      valueFlags.replace("tor", true);
    }

    float getToG() {
      return toG;
    }

    void setToG(float g) {
      this.toG = g;
      valueFlags.replace("tog", true);
    }

    float getToB() {
      return toB;
    }

    void setToB(float b) {
      this.toB = b;
      valueFlags.replace("tob", true);
    }

    int getStart() {
      return start;
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    int getEnd() {
      return end;
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }
  }

  class ScaleByInfo extends Inputable {
    private String name;
    private float fromSx;
    private float fromSy;
    private float toSx;
    private float toSy;
    private int start;
    private int end;

    ScaleByInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromsx", false);
      valueFlags.put("fromsy", false);
      valueFlags.put("tosx", false);
      valueFlags.put("tosy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    String getName() {
      return name;
    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    float getFromXScale() {
      return fromSx;
    }

    void setFromXScale(float sx) {
      this.fromSx = sx;
      valueFlags.replace("fromsx", true);
    }

    float getFromYScale() {
      return fromSy;
    }

    void setFromYScale(float sy) {
      this.fromSy = sy;
      valueFlags.replace("fromsy", true);
    }

    float getToXScale() {
      return toSx;
    }

    void setToXScale(float sx) {
      this.toSx = sx;
      valueFlags.replace("tosx", true);
    }

    float getToYScale() {
      return toSy;
    }

    void setToYScale(float sy) {
      this.toSy = sy;
      valueFlags.replace("tosy", true);
    }

    int getStart() {
      return start;
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    int getEnd() {
      return end;
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }
  }

  class SloInfo extends Inputable {
    private int tempo;
    private int start;
    private int end;

    SloInfo() {
      super();
      valueFlags.put("speed", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);
    }

    int getTempo() {
      return tempo;
    }

    void setTempo(int tempo) {
      this.tempo = tempo;
      valueFlags.replace("speed", true);
    }

    int getStart() {
      return start;
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    int getEnd() {
      return end;
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }
  }
}
