package cs3500.animator.view;

import oracle.jvm.hotspot.jfr.JFR;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IReadOnlyShape;

public class VisualView extends JFrame implements IView {
  List<IReadOnlyShape> shapes;

  public VisualView(int canvasWidth, int canvasHeight, int maxX, int maxY) {
    this.shapes = new ArrayList<>();

    setSize(canvasWidth, canvasHeight);
    setPreferredSize(new Dimension(maxX, maxY));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocation(canvasWidth / 2, canvasHeight / 2);
    setVisible(true);
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

  }
}
