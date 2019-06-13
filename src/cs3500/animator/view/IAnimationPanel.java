package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.IReadOnlyShape;

public interface IAnimationPanel {
  void draw(List<IReadOnlyShape> toDraw);
}
