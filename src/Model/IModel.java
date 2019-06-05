package Model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * This interface represents a model used for an animation appplicaton. It contains the logic to
 * animate shapes.
 */
public interface IModel {

  /**
   * Prints the shapes and its associated motions involved in the animation that this model
   * represents. Specific implementations with output different aspects of the animations.
   * Implementing classes should specify what is printed.
   *
   * @return the string representation of the animation
   */
  String printAnimations();

  // TODO: Throw illegal exception for negative ticks
  void addMotion(String id, int startTick, Point2D startPoint, double startWidth,
      double startHeight, Color startColor, int endTick, Point2D endPoint, double endWidth,
      double endHeight, Color endColor) throws IllegalArgumentException;

  /**
   * Adds a shape to this model to be represented in an animation. Each shape is specified by an
   * string ID and a ShapeType enum that represents the supported shapes. It gives the model the
   * information required to build a shape, however it may be implemented.
   *
   * @param id the String id of the shape
   * @param shape the type of supported shape it is
   * @param width the width of the shape
   * @param height the height of the shape
   * @param color the color of the shape
   * @param point the current x,y point that represents the shape's center
   * @throws IllegalArgumentException if any of the arguments are null, or if the ID already exists
   */
  void addShape(String id, ShapeType shape, double width, double height, Color color, Point2D point)
      throws IllegalArgumentException;

  /**
   * Returns a copy of the model's shapes. This list represents the current shapes to be drawn on
   * the screen at a given tick.
   *
   * @return a copy of the model's list of shapes
   */
  List<IShape> getShapes();

  /**
   * Updates all the shapes in this animation (model's list of shapes) to the given tick. A tick
   * represents any given point in the animation, and the model's list of shapes should represent
   * the instance the shapes are in at the given tick.
   *
   * @param tick the current tick of the animation
   */
  void updateDrawing(int tick) throws IllegalArgumentException;

}
