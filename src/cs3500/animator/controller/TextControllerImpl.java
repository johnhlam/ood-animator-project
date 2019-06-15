package cs3500.animator.controller;

import cs3500.animator.model.IModel;
import cs3500.animator.view.IView;

public class TextControllerImpl implements IController {
  private final IView view;
  private final IModel model;
  private int tickRate;

  public TextControllerImpl(IView view, IModel model, int tickRate) throws IllegalArgumentException {
    if(view == null || model == null) {
      throw new IllegalArgumentException("Given view and/or model cannot be null");
    }

    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate cannot be less than 0");
    }

    this.view = view;
    this.model = model;
    this.tickRate = tickRate;
    this.view.setCanvas(model.getX(), model.getMaxY(), model.getWidth(), model.getHeight());
  }

  @Override
  public void setTickRate(int tickRate) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Tick rate of a text-based animation should not be " +
            "changed.");
  }

  @Override
  public void run() {
    this.view.play(this.model.getShapes());
  }
}
