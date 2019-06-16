package cs3500.animator.controller;

/**
 * IController is an interface that represents a controller that will mediate operations between a
 * model and a view. Neither the model nor the view should directly communicate with each other; all
 * operations will go through and be called from the controller. Each instance of an IController
 * should be able to control operations between 1 model and 1 view. Specific implementations may
 * differ in how exactly the controller will relay information to the model and view.
 */
public interface IController {

  /**
   * Sets the controller's tick rate the given value.
   *
   * <p>NOTE: This method is not used in this assignment. It was created in anticipation for a
   * future assignment, where such an operation may need to be supported.</p>
   *
   * @param tickRate is the new tick rate
   * @throws UnsupportedOperationException if the controller does not support changing tick rates
   */
  void setTickRate(int tickRate) throws UnsupportedOperationException;

  /**
   * Runs the program. Generally, this means relaying information between the model and the view,
   * and producing some kind of output. Specific implementations may decide how exactly they want to
   * relay this information, and what their output looks like.
   */
  void run();

}
