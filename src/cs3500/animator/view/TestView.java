package cs3500.animator.view;

import java.util.List;
import java.util.Scanner;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IReadOnlyShape;

public class TestView implements IView {
  private Features features;
  private final Readable readable;

  public TestView(Readable readable) {
    this.readable = readable;
  }

  @Override
  public void setCanvas(int x, int y, int width, int height, int maxX, int maxY) throws IllegalArgumentException {

  }

  @Override
  public void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws UnsupportedOperationException {

  }

  @Override
  public void render(List<IReadOnlyShape> shapes) throws UnsupportedOperationException {
    Scanner scanner = new Scanner(readable);
    String command = scanner.next();
    switch (command) {
      case "":
    }
  }

  @Override
  public void setFeatures(Features features) throws UnsupportedOperationException {

  }

  @Override
  public void setShapes(List<IReadOnlyShape> shapes) {

  }
}
