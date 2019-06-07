package model;

import java.awt.Color;

/**
 * Represents a shape for the model, but only has the functionality to read from its field. An
 * IModelShape interface will extend from this one with further methods that a complete shape should
 * be able to complete. This ReadOnly shape will be passed to the controller and view.
 */
public interface IReadOnlyShape {
  /**
   * Returns the shape's width.
   *
   * @return the width
   */
  double getWidth();

  /**
   * Returns the shape's height.
   *
   * @return the height
   */
  double getHeight();

  /**
   * Returns the shape's x position.
   *
   * @return the x coordinate
   */
  double getX();

  /**
   * Returns the shape's y position.
   *
   * @return the y coordinate
   */
  double getY();

  /**
   * Returns the shape's color.
   *
   * @return the color
   */
  Color getColor();

  /**
   * Returns the shape's ShapeType.
   *
   * @return the shape type enum
   */
  ShapeType getType();
}
