package cs3500.animator.model;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TestModel represents a model used for testing purposes only. Most methods will be left
 * unimplemented as only the important ones (the ones the controller calls) will be tested to check
 * for the wiring in our MVC structure.
 */
public class TestModel implements IModel {

  private final Appendable ap;

  /**
   * Constructs an instance of the test model with the given Appendable that it will write to.
   *
   * @param ap the Appendable to output to
   */
  public TestModel(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public String printAnimations() {
    // see class javadoc
    return null;
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void addKeyframe(String id, int tick, double x, double y, double width, double height,
      Color color) throws IllegalArgumentException {
    this.attemptAppend("Model: Keyframe added for shape " + id + " at tick " + tick);
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {
    this.attemptAppend("Model: Keyframe removed for shape " + id + " at tick " + tick);
  }

  @Override
  public List<IKeyframe> getKeyframesAtTick(int tick) throws IllegalArgumentException {
    return null;
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void addShape(String id, ShapeType shape, double width, double height, double x,
      double y, Color color) throws IllegalArgumentException {
    this.attemptAppend(
        "Model: Shape added with ID " + id + " of type " + ShapeType.typeToString(shape));
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    this.attemptAppend("Model: Shape removed with ID " + id);
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException {
    // see class javadoc
    return null;
  }

  @Override
  public int getX() {
    // see class javadoc
    return 0;
  }

  @Override
  public int getY() {
    // see class javadoc
    return 0;
  }

  @Override
  public int getWidth() {
    // see class javadoc
    return 0;
  }

  @Override
  public int getHeight() {
    // see class javadoc
    return 0;
  }

  @Override
  public int getMaxX() {
    // see class javadoc
    return 0;
  }

  @Override
  public int getMaxY() {
    // see class javadoc
    return 0;
  }

  @Override
  public List<IReadOnlyShape> getShapes() {
    // see class javadoc
    return new ArrayList<>();
  }

  @Override
  public int getFinalTick() {
    // see class javadoc
    return 0;
  }

  /**
   * Attempts to append the given message on to the Appendable. Throws an IllegalStateException if
   * attempt is unsuccessful.
   *
   * @param message the message to append
   * @throws IllegalStateException if appending fails
   */
  private void attemptAppend(String message) throws IllegalStateException {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Attempt to append failed.");
    }
  }
}
