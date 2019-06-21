package cs3500.animator.controller;

import java.awt.*;
import java.io.IOException;

import cs3500.animator.model.IModel;
import cs3500.animator.model.ShapeType;
import cs3500.animator.view.IView;

/**
 * TestController is a mock controller used for testing the wiring between the view and the
 * controller. Methods that require the model will be further tested through a test model.
 */
public class TestController implements IController, Features {
  private IView view;
  private final Appendable ap;

  /**
   * Constructs an instance of the test controller with the given view that it will respond to and
   * Appendable that it will write to.
   */
  public TestController(IView view, Appendable ap) {
    this.view = view;
    this.ap = ap;
    this.view.setFeatures(this);
  }

  @Override
  public void setTickRate(int tickRate) {
    // empty since doesn't have to do with testing wiring
  }

  @Override
  public void outputText() {
    // empty since doesn't have to do with testing wiring
  }

  @Override
  public void renderAnimation() {
    // empty since doesn't have to do with testing wiring
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void play() {
    this.attemptAppend("Play was pressed.");
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void stop() {
    this.attemptAppend("Stop was pressed.");
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void restart() {
    this.attemptAppend("Restart was pressed.");
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void faster() {
    this.attemptAppend("Speed increased.");
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void slower() {
    this.attemptAppend("Speed decreased.");
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void toggleLoopback() {
    this.attemptAppend("Loopback was checked.");
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void addShape(String id, ShapeType type) throws IllegalArgumentException {
    this.attemptAppend("Shape added with ID " + id + " of type " + ShapeType.typeToString(type));
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    this.attemptAppend("Shape removed with ID " + id);
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void addKeyframe(String id, int tick, double width, double height, double x, double y,
                          Color color) throws IllegalArgumentException {
    this.attemptAppend("Keyframe added for shape " + id + " at tick " + tick);
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {
    this.attemptAppend("Keyframe removed for shape " + id + " at tick " + tick);
  }

  /**
   * Appends the appropriate message to the Appendable.
   */
  @Override
  public void modifyKeyframe(String id, int tick, double width, double height, double x, double y
          , Color color) throws IllegalArgumentException {
    this.attemptAppend("Keyframe modified for shape " + id + " at tick " + tick);
  }

  /**
   * Attempts to append the given message on to the Appendable. Throws an IllegalStateException if
   * attempt is unsuccessful.
   *
   * @param message the message to append
   * @throws IllegalStateException if appending fails
   */
  private void attemptAppend(String message) {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Appending failed");
    }
  }
}
