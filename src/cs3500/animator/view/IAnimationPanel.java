package cs3500.animator.view;


import cs3500.animator.model.IReadOnlyShape;
import java.util.List;

public interface IAnimationPanel {
  void draw(List<IReadOnlyShape> toDraw);
}
