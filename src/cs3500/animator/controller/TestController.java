package cs3500.animator.controller;

import java.awt.*;

import cs3500.animator.model.ShapeType;
import cs3500.animator.view.IView;

public class TestController implements IController, Features {
  // test with outputting to appendable
  private final Appendable ap;

  TestController(IView view, Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void setTickRate(int tickRate) {

  }

  @Override
  public void outputText() {

  }

  @Override
  public void renderAnimation() {

  }

  @Override
  public void play() {

  }

  @Override
  public void stop() {

  }

  @Override
  public void restart() {

  }

  @Override
  public void faster() {

  }

  @Override
  public void slower() {

  }

  @Override
  public void toggleLoopback() {

  }

  @Override
  public void addShape(String id, ShapeType type) throws IllegalArgumentException {

  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {

  }

  @Override
  public void addKeyframe(String id, int tick, double width, double height, double x, double y, Color color) throws IllegalArgumentException {

  }

  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {

  }

  @Override
  public void modifyKeyframe(String id, int tick, double width, double height, double x, double y, Color color) throws IllegalArgumentException {

  }
}
