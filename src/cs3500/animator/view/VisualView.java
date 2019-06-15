package cs3500.animator.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IReadOnlyShape;

public class VisualView extends JFrame implements IView {

  private int canvasWidth;
  private int canvasHeight;
  private int maxX;
  private int maxY;
  private AnimationPanel panel;
  private JScrollPane scrollPane;

  public VisualView(int canvasWidth, int canvasHeight, int maxX, int maxY) {

    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.maxX = maxX;
    this.maxY = maxY;

    this.panel = new AnimationPanel();
    this.panel.setMinimumSize(new Dimension(this.canvasWidth, this.canvasHeight));
    this.panel.setPreferredSize(new Dimension(this.maxX, this.maxY));

    super.setSize(this.canvasWidth, this.canvasHeight);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocation(0, 0);

    this.scrollPane = new JScrollPane(this.panel);

    super.add(scrollPane);

    super.setVisible(true);
  }

  @Override
  public void setCanvas(int x, int y, int width, int height) {
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
