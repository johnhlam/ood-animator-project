package cs3500.animator.view;

import java.awt.Dimension;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cs3500.animator.model.IReadOnlyShape;

/**
 * VisualView is a class that extends JFrame and implements IView. It extends JFrame so that it can
 * create a visual animation that you can see on the screen by using various methods from the class.
 * When the constructor for this class is called, an empty window will pop-up, with scroll bars.
 * Calling the {@link VisualView#render(List)}  method for this class will generate one frame of the
 * animation.
 */
public class VisualView extends JFrame implements IView {

  private AnimationPanel panel;

  /**
   * Constructs an instance of a  VisualView. Initializes the animation panel to a new animation
   * panel.
   */
  public VisualView() {
    this.panel = new AnimationPanel();
  }

  /**
   * After doing what the interface says, also initializes the animation's canvas/panel with the
   * given values.
   */
  @Override
  public void setCanvas(int x, int y, int width, int height, int maxX, int maxY) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Given width and height for VisualView#setCanvas(int, "
              + "int) cannot be non-positive");
    }

    if (maxX < 0 || maxY < 0) {
      throw new IllegalArgumentException("Max x and max y coordinates cannot be negative");
    }

    this.panel.setMinimumSize(new Dimension(width, height));
    this.panel.setPreferredSize(new Dimension(maxX, maxY));

    super.setSize(width, height);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Sets the default popup location of the window to the top-left corner of the screen
    super.setLocation(0, 0);

    JScrollPane scrollPane = new JScrollPane(this.panel);

    super.add(scrollPane);

    super.setVisible(true);
  }

  /**
   * Since a visual view cannot output text to an Appendable, it throws an
   * UnsupportedOperationException.
   *
   * @param shapes   is the list of shapes to output to the Appendable
   * @param ap       the Appendable to output to
   * @param tickRate the tick rate of the animation
   * @throws UnsupportedOperationException due to visual view not being able to output text
   */
  @Override
  public void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view cannot output text.");
  }

  /**
   * Renders the given list of shapes on the panel (the window) that the animation is being played
   * on. Details of how each shape is drawn is left to the panel. This method should be called on
   * every tick/frame of the animation.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will display.
   * @throws IllegalArgumentException if the given list of shapes is null, or the panel is null
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("Given shapes to setShapes is null");
    }

    this.panel.draw(shapes);
  }
}
