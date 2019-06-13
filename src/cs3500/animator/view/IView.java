package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyShape;
import java.util.List;

public interface IView {

  void setShapes(List<IReadOnlyShape> shapes);

  void toOutput(int x, int y, int width, int height)
          throws IllegalStateException, UnsupportedOperationException;

  void render() throws UnsupportedOperationException;
}
