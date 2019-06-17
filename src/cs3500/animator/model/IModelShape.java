package cs3500.animator.model;

/**
 * Represents a shape in an animation. Each shape holds directions for its own animations. Shapes
 * can have animations added to them that affect their fields. Each shape should have a ShapeType,
 * location (x,y), size (width, height), and color. An IModelShape object represents its ShapeType
 * with attributes given by its own fields.
 */
public interface IModelShape extends IReadOnlyShape {

  /**
   * Returns a new IReadOnlyShape based on its motions to become what they should be at the given
   * tick during the animation. Specific implementations should specify further details about how
   * the shape handles its motions for a given tick.
   *
   * @param tick the tick to update the shape to
   * @return an IReadOnlyShape that contains this shape's state at the given tick
   * @throws IllegalArgumentException if the tick is negative
   */
  IReadOnlyShape getShapeAtTick(int tick) throws IllegalArgumentException;

  /**
   * Add an IMotion that the shape can perform. How the motion is represented/stored is up to the
   * implementation. May also throw extra exceptions, which should also be specified.
   *
   * @param motion the motion for the shape to perform
   * @throws IllegalArgumentException if the argument is null
   */
  @Deprecated
  void addMotion(IMotion motion) throws IllegalArgumentException;

  /**
   * Removes the motion with the given start tick from this shape's motions. If no motion is found
   * for the start tick, nothing is done.
   *
   * @param startTick the starting tick of the motion
   */
  void removeMotion(int startTick);

  void addKeyframe(IKeyframe keyframe) throws IllegalArgumentException;
  void removeKeyframe(int tick);
}