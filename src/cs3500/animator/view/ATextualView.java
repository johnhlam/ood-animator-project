package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IReadOnlyShape;

public abstract class ATextualView implements IView {

  protected List<IReadOnlyShape> shapes;
  protected final Appendable ap;

  protected ATextualView(Appendable ap) {
    this.shapes = new ArrayList<IReadOnlyShape>();
    this.ap = ap;
  }

  @Override
  public void setShapes(List<IReadOnlyShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public abstract void toOutput(int x, int y, int width, int height) throws IllegalStateException;

  @Override
  public void render() {
    throw new UnsupportedOperationException("Text views cannot render images.");
  }

  protected void attemptAppend(String toBeAppended) throws IllegalStateException {
    try {
      this.ap.append(toBeAppended);
    } catch(IOException e) {
      throw new IllegalStateException("Could not append String to Appendable.");
    }
  }

}
