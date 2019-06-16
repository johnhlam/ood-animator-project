package cs3500.animator.controller;

import cs3500.animator.model.IModel;
import cs3500.animator.view.IView;

import java.util.List;

/**
 * TextControllerImpl is a class that implements IController. It controls operations between a given
 * IModel, and a text-based IView. It is designed to relay information from a model to a text-based
 * view, so that the view can generate some type of text output. Such output can take the form of a
 * file, or simply a String that is printed.
 */
public class TextControllerImpl implements IController {

  private final IView view;
  private final IModel model;

  /**
   * Constructs an instance of this class with the given IView and an IModel. Each of these fields
   * are final, and therefore cannot be changed at a later time. Therefore, each instance of this
   * class can only run with one IView and one IModel. To run with a different set of views/models,
   * a new instance of this class must be created.
   *
   * @param view  is the (text-based) IView that will produce output when necessary.
   * @param model is the IModel that will provide data so that the IView can produce the appropriate
   *              output.
   * @throws IllegalArgumentException if the given IView or IModel are null, or if the given tick
   *                                  rate is not positive
   */
  public TextControllerImpl(IView view, IModel model)
      throws IllegalArgumentException {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Given view and/or model cannot be null");
    }

    this.view = view;
    this.model = model;
    this.view.setCanvas(model.getX(), model.getY(), model.getWidth(), model.getHeight());
  }

  /**
   * This controller does not support being able to set tick rates, so it throws an exception.
   *
   * @param tickRate is the new tick rate (which isn't used)
   * @throws UnsupportedOperationException since this controller does not support being able to set
   *                                       tick rates.
   */
  @Override
  public void setTickRate(int tickRate) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Tick rate of a text-based animation should not be " +
        "changed.");
  }

  /**
   * Gives the IView a list of (read-only) shapes from the IModel, and tells the view to produce
   * some kind of output by calling its {@link IView#play(List)} method. Examples of output that
   * might be generated include files with text, or a String that is printed in the
   * console/terminal.
   */
  @Override
  public void run() {
    this.view.play(this.model.getShapes());
  }
}
