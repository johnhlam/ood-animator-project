package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IReadOnlyShape;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * IView is an interface representing an view for animating shapes (not necessarily visual
 * animation). It contains operations that can be offered by implementing classes. Each view should
 * be able to process a list of shapes into some kind of "view" (e.g. a visual or text-based view).
 */
public interface IView {

  /**
   * Sets the view's canvas size and starting x and y position. These values represent the window in
   * which an animation can be depicted (whether textual or visual).
   *
   * @param x      the lowest x value for the animation
   * @param y      the lowest y value for the animation
   * @param width  the width of the animation canvas
   * @param height the width of the animation canvas
   * @param maxX   the max x coordinate that the animation reaches
   * @param maxY   the max y coordinate that the animation reaches
   * @throws IllegalArgumentException if the given width and/or height are negative
   */
  void setCanvas(int x, int y, int width, int height, int maxX, int maxY) throws IllegalArgumentException;

  /**
   * Outputs a textual representation of the animation to the given Appendable object. The animation
   * to represent is given as a list of shapes. Some views may not support this operation, in which
   * case it should be specified by implementing classes.
   *
   * @param shapes   is the list of shapes to output to the Appendable
   * @param ap       the Appendable to output to
   * @param tickRate the tick rate of the animation
   */
  void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws UnsupportedOperationException;

  /**
   * Plays the animation visually by drawing the given shapes. Some views may not support this
   * operation, in which case it should be specified by implementing classes.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will display.
   * @throws IllegalArgumentException if the given List is null.
   */
  void render(List<IReadOnlyShape> shapes) throws UnsupportedOperationException;

  void setFeatures(Features features) throws UnsupportedOperationException;

  void setShapes(List<IReadOnlyShape> shapes);
}
