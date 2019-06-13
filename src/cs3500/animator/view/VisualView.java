package cs3500.animator.view;

import cs3500.animator.util.AnimationBuilder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IReadOnlyShape;

public class VisualView extends JFrame implements IView {
  List<IReadOnlyShape> shapes;

  AnimationPanel panel;
  JScrollPane scrollPane;

  public VisualView(int canvasWidth, int canvasHeight, int maxX, int maxY) {
    this.shapes = new ArrayList<>();

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
  public void setShapes(List<IReadOnlyShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void toOutput(int x, int y, int width, int height) throws IllegalStateException, UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view cannot display text.");
  }

  @Override
  public void render() {
    if (this.panel != null) {
      this.panel.draw(this.shapes);
    }
  }
}
