package Model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class IModelImpl implements IModel {

  // A list of shapes that the model is currently representing as drawings/animations.
  private final List<IShape> shapes;

  public IModelImpl() {
    this.shapes = new ArrayList<>();
  }

  @Override
  public List<IShape> getShapes() {
    return new ArrayList<>(this.shapes); // deep copy if possible
  }

  @Override
  public void updateDrawing(int tick) throws IllegalArgumentException {
    // this method is left blank as the implementation of it will be decided in future
    // assignments when the tweening function is revealed.
  }

  /**
   * Prints out each shape in this model with its ID followed by the motions it contains. Each
   * motion is on a new line, and ouputs start and ending information about the shape. This
   * includes tick, position, size, and color.
   *
   * @return the string representation of the animation
   */
  @Override
  public String printAnimations() {
    StringBuilder builder = new StringBuilder();

    for(IShape s : this.shapes) {
      builder.append(s.printMotions())
          .append("\n");
    }

    return builder.toString();
  }

  @Override
  public void addMotion(
      String id, int startTick, Point2D startPoint, double startWidth,
      double startHeight, Color startColor, int endTick, Point2D endPoint, double endWidth,
      double endHeight, Color endColor) throws IllegalArgumentException {

    if (startPoint == null || startColor == null || endPoint == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for addMotion cannot be null.");
    }

    for (int i = 0; i < this.shapes.size(); i++) {
      IShape cur = this.shapes.get(i);
      if (cur.getID().equals(id)) {
        cur.addMotion(
            new IMotionImpl(
                startTick, startWidth, startHeight, startPoint, startColor,
                endTick, endWidth, endHeight, endPoint, endColor));

        // Does not need to iterate through the rest of the list if a shape with the given id has
        // been found
        return;
      }
    }
  }

  @Override
  public void addShape(String id, ShapeType type, double width, double height, Color color,
      Point2D point) throws IllegalArgumentException {
    if (id == null || type == null || color == null || point == null) {
      throw new IllegalArgumentException("Arguments for addShape cannot be null.");
    }
    for (IShape shape : this.shapes) {
      if (shape.getID().equals(id)) {
        throw new IllegalArgumentException("Shape with same id already exists.");
      }
    }

    IShape newShape = new IShapeImpl(id, type, width, height, point, color);

    this.shapes.add(newShape);
  }
}
