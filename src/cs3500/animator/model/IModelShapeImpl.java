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
  private final List<IMotion> motionList; // TODO: Most likely will delete
  private final List<IKeyframe> keyframeList;
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
    this.keyframeList = new ArrayList<>();
  }

  /**
   * Along with the specifications in the interface, this method also prints each motion that this
   * shape undergoes. It outputs the start and end time, size, location, and color in a sequence of
   * lines for each motion.
   *
   * @return string representation of the animation
   */
  // TODO: May want to refactor->rename at some point
  @Override
  public String printMotions() {
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
        builder.append(this.keyframeList.get(i).printKeyframe())
            .append("\n")
            .append("motion ")
            .append(this.id)
            .append(" ")
            .append(this.keyframeList.get(i + 1).printKeyframe())
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
    public IReadOnlyShape getShapeAtTick (int tick) throws IllegalArgumentException {
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
      }
      else if (this.keyframeList.size() == 1) {
        IKeyframe keyframe = this.keyframeList.get(0);
        shapeToReturn = new IModelShapeImpl(this.id, this.type, keyframe.getWidth(),
                keyframe.getHeight(), keyframe.getX(), keyframe.getY(), keyframe.getColor());
      } else {
        shapeToReturn = this.getShapeBetweenFrames(tick);
      }

      return shapeToReturn;
    }

    private IModelShape getShapeBetweenFrames (int tick){
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
    private double calculateParamAtTick ( double startParam, double endParam, int tick,
    int startTick,
    int endTick){
      double frac1 = ((double) (endTick - tick)) / (endTick - startTick);
      double frac2 = ((double) (tick - startTick)) / (endTick - startTick);

      return Math.round(startParam * frac1 + endParam * frac2);
    }

    /**
     * Adds a motion to this shape's list of motions such that the list of motions remains sorted.
     * Throws an exception if the given motion has overlapping ticks with the motions in the list.
     *
     * @throws IllegalArgumentException if the given motion overlaps with pre-existing motions or if
     *                                  the motion is null
     */
    @Override
    public void addMotion (IMotion motion) throws IllegalArgumentException {
      if (motion == null) {
        throw new IllegalArgumentException("Given motion cannot be null for addMotion");
      }

      if (this.hasTickOverlap(motion)) {
        throw new IllegalArgumentException("Given motion has overlapping ticks");
      }

      this.insertMotion(motion);

      // TODO: Delete the method body above to commit fully to keyframes, and make the method take
      //  in raw parameters;; Not entirely sure if this method is necessary anymore

      // Adds the start keyframe
      this.addKeyframe(motion.getStartTick(), motion.getStartWidth(),
          motion.getStartHeight(), motion.getStartX(), motion.getStartY(), motion.getStartColor());

      // Adds the end keyframe
      this.addKeyframe(motion.getEndTick(), motion.getEndWidth(),
          motion.getEndHeight(), motion.getEndX(), motion.getEndY(), motion.getEndColor());

    }

    @Override
    public void removeMotion ( int startTick){
      for (int i = 0; i < this.motionList.size(); i++) {
        if (this.motionList.get(i).getStartTick() == startTick) {
          this.motionList.remove(this.motionList.get(i));
          return;
        }
      }
    }

    /**
     * Inserts the given motion into this shape's motions based on start tick time. If the given
     * motion does not come before an existing motion, it is placed at the end of the list.
     * INVARIANT:
     * Adjacent motions (two motions with same end tick and start tick) will always have the
     * corresponding states.
     *
     * @param motion the motion to add
     * @throws IllegalArgumentException if the given motion does not follow right after the previous
     *                                  motion in the list
     */
    private void insertMotion (IMotion motion) throws IllegalArgumentException {
      if (this.motionList.isEmpty()) {
        this.motionList.add(motion);
      } else if (this.motionList.get(this.motionList.size() - 1).getEndTick() != motion
          .getStartTick()) {
        throw new IllegalArgumentException("Given motion leaves a gap between previous motion.");
      } else {
        this.handleInsertion(motion);
      }
    }

    /**
     * Adds the given motion if the motion at the given index in the existing motion list has the
     * same
     * end state as this motion's start state. If they do not have overlapping ticks, then it is
     * added. Additionally, if the index is 0, it is added as well.
     *
     * @param motion is the motion to be inserted into this.motionList
     * @throws IllegalArgumentException if the motion is adjacent to another and does not have
     *                                  matching states
     */
    private void handleInsertion (IMotion motion) throws IllegalArgumentException {
      if (this.sameStateIfAdjacent(motion, motionList.get(this.motionList.size() - 1))) {
        this.motionList.add(motion);
      } else {
        throw new IllegalArgumentException("Adjacent motions cannot disagree with end and start "
            + "states.");
      }
    }

    /**
     * Determines if two given motions that are adjacent (overlapping end and start ticks) have the
     * same state. If they don't, return false.
     *
     * @param motion     the motion whose start tick is in question
     * @param prevMotion the motion whose end tick is in question
     * @return true if they two motions are adjacent and have the same state, and false otherwise
     */
    private boolean sameStateIfAdjacent (IMotion motion, IMotion prevMotion){
      return motion.getStartX() == prevMotion.getEndX()
          && motion.getStartY() == prevMotion.getEndY()
          && motion.getStartWidth() == prevMotion.getEndWidth()
          && motion.getStartHeight() == prevMotion.getEndHeight()
          && motion.getStartColor().equals(prevMotion.getEndColor());
    }

    /**
     * Determines if any motions in this shape's motions overlap with the given motion.
     *
     * @param motion the motion to check for overlap
     * @return true if there is overlap, false otherwise
     */
    private boolean hasTickOverlap (IMotion motion){
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
     * Checks if two motions are overlapping in ticks. Note - kept in Shape class since the logic
     * for overlapping motions should be kept in the shape, not in the motion.
     *
     * @param motionToAdd    motion being added to the list
     * @param existingMotion an existing motion already in the list
     * @return true if there is overlap, false otherwise
     */
    private boolean areTicksOverlapping (IMotion motionToAdd, IMotion existingMotion){
      int addStartTick = motionToAdd.getStartTick();
      int addEndTick = motionToAdd.getEndTick();
      int existingStartTick = existingMotion.getStartTick();
      int existingEndTick = existingMotion.getEndTick();
      return (addStartTick > existingStartTick && addStartTick < existingEndTick)
          || (addEndTick > existingStartTick && addEndTick < existingEndTick);
    }

    @Override
    public String getID () {
      return this.id;
    }

    @Override
    public double getWidth () {
      return this.width;
    }

    @Override
    public double getHeight () {
      return this.height;
    }

    @Override
    public double getX () {
      return this.x;
    }

    @Override
    public double getY () {
      return this.y;
    }

    @Override
    public Color getColor () {
      return new Color(this.color.getRGB());
    }

    @Override
    public ShapeType getType () {
      return this.type;
    }

    @Override
    public List<IMotion> getMotions () {
      return new ArrayList<>(this.motionList);
    }

    // -----------------------------------------------------------------------------------------------
    // NEW STUFF

    // TODO: Note that this method means that the list of keyframes will always be sorted by tick
    // TODO: Add functionality where keyframes are not added if they have the same tick and fields
    //   as existing keyframes -- Added for now
    @Override
    public void addKeyframe (int tick, double width, double height, double x, double y, Color color)
        throws IllegalArgumentException {
      if (color == null) {
        throw new IllegalArgumentException("Given Color to addKeyframe is null");
      }
      if (tick < 0 || width < 0 || height < 0) {
        throw new IllegalArgumentException("Given tick, width, and/or height to addKeyframe "
            + "cannot be negative");
      }

      if (keyframeList.isEmpty()
          || (tick > this.keyframeList.get(this.keyframeList.size() - 1).getTick())) {
        this.keyframeList.add(new IKeyframeImpl(tick, width, height, x, y, color));
        return;
      }

      this.insertKeyframe(tick, width, height, x, y, color);
    }

  /**
   * Inserts the given keyframe into the the list of keyframes, such that the list of keyframes
   * remains sorted by tick. If a keyframe exists with the same tick as the given keyframe,
   * instead modifies that keyframe to contain the values of the given keyframe.
   *
   * @param tick   is the tick of the keyframe to be added
   * @param width  is the width of the keyframe to be added
   * @param height is the height of the keyframe to be added
   * @param x      is the x value of the keyframe to be added
   * @param y      is the y value of the keyframe to be added
   * @param color  is the color of the keyframe to be added
   * @throws IllegalArgumentException if a keyframe at the given tick already exists for this shape
   *
   */
  private void insertKeyframe(int tick, double width, double height,
      double x, double y, Color color) throws IllegalArgumentException {

      // Checks for any coinciding keyframes (i.e. keyframes for the same tick)
      // Subtracts one from the loop termination condition because the loop checks the current
      // keyframe and the next keyframe
      for (int i = 0; i < this.keyframeList.size() - 1; i++) {
        if (this.keyframeList.get(i).getTick() == tick) {
          throw new IllegalArgumentException("There is already a keyframe at the given tick:"
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

    }

    @Override
    public void removeKeyframe ( int tick){
      for (int i = 0; i < this.keyframeList.size(); i++) {
        if (this.keyframeList.get(i).getTick() == tick) {
          this.keyframeList.remove(i);
          return;
        }
      }
    }

    @Override
    public List<IKeyframe> getKeyframes () {
      return new ArrayList<>(this.keyframeList);
    }
  }

