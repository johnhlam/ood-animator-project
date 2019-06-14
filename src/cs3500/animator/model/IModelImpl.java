package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IModel. Holds all the shapes needed to be drawn in the animation as an
 * ArrayList of shapes. The shapes themselves hold the knowledge on how they animate.
 */
public class IModelImpl implements IModel {

  // A list of shapes that the model is currently representing as drawings/animations.
  private final List<IModelShape> shapes;

  //
  private final int topX;
  private final int topY;
  private final int width;
  private final int height;
  private double maxX;
  private double maxY;

  // TODO: Allow for no gaps by having motions come in order

  public IModelImpl(int topX, int topY, int width, int height, List<IModelShape> shapes) {
    this.topX = topX;
    this.topY = topY;
    this.width = width;
    this.height = height;
    this.shapes = shapes;
  }

  public IModelImpl() {
    this(0, 0, 0, 0, new ArrayList<>());
  }

  @Override
  public List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException {
    this.ensureNoGaps();
    ArrayList<IReadOnlyShape> shapesAtTick = new ArrayList<>();

    for (IModelShape shape : this.shapes) {
      shapesAtTick.add(shape.getShapeAtTick(tick));
    }

    return shapesAtTick;
  }

  @Override
  public void removeMotionAtStartTick(int tick) throws IllegalArgumentException {
    //TODO: Fill in
  }

  @Override
  public List<IMotion> getMotionsAtTick(int tick) throws IllegalArgumentException {
    return null; // TODO: Fill in
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
    return (int)this.maxX;
  }

  @Override
  public int getMaxY() {
    return (int)this.maxY;
  }

  @Override
  public List<IReadOnlyShape> getShapes() {
    return new ArrayList<IReadOnlyShape>(this.shapes);
  }


  /**
   * Prints out each shape in this model with its ID followed by the motions it contains. Each
   * motion is on a new line, and ouputs start and ending information about the shape. This includes
   * tick, position, size, and color.
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
   * Throws an exception if the given motion's start or end ticks overlap with pre-existing
   * motions. The model will also update the largest x and y values seen based on the start and
   * end of the motion.
   * INVARIANT: All existing motions in the shapes will be chronologically ordered, with no overlap
   * in ticks.
   *
   * @throws IllegalArgumentException if the given motion's start or end ticks overlap with
   *                                  pre-existing motions.
   */
  @Override
  public void addMotion(String id,
      int startTick, double startX, double startY, double startWidth, double startHeight,
      Color startColor,
      int endTick, double endX, double endY, double endWidth, double endHeight, Color endColor)
      throws IllegalArgumentException {

    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for addMotion cannot be null.");
    }

    if (startTick < 0 || endTick < 0 || startWidth < 0 || startHeight < 0
        || endWidth < 0 || endHeight < 0) {
      throw new IllegalArgumentException("Ticks and sizes cannot be negative.");
    }

    if (endTick <= startTick) {
      throw new IllegalArgumentException("End tick must come after start tick");
    }

    // update largest x and y values
    maxX = Math.max(maxX, startX + startWidth);
    maxX = Math.max(maxX, endX + endWidth);
    maxY = Math.max(maxY, startY + startHeight);
    maxY = Math.max(maxY, endY + endHeight);

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

  public static Builder builder() {
    return new Builder();
  }

  public final static class Builder implements AnimationBuilder<IModelImpl> {

    private List<IModelShape> shapes;

    private int topX;
    private int topY;
    private int width;
    private int height;

    public Builder()  {
      // Initializes topX and topY to 0 as default values, and width and height to 200 as default
      // values
      this.topX = 0;
      this.topY = 0;
      this.width = 200;
      this.height = 200;
      this.shapes = new ArrayList<>();

    }

    @Override
    public IModelImpl build() {

      return new IModelImpl(this.topX, this.topY, this.width, this.height, this.shapes);
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

      if(name == null || type == null) {
        throw new IllegalArgumentException("Arguments for declareShape cannot be null");
      }

      ShapeType sType = ShapeType.stringToType(type);
      // Sets width, height, x, and y to 0 as default values, and the default color to black
      IModelShape shapeToAdd = new IModelShapeImpl(name, sType, 0, 0, 0, 0, Color.BLACK);

      this.shapes.add(shapeToAdd);
      return this;
    }

    @Override
    public AnimationBuilder<IModelImpl> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {
      if(name == null) {
        throw new IllegalArgumentException("Arguments for addMotion cannot be null");
      }

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

    @Override
    public AnimationBuilder<IModelImpl> addKeyframe(String name, int t, int x, int y, int w, int h,
        int r, int g, int b) {
      return this;
    }
  }
}
