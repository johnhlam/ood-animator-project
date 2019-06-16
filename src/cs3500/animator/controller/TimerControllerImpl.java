package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.Timer;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.view.IView;

/**
 * TimerControllerImpl is a class that implements the IController and the ActionListener interface.
 * It represents a controller that can run programs that require a Timer and a tick rate (i.e. a
 * program that needs to perform actions on every tick), such as a program that needs to show visual
 * animations. It is designed to relay information from a model to a view that requires some type of
 * timer to perform actions on each tick.
 */
public class TimerControllerImpl implements IController, ActionListener {

  private final IView view;
  private final IModel model;
  private Timer timer;
  private int tickRate;
  // tick is the current tick of the program
  private int tick;

  /**
   * Constructs an instance of this class with a given IView, IModel, and integer tick rate. Each
   * instance of this class can only work with one IView and one IModel; to change either one, a new
   * instance of this class must be created. This constructor also takes in a tick rate, which is
   * stored in the class, and is used to create a javax.swing.Timer.
   *
   * @param view     is the IView that will produce output when necessary.
   * @param model    is the IModel that will provide data to the IView at each tick.
   * @param tickRate is the tick rate that this controller will run at (in ticks/second).
   * @throws IllegalArgumentException if the given IView and/or IModel are null, or if the tick rate
   *                                  is not positive.
   */
  public TimerControllerImpl(IView view, IModel model, int tickRate)
      throws IllegalArgumentException {
    if (view == null || model == null) {
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

  /**
   * Starts the timer, and therefore starts calling
   * {@link TimerControllerImpl#actionPerformed(ActionEvent)}
   * on every tick.
   */
  @Override
  public void run() {
    this.timer.start();
  }

  /**
   * This method is called on every tick once the Timer starts. On every tick, it will query the
   * model for a list of shapes (based on the current tick), and will pass that information into the
   * view. The view will use that data to generate some type of output on each tick.
   *
   * @param e is the ActionEvent that the timer passes in
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = this.model.getShapesAtTick(tick++);
    this.view.play(toRender);
  }
}
