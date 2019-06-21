package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IModel. Holds all the shapes needed to be drawn in the animation as an
 * ArrayList of shapes. The shapes themselves hold the knowledge on how they animate.
 */
public class IModelImpl implements IModel {

  // A list of shapes that the model is currently representing as drawings/animations.
  private final List<IModelShape> shapes;

  private final int topX;
  private final int topY;
  private final int width;
  private final int height;
  private final double maxX;
  private final double maxY;

  /**
   * Constructs an instance of this model with the given parameters.
   *
   * @param topX   is the lowest (left most) x-coordinate of the canvas
   * @param topY   is the lowest (top most) y-coordinate of the canvas
   * @param width  is the width of the canvas
   * @param height is the height of the canvas
   * @param maxX   is the highest (right most) x-coordinate of the canvas
   * @param maxY   is the highest (bottom most) y-coordinate of the canvas
   * @param shapes is the list of shapes that the model uses to represents drawings/animations
   * @throws IllegalArgumentException if the given list of shapes is null, or if the given width
   *                                  and/or height are not positive
   */
  public IModelImpl(int topX, int topY, int width, int height, int maxX,
                    int maxY, List<IModelShape> shapes) throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("Given list of shapes for IModelImpl cannot be null");
    }

    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Given width and height to IModelImpl cannot be negative");
    }

    this.topX = topX;
    this.topY = topY;
    this.width = width;
    this.height = height;
    this.maxX = maxX;
    this.maxY = maxY;
    this.shapes = shapes;
  }

  /**
   * Constructs a default model with 0 for all numeric fields, and an empty list of shapes.
   */
  public IModelImpl() {
    this(0, 0, 0, 0, 0, 0, new ArrayList<>());
  }

  @Override
  public List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick cannot be negative.");
    }
    ArrayList<IReadOnlyShape> shapesAtTick = new ArrayList<>();

    for (IModelShape shape : this.shapes) {
      shapesAtTick.add(shape.getShapeAtTick(tick));
    }

    return shapesAtTick;
  }

  @Override
  public int getX() {
    return this.topX;
  }

  @Override
  public int getY() {
    return this.topY;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxX() {
    return (int) this.maxX;
  }

  @Override
  public int getMaxY() {
    return (int) this.maxY;
  }

  @Override
  public List<IReadOnlyShape> getShapes() {
    return new ArrayList<IReadOnlyShape>(this.shapes);
  }

  @Override
  public int getFinalTick() {
    int res = 0;
    for (IModelShape shape : this.shapes) {
      if (!shape.getKeyframes().isEmpty()) {
        res = Math.max(res, shape.getKeyframes().get(shape.getKeyframes().size() - 1).getTick());
      }
    }
    return res;
  }


  /**
   * Prints out each shape in this model with its ID followed by the motions it contains. Each
   * motion is on a new line, and outputs start and ending information about the shape. This
   * includes tick, position, size, and color.
   *
   * @return the string representation of the animation
   */
  @Override
  public String printAnimations() {
    StringBuilder builder = new StringBuilder();

    for (IModelShape s : this.shapes) {
      builder.append(s.printKeyframes())
              .append("\n\n");
    }

    // Removes the last \n in the builder
    if (!builder.toString().equals("")) {
      builder.delete(builder.length() - 2, builder.length());
    }

    return builder.toString();
  }

  /**
   * Adds a keyframe to the shape corresponding with the given ID. Keyframes can be given in any
   * order, and the shape will insert them in such a way that keyframes are sorted based on tick. If
   * there is an already existing keyframe for the given tick, for the shape with the given id,
   * an error will be thrown.  The model will also update the largest x and y values seen based
   * on the values of the keyframe.
   * INVARIANT: All existing keyframes in the shapes will be chronologically ordered, with no
   * overlap in ticks.
   *
   * @param id     is the id of the shape the keyframe will be added to
   * @param tick   is the tick value of the keyframe
   * @param width  is the width value of the keyframe
   * @param height is the height value of the keyframe
   * @param x      is the x value of the keyframe
   * @param y      is the y value of the keyframe
   * @param color  is the color of the keyframe
   * @throws IllegalArgumentException if the given id is null, or if the given tick, width, or
   *                                  height is negative, or if there is already an existing keyfram
   *                                  at the given tick.
   */
  @Override
  public void addKeyframe(String id, int tick, double x, double y,  double width, double height,
      Color color) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("Given id to addKeyframe is null");
    }
    if (tick < 0 || width < 0 || height < 0) {
      throw new IllegalArgumentException("Given tick, width, and/or height to add keyframe cannot"
              + " be negative");
    }

    for (IModelShape cur : this.shapes) {
      if (cur.getID().equals(id)) {
        cur.addKeyframe(tick, width, height, x, y, color);
        // Returns to exit the loop and the method
        return;
      }
    }

    // throw an exception if it reaches here, meaning the ID was not in the list
    throw new IllegalArgumentException("ID could not be found in addKeyframe");
  }

  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("Given id to removeKeyframe cannot be null");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Given tick to removeKeyframe cannot be negative");
    }

    for (IModelShape shape : this.shapes) {
      if (shape.getID().equals(id)) {
        shape.removeKeyframe(tick);
      }
    }
  }

  @Override
  public List<IKeyframe> getKeyframesAtTick(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Given tick to getKeyframesAtTick is negative");
    }

    List<IKeyframe> keyframeList = new ArrayList<>();

    for (IModelShape s : this.shapes) {
      List<IKeyframe> sKeyframes = s.getKeyframes();

      for (IKeyframe kf : sKeyframes) {
        if (kf.getTick() == tick) {
          keyframeList.add(kf);
          // Breaks out of the loop, since there can only be one keyframe per shape with the
          // given tick
          break;
        }
      }
    }
    return keyframeList;
  }

  @Override
  public void addShape(String id, ShapeType type, double width, double height, double x, double y,
                       Color color) throws IllegalArgumentException {
    if (id == null || type == null || color == null) {
      throw new IllegalArgumentException("Arguments for addShape cannot be null.");
    }

    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Given width and/or height are negative for addShape");
    }

    for (IModelShape shape : this.shapes) {
      if (shape.getID().equals(id)) {
        throw new IllegalArgumentException("Shape with same id already exists.");
      }
    }

    IModelShape newShape = new IModelShapeImpl(id, type, width, height, x, y, color);

    this.shapes.add(newShape);
  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("Given id to removeShape is null");
    }

    for (int i = 0; i < this.shapes.size(); i++) {
      IModelShape cur = this.shapes.get(i);

      // If you find the shape with the given id, you can remove it and return to break out of
      // the loop.
      if (cur.getID().equals(id)) {
        this.shapes.remove(i);
        return;
      }
    }

    // If you finish going through the list, and none of the shape's ids match up, then the shape
    // with the given id could not be found.
    throw new IllegalArgumentException("Shape with the given id, " + id + ", cannot be found.");
  }

  /**
   * Returns a Builder object that can build instances of this model.
   *
   * @return a new Builder object
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Represents an implementation of the AnimationBuilder that can construct instances of this model
   * implementation by setting parameters and adding motions/shapes. The model is constructed with
   * the build method.
   */
  public static final class Builder implements AnimationBuilder<IModelImpl> {

    private List<IModelShape> shapes;

    private int topX;
    private int topY;
    private int width;
    private int height;
    private int maxX;
    private int maxY;


    /**
     * Constructor for the Builder. Initializes the values it has to defaults. Defaults are
     * specified below.
     */
    public Builder() {
      // Initializes topX and topY to 0 as default values, and width and height to 200 as default
      // values
      this.topX = 0;
      this.topY = 0;
      this.width = 200;
      this.height = 200;
      this.maxX = 0;
      this.maxY = 0;
      this.shapes = new ArrayList<>();

    }

    @Override
    public IModelImpl build() {

      return new IModelImpl(this.topX, this.topY, this.width, this.height, this.maxX,
              this.maxY, this.shapes);
    }

    @Override
    public AnimationBuilder<IModelImpl> setBounds(int x, int y, int width, int height) {
      this.topX = x;
      this.topY = y;
      this.width = width;
      this.height = height;

      return this;
    }

    @Override
    public AnimationBuilder<IModelImpl> declareShape(String name, String type) {

      if (name == null || type == null) {
        throw new IllegalArgumentException("Arguments for declareShape cannot be null");
      }

      ShapeType sType = ShapeType.stringToType(type);
      // Sets width, height, x, and y to 0 as default values, and the default color to black
      IModelShape shapeToAdd = new IModelShapeImpl(name, sType, 0, 0, 0, 0, Color.BLACK);

      this.shapes.add(shapeToAdd);
      return this;
    }

    /**
     * In this implementation, this method also updates the builder to keep track of the max x and y
     * coordinates of the animation.
     */
    @Override
    public AnimationBuilder<IModelImpl> addMotion(String name, int t1, int x1, int y1, int w1,
                                                  int h1, int r1, int g1, int b1, int t2, int x2,
                                                  int y2, int w2, int h2, int r2, int g2,
                                                  int b2) throws IllegalArgumentException {
      if (name == null) {
        throw new IllegalArgumentException("Arguments for addMotion cannot be null");
      }
      if (t1 < 0 || t2 < 0 || w1 < 0 || w2 < 0 || h1 < 0 || h2 < 0) {
        throw new IllegalArgumentException("Ticks, widths, and/or heights for add motion cannot "
                + "be negative");
      }
      if (t1 > t2) {
        throw new IllegalArgumentException("Given start tick to Builder#addMotion is after end "
            + "tick");
      }

      maxX = Math.max(maxX, x1 + w1);
      maxX = Math.max(maxX, x2 + w2);
      maxY = Math.max(maxY, y1 + h1);
      maxY = Math.max(maxY, y2 + h2);

      for (IModelShape cur : this.shapes) {
        if (cur.getID().equals(name)) {
            cur.addKeyframe(t1, w1, h1, x1, y1, new Color(r1, g1, b1));
            cur.addKeyframe(t2, w2, h2, x2, y2, new Color(r2, g2, b2));
          // Does not need to iterate through the rest of the list if a shape with the given id
          // has been found
          return this;
        }
      }

      // throw an exception if it reaches here, meaning the ID was not in the list
      throw new IllegalArgumentException("Given ID could not be found for addMotion");
    }

    /**
     * In this implementation, this method also updates the builder to keep track of the max x and y
     * coordinates of the animation.
     */
    @Override
    public AnimationBuilder<IModelImpl> addKeyframe(String name, int t, int x, int y, int w,
                                                    int h, int r, int g, int b) throws IllegalArgumentException {

      if (name == null) {
        throw new IllegalArgumentException("Arguments for addKeyframe cannot be null");
      }
      if (t < 0 || w < 0 || h < 0) {
        throw new IllegalArgumentException("Tick, width, and/or height for addKeyframe cannot be "
                + "negative");
      }

      maxX = Math.max(maxX, x + w);
      maxY = Math.max(maxY, y + h);

      for (IModelShape cur : this.shapes) {
        if (cur.getID().equals(name)) {
          cur.addKeyframe(t, w, h, x, y, new Color(r, g, b));

          // Does not need to iterate through the rest of the list if a shape with the given id has
          // been found
          return this;
        }
      }

      // throw an exception if it reaches here, meaning the ID was not in the list
      throw new IllegalArgumentException("Given ID could not be found for addKeyframe");
    }
  }
}


