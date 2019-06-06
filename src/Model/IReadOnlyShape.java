package Model;

import java.awt.*;

/**
 * Represents a shape for the model, but only has the functionality to read from its field. An
 * IModelShape interface will extend from this one with further methods that a complete shape should
 * be able to complete. This ReadOnly shape will be passed to the controller and view.
 */
public interface IReadOnlyShape {
  /**
   * Returns this shape's width.
   *
   * @return the width
   */
  double getWidth();

  /**
   * Returns this shape's height.
   *
   * @return the height
   */
  double getHeight();

  /**
   * Returns this shape's x position.
   *
   * @return the x coordinate
   */
  double getX();

  /**
   * Returns this shape's y position.
   *
   * @return the y coordinate
   */
  double getY();

  /**
   * Returns this shape's color.
   *
   * @return the color
   */
  Color getColor();
}
