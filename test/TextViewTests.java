import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.animator.model.IModelShape;
import cs3500.animator.model.IModelShapeImpl;
import cs3500.animator.model.IMotion;
import cs3500.animator.model.IMotionImpl;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.view.IView;
import cs3500.animator.view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the methods of TextView.
 */
public class TextViewTests {

  private Appendable out;
  private IView textView;
  private IMotion motion1 = new IMotionImpl(1, 10, 10, 0, 0, Color.BLACK, 10, 15, 15, 0, 0,
      Color.RED);
  private IMotion motion2 = new IMotionImpl(5, 15, 10, 20, 20, Color.BLACK, 10, 15, 15, 0, 0,
      Color.RED);
  private IMotion motion3 = new IMotionImpl(10, 15, 15, 0, 0, Color.RED, 20, 15, 15, 0, 0,
      Color.RED);
  private List<IReadOnlyShape> shapes;

  /**
   * Represents a mock Appendable class that throws exceptions on appending to test that an
   * IllegalStateException is thrown.
   */
  private class AppendableThrowsException implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("Error");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("Error");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("Error");
    }
  }

  @Before
  public void setUp() {
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);

    IModelShape ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12,
        Color.WHITE);

    out = new StringBuilder();
    this.textView = new TextView(out);
    rect1.addMotion(motion1);
    rect1.addMotion(motion3);

    ellipse1.addMotion(motion2);
    ellipse1.addMotion(motion3);

    shapes = new ArrayList<>();
    shapes.add(rect1);
    shapes.add(ellipse1);
  }

  /**
   * Tests that passing a null appendable throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullTextView() {
    new TextView(null);
  }

  /**
   * Tests that setting the text view's max window sizes throws an unsupported operation exception.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testSetMaxWindowSize() {
    this.textView.setMaxWindowSize(100, 100);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas1() {
    this.textView.setCanvas(0, 0, -20, 30);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas2() {
    this.textView.setCanvas(0, 0, 20, -30);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas3() {
    this.textView.setCanvas(0, 0, 0, 0);
  }

  /**
   * Tests setting the canvas changes the canvas fields.
   */
  @Test
  public void testSetCanvas() {
    this.textView.setCanvas(100, 100, 360, 360);
    this.textView.play(new ArrayList<>());
    assertEquals("canvas 100 100 360 360\n", out.toString());
  }

  /**
   * Tests playing a null input throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayingNull() {
    this.textView.play(null);
  }

  /**
   * Tests appending throwing an IOException.
   */
  @Test(expected = IllegalStateException.class)
  public void testAppendableException() {
    IView view = new TextView(new AppendableThrowsException());
    view.play(new ArrayList<>());
  }

  /**
   * Tests printing an empty animation.
   */
  @Test
  public void testPrintEmptyAnimation() {
    this.textView.play(new ArrayList<>());
    assertEquals("canvas 0 0 0 0\n", out.toString());
  }

  /**
   * Tests printing an animation with only shapes.
   */
  @Test
  public void testPrintShapeNoMotion() {
    List<IReadOnlyShape> noMotions = new ArrayList<>();
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    IModelShape ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12,
        Color.WHITE);
    noMotions.add(rect1);
    noMotions.add(ellipse1);
    this.textView.play(noMotions);
    assertEquals("canvas 0 0 0 0\n" +
        "shape R1 rectangle\n" +
        "shape E1 ellipse\n", out.toString());
  }

  /**
   * Tests printing an animation with shapes and motions.
   */
  @Test
  public void testPrintShapesAndMotions() {
    this.textView.play(this.shapes);
    assertEquals("canvas 0 0 0 0\n" +
        "shape R1 rectangle\n" +
        "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n" +
        "motion R1 10 0 0 15 15 255 0 0\t20 0 0 15 15 255 0 0\n" +
        "shape E1 ellipse\n" +
        "motion E1 5 20 20 15 10 0 0 0\t10 0 0 15 15 255 0 0\n" +
        "motion E1 10 0 0 15 15 255 0 0\t20 0 0 15 15 255 0 0\n", out.toString());
  }

  /**
   * Tests that outputting to a file works correctly. NOTE: Working directory must be set to
   * resources, otherwise, test will most likely not be able to find file.
   */
  @Test
  public void testToFile() throws IOException {
    Appendable writer = new FileWriter("test2.txt");
    IView toFile = new TextView(writer);
    toFile.play(this.shapes);
    ((Closeable) writer).close();
    Scanner reader = new Scanner(new FileReader("test2.txt"));
    StringBuilder sb = new StringBuilder();
    while (reader.hasNextLine()) {
      sb.append(reader.nextLine() + "\n");
    }
    reader.close();
    assertEquals("canvas 0 0 0 0\n" +
        "shape R1 rectangle\n" +
        "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n" +
        "motion R1 10 0 0 15 15 255 0 0\t20 0 0 15 15 255 0 0\n" +
        "shape E1 ellipse\n" +
        "motion E1 5 20 20 15 10 0 0 0\t10 0 0 15 15 255 0 0\n" +
        "motion E1 10 0 0 15 15 255 0 0\t20 0 0 15 15 255 0 0\n", sb.toString());
  }


}
