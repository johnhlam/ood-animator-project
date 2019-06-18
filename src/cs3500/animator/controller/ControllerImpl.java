package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.view.IView;

/**
 * Represents one implementation of the controller. It contains the timer logic for an animation,
 * as well as the Appendable for textual views. It relays information back and forth between the
 * model and the view. It also acts as a listener to user events and handles them accordingly.
 */
public class ControllerImpl implements IController, ActionListener {

  private final IView view;
  private final IModel model;
  private final Appendable ap;
  private int tickRate;
  private Timer timer;
  private int tick;

  /**
   * Constructs a controller that holds all the information needed for the view to represent an
   * animation. It initializes a timer to the given tick rate, as well as initializes the view to
   * carry the proper information about the animation, such as the size and max x and y values.
   *
   * @param view the view to view the animation
   * @param model the model that holds the animation logic
   * @param tickRate the tick rate of the animation
   * @param ap the Appendable to output to
   * @throws IllegalArgumentException if arguments are null or tick rate is negative
   */
  public ControllerImpl(IView view, IModel model, int tickRate, Appendable ap)
          throws IllegalArgumentException {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Given view and/or model cannot be null.");
    }

    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate cannot be less than or equal to 0.");
    }

    this.ap = ap;
    this.view = view;
    this.model = model;
    this.tickRate = tickRate;

    this.tick = 0; // Starts the tick count at 0
    this.timer = new Timer(1000 / this.tickRate, this);
    this.view.setCanvas(model.getX(), model.getY(), model.getWidth(), model.getHeight(),
            model.getMaxX(), model.getMaxY());
  }



  /**
   * Sets the tickRate to the given tick rate. Also, resets the Timer, gives it a new delay based on
   * the tick rate, and restarts it.
   *
   * @param tickRate is the new tick rate
   */
  @Override
  public void setTickRate(int tickRate) {
    this.tickRate = tickRate;
    this.timer.stop();
    this.timer.setDelay(tickRate);
    this.timer.start();
  }

  @Override
  public void outputText() {
    view.toOutput(model.getShapes(), this.ap, this.tickRate);
  }

  @Override
  public void renderAnimation() {
    timer.start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = this.model.getShapesAtTick(tick++);
    this.view.render(toRender);
  }
}