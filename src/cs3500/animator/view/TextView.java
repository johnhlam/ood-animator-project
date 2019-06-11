package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyShape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextView implements IView {

  List<IReadOnlyShape> shapes;
  Appendable app;

  public TextView() {
    this.shapes = new ArrayList<IReadOnlyShape>();
    // Temporary Appendable
    this.app = new StringBuilder();
  }

  public TextView(List<IReadOnlyShape> shapes, Appendable app) {
    this.shapes = shapes;
    this.app = app;
  }

  @Override
  public void setShapes(List<IReadOnlyShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public Appendable toOutput(int x, int y, int width, int height) throws IllegalStateException {

    String errorMessage = "Could not append String to Appendable, from TextView.";

    this.attemptAppend("canvas ", errorMessage);
    this.attemptAppend(Integer.toString(x), errorMessage);
    this.attemptAppend(" ", errorMessage);
    this.attemptAppend(Integer.toString(y), errorMessage);
    this.attemptAppend(" ", errorMessage);
    this.attemptAppend(Integer.toString(width), errorMessage);
    this.attemptAppend(" ", errorMessage);
    this.attemptAppend(Integer.toString(height), errorMessage);
    this.attemptAppend("\n", errorMessage);

    for(IReadOnlyShape s : this.shapes) {
        this.attemptAppend(s.printMotions(), errorMessage);
        this.attemptAppend("\n", errorMessage);
    }

    return this.app;
  }

  private void attemptAppend(String toBeAppended, String errorMessage) throws IllegalStateException {
    try {
      this.app.append(toBeAppended);
    } catch(IOException e) {
      throw new IllegalStateException(errorMessage);
    }
  }
  
}
