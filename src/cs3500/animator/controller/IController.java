package cs3500.animator.controller;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public interface IController {

  void setTickRate(int tickRate);

  void run();

  Dimension getCanvasSize();

  Point2D getTopXY();

  Point2D getMaxXY();

  void setAppendable(Appendable ap);
}
