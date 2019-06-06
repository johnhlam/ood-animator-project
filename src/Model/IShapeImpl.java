package Model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IShape. Stores motions as a list of IMotions. Motions are added to this
 * shape in any order, but its list will invariably be ordered based on start tick.
 */
public class IShapeImpl implements IShape {

  private final String id;
  private final double width;
  private final double height;
  private final double x;
  private final double y;
  private final Color color;
  private final List<IMotion> motionList;
  private final ShapeType type;

  /**
   * Constructor to create an IShapeImpl object. Takes in the ID, shape type, size, position, and
   * color and initializes them. Also initializes an empty list of motions.
   *
   * @param id the ID of this shape
   * @param type the type of shape this is
   * @param width the width of this shape
   * @param height the height of this shape
   * @param x the x position of this shape
   * @param y the y position of this shape
   * @param color the color of this shape
   * @throws IllegalArgumentException if arguments are null, or the width or height are negative
   */
  public IShapeImpl(String id, ShapeType type, double width, double height, double x, double y,
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
    this.motionList = new ArrayList<>();
  }

  /**
   * Along with the specifications in the interface, this method also prints each motion that this
   * shape undergoes. It outputs the start and end time, size, location, and color in a sequence of
   * lines for each motion.
   *
   * @return string representation of the animation
   */
  @Override
  public String printMotions() {
    StringBuilder builder = new StringBuilder();

    builder.append("shape ").append(this.id).append(" ").append(ShapeType.typeToString(this.type))
            .append("\n");

    for (IMotion curMotion : this.motionList) {
      builder.append("motion ")
              .append(this.id)
              .append(" ")
              .append(curMotion.printMotion())
              .append("\n");
    }

    // Removes the last \n in the builder
    // builder.delete(builder.length() - 2, builder.length() - 1);
    return builder.toString();

  }

  /**
   * Creates the shape at the given tick based on this shape's motions. If no motion is found at
   * the given tick, the shape remains the same as its previous motion's final state.
   */
  @Override
  public IReadOnlyShape getShapeAtTick(int tick) throws IllegalArgumentException {
    // left blank until we receive tweening function.
    return null;
  }

  /**
   * Adds a motion to this shape's list of motions such that the list of motions remains sorted.
   * Throws an exception if the given motion has overlapping ticks with the motions in the list.
   *
   * @throws IllegalArgumentException if the given motion overlaps with pre-existing motions
   */
  @Override
  public void addMotion(IMotion motion) throws IllegalArgumentException {
    if (motion == null) {
      throw new IllegalArgumentException("Given motion cannot be null for addMotion");
    }

    if (this.hasTickOverlap(motion)) {
      throw new IllegalArgumentException("Given motion has overlapping ticks");
    }

    this.insertMotion(motion);

  }

  /**
   * Inserts the given motion into this shape's motions based on start tick time. If the given
   * motion does not come before an existing motion, it is placed at the end of the list
   *
   * @param motion the motion to add
   */
  private void insertMotion(IMotion motion) {
    for (int i = 0; i < this.motionList.size(); i++) {
      IMotion curMotion = this.motionList.get(i);
      if (motion.getStartTick() < curMotion.getStartTick()) {
        this.motionList.add(i, motion);
        // Returns because you don't have to iterate through the rest of the list
        return;
      }
    }

    // If it reaches here, then the given motion's start tick is after all of this.motionList's
    // endTicks, and should thus be the last motion
    this.motionList.add(motion);
  }

  /**
   * Determines if any motions in this shape's motions overlap with the given motion.
   *
   * @param motion the motion to check for overlap
   * @return true if there is overlap, false otherwise
   */
  private boolean hasTickOverlap(IMotion motion) {
    for (IMotion curMotion : this.motionList) {
      if (this.areTicksOverlapping(motion, curMotion)) {
        // Returns true to break out of the loop
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if two motions are overlapping in ticks.
   *
   * @param motionToAdd motion being added to the list
   * @param existingMotion an existing motion already in the list
   * @return true if there is overlap, false otherwise
   */
  private boolean areTicksOverlapping(IMotion motionToAdd, IMotion existingMotion) {
    int addStartTick = motionToAdd.getStartTick();
    int addEndTick = motionToAdd.getEndTick();
    int existingStartTick = existingMotion.getStartTick();
    int existingEndTick = existingMotion.getEndTick();
    return (addStartTick > existingStartTick && addStartTick < existingEndTick)
            || (addEndTick > existingStartTick && addEndTick < existingEndTick);
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


}
