package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a Keyframe for a shape. Each keyframe represents the state of the shape it
 * is in at a given tick. Besides the read-only functionalities that the IReadOnlyKeyframe
 * interface provides, this interface also provides a series of setters to modify the keyframe.
 * This version of the keyframe is meant to work closely with the model itself, while the
 * read-only version is meant to work with controllers and views.
 */
public interface IKeyframe extends IReadOnlyKeyframe {

  /**
   * Sets the keyframe's tick to the given value.
   *
   * @param tick is the tick value to set
   */
  void setTick(int tick);

  /**
   * Sets the keyframe's x to the given value.
   *
   * @param x is the x value to set
   */
  void setX(double x);

  /**
   * Sets the keyframe's y to the given value.
   *
   * @param y is the y value to set
   */
  void setY(double y);

  /**
   * Sets the keyframe's width to the given value.
   *
   * @param width is the width value to set
   */
  void setWidth(double width);

  /**
   * Sets the keyframe's height to the given value.
   *
   * @param height is the height value to set
   */
  void setHeight(double height);

  /**
   * Sets the keyframe's Color to the given value.
   *
   * @param color is the x value to set
   */
  void setColor(Color color);
}
