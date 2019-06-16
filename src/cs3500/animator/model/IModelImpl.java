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


  /**
   * If no shape is found with the given ID or no motion is found with the given start tick, nothing
   * is done.
   */
  @Override
  public void removeMotionAtStartTick(String id, int startTick) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }

    if (startTick < 0) {
      throw new IllegalArgumentException("Start tick cannot be less than 0.");
    }

    for (IModelShape shape : this.shapes) {
      if (shape.getID().equals(id)) {
        shape.removeMotion(startTick);
      }
    }
  }

  /**
   * If no motion is found with the given start tick, nothing is done.
   */
  @Override
  public List<IMotion> getMotionsAtTick(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick cannot be 0.");
    }

    List<IMotion> motionsToReturn = new ArrayList<IMotion>();
    for (IModelShape shape : this.shapes) {
      List<IMotion> motions = shape.getMotions();
      for (int i = 0; i < motions.size(); i++) {
        IMotion curMotion = motions.get(i);
        if (tick > curMotion.getStartTick() && tick < curMotion.getEndTick()) {
          motionsToReturn.add(curMotion);
          break;
        }
      }
    }
    return motionsToReturn;
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
      builder.append(s.printMotions())
              .append("\n\n");
    }

    // Removes the last \n in the builder
    if (!builder.toString().equals("")) {
      builder.delete(builder.length() - 2, builder.length());
    }

    return builder.toString();
  }

  /**
   * Adds a motion to the shape corresponding with the given ID. Motions can be given in any order,
   * and the shape will insert them in such a way that motions are sorted based on start ticks.
   * Throws an exception if the given motion's start or end ticks overlap with pre-existing motions.
   * The model will also update the largest x and y values seen based on the start and end of the
   * motion. INVARIANT: All existing motions in the shapes will be chronologically ordered, with no
   * overlap in ticks.
   *
   * @throws IllegalArgumentException if the given motion's start or end ticks overlap with
   *                                  pre-existing motions or if the motion leaves a gap between the
   *                                  previous motion.
   */
  @Override
  public void addMotion(String id,
                        int startTick, double startX, double startY, double startWidth,
                        double startHeight,
                        Color startColor,
                        int endTick, double endX, double endY, double endWidth, double endHeight,
                        Color endColor)
          throws IllegalArgumentException {

    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for addMotion cannot be null.");
    }

    if (startTick < 0 || endTick < 0 || startWidth < 0 || startHeight < 0
            || endWidth < 0 || endHeight < 0) {
      throw new IllegalArgumentException("Ticks and sizes cannot be negative.");
    }

    if (endTick < startTick) {
      throw new IllegalArgumentException("End tick must come after start tick");
    }

    for (IModelShape cur : this.shapes) {
      if (cur.getID().equals(id)) {
        cur.addMotion(
                new IMotionImpl(
                        startTick, startWidth, startHeight, startX, startY, startColor,
                        endTick, endWidth, endHeight, endX, endY, endColor));

        // Does not need to iterate through the rest of the list if a shape with the given id has
        // been found
        return;
      }
    }
    // throw an exception if it reaches here, meaning the ID was not in the list
    throw new IllegalArgumentException("ID could not be found");
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
                                                  int b2) {
      if (name == null) {
        throw new IllegalArgumentException("Arguments for addMotion cannot be null");
      }

      maxX = Math.max(maxX, x1 + w1);
      maxX = Math.max(maxX, x2 + w2);
      maxY = Math.max(maxY, y1 + h1);
      maxY = Math.max(maxY, y2 + h2);

      for (IModelShape cur : this.shapes) {
        if (cur.getID().equals(name)) {
          cur.addMotion(
                  new IMotionImpl(
                          t1, w1, h1, x1, y1, new Color(r1, g1, b1),
                          t2, w2, h2, x2, y2, new Color(r2, g2, b2)));

          // Does not need to iterate through the rest of the list if a shape with the given id has
          // been found
          return this;
        }
      }

      // throw an exception if it reaches here, meaning the ID was not in the list
      throw new IllegalArgumentException("Given ID could not be found");
    }

    /**
     * Left blank as instructed by professors and TAs.
     */
    @Override
    public AnimationBuilder<IModelImpl> addKeyframe(String name, int t, int x, int y, int w, int h,
                                                    int r, int g, int b) {
      return this;
    }
  }
}
