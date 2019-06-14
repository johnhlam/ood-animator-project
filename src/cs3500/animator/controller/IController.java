package cs3500.animator.controller;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public interface IController {

  void setTickRate(int tickRate) throws UnsupportedOperationException;

  void run();

}
