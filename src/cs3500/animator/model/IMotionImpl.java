package cs3500.animator.model;

import java.awt.Color;

/**
 * An implementation of the IMotion interface. Holds all the required fields as individual double
 * fields (with the exception of the color and the ticks, which are Color and ints respectively).
 * Represents the start and end of a shape's animation.
 */
public class IMotionImpl implements IMotion {

  private final int startTick;
  private final double startX;
  private final double startY;
  private final double startWidth;
  private final double startHeight;
  private final Color startColor;
  private final int endTick;
  private final double endX;
  private final double endY;
  private final double endWidth;
  private final double endHeight;
  private final Color endColor;

  /**
   * Constructor for a motion. Throws an exception if any inputs are null, or if tick rate is
   * negative.
   *
   * @param startTick   the starting tick
   * @param startWidth  the starting width
   * @param startHeight the starting height
   * @param startX      the starting x coordinate
   * @param startY      the starting y coordinate
   * @param startColor  the starting color
   * @param endTick     the ending tick
   * @param endWidth    the ending width
   * @param endHeight   the ending height
   * @param endX        the ending x coordinate
   * @param endY        the ending y coordinate
   * @param endColor    the ending color
   * @throws IllegalArgumentException if any arguments are null, or if ticks are negative
   */
  public IMotionImpl(
      int startTick, double startWidth, double startHeight, double startX, double startY,
      Color startColor,
      int endTick, double endWidth, double endHeight, double endX, double endY, Color endColor)
      throws IllegalArgumentException {
    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for IMotionImpl cannot be null.");
    }

    if (startTick < 0 || endTick < 0 || startWidth < 0 || startHeight < 0
        || endWidth < 0 || endHeight < 0) {
      throw new IllegalArgumentException("Ticks and sizes cannot be negative.");
    }

    if (endTick < startTick) {
      throw new IllegalArgumentException("End tick must come after start tick");
    }

    this.startTick = startTick;
    this.endTick = endTick;
    this.startX = startX;
    this.startY = startY;
    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.startColor = startColor;
    this.endX = endX;
    this.endY = endY;
    this.endWidth = endWidth;
    this.endHeight = endHeight;
    this.endColor = endColor;
  }

  /**
   * Prints this motion's fields in the following order: start tick, start x, start y, start
   * width, start height, start color R value, start color G value, start color B value, end tick,
   * end x, end y, end width, end height, end color R value, end color G value, end color B
   * value. Spaces are placed in between each field, and start and end parameters are separated
   * by a tab.
   *
   * @return the motion's parameters in the above specified format
   */
  @Override
  public String printMotion() {
    StringBuilder builder = new StringBuilder();

    builder.append(this.startTick)
        .append(" ")
        .append(this.startX)
        .append(" ")
        .append(this.startY)
        .append(" ")
        .append(this.startWidth)
        .append(" ")
        .append(this.startHeight)
        .append(" ")
        .append(this.startColor.getRed())
        .append(" ")
        .append(this.startColor.getGreen())
        .append(" ")
        .append(this.startColor.getBlue())
        .append("\t")

        .append(this.endTick)
        .append(" ")
        .append(this.endX)
        .append(" ")
        .append(this.endY)
        .append(" ")
        .append(this.endWidth)
        .append(" ")
        .append(this.endHeight)
        .append(" ")
        .append(this.endColor.getRed())
        .append(" ")
        .append(this.endColor.getGreen())
        .append(" ")
        .append(this.endColor.getBlue());

    return builder.toString();
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }

  @Override
  public double getStartX() {
    return this.startX;
  }

  @Override
  public double getStartY() {
    return this.startY;
  }

  @Override
  public double getStartWidth() {
    return this.startWidth;
  }

  @Override
  public double getStartHeight() {
    return this.startHeight;
  }

  @Override
  public Color getStartColor() {
    return this.startColor;
  }

  @Override
  public double getEndX() {
    return this.endX;
  }

  @Override
  public double getEndY() {
    return this.endY;
  }

  @Override
  public double getEndWidth() {
    return this.endWidth;
  }

  @Override
  public double getEndHeight() {
    return this.endHeight;
  }

  @Override
  public Color getEndColor() {
    return this.endColor;
  }

}
