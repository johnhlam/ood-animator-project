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
  private Timer timer;
  private int tickRate;
  private int tick;


  public ControllerImpl(IView view, IModel model, int tickRate) throws IllegalArgumentException {
    if(view == null || model == null) {
      throw new IllegalArgumentException("Given view and/or model cannot be null");
    }

    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate cannot be less than 0");
    }

    this.view = view;
    this.model = model;
    this.tickRate = tickRate;

    this.tick = 0; // Starts the tick count at 0
    this.timer = new Timer(1000 / this.tickRate, this);
  }

  @Override
  public void setTickRate(int tickRate) {
    this.tickRate = tickRate;
    this.timer = new Timer(1000 / this.tickRate, this);
    this.timer.start();
  }

  @Override
  public void run() {

  }

  @Override
  public Dimension getCanvasSize() {
    return new Dimension(this.model.getWidth(), this.model.getHeight());
  }

  @Override
  public Point2D getTopXY() {
    return new Point(this.model.getX(), this.model.getY());
  }

  @Override
  public Point2D getMaxXY() {
    return new Point(this.model.getMaxX(), this.model.getMaxY());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = this.model.getShapesAtTick(tick++);
    this.view.setShapes(toRender);
    this.view.render(); //TODO: Change to play later
  }
}
