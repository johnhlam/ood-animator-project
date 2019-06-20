package cs3500.animator.controller;

import cs3500.animator.model.ShapeType;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;

/**
 * Represents one implementation of the controller. It contains the timer logic for an animation,
 * as well as the Appendable for textual views. It relays information back and forth between the
 * model and the view. It also acts as a listener to user events and handles them accordingly.
 */
public class ControllerImpl implements IController, Features {

  private final IView view;
  private final IModel model;
  private final Appendable ap;
  private int tickRate;
  private Timer timer;
  private int tick;
  private boolean loopbackToggle = false;

  /**
   * Constructs a controller that holds all the information needed for the view to represent an
   * animation. It initializes a timer to the given tick rate, as well as initializes the view to
   * carry the proper information about the animation, such as the size and max x and y values.
   *
   * @param view     the view to view the animation
   * @param model    the model that holds the animation logic
   * @param tickRate the tick rate of the animation
   * @param ap       the Appendable to output to
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

    this.tick = 1; // Starts the tick count at 1
    this.timer = new Timer(1000 / this.tickRate, (ActionEvent e) -> {
      if (this.tick > this.model.getFinalTick() && this.loopbackToggle) {
        this.tick = 1;
      }
      List<IReadOnlyShape> toRender = this.model.getShapesAtTick(tick++);
      this.view.render(toRender);

    });

    this.view.setCanvas(model.getX(), model.getY(), model.getWidth(), model.getHeight(),
        model.getMaxX(), model.getMaxY());

    try {
      this.view.setFeatures(this);
      this.view.setShapes(model.getShapes());
    } catch (UnsupportedOperationException e) {

    }
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
    this.view.render(model.getShapesAtTick(1));
//    timer.start();
    // TODO: For a visual, the timer should start, but for the editor, the timer should not
  }

  @Override
  public void play() {
    this.timer.start();
  }

  @Override
  public void stop() {
    this.timer.stop();
  }

  @Override
  public void restart() {
    this.tick = 1;
    this.view.render(model.getShapesAtTick(1));
  }

  @Override
  public void faster() {
    this.tickRate += 5;
    this.timer.setDelay(1000 / this.tickRate);
  }

  @Override
  public void slower() {
    if (this.tickRate > 5) {
      this.tickRate -= 5;
      this.timer.setDelay(1000 / this.tickRate);
    }
  }

  @Override
  public void toggleLoopback() {
    this.loopbackToggle = !this.loopbackToggle;
  }

  @Override
  public void addShape(String id, ShapeType type) throws IllegalArgumentException {
    if (id == null || type == null) {
      throw new IllegalArgumentException("Given id, ShapeType, and/or Color in addShape are null");
    }

    // default values for a shape
    this.model.addShape(id, type, 0, 0, 0, 0, Color.BLACK);
    this.view.setShapes(model.getShapes());
    // TODO: Vido had a tick field in class????
  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("Given id to removeShape is null");
    }

    this.model.removeShape(id);
    this.view.setShapes(model.getShapes());
  }

  @Override
  public void addKeyframe(String id, int tick, double width, double height, double x, double y,
      Color color) throws IllegalArgumentException {
    this.model.addKeyframe(id, tick, x, y, width, height, color);
    this.view.setShapes(model.getShapes());
  }

  @Override
  public void removeKeyframe(String id, int tick) throws IllegalArgumentException {
    this.model.removeKeyframe(id, tick);
    this.view.setShapes(model.getShapes());
  }

  @Override
  public void modifyKeyframe(String id, int tick, double width, double height,  double x, double y,
      Color color) throws IllegalArgumentException {
    this.model.addKeyframe(id, tick, width, height, x, y, color);
    this.view.setShapes(model.getShapes());
    // FIXME!!!
    //  Note: should separate adding and modifying keyframes in the model as separate methods
    //  (because they are one method right now).
  }
}
