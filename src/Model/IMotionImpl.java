package Model;

import java.awt.Color;
import java.awt.geom.Point2D;

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

  public IMotionImpl(
          int startTick, double startWidth, double startHeight, double startX, double startY,
          Color startColor,
          int endTick, double endWidth, double endHeight, double endX, double endY, Color endColor)
          throws IllegalArgumentException {
    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for IMotionImpl cannot be null.");
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
}
