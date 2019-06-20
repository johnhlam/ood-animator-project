package cs3500.animator.model;

import java.awt.Color;

/**
 * This class represents an individual keyframe for a shape. Each keyframe (each instance of
 * this class) contains fields that describe a shape at a certain tick. This class contains a
 * series of getters and setters to read from and mutate the keyframe.
 */
public class IKeyframeImpl implements IKeyframe {

  private final int tick;
  private final double width;
  private final double height;
  private final double x;
  private final double y;
  private Color color;

  public IKeyframeImpl(int tick, double width, double height, double x, double y,  Color color)
    throws IllegalArgumentException{
    if (color == null) {
      throw new IllegalArgumentException("Given color to IKeyframeImpl cannot be null");
    }
    this.tick = tick;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.color = color;
  }

  @Override
  public String printKeyframe() {
    StringBuilder builder = new StringBuilder();

    builder.append(this.tick)
        .append(" ")
        .append((int) this.x)
        .append(" ")
        .append((int) this.y)
        .append(" ")
        .append((int) this.width)
        .append(" ")
        .append((int) this.height)
        .append(" ")
        .append(this.color.getRed())
        .append(" ")
        .append(this.color.getGreen())
        .append(" ")
        .append(this.color.getBlue());

    return builder.toString();
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
}
