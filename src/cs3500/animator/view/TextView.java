package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyShape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextView extends ATextualView {

  public TextView(Appendable ap) {
    super(ap);
  }

  @Override
  public void toOutput(int x, int y, int width, int height) throws IllegalStateException {

    this.attemptAppend("canvas ");
    this.attemptAppend(Integer.toString(x));
    this.attemptAppend(" ");
    this.attemptAppend(Integer.toString(y));
    this.attemptAppend(" ");
    this.attemptAppend(Integer.toString(width));
    this.attemptAppend(" ");
    this.attemptAppend(Integer.toString(height));
    this.attemptAppend("\n");

    for(IReadOnlyShape s : this.shapes) {
        this.attemptAppend(s.printMotions());
        this.attemptAppend("\n");
    }
  }
  
}
