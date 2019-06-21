package cs3500.animator.model;

import java.awt.Color;
import java.util.List;

/**
 * This interface represents a model used for an animation appplicaton. It contains the logic to
 * animate shapes. Each model should have some form of storing shapes and be able to return them as
 * a List. Each model should also have its own way of storing animations as well and associate them
 * to the appropriate shapes.
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

  /**
   * Adds a keyframe to the shape in the model with the given ID. If there is an already existing
   * keyframe at the given tick, modifies that keyframe to contain the given parameters.
   *
   * @param id is the id of the shape the keyframe will be added to
   * @param tick is the tick value of the keyframe
   * @param x is the x value of the keyframe
   * @param y is the y value of the keyframe
   * @param width is the width value of the keyframe
   * @param height is the height value of the keyframe
   * @param color is the color of the keyframe
   * @throws IllegalArgumentException if the given id is null, if the given tick, width, or height
   * is negative, or if the given keyframe already exists.
   */
  void addKeyframe(String id, int tick, double x, double y, double width, double height,
      Color color) throws IllegalArgumentException;

  /**
   * Specific implementations of the model will have different ways of doing so. Each
   * implementation should further specify the details of this method.
   *
   * @param id is the id of the shape that they keyframe will be removed in
   * @param tick is the tick of the keyframe in the shape that is to be removed
   * @throws IllegalArgumentException if the given id is null, or if the keyframe with the given
   * tick does not exist in the shape
   */
  void removeKeyframe(String id, int tick) throws IllegalArgumentException;

  /**
   * Adds a shape to this model to be represented in an animation. Each shape is specified by an
   * string ID and a ShapeType enum that represents the supported shapes. It gives the model the
   * information required to build a shape, however it may be implemented.
   *
   * @param id     the String id of the shape
   * @param shape  the type of supported shape it is
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param color  the color of the shape
   * @param x      the current x coordinate for the shape's center
   * @param y      the current x coordinate for the shape's center
   * @throws IllegalArgumentException if any of the arguments are null, or if the ID already exists
   */
  void addShape(String id, ShapeType shape, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException;

  /**
   * Removes the shape and its associated motions with the given ID in the list. Throws an exception
   * if the string is null or if the ID cannot be found.
   *
   * @param id the id of the shape to remove
   * @throws IllegalArgumentException if null argument, or ID cannot be found in the list
   */
  void removeShape(String id) throws IllegalArgumentException;

  /**
   * Returns a copy of the model's shapes as a list at the given tick. Shapes are newly created and
   * are read only, meaning the shapes only have getters. A tick represents any given point in the
   * animation, and the model's list of shapes should represent the instance the shapes are in at
   * the given tick. Implementations should specify if extra exceptions are thrown.
   *
   * @param tick is the tick value for which each shape's state (at that tick) will be returned
   * @return a copy of the model's list of shapes
   * @throws IllegalArgumentException for negative ticks
   */
  List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException;

  /**
   * Gets the starting X value of the animation.
   *
   * @return the starting x coordinate
   */
  int getX();

  /**
   * Gets the starting Y value of the animation.
   *
   * @return the starting y coordinate
   */
  int getY();

  /**
   * Gets the width of the animation canvas.
   *
   * @return the width
   */
  int getWidth();

  /**
   * Gets the height of the animation canvas.
   *
   * @return the height
   */
  int getHeight();

  /**
   * Gets the max y coordinate of the animation.
   *
   * @return the max x coordinate
   */
  int getMaxX();

  /**
   * Gets the max y coordinate of the animation.
   *
   * @return the max x coordinate
   */
  int getMaxY();

  /**
   * Returns a copy of the model's shapes in a read-only format.
   *
   * @return a list of the model's shapes, in read-only form
   */
  List<IReadOnlyShape> getShapes();

  /**
   * Returns the tick of the last animation being played.
   *
   * @return the final tick
   */
  int getFinalTick();
}
