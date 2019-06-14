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
            // Does nothing, since you should never get here.
        }
      }
    }
  }


  @Override
  public void draw(List<IReadOnlyShape> toDraw) throws IllegalArgumentException {
    if(toDraw == null) {
      throw new IllegalArgumentException("Given list of shapes for toDraw is null");
    }

    this.shapes = toDraw;
    super.repaint();
  }
}
