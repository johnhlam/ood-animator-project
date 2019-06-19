package cs3500.animator.controller;

import cs3500.animator.model.ShapeType;
import java.awt.Color;

public interface Features {

  void play();

  void stop();

  void restart();

  void faster();

  void slower();

  void toggleLoopback();


  void addShape(String id, ShapeType type,  double width, double height, double x, double y,
      Color color)
      throws IllegalArgumentException;

  void removeShape(String id) throws IllegalArgumentException;

  void addKeyframe(String id, int tick, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException;

  void removeKeyframe(String id, int tick) throws IllegalArgumentException;

  void modifyKeyframe(String id, int tick, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException;

}
