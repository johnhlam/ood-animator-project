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
   * Add a motion to the model for the given ID. Specific implementations of the model will have
   * different ways of doing so. Each implementation should further specify the details of this
   * method. Throws exceptions if null arguments are give, or if either of the ticks are
   * negative, or if the end tick comes before the start tick.
   *
   * @param id          ID of the shape the motion belongs to
   * @param startTick   starting tick
   * @param startX      starting x position
   * @param startY      starting y position
   * @param startWidth  starting width
   * @param startHeight starting height
   * @param startColor  starting color
   * @param endTick     ending tick
   * @param endX        ending x position
   * @param endY        ending y position
   * @param endWidth    ending width
   * @param endHeight   ending height
   * @param endColor    ending color
   * @throws IllegalArgumentException if arguments are null, or if ticks are negative, or if the ID
   *                                  is not in the list, or if the end tick comes before the
   *                                  start tick
   */
  void addMotion(String id, int startTick, double startX, double startY, double startWidth,
      double startHeight, Color startColor, int endTick, double endX, double endY, double endWidth,
      double endHeight, Color endColor) throws IllegalArgumentException;

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
   * the given tick.
   *
   * @param tick is the tick value for which each shape's state (at that tick) will be returned
   * @return a copy of the model's list of shapes
   * @throws IllegalArgumentException for negative ticks
   */
  List<IReadOnlyShape> getShapesAtTick(int tick) throws IllegalArgumentException;

  void removeMotionAtStartTick(int tick) throws IllegalArgumentException;

  List<IMotion> getMotionsAtTick(int tick) throws IllegalArgumentException;

  int getX();
  int getY();
  int getWidth();
  int getHeight();
  int getMaxX();
  int getMaxY();
}
