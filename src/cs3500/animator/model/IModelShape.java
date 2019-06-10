package cs3500.animator.model;

/**
 * Represents a shape in an animation. Each shape holds directions for its own animations. Shapes
 * can have animations added to them that affect their fields. Each shape should have a ShapeType,
 * location (x,y), size (width, height), and color. An IModelShape object represents its ShapeType
 * with
 * attributes given by its own fields.
 */
public interface IModelShape extends IReadOnlyShape {

  /**
   * Prints the animations that the shape holds. It should output the type of shape, the ID of the
   * shape, and whatever animations it needs to perform. Specific implementations should specify the
   * exact output.
   *
   * @return the string representation of the animation
   */
  String printMotions();

  /**
   * Returns the string ID of the shape.
   *
   * @return the string ID
   */
  String getID();

  /**
   * Returns a new IReadOnlyShape based on its motions to become what they should be at the given
   * tick during the animation. Specific implementations should specify further details about how
   * the shape handles its motions for a given tick.
   *
   * @param tick the tick to update the shape to
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
  void addMotion(IMotion motion) throws IllegalArgumentException;
}