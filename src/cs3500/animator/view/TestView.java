package cs3500.animator.view;

import java.awt.Color;
import java.util.List;
import java.util.Scanner;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;

/**
 * Represents a view to be used for testing. It simulates actions using a Readable. It uses its
 * render(...) method to parse through the Readable and calls the appropriate method on its Features
 * object. For methods that require more user input, it sets concrete values to pass on for easier
 * testing. It leaves many methods unimplemented as they are not needed for testing.
 */
public class TestView implements IView {

  private Features features;
  private final Readable readable;

  /**
   * Constructs an instance of the test view with the given Readable object, preset with a command.
   */
  public TestView(Readable readable) {
    this.readable = readable;
  }

  @Override
  public void setCanvas(int x, int y, int width, int height, int maxX, int maxY)
      throws IllegalArgumentException {
    // see javadoc for the class
  }

  @Override
  public void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate)
      throws UnsupportedOperationException {
    // see javadoc for the class
  }

  /**
   * Parses through the Readable to determine which action should be simulated. It calls the
   * appropriate method on Features.
   *
   * @param shapes is ignored, as it is not needed
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) {
    Scanner scanner = new Scanner(readable);
    String command = scanner.next();
    switch (command) {
      case "play":
        this.features.play();
        break;
      case "stop":
        this.features.stop();
        break;
      case "restart":
        this.features.restart();
        break;
      case "fast":
        this.features.faster();
        break;
      case "slow":
        this.features.slower();
        break;
      case "loop":
        this.features.toggleLoopback();
        break;
      case "addShape":
        this.features.addShape("shape added", ShapeType.RECTANGLE);
        break;
      case "removeShape":
        this.features.removeShape("shape removed");
        break;
      case "addKeyframe":
        this.features.addKeyframe("keyframe added", 38, 20, 20, 20, 20, Color.red);
        break;
      case "removeKeyframe":
        this.features.removeKeyframe("keyframe removed", 12);
        break;
      case "modifyKeyframe":
        this.features.modifyKeyframe("keyframe modified", 23, 30, 20, 20, 20, Color.red);
        break;
      default:
    }
  }

  @Override
  public void setFeatures(Features features) throws UnsupportedOperationException {
    this.features = features;
  }

  @Override
  public void setShapes(List<IReadOnlyShape> shapes) {
    // see javadoc for the class
  }
}
