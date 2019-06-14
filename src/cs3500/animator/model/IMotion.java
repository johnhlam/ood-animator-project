package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a motion that a shape can undergo. Each motion must have start and end ticks, start
 * and end coordinate positions, start and end sizes, and start and end colors. An implementation
 * of this interface is a representation of the start and end of an animation. Each motion should
 * be able to return the appropriate fields based on a given tick during its animation.
 */
public interface IMotion {

  /**
   * Prints the parameters of this motion in one line. Each implementation should specify how
   * this information is represented.
   *
   * @return the string representation of the start and end parameters of the motion
   */
  String printMotion();

  /**
   * Return the start tick of the motion.
   *
   * @return the start tick
   */
  int getStartTick();

  /**
   * Return the end tick of the motion.
   *
   * @return the end tick
   */
  int getEndTick();

  /**
   * Return the starting x coordinate.
   *
   * @return the x coordinate
   */
  double getStartX();

  /**
   * Return the starting y coordinate.
   *
   * @return the y coordinate
   */
  double getStartY();

  /**
   * Return the starting width.
   *
   * @return the width
   */
  double getStartWidth();

  /**
   * Return the starting height.
   *
   * @return the height
   */
  double getStartHeight();

  /**
   * Return the starting color.
   *
   * @return the color
   */
  Color getStartColor();

  /**
   * Return the ending x coordinate.
   *
   * @return the x coordinate
   */
  double getEndX();

  /**
   * Return the ending y coordinate.
   *
   * @return the y coordinate
   */
  double getEndY();

  /**
   * Return the ending width.
   *
   * @return the width
   */
  double getEndWidth();

  /**
   * Return the ending height.
   *
   * @return the height
   */
  double getEndHeight();

  /**
   * Return the ending color.
   *
   * @return the color
   */
  Color getEndColor();
}
