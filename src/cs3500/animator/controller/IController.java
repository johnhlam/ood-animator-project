package cs3500.animator.controller;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public interface IController {

  void setTickRate();

  void transmitShapes (int tick);

  void go();

  Dimension getAnimationDim();

  Point2D getTopXY();
}

/*
  - Should we keep the tick/timer in the controller or the view? It would make more sense to us
    to keep it in the view, but then we don't know how we can refresh the list of shapes at every
    tick (since the view doesn't have a oontroller). If we put it in the controller, it doesn't
    really do anything for the non-GUI views.
  -
 */