package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a keyframe for an shape. Each keyframe represents the state of the shape it
 * is in at a given tick. This interface provides methods that can only read from the keyframe's
 * fields. Other interfaces may extend this one to add further functionality. This read-only
 * keyframe is meant to work with the controller and view.
 */
public interface IReadOnlyKeyframe {
  /**
   * Prints the parameters of this motion in one line. Each implementation should specify how this
   * information is represented.
   *
   * @return the string representation of the start and end parameters of the motion
   */
  String printKeyframe();

  /**
   * Return the tick of the motion.
   *
   * @return the tick
   */
  int getTick();

  /**
   * Return the x coordinate.
   *
   * @return the x coordinate
   */
  double getX();

  /**
   * Return the y coordinate.
   *
   * @return the y coordinate
   */
  double getY();

  /**
   * Return the width.
   *
   * @return the width
   */
  double getWidth();

  /**
   * Return the height.
   *
   * @return the height
   */
  double getHeight();

  /**
   * Return the color.
   *
   * @return the color
   */
  Color getColor();
}
