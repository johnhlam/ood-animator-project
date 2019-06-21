package cs3500.animator.view;


import cs3500.animator.model.IReadOnlyShape;

import java.util.List;

/**
 * This interface represents a panel on which animations would be drawn. Details of how exactly the
 * animation should be drawn are left to implementing classes.
 */
public interface IAnimationPanel {

  /**
   * Renders the given list of shapes onto a panel. Details of what "rendering" entails are left to
   * implementing classes.
   *
   * @param toDraw is the list of shapes to be drawn
   * @throws IllegalArgumentException if the given list of shapes is null
   */
  void draw(List<IReadOnlyShape> toDraw) throws IllegalArgumentException;

  /**
   * Sets this animation panel's minimum size to the given width and height, and the preferred size
   * to the given max x and y.
   *
   * @param width      the minimum width
   * @param height     the minimum height
   * @param preferredX the maximum width
   * @param preferredY the maximum height
   */
  void setDrawPanelSize(int width, int height, int preferredX, int preferredY);
}
