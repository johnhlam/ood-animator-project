package cs3500.animator.model;

import java.awt.Color;

public class IKeyframeImpl implements IKeyframe {

  private int tick;
  private double width;
  private double height;
  private double x;
  private double y;
  private Color color;

  public IKeyframeImpl(int tick, double width, double height, double x, double y,  Color color) {
    this.tick = tick;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.color = color;
  }

  @Override
  public String printKeyframe() {
    return null;
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setX(double x) {
    this.x = x;
  }

  @Override
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public void setWidth(double width) {
    this.width = width;
  }

  @Override
  public void setHeight(double height) {
    this.height = height;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }
}
