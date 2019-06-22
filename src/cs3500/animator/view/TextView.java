package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.IReadOnlyShape;

/**
 * TextView is a class that extends ATextualView. It provides a way to convert a list of shapes into
 * a textual representation. Details on how exactly it is formatted is described in the {@link
 * TextView#toOutput(List, Appendable, int)} method.
 */
public class TextView extends ATextualView {

  /**
   * Turns this view into a textual representation and appends the textual output to this.ap. The
   * textual representation is formatted based on the following: <br> canvas {@code x y width
   * height} <br> shape {@code shapeID shapeType} <br> motion {@code shapeID startTick startX startY
   * startWidth startHeight startRed startGreen startBlue} \t {@code endX endY endWidth endHeight
   * endRed endGreen endBlue} <br> ... <i>other motions</i>... <br> ... <i>other shapes</i>...
   *
   * @param shapes   is the List of IReadOnlyShapes that this IView will display.
   * @param ap       is the Appendable to output to
   * @param tickRate is the tick rate of the animation
   * @throws IllegalStateException if this.ap is unable to be appended to, or is unable to transmit
   *                               output.
   */
  @Override
  public void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws RuntimeException {
    if (shapes == null || ap == null) {
      throw new IllegalArgumentException(
              "Given list of read-only shapes and Appendable for toOutput cannot be null");
    }
    if (tickRate < 1) {
      throw new IllegalArgumentException("Given tickRate to toOutput must be positive");
    }
    this.attemptAppend("canvas ", ap);
    this.attemptAppend(Integer.toString(x), ap);
    this.attemptAppend(" ", ap);
    this.attemptAppend(Integer.toString(y), ap);
    this.attemptAppend(" ", ap);
    this.attemptAppend(Integer.toString(width), ap);
    this.attemptAppend(" ", ap);
    this.attemptAppend(Integer.toString(height), ap);
    this.attemptAppend("\n", ap);

    for (IReadOnlyShape s : shapes) {
      this.attemptAppend(s.printKeyframes(), ap);
      this.attemptAppend("\n", ap);
    }
  }

}
