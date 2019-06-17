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
   * @param tickRate is the new tick rate
   */
  void setTickRate(int tickRate);

  /**
   * Outputs the animation as a string to an Appendable object. Specific implementations of the
   * view may not support this functionality.
   */
  void outputText();

  /**
   * Renders the animation that the controller's view represents. Specific implementations of the
   * view may not support this functionality.
   */
  void renderAnimation();

}
