package cs3500.animator.view;

import java.awt.Dimension;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cs3500.animator.model.IReadOnlyShape;

/**
 * VisualView is a class that extends JFrame and implements IView. It extends JFrame so that it
 * can create a visual animation that you can see on the screen by using various methods from the
 * class. When the constructor for this class is called, an empty window will pop-up, with scroll
 * bars. Calling the {@link VisualView#play(List)} method for this class will generate one frame
 * of the animation.
 */
public class VisualView extends JFrame implements IView {

  private int canvasWidth;
  private int canvasHeight;
  private int maxX;
  private int maxY;
  private AnimationPanel panel;
  private JScrollPane scrollPane;

  /**
   * Constructs a VisualView with the given parameters, each of which affect the final window
   * size in some way. Constructing an instance of this class will create a window with the
   * specified dimension, and a scroll bar along the right and the bottom. The created window
   * will be empty, until the {@link VisualView#play(List)} is called.
   *
   * @param canvasWidth is the width of the panel
   * @param canvasHeight is the height of the panel
   * @param maxX is the preferred size of the panel (maximum x value)
   * @param maxY is the preferred size of the panel (maximum y value)
   * @throws IllegalArgumentException if either the width or height are not positive
   */
  public VisualView(int canvasWidth, int canvasHeight, int maxX, int maxY) throws IllegalArgumentException {

    if (canvasWidth <= 0 || canvasHeight <= 0) {
      throw new IllegalArgumentException("Given width and/or height to VisualView is not positive");
    }

    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.maxX = maxX;
    this.maxY = maxY;


    this.panel = new AnimationPanel();
    this.panel.setMinimumSize(new Dimension(this.canvasWidth, this.canvasHeight));
    this.panel.setPreferredSize(new Dimension(this.maxX, this.maxY));

    super.setSize(this.canvasWidth, this.canvasHeight);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Sets the default popup location of the window to the top-left corner of the screen
    super.setLocation(0, 0);

    this.scrollPane = new JScrollPane(this.panel);

    super.add(scrollPane);

    super.setVisible(true);
  }

  /**
   * Sets the canvas size to the given width and height values. {@code x} and {@code y} are not
   * used, since the visual view does not require those values.
   *
   * @param x      the lowest x value for the animation
   * @param y      the lowest y value for the animation
   * @param width  the width of the animation canvas
   * @param height the width of the animation canvas
   * @throws IllegalArgumentException if width and/or height are not positive
   */
  @Override
  public void setCanvas(int x, int y, int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Given width and height for VisualView#setCanvas(int, "
          + "int) cannot be non-positive");
    }
    this.canvasWidth = width;
    this.canvasHeight = height;
  }

  @Override
  public void play(List<IReadOnlyShape> shapes) throws RuntimeException {
    if (shapes == null) {
      throw new IllegalArgumentException("Given shapes to setShapes is null");
    }

    if (this.panel == null) {
      throw new RuntimeException("Attempted to render a null panel");
    }

    this.panel.draw(shapes);

  }

  @Override
  public void setMaxWindowSize(int width, int height) {
    this.maxX = width;
    this.maxY = height;
  }
}
