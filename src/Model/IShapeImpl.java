package Model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class IShapeImpl implements IShape {

  private final String id;
  private double width;
  private double height;
  private Point2D center;
  private Color color;
  private List<IMotion> motionList;
  private final ShapeType type;

  public IShapeImpl(String id, ShapeType type, double width, double height, Point2D center,
                    Color color) {

    if (id == null || type == null || color == null || center == null) {
      throw new IllegalArgumentException("No null args allowed.");
    }

    if (this.width < 0 || this.height < 0) {
      throw new IllegalArgumentException("Given width and/or height are negative for shape");
    }

    this.id = id;
    this.type = type;
    this.width = width;
    this.height = height;
    this.center = center;
    this.color = color;
    this.motionList = new ArrayList<>();
  }


  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public void setHeight(int height) {

  }

  @Override
  public void setCenter(Point2D point) {

  }

  @Override
  public String getName() {
    return null;
  }


  /**
   * Along with the specifications in the interface, this method also prints each motion that this
   * shape undergoes. It outputs the start and end time, size, location, and color in a sequence of
   * lines for each motion.
   *
   * @return string representation of the animation
   */
  @Override
  public String printMotions() {
    StringBuilder builder = new StringBuilder();

    builder.append("shape ").append(this.id).append(" ").append(ShapeType.typeToString(this.type))
            .append("\n");

    for (IMotion curMotion : this.motionList) {
      builder.append("motion ")
              .append(this.id)
              .append(" ")
              .append(curMotion.printMotion())
              .append("\n");
    }

    // Removes the last \n in the builder
    // builder.delete(builder.length() - 2, builder.length() - 1);
    return builder.toString();

  }

  @Override
  public String getID() {
    return this.id;
  }

  @Override
  public void setColor(Color color) {

  }

  @Override
  public void addMotion(IMotion motion) throws IllegalArgumentException {
    if (motion == null) {
      throw new IllegalArgumentException("Given motion cannot be null for addMotion");
    }

    if (this.hasTickOverlap(motion)) {
      throw new IllegalArgumentException("Given motion has overlapping ticks");
    }

    this.insertMotion(motion);

  }

  private void insertMotion(IMotion motion) {
    for (int i = 0; i < this.motionList.size(); i++) {
      IMotion curMotion = this.motionList.get(i);
      if (motion.getStartTick() < curMotion.getStartTick()) {
        this.motionList.add(i, motion);
        // Returns because you don't have to iterate through the rest of the list
        return;
      }
    }

    // If it reaches here, then the given motion's start tick is after all of this.motionList's
    // endTicks
    this.motionList.add(motion);
  }

  private boolean hasTickOverlap(IMotion motion) {
    for (IMotion curMotion : this.motionList) {
      if (this.areTicksOverlapping(curMotion.getStartTick(), curMotion.getEndTick(),
              motion.getStartTick())
              || this.areTicksOverlapping(curMotion.getStartTick(), curMotion.getEndTick(),
              motion.getEndTick())) {
        // Returns true to break out of the loop
        return true;
      }
    }

    return false;
  }

  private boolean areTicksOverlapping(double lower, double upper, double num) {
    return num >= lower && num <= upper;
  }

}
