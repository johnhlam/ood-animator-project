package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IModel. Holds all the shapes needed to be drawn in the animation as an
 * ArrayList of shapes. The shapes themselves hold the knowledge on how they animate.
 */
public class IModelImpl implements IModel {

  // A list of shapes that the model is currently representing as drawings/animations.
  private final List<IModelShape> shapes;

  /**
   * Constructs an instance of the model. Initializes the shapes to an empty list.
   */
  public IModelImpl() {
    this.shapes = new ArrayList<>();
  }

  @Override
  public List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException {
    // this method is left blank as the implementation of it will be decided in future
    // assignments when the tweening function is revealed. Will most likely call a method on
    // shapes to update each shapes in the list, so that stub is written in the shape interface
    // as well.
    return new ArrayList<>();
  }


  /**
   * Prints out each shape in this model with its ID followed by the motions it contains. Each
   * motion is on a new line, and ouputs start and ending information about the shape. This includes
   * tick, position, size, and color.
   *
   * @return the string representation of the animation
   */
  @Override
  public String printAnimations() {
    StringBuilder builder = new StringBuilder();

    for (IModelShape s : this.shapes) {
      builder.append(s.printMotions())
              .append("\n");
    }

    return builder.toString();
  }

  /**
   * Adds a motion to the shape corresponding with the given ID. Motions can be given in any order,
   * and the shape will insert them in such a way that motions are sorted based on start ticks.
   * Throws an exception if the given motion's start or end ticks overlap with pre-existing
   * motions.
   *
   * INVARIANT: All existing motions in the shapes will be chronologically ordered, with no overlap
   * in ticks.
   *
   * @throws IllegalArgumentException if the given motion's start or end ticks overlap with
   *                                  pre-existing motions.
   */
  @Override
  public void addMotion(
          String id, int startTick, double startX, double startY, double startWidth,
          double startHeight, Color startColor, int endTick, double endX, double endY, double endWidth,
          double endHeight, Color endColor) throws IllegalArgumentException {

    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("Arguments for addMotion cannot be null.");
    }

    if (startTick < 0 || endTick < 0 || startWidth < 0 || startHeight < 0
    || endWidth < 0 || endHeight < 0) {
      throw new IllegalArgumentException("Ticks and sizes cannot be negative.");
    }

    if (endTick <= startTick) {
      throw new IllegalArgumentException("End tick must come after start tick");
    }

    for (int i = 0; i < this.shapes.size(); i++) {
      IModelShape cur = this.shapes.get(i);
      if (cur.getID().equals(id)) {
        cur.addMotion(
                new IMotionImpl(
                        startTick, startWidth, startHeight, startX, startY, startColor,
                        endTick, endWidth, endHeight, endX, endY, endColor));

        // Does not need to iterate through the rest of the list if a shape with the given id has
        // been found
        return;
      }
    }
    // throw an exception if it reaches here, meaning the ID was not in the list
    throw new IllegalArgumentException("ID could not be found");
  }

  @Override
  public void addShape(String id, ShapeType type, double width, double height, Color color,
                       double x, double y) throws IllegalArgumentException {
    if (id == null || type == null || color == null) {
      throw new IllegalArgumentException("Arguments for addShape cannot be null.");
    }
    for (IModelShape shape : this.shapes) {
      if (shape.getID().equals(id)) {
        throw new IllegalArgumentException("Shape with same id already exists.");
      }
    }

    IModelShape newShape = new IModelShapeImpl(id, type, width, height, x, y, color);

    this.shapes.add(newShape);
  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {

  }
}
