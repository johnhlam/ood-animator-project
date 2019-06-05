package Model;

import java.awt.Color;
import java.awt.geom.Point2D;

public class IMotionImpl implements IMotion {
  private final int startTick;
  private final int endTick;
  private final Point2D startPoint;
  private final double startWidth;
  private final double startHeight;
  private final Color startColor;
  private final Point2D endPoint;
  private final double endWidth;
  private final double endHeight;
  private final Color endColor;

  public IMotionImpl(
      int startTick, double startWidth, double startHeight, Point2D startPoint, Color startColor,
      int endTick, double endWidth, double endHeight, Point2D endPoint, Color endColor)
      throws IllegalArgumentException {
    if(startPoint == null || startColor == null || endPoint == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for IMotionImpl cannot be null.");
    }
    this.startTick = startTick;
    this.endTick = endTick;
    this.startPoint = startPoint;
    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.startColor = startColor;
    this.endPoint = endPoint;
    this.endWidth = endWidth;
    this.endHeight = endHeight;
    this.endColor = endColor;
  }

  @Override
  public String printMotion() {
    StringBuilder builder = new StringBuilder();

    builder.append(this.startTick)
        .append(" ")
        .append(this.startPoint.getX())
        .append(" ")
        .append(this.startPoint.getY())
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
        .append(this.endPoint.getX())
        .append(" ")
        .append(this.endPoint.getY())
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
