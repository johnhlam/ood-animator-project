package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IReadOnlyShape;

/**
 * Represents a type of view that outputs text in some form. This abstract class contains an
 * Appendable that the aforementioned text will be appended to. Concrete classes implementing this
 * abstract class may choose to keep track of other information within themselves. Subclasses
 * must also override the toOutput method so that it appends a textual representation of the view
 * to the Appendable.
 */
public abstract class ATextualView implements IView {

  protected List<IReadOnlyShape> shapes;
  protected final Appendable ap;

  /**
   * Constructs an instance of ATextualView with the given Appendable, and an empty
   * ArrayList of IReadOnlyShapes. Concrete classes calling this super constructor may choose to
   * keep track of other information within themselves.
   *
   * @param ap is the Appendable that text will be added to
   * @throws IllegalArgumentException if the given Appendable is null
   */
  protected ATextualView(Appendable ap) throws IllegalArgumentException {
    if(ap == null) {
      throw new IllegalArgumentException("Cannot pass in a null appendable for ATextualView");
    }

    this.shapes = new ArrayList<>();
    this.ap = ap;
  }

  @Override
  public void setShapes(List<IReadOnlyShape> shapes) throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException(
          "Given list of read-only shapes for setShapes cannot be null");
    }
    this.shapes = shapes;
  }

  /**
   * Turns this view into a textual representation. Subclasses implementing this method should
   * append the textual output to this.ap.
   *
   * @param x      is the leftmost x-value (i.e. smallest x-value) of the view.
   * @param y      is the topmost y-value (i.e. the smallest y-value) of the view.
   * @param width  is the width of the bounding box of the view.
   * @param height is the height of the bounding box of the view.
   *
   * @throws IllegalStateException if this.ap is unable to be appended to, or is unable to
   * transmit output.
   */
  @Override
  public abstract void toOutput(int x, int y, int width, int height) throws IllegalStateException;

  @Override
  public void render() {
    throw new UnsupportedOperationException("Text views cannot render images.");
  }

  /**
   * Attempts to append the given String to this.ap.
   *
   * @param toBeAppended the String to be appended to this.ap.
   * @throws IllegalStateException if the given String could not be appended to this.ap (i.e. an
   * IOException was thrown).
   */
  protected void attemptAppend(String toBeAppended) throws IllegalStateException {
    try {
      this.ap.append(toBeAppended);
    } catch(IOException e) {
      throw new IllegalStateException("Could not append String to Appendable.");
    }
  }

}
