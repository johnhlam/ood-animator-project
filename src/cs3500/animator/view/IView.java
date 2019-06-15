package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyShape;
import java.util.List;

/**
 * IView is an interface representing an view for animating shapes (not necessarily visual
 * animation). It contains operations that can be offered by implementing classes. Each view
 * should be able to process a list of shapes into some kind of "view" (e.g. a visual or
 * text-based view).
 */
public interface IView {

  /**
   * Sets the view's canvas size and starting x and y position. These values represent the window
   * in which an animation can be depicted (whether textual or visual).
   *
   * @param x      the lowest x value for the animation
   * @param y      the lowest y value for the animation
   * @param width  the width of the animation canvas
   * @param height the width of the animation canvas
   */
  void setCanvas(int x, int y, int width, int height);

  /**
   * Outputs however this view represents an animation. It could be textual in the form of SVG or
   * plain text, or visual in the form of drawn images. Details of how it is shown is left to be
   * defined by the implementing classes.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will display.
   * @throws IllegalArgumentException if the given List is null.
   */
  void play(List<IReadOnlyShape> shapes) throws RuntimeException;

  /**
   * Sets the maximum window size to the given width and height. Some implementations may not
   * support this operation, and thus may throw an exception if this method is called.
   *
   * @param width  is the maximum width of the window
   * @param height is the maximum height of the window
   * @throws UnsupportedOperationException if the implementing class chooses not to support this
   *                                       operation
   */
  void setMaxWindowSize(int width, int height) throws UnsupportedOperationException;

}
