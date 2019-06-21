package cs3500.animator.model;

import java.awt.*;
import java.util.List;

/**
 * TestModel represents a model used for testing purposes only. Most methods will be left
 * unimplemented as only the important ones (the ones the controller calls) will be tested to
 * check for the wiring in our MVC structure.
 */
public class TestModel implements IModel {
  private final Appendable ap;

  public TestModel(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public String printAnimations() {
    return null;
  }


  @Override
  public void addKeyframe(String id, int tick, double x, double y, double width, double height, Color color) throws IllegalArgumentException {

  }

  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {

  }

  @Override
  public void addShape(String id, ShapeType shape, double width, double height, double x, double y, Color color) throws IllegalArgumentException {

  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {

  }

  @Override
  public List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int getX() {
    return 0;
  }

  @Override
  public int getY() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getMaxX() {
    return 0;
  }

  @Override
  public int getMaxY() {
    return 0;
  }

  @Override
  public List<IReadOnlyShape> getShapes() {
    return null;
  }

  @Override
  public int getFinalTick() {
    return 0;
  }
}
