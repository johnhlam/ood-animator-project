package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IModelShape. Stores animations as a list of keyframes. Keyframes are added
 * to this shape in any order, but its list of keyframes will invariably be ordered based on start
 * tick.
 */
public class IModelShapeImpl implements IModelShape {

  private final String id;
  private final double width;
  private final double height;
  private final double x;
  private final double y;
  private final Color color;
  private final List<IKeyframe> keyframeList;
  private final ShapeType type;

  /**
   * Constructor to create an IModelShapeImpl object. Takes in the ID, shape type, size, position,
   * and color and initializes them. Also initializes an empty list of keyframes.
   *
   * @param id     the ID of this shape
   * @param type   the type of shape this is
   * @param width  the width of this shape
   * @param height the height of this shape
   * @param x      the x position of this shape
   * @param y      the y position of this shape
   * @param color  the color of this shape
   * @throws IllegalArgumentException if arguments are null, or the width or height are negative
   */
  public IModelShapeImpl(String id, ShapeType type, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException {

    if (id == null || type == null || color == null) {
      throw new IllegalArgumentException("No null args allowed.");
    }

    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Given width and/or height are negative for shape");
    }

    this.id = id;
    this.type = type;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.color = color;
    this.keyframeList = new ArrayList<>();
  }

  /**
   * Along with the specifications in the interface, this method also prints each motion that this
   * shape undergoes. It outputs the start and end time, size, location, and color in a sequence of
   * lines for each motion.
   *
   * @return string representation of the animation
   */
  @Override
  public String printKeyframes() {
    StringBuilder builder = new StringBuilder();

    builder.append("shape ")
        .append(this.id).append(" ")
        .append(ShapeType.typeToString(this.type));

    if (!this.keyframeList.isEmpty()) {
      // Adds the contents of the first keyframe to the builder
      builder.append("\n")
          .append("motion ")
          .append(this.id)
          .append(" ")
          .append(this.keyframeList.get(0).printKeyframe())
          .append("\t");

      // Starts at 1 to exclude the first keyframe, and ends at size - 1 to exclude the last
      // keyframe
      for (int i = 1; i < keyframeList.size() - 1; i++) {
        IKeyframe curKeyframe = this.keyframeList.get(i);
        builder.append(curKeyframe.printKeyframe())
            .append("\n")
            .append("motion ")
            .append(this.id)
            .append(" ")
            .append(curKeyframe.printKeyframe())
            .append("\t");
      }

      // Adds the contents of the last keyframe to the builder
      builder.append(this.keyframeList.get(this.keyframeList.size() - 1).printKeyframe());
    }

    return builder.toString();
  }

  /**
   * Creates the shape at the given tick based on this shape's motions. If no motion is found
   * at the
   * given tick, the shape remains the same as its previous motion's final state.
   *
   * @throws IllegalArgumentException if the given tick is negative
   */
  @Override
  public IReadOnlyShape getShapeAtTick(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Given tick to getShapeAtTick is negative");
    }

    double xAtTick;
    double yAtTick;
    double widthAtTick;
    double heightAtTick;

    IModelShape shapeToReturn = null;

    // If there are no keyframes in the list, then returns the initial state of the shape.
    if (this.keyframeList.isEmpty() || tick < this.keyframeList.get(0).getTick()
        || tick > this.keyframeList.get(this.keyframeList.size() - 1).getTick()) {
      return this;

      // If the given tick is after all keyframes, then returns the state of the shape at the
      // last keyframe
    } else if (this.keyframeList.size() == 1) {
      IKeyframe keyframe = this.keyframeList.get(0);
      shapeToReturn = new IModelShapeImpl(this.id, this.type, keyframe.getWidth(),
          keyframe.getHeight(), keyframe.getX(), keyframe.getY(), keyframe.getColor());
    } else {
      shapeToReturn = this.getShapeBetweenFrames(tick);
    }

    return shapeToReturn;
  }

  private IModelShape getShapeBetweenFrames(int tick) {
    double xAtTick;
    double yAtTick;
    double widthAtTick;
    double heightAtTick;
    double redAtTick;
    double greenAtTick;
    double blueAtTick;
    IModelShape shapeToReturn = null;

    for (int i = 0; i < this.keyframeList.size() - 1; i++) {
      IKeyframe curKeyframe = this.keyframeList.get(i);
      IKeyframe nextKeyframe = this.keyframeList.get(i + 1);

      if (curKeyframe.getTick() <= tick && nextKeyframe.getTick() >= tick) {
        int startTick = curKeyframe.getTick();
        int endTick = nextKeyframe.getTick();

        xAtTick = this.calculateParamAtTick(curKeyframe.getX(), nextKeyframe.getX(),
            tick, startTick, endTick);
        yAtTick = this.calculateParamAtTick(curKeyframe.getY(), nextKeyframe.getY(),
            tick, startTick, endTick);
        widthAtTick = this
            .calculateParamAtTick(curKeyframe.getWidth(), nextKeyframe.getWidth(),
                tick, startTick, endTick);
        heightAtTick = this
            .calculateParamAtTick(curKeyframe.getHeight(), nextKeyframe.getHeight(),
                tick, startTick, endTick);

        Color colorAtStart = curKeyframe.getColor();
        Color colorAtEnd = nextKeyframe.getColor();

        redAtTick = this.calculateParamAtTick(colorAtStart.getRed(),
            colorAtEnd.getRed(), tick, startTick, endTick);
        greenAtTick = this.calculateParamAtTick(colorAtStart.getGreen(),
            colorAtEnd.getGreen(), tick, startTick, endTick);
        blueAtTick = this.calculateParamAtTick(colorAtStart.getBlue(),
            colorAtEnd.getBlue(), tick, startTick, endTick);

        shapeToReturn = new IModelShapeImpl(this.id,
            this.type,
            widthAtTick,
            heightAtTick,
            xAtTick,
            yAtTick,
            new Color((int) redAtTick, (int) greenAtTick, (int) blueAtTick));
      }
    }

    return shapeToReturn;
  }

  /**
   * Given a start and end parameter and a tick, uses the tweening function to return the value of
   * the parameter between the given start and end tick.
   *
   * @param startParam start parameter of a motion
   * @param endParam   end parameter of a motion
   * @param tick       current tick
   * @param startTick  start tick of a motion
   * @param endTick    end tick of a motion
   * @return the calculated value of the parameter
   */
  private double calculateParamAtTick(double startParam, double endParam, int tick,
      int startTick,
      int endTick) {
    double frac1 = ((double) (endTick - tick)) / (endTick - startTick);
    double frac2 = ((double) (tick - startTick)) / (endTick - startTick);

    return Math.round(startParam * frac1 + endParam * frac2);
  }

  @Override
  public String getID() {
    return this.id;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  @Override
  public ShapeType getType() {
    return this.type;
  }

  /**
   * Adds a keyframe with the given parameters to this shape. The keyframe will be inserted into
   * the list based on its tick, such that the list of keyframes is always sorted by tick. If there
   * is already an existing keyframe at the given tick, then an error will be thrown.
   *
   * @param tick   is the tick of the keyframe to be added
   * @param width  is the width of the keyframe to be added
   * @param height is the height of the keyframe to be added
   * @param x      is the x value of the keyframe to be added
   * @param y      is the y value of the keyframe to be added
   * @param color  is the color of the keyframe to be added
   * @throws IllegalArgumentException if the given Color is null, or if the tick, width, and/or
   *                                  height are negative, or if there is already an existing
   *                                  keyframe at the given tick.
   */
  @Override
  public void addKeyframe(int tick, double width, double height, double x, double y, Color color)
      throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Given Color to addKeyframe is null");
    }
    if (tick < 0 || width < 0 || height < 0) {
      throw new IllegalArgumentException("Given tick, width, and/or height to addKeyframe "
          + "cannot be negative");
    }

    // If the keyframe list is empty, or if given tick is after the last keyframe's tick, then
    // adds the keyframe (with the given params) to the list
    if (keyframeList.isEmpty()
        || (tick > this.keyframeList.get(this.keyframeList.size() - 1).getTick())) {
      this.keyframeList.add(new IKeyframeImpl(tick, width, height, x, y, color));

      // If the given tick is before the first keyframe's tick, then adds the keyframe (with the
      // given params) to the beginning of the list
    } else if (tick < this.keyframeList.get(0).getTick()) {
      this.keyframeList.add(0, new IKeyframeImpl(tick, width, height, x, y, color));

      // Else, the given tick lies somewhere between in the list of keyframes, so calls
      // insertKeyframe to insert the keyframe so that the list remains sorted
    } else {
      this.insertKeyframe(tick, width, height, x, y, color);
    }
  }

  /**
   * Inserts the given keyframe into the the list of keyframes, such that the list of keyframes
   * remains sorted by tick. If a keyframe exists with the same tick as the given keyframe, instead
   * modifies that keyframe to contain the values of the given keyframe.
   *
   * @param tick   is the tick of the keyframe to be added
   * @param width  is the width of the keyframe to be added
   * @param height is the height of the keyframe to be added
   * @param x      is the x value of the keyframe to be added
   * @param y      is the y value of the keyframe to be added
   * @param color  is the color of the keyframe to be added
   * @throws IllegalArgumentException if a keyframe at the given tick already exists for this shape
   */
  private void insertKeyframe(int tick, double width, double height,
      double x, double y, Color color) throws IllegalArgumentException {
    // Checks for any coinciding keyframes (i.e. keyframes for the same tick)
    // Subtracts one from the loop termination condition because the loop checks the current
    // keyframe and the next keyframe
    for (int i = 0; i < this.keyframeList.size() - 1; i++) {
      IKeyframe keyframe = this.keyframeList.get(i);
      if (keyframe.getTick() == tick
          && Math.abs(keyframe.getWidth() - width) < 0.0001
          && Math.abs(keyframe.getHeight() - height) < 0.0001
          && Math.abs(keyframe.getX() - x) < 0.0001
          && Math.abs(keyframe.getY() - y) < 0.0001
          && keyframe.getColor().equals(color)) {
        // Does nothing if all of the given fields are the same
      } else if (keyframe.getTick() == tick) {
        throw new IllegalArgumentException("There is already a keyframe at the given tick: "
            + tick + ", for shape " + this.id);

        //If the given keyframe is between the current keyframe and the next keyframe, adds it in
      } else if (this.keyframeList.get(i).getTick() < tick
          && this.keyframeList.get(i + 1).getTick() > tick) {
        // Adds the keyframe in between the current and the next keyframe
        this.keyframeList.add(i + 1,
            new IKeyframeImpl(tick, width, height, x, y, color));
        // Returns to exit the loop
        return;
      }
    }

    if (this.keyframeList.get(this.keyframeList.size() - 1).getTick() == tick
        && Math.abs(this.keyframeList.get(this.keyframeList.size() - 1).getWidth() - width) < 0.0001
        && Math.abs(this.keyframeList.get(this.keyframeList.size() - 1).getHeight() - height)
        < 0.0001
        && Math.abs(this.keyframeList.get(this.keyframeList.size() - 1).getX() - x) < 0.0001
        && Math.abs(this.keyframeList.get(this.keyframeList.size() - 1).getY() - y) < 0.0001
        && this.keyframeList.get(this.keyframeList.size() - 1).getColor().equals(color)) {
      // Does nothing if all of the given fields are the same
    }
    // Checks to make sure that the given tick isn't the same as the last keyframe, since the
    // loop does not check the last keyframe
    else if (this.keyframeList.get(this.keyframeList.size() - 1).getTick() == tick) {
      throw new IllegalArgumentException("There is already a keyframe at the given tick: "
          + tick + ", for shape " + this.id);
    }

  }

  @Override
  public void removeKeyframe(int tick) {
    for (int i = 0; i < this.keyframeList.size(); i++) {
      if (this.keyframeList.get(i).getTick() == tick) {
        this.keyframeList.remove(i);
        return;
      }
    }
  }

  @Override
  public List<IKeyframe> getKeyframes() {
    return new ArrayList<>(this.keyframeList);
  }
}

