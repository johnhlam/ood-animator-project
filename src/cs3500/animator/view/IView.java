package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyShape;
import java.util.List;

/**
 * IView is an interface representing an view for animating shapes. It contains operations
 * that can be offered by implementing classes. Each view should be able to process a list of
 * shapes into some kind of "view" (e.g. a visual or text-based view).
 */
public interface IView {

  /**
   * Sets this IView's list of shapes to the given list.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will store.
   * @throws IllegalArgumentException if the given List is null.
   */
  void setShapes(List<IReadOnlyShape> shapes) throws IllegalArgumentException;

  /**
   * Turns this view into a textual representation. Details of how that textual view is formatted
   * and stored is left to implementing classes.
   *
   * @param x is the leftmost x-value (i.e. smallest x-value) of the view.
   * @param y is the topmost y-value (i.e. the smallest y-value) of the view.
   * @param width is the width of the bounding box of the view.
   * @param height is the height of the bounding box of the view.
   *
   * @throws IllegalStateException         if an input/output error occurs (e.g. if an IOException
   *                                       is thrown).
   * @throws UnsupportedOperationException if the implementing class chooses not to support
   *                                       outputting the view in a textual format.
   */
  void toOutput(int x, int y, int width, int height)
      throws IllegalStateException, UnsupportedOperationException;

<<<<<<< HEAD
  void render() throws UnsupportedOperationException;

  void setCanvas(int x, int y, int width, int height);

  void play();
=======
  /**
   * Renders this view in the form of an animation. Details of exactly how it is rendered is left
   * to implementing classes.
   *
   * @throws RuntimeException if the class is unable to render the view, for whatever reason
   * @throws UnsupportedOperationException if the implementing class chooses not to support
   *                                       rendering the view in the form of an animation.
   */
  void render() throws RuntimeException, UnsupportedOperationException;
>>>>>>> 981b2ee324bc1d2845db50c973f49ece5505b815
}
