package cs3500.animator.model;

import java.awt.Color;

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
   * Adds a keyframe with the given parameters to this shape. If there is already an existing
   * keyframe at the given tick, then that keyframe will be modified to contain the given
   * parameters. How each keyframe is represented/stored is left for the implementation.
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
  void addKeyframe(int tick, double width, double height, double x, double y, Color color)
          throws IllegalArgumentException;

  /**
   * Removes the keyframe with the given tick from this shape's keyframes. If no keyframe is found
   * with the given tick, this method has no effect.
   *
   * @param tick is the tick of the keyframe to be removed
   */
  void removeKeyframe(int tick);
}