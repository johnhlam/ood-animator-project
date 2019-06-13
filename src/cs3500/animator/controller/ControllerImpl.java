package cs3500.animator.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

public class ControllerImpl implements IController, ActionListener {
  private final IView view;
  private final IModel model;
  private final String viewType;
  private Timer timer;
  private int tickRate;
  private int tick = 0;
  private Appendable ap;

  public ControllerImpl(Readable readable, String viewType, Appendable ap, int tickRate) {
    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate cannot be 0");
    }
    this.tickRate = tickRate;
    AnimationBuilder<IModelImpl> builder = IModelImpl.builder();
    IModel model = AnimationReader.parseFile(readable, builder);
    switch (viewType) {
      case "text":
        view = new TextView(ap);
      case "svg":
        view = new SVGView(ap, tickRate);
      case "visual":
      default:
        throw new RuntimeException("View type supplied is not supported.")
    }
  }

  @Override
  public void setTickRate(int tickRate) {
    this.tickRate = tickRate;
  }

  @Override
  public void run() {
    switch (viewType) {
      case "text":

      case "svg":
      case "visual":
      default:
        throw new RuntimeException("View type supplied is not supported.")
    }
  }

  @Override
  public Dimension getCanvasSize() {
    return new Dimension(model.getWidth(), model.getHeight());
  }

  @Override
  public Point2D getTopXY() {
    return new Point(model.getX(), model.getY());
  }

  @Override
  public Point2D getMaxXY() {
    return new Point(model.getMaxX(), model.getMaxY());
  }

  @Override
  public void setAppendable(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = model.getShapesAtTick(tick++);
    view.setShapes(toRender);
    view.render();
  }
}
