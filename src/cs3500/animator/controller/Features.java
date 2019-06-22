package cs3500.animator.controller;

import cs3500.animator.model.ShapeType;

import java.awt.Color;

/**
 * Represents a series of features that an animation view could support. All implementations must
 * have a way to support playing, stopping, restarting, speeding up, slowing down, toggle looping,
 * adding/removing shapes, and adding/removing/modifying keyframes.
 */
public interface Features {

  /**
   * Plays/resumes the animation if it was paused, or does nothing if it is already playing.
   */
  void play();

  /**
   * Stops the animation if it was playing, or does nothing if it is already stopped.
   */
  void stop();

  /**
   * Restarts the animation at any point back to the beginning.
   */
  void restart();

  /**
   * Increases the tickrate of the animation. Specifics of the implementation is decided by
   * implementing classes.
   */
  void faster();

  /**
   * Decreases the tickrate of the animation. Specifics of the implementation is decided by
   * implementing classes.
   */
  void slower();

  /**
   * Toggles loopback so the animation restarts once it reaches the end.
   */
  void toggleLoopback();

  /**
   * Adds a shape to the animation with the given ID and type.
   *
   * @param id   of the shape
   * @param type of the shape
   */
  void addShape(String id, ShapeType type)
      throws IllegalArgumentException;

  /**
   * Removes the shape of the given ID.
   *
   * @param id of shape to remove
   */
  void removeShape(String id) throws IllegalArgumentException;

  /**
   * Adds a keyframe to the animation to the shape of the given ID.
   *
   * @param id     of the shape who should get the new keyframe
   * @param tick   of keyframe
   * @param width  of shape at that keyframe
   * @param height of shape at that keyframe
   * @param x      value of shape at that keyframe
   * @param y      value of shape at that keyframe
   * @param color  of shape at that keyframe
   * @throws IllegalArgumentException if model disallows a keyframe with the given arguments
   */
  void addKeyframe(String id, int tick, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException;

  /**
   * Removes the keyframe with the given tick from the shape of the given ID.
   *
   * @param id   of the shape
   * @param tick of the keyframe to remove
   * @throws IllegalArgumentException if model disallows removing a keyframe with the given
   *                                  arguments
   */
  void removeKeyframe(String id, int tick) throws IllegalArgumentException;

  /**
   * Modifies a selected keyframe to the animation to the shape of the given ID.
   *
   * @param id     of the shape whose keyframe is being modified
   * @param tick   new tick of keyframe
   * @param width  new width of shape at that keyframe
   * @param height new height of shape at that keyframe
   * @param x      new x value of shape at that keyframe
   * @param y      new y value of shape at that keyframe
   * @param color  new color of shape at that keyframe
   * @throws IllegalArgumentException if model disallows a modification with the given arguments
   */
  void modifyKeyframe(String id, int tick, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException;

}
