package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.view.IView;

public class TimerControllerImpl implements IController, ActionListener {
  private final IView view;
  private final IModel model;
  private Timer timer;
  private int tickRate;
  private int tick;


  public TimerControllerImpl(IView view, IModel model, int tickRate) throws IllegalArgumentException {
    if(view == null || model == null) {
      throw new IllegalArgumentException("Given view and/or model cannot be null.");
    }

    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate cannot be less than or equal to 0.");
    }

    this.view = view;
    this.model = model;
    this.tickRate = tickRate;

    this.tick = 0; // Starts the tick count at 0
    this.timer = new Timer(1000 / this.tickRate, this);
    this.view.setCanvas(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    this.view.setMaxWindowSize(model.getMaxX(), model.getMaxY());
  }

  @Override
  public void setTickRate(int tickRate) {
    this.tickRate = tickRate;
    this.timer = new Timer(1000 / this.tickRate, this);
    this.timer.start();
  }

  @Override
  public void run() {
    this.timer.start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = this.model.getShapesAtTick(tick++);
    this.view.play(toRender);
  }
}