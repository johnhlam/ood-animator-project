package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.IReadOnlyShape;

public class EditorView implements IView {
  IView animationView = new VisualView();


  @Override
  public void setCanvas(int x, int y, int width, int height, int maxX, int maxY) throws IllegalArgumentException {

  }

  @Override
  public void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws UnsupportedOperationException {

  }

  @Override
  public void render(List<IReadOnlyShape> shapes) throws UnsupportedOperationException {

  }
}
