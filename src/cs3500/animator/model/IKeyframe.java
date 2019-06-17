package cs3500.animator.model;

import java.awt.Color;

public interface IKeyframe {
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

  void setX(double x);

  void setY(double y);

  void setWidth(double width);

  void setHeight(double height);

  void setColor(Color color);
}
