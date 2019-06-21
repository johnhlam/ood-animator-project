package cs3500.animator.controller;

import java.awt.*;
import java.io.IOException;

import cs3500.animator.model.ShapeType;
import cs3500.animator.view.IView;

/**
 * TestController is a mock controller used for testing the wiring between the view and the
 * controller. Methods that require the model will be tested through a test model.
 */
public class TestController implements IController, Features {
  private IView view;
  // test with outputting to appendable
  private final Appendable ap;

  TestController(IView view, Appendable ap) {
    this.view = view;
    this.ap = ap;
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

  @Override
  public void play() {
    this.attemptAppend("Play was pressed.");
  }

  @Override
  public void stop() {
    this.attemptAppend("Stop was pressed.");
  }

  @Override
  public void restart() {
    this.attemptAppend("Restart was pressed.");
  }

  @Override
  public void faster() {
    this.attemptAppend("Speed increased.");
  }

  @Override
  public void slower() {
    this.attemptAppend("Speed decreased.");
  }

  @Override
  public void toggleLoopback() {
    this.attemptAppend("Loopback was checked.");
  }

  @Override
  public void addShape(String id, ShapeType type) throws IllegalArgumentException {
    // left for the test model
  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    // left for the test model
  }

  @Override
  public void addKeyframe(String id, int tick, double width, double height, double x, double y, Color color) throws IllegalArgumentException {
    // left for the test model
  }

  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {
    // left for the test model
  }

  @Override
  public void modifyKeyframe(String id, int tick, double width, double height, double x, double y, Color color) throws IllegalArgumentException {
    // left for the test model
  }

  private void attemptAppend(String message) {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Appending failed");
    }
  }
}
