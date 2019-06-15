package cs3500.animator.controller;

public interface IController {

  void setTickRate(int tickRate) throws UnsupportedOperationException;

  void run();

}
