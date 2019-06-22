package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.model.IReadOnlyShape;


/**
 * AnimationPanel is a class that implements JPanel and implements IAnimationPanel. Its purpose is
 * to render a given list of shapes onto the panel. It stores a list of shapes that represent each
 * shape's current state, and draws them as such.
 */
public class AnimationPanel extends JPanel implements IAnimationPanel {

  // The list of shapes that are currently being rendered
  List<IReadOnlyShape> shapes = null;

  /**
   * Renders the list of shapes stored in the class as images in the panel. Currently supports
   * drawing rectangles and ellipses.
   *
   * @param g is the Graphics object onto which the images will be drawn
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (shapes != null) {
      for (IReadOnlyShape shape : shapes) {
        g.setColor(shape.getColor());
        switch (shape.getType()) {
          case RECTANGLE:
            g.fillRect((int) shape.getX(), (int) shape.getY(), (int) shape.getWidth(),
                (int) shape.getHeight());
            break;
          case ELLIPSE:
            g.fillOval((int) shape.getX(), (int) shape.getY(), (int) shape.getWidth(),
                (int) shape.getHeight());
            break;
          default:
            // Does nothing, since you should never get here.
        }
      }
    }
  }

  /**
   * Stores the given list of shapes in the class, and delegates drawing to super.repaint().
   *
   * @param toDraw is the list of shapes to be drawn
   * @throws IllegalArgumentException if the given list of shapes is null
   */
  @Override
  public void draw(List<IReadOnlyShape> toDraw) throws IllegalArgumentException {
    if (toDraw == null) {
      throw new IllegalArgumentException("Given list of shapes for toDraw is null");
    }

    this.shapes = toDraw;
    super.repaint();
  }

  @Override
  public void setDrawPanelSize(int width, int height, int preferredX, int preferredY) {
    this.setMinimumSize(new Dimension(width, height));
    this.setPreferredSize(new Dimension(preferredX, preferredY));
  }
}
