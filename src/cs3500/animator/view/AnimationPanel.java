package cs3500.animator.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;

public class AnimationPanel extends JPanel implements IAnimationPanel {
  List<IReadOnlyShape> shapes = null;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (shapes != null) {
      for (IReadOnlyShape shape : shapes) {
        switch (shape.getType()) {
          case RECTANGLE:
            g.fillRect((int)shape.getX(), (int)shape.getY(), (int)shape.getWidth(), (int)shape.getHeight());
            break;
          case ELLIPSE:
            g.fillOval((int)shape.getX(), (int)shape.getY(), (int)shape.getWidth(),
                  (int)shape.getHeight());
            break;
          default:

        }
      }
    }
  }


  @Override
  public void draw(List<IReadOnlyShape> toDraw) {
    shapes = toDraw;
    repaint();
  }
}
