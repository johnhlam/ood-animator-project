package Model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 *
 */
public interface IShape {

  /**
   * Sets the width to the given int.
   */
  void setWidth(int width);
  void setHeight(int height);
  void setColor(Color color);
  void setCenter(Point2D center) throws IllegalArgumentException ;
  String getName();

  /**
   * Prints the animations that the shape holds. It should output the type of shape, the ID of
   * the shape, and whatever animations it needs to perform. Specific implementations should
   * specify the exact output.
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

  void addMotion(IMotion motion) throws IllegalArgumentException ;
}