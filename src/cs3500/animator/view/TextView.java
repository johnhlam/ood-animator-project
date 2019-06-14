package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyShape;

/**
 * TextView is a class that extends ATextualView. It provides a way to convert
 * a list of shapes into a textual representation. Details on how exactly it is formatted is
 * described in the {@link TextView#toOutput} method.
 */
public class TextView extends ATextualView {

  /**
   * Constructs an instance of TextView with the given Appendable.
   *
   * @param ap is the Appendable that the text output will be appended to.
   * @throws IllegalArgumentException if the given Appendable is null.
   */
  public TextView(Appendable ap) throws IllegalArgumentException {
    super(ap);
  }

  /**
   * Turns this view into a textual representation and appends the textual output to this.ap.
   * The textual representation is formatted based on the following: <br>
   * canvas {@code x y width height} <br>
   * shape {@code shapeID shapeType} <br>
   * motion
   * {@code shapeID startTick startX startY startWidth startHeight startRed startGreen startBlue}
   * \t
   * {@code endX endY endWidth endHeight endRed endGreen endBlue} <br>
   * ... <i>other motions</i>... <br>
   * ... <i>other shapes</i>...
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
  public void toOutput(int x, int y, int width, int height) throws IllegalStateException {

    this.attemptAppend("canvas ");
    this.attemptAppend(Integer.toString(x));
    this.attemptAppend(" ");
    this.attemptAppend(Integer.toString(y));
    this.attemptAppend(" ");
    this.attemptAppend(Integer.toString(width));
    this.attemptAppend(" ");
    this.attemptAppend(Integer.toString(height));
    this.attemptAppend("\n");

    for(IReadOnlyShape s : this.shapes) {
        this.attemptAppend(s.printMotions());
        this.attemptAppend("\n");
    }
  }
  
}
