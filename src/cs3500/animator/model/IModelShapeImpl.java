package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IModelShape. Stores motions as a list of IMotions. Motions are added to this
 * shape in any order, but its list will invariably be ordered based on start tick.
 */
public class IModelShapeImpl implements IModelShape {

  private final String id;
  private final double width;
  private final double height;
  private final double x;
  private final double y;
  private final Color color;
  private final List<IMotion> motionList;
  private final ShapeType type;

  /**
   * Constructor to create an IModelShapeImpl object. Takes in the ID, shape type, size, position,
   * and color and initializes them. Also initializes an empty list of motions.
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
    builder.delete(builder.length() - 1, builder.length());
    return builder.toString();

  }

  /**
   * Creates the shape at the given tick based on this shape's motions. If no motion is found at the
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
    double redAtTick;
    double greenAtTick;
    double blueAtTick;
    IModelShape shapeToReturn = null;

    // If the given tick is before any animations, or if there are no motions in the list,
    // then returns the initial state of the shape.
    if (this.motionList.isEmpty() || tick < this.motionList.get(0).getStartTick()) {
      return this;
    } else if (tick > this.motionList.get(this.motionList.size() - 1).getEndTick()) {
      // If the given tick is after all of the animations, then returns the last state of the shape
      // (based on the last motion in the list)

      IMotion lastMotion = this.motionList.get(this.motionList.size() - 1);
      int startTick = lastMotion.getStartTick();
      int lastTick = lastMotion.getEndTick();

      xAtTick = lastMotion.getEndX();
      yAtTick = lastMotion.getEndY();
      widthAtTick = lastMotion.getEndWidth();
      heightAtTick = lastMotion.getEndHeight();
      Color lastColor = lastMotion.getEndColor();

      shapeToReturn = new IModelShapeImpl(this.id,
          this.type,
          widthAtTick,
          heightAtTick,
          xAtTick,
          yAtTick,
          lastColor);
    } else {

      // If the given tick is not before all of the animations, and isn't after all of the
      // animations, then it must be during the animations
      for (IMotion curMotion : this.motionList) {
        if (tick >= curMotion.getStartTick() && tick <= curMotion.getEndTick()) {
          int startTick = curMotion.getStartTick();
          int endTick = curMotion.getEndTick();

          xAtTick = this.calculateParamAtTick(curMotion.getStartX(), curMotion.getEndX(),
              tick, startTick, endTick);
          yAtTick = this.calculateParamAtTick(curMotion.getStartY(), curMotion.getEndY(),
              tick, startTick, endTick);
          widthAtTick = this
              .calculateParamAtTick(curMotion.getStartWidth(), curMotion.getEndWidth(),
                  tick, startTick, endTick);
          heightAtTick = this
              .calculateParamAtTick(curMotion.getStartHeight(), curMotion.getEndHeight(),
                  tick, startTick, endTick);
          redAtTick = this.calculateParamAtTick(curMotion.getStartColor().getRed(),
              curMotion.getEndColor().getRed(), tick, startTick, endTick);
          greenAtTick = this.calculateParamAtTick(curMotion.getStartColor().getGreen(),
              curMotion.getEndColor().getGreen(), tick, startTick, endTick);
          blueAtTick = this.calculateParamAtTick(curMotion.getStartColor().getBlue(),
              curMotion.getEndColor().getBlue(), tick, startTick, endTick);
          shapeToReturn = new IModelShapeImpl(this.id,
              this.type,
              widthAtTick,
              heightAtTick,
              xAtTick,
              yAtTick,
              new Color((int) redAtTick, (int) greenAtTick, (int) blueAtTick));
          // Returns here to exit the loop
          return shapeToReturn;
        }
      }
    }

    return shapeToReturn;
  }

  private double calculateParamAtTick(double startParam, double endParam, int tick, int startTick,
      int endTick) {
    double frac1 = ((double) (endTick - tick)) / (endTick - startTick);
    double frac2 = ((double) (tick - startTick)) / (endTick - startTick);

    double newParam = startParam * frac1 + endParam * frac2;

    return newParam;
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
   * motion does not come before an existing motion, it is placed at the end of the list.
   * INVARIANT: Adjacent motions (two motions with same end tick and start tick) will always have
   * the according states.
   *
   * @param motion the motion to add
   */
  private void insertMotion(IMotion motion) {
    for (int i = 0; i < this.motionList.size(); i++) {
      IMotion curMotion = this.motionList.get(i);
      if (motion.getStartTick() < curMotion.getStartTick()) {
        this.handleInsertion(motion, i);
        // Returns because you don't have to iterate through the rest of the list
        return;
      }
    }
    // If it reaches here, then the given motion's start tick is after all of this.motionList's
    // endTicks, and should thus be the last motion
    this.handleInsertion(motion, this.motionList.size());
  }

  /**
   * Adds the given motion if the motion at the given index in the existing motion list has the same
   * end state as this motion's start state. If they do not have overlapping ticks, then it is
   * added. Additionally, if the index is 0, it is added as well.
   *
   * @param motion is the motion to be inserted into this.motionList
   * @param i      is the index where the given motion is to be added
   * @throws IllegalArgumentException if the motion is adjacent to another and does not have
   *                                  matching states
   */
  private void handleInsertion(IMotion motion, int i) throws IllegalArgumentException {
    if (i == 0 || this.sameStateIfAdjacent(motion, motionList.get(i - 1))) {
      this.motionList.add(i, motion);
    } else if (i == this.motionList.size() && this.sameStateIfAdjacent(motion,
        motionList.get(i - 1))) {
      this.motionList.add(motion);
    } else {
      throw new IllegalArgumentException("Adjacent motions cannot disagree with end and start " +
          "states.");
    }
  }

  /**
   * Determines if two given motions are adjacent (overlapping end and start ticks), and if they
   * are, check if they have the same state. If they aren't, return true.
   *
   * @param motion     the motion whose start tick is in question
   * @param prevMotion the motion whose end tick is in question
   * @return true if they two motions are adjacent and have the same state or if they aren't
   *     adjacent, and false otherwise
   */
  private boolean sameStateIfAdjacent(IMotion motion, IMotion prevMotion) {
    if (motion.getStartTick() == prevMotion.getEndTick()) {
      return motion.getStartX() == prevMotion.getEndX()
          && motion.getStartY() == prevMotion.getEndY()
          && motion.getStartWidth() == prevMotion.getEndWidth()
          && motion.getStartHeight() == prevMotion.getEndHeight()
          && motion.getStartColor().equals(prevMotion.getEndColor());
    } else {
      return true;
    }
  }

  /**
   * Determines if any motions in this shape's motions overlap with the given motion.
   *
   * @param motion the motion to check for overlap
   * @return true if there is overlap, false otherwise
   */
  private boolean hasTickOverlap(IMotion motion) {
    for (IMotion curMotion : this.motionList) {
      if (this.areTicksOverlapping(motion, curMotion) || this.areTicksOverlapping(curMotion,
          motion)) {
        // Returns true to break out of the loop
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if two motions are overlapping in ticks. Note - kept in Shape class since the logic for
   * overlapping motions should be kept in the shape, not in the motion.
   *
   * @param motionToAdd    motion being added to the list
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

  @Override
  public ShapeType getType() {
    return this.type;
  }


  //TODO: tests for this
  @Override
  public List<IMotion> getMotions() {
    return new ArrayList<>(this.motionList);
  }
}
