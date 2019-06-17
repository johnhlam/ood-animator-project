package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.model.IReadOnlyShape;

/**
 * Represents a type of view that outputs text in some form. This abstract class contains an
 * Appendable that the aforementioned text will be appended to. Concrete classes implementing this
 * abstract class may choose to keep track of other information within themselves. Subclasses must
 * also override the toOutput method so that it appends a textual representation of the view to the
 * Appendable.
 */
public abstract class ATextualView implements IView {

  // x is the lowest x value of the canvas
  protected int x;
  // y is the lowest y value of the canvas
  protected int y;
  // width is the width of the canvas
  protected int width;
  // height is the height of the canvas
  protected int height;

  /**
   * Turns this view into a textual representation. Subclasses implementing this method should
   * append the textual output to this.ap.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will display.
   * @param ap is the Appendable to output to
   * @param tickRate is the tick rate of the animation
   * @throws IllegalStateException if this.ap is unable to be appended to, or is unable to transmit
   *                               output.
   */
  @Override
  public abstract void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws IllegalStateException;

  /**
   * Since textual views cannot render an animation visually, this throws an
   * UnsupportedOperationException.
   *
   * @throws UnsupportedOperationException due to not being able to render images
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Textual views cannot render images.");
  }

  @Override
  public void setCanvas(int x, int y, int width, int height, int maxX, int maxY) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Given width and height to ATextualView#setCanvas(...) "
          + "cannot be less than or equal to 0");
    }
    if (maxX < 0 || maxY < 0) {
      throw new IllegalArgumentException("Max x and max y coordinates cannot be negative");
    }

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Attempts to append the given String to this.ap.
   *
   * @param toBeAppended the String to be appended to this.ap.
   * @throws IllegalStateException if the given String could not be appended to this.ap (i.e. an
   *                               IOException was thrown).
   */
  protected void attemptAppend(String toBeAppended, Appendable ap) throws IllegalStateException {
    try {
      ap.append(toBeAppended);
    } catch (IOException e) {
      throw new IllegalStateException("Could not append String to Appendable.");
    }
  }

}
