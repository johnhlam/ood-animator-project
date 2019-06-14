package cs3500.animator.view;

import java.awt.*;
import java.util.ArrayList;
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

  public VisualView() {

    this.panel = new AnimationPanel();
    this.panel.setMinimumSize(new Dimension(canvasWidth, canvasHeight));
    this.panel.setPreferredSize(new Dimension(maxX, maxY));

    this.scrollPane = new JScrollPane(this.panel);

    super.setSize(canvasWidth, canvasHeight);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocation(canvasWidth / 2, canvasHeight / 2);

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
