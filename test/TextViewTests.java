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
    this.textView = new TextView();
    rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    rect1.addKeyframe(20, 15, 15, 0, 0, Color.RED);

    ellipse1.addKeyframe(5, 15, 10, 20, 20, Color.BLACK);
    ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    ellipse1.addKeyframe(20, 15, 15, 0, 0, Color.RED);

    shapes = new ArrayList<>();
    shapes.add(rect1);
    shapes.add(ellipse1);
  }

  /**
   * Tests that passing a null list of shapes throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullList() {
    this.textView.toOutput(null, this.out, 10);
  }

  /**
   * Tests that passing a null appendable throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullAp() {
    this.textView.toOutput(this.shapes, null, 10);
  }

  /**
   * Tests that setting the text view's max window sizes to negative values throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetMaxWindowSize() {
    this.textView.setCanvas(100, 100, 100, 100, -1, -1);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas1() {
    this.textView.setCanvas(0, 0, -20, 30, 100, 100);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas2() {
    this.textView.setCanvas(0, 0, 20, -30, 100, 100);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas3() {
    this.textView.setCanvas(0, 0, 0, 0, 100, 100);
  }

  /**
   * Tests setting the canvas changes the canvas fields.
   */
  @Test
  public void testSetCanvas() {
    this.textView.setCanvas(100, 100, 360, 360, 500, 500);
    this.textView.toOutput(new ArrayList<>(), this.out, 10);
    assertEquals("canvas 100 100 360 360\n", out.toString());
  }

  /**
   * Tests that calling render on a text view throws an UnsupportedOperationException
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testRenderError() {
    this.textView.render(this.shapes);
  }

  /**
   * Tests that calling setFeatures on a text view throws an UnsupportedOperationException
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testSetFeaturesError() {

    this.textView.setFeatures(null);
  }

  /**
   * Tests that calling setShapes on a text view does nothing
   */
  @Test
  public void testSetShapesError() {

    this.textView.setShapes(this.shapes);
    this.textView.toOutput(new ArrayList<>(), this.out, 10);

    assertEquals("canvas 0 0 0 0\n", this.out.toString());
  }

  /**
   * Tests playing a null list of shapes throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayingNull() {
    this.textView.toOutput(null, this.out, 10);
  }

  /**
   * Tests appending throwing an IOException.
   */
  @Test(expected = IllegalStateException.class)
  public void testAppendableException() {
    this.textView.toOutput(new ArrayList<>(), new AppendableThrowsException(), 10);
  }

  /**
   * Tests printing an empty animation.
   */
  @Test
  public void testPrintEmptyAnimation() {
    this.textView.toOutput(new ArrayList<>(), this.out, 10);
    assertEquals("canvas 0 0 0 0\n", out.toString());
  }

  /**
   * Tests to make sure that printing a view with 1 shape and 1 keyframe works properly.
   */
  @Test
  public void testPrint1Shape1Keyframe() {

    List<IReadOnlyShape> oneKeyframe = new ArrayList<>();
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    rect1.addKeyframe(10, 20, 30, 40, 50, Color.BLACK);
    oneKeyframe.add(rect1);
    this.textView.toOutput(oneKeyframe, this.out, 10);
    assertEquals(
        "canvas 0 0 0 0\n"
            + "shape R1 rectangle\n"
            + "motion R1 10 40 50 20 30 0 0 0\t10 40 50 20 30 0 0 0\n", out.toString());
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
    this.textView.toOutput(noMotions, this.out, 10);
    assertEquals("canvas 0 0 0 0\n" +
        "shape R1 rectangle\n" +
        "shape E1 ellipse\n", out.toString());
  }

  /**
   * Tests printing an animation with shapes and motions.
   */
  @Test
  public void testPrintShapesAndMotions() {
    this.textView.toOutput(this.shapes, this.out, 10);
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
    this.textView.toOutput(this.shapes, writer, 10);
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
