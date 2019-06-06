package Model;

import java.awt.*;

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
   * Returns the x position in the animation at the given tick based on start and end positions.
   *
   * @param tick the query tick
   * @return the appropriate x coordinate
   */
  double getXAtTick(int tick);

  /**
   * Returns the y position in the animation at the given tick based on start and end positions.
   *
   * @param tick the query tick
   * @return the appropriate y coordinate
   */
  double getYAtTick(int tick);

  /**
   * Returns the width in the animation at the given tick based on start and end sizes.
   *
   * @param tick the query tick
   * @return the appropriate width value
   */
  double getWidthAtTick(int tick);

  /**
   * Returns the height in the animation at the given tick based on start and end sizes.
   *
   * @param tick the query tick
   * @return the appropriate height value
   */
  double getHeightAtTick(int tick);

  /**
   * Returns the color in the animation at the given tick based on start and end colors.
   *
   * @param tick the query tick
   * @return the appropriate color object
   */
  Color getColorAtTick(int tick);

}
