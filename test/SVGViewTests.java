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
import cs3500.animator.view.SVGView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for methods of SVGView.
 */
public class SVGViewTests {

  private Appendable out;
  private IView svgView;
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

    IModelShape ellipse1 =
        new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12, Color.WHITE);

    out = new StringBuilder();
    this.svgView = new SVGView();
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
   * Tests that passing a null appendable throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullList() {
    this.svgView.toOutput(null, this.out, 30);
  }

  /**
   * Tests that passing a null appendable throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullAp() {
    this.svgView.toOutput(this.shapes, null, 30);
  }

  /**
   * Tests that passing a negative tick rate throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegSpeed() {
    this.svgView.toOutput(this.shapes, this.out, -1);
  }

  /**
   * Tests that passing a tick rate of 0 throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSpeed0() {
    this.svgView.toOutput(this.shapes, this.out, 0);
  }

  /**
   * Tests that setting the svg view's max window sizes to a negative value throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetMaxWindowSize() {
    this.svgView.setCanvas(100, 100, 100, 100, -1, -1);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas1() {
    this.svgView.setCanvas(0, 0, -20, 30, 100, 100);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas2() {
    this.svgView.setCanvas(0, 0, 20, -30, 100, 100);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas3() {
    this.svgView.setCanvas(0, 0, 0, 0, 100, 100);
  }

  /**
   * Tests setting the canvas changes the canvas fields.
   */
  @Test
  public void testSetCanvas() {
    this.svgView.setCanvas(100, 100, 360, 360, 500, 500);
    this.svgView.toOutput(new ArrayList<>(), this.out, 10);
    assertEquals("<svg width=\"460\" height=\"460\" xmlns=\"http://www.w3.org/2000/svg\" " +
        "version=\"1.1\">\n</svg>", out.toString());
  }

  /**
   * Tests that calling render on a text view throws an UnsupportedOperationException.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testRenderError() {
    this.svgView.render(this.shapes);
  }

  /**
   * Tests that calling setFeatures on a text view throws an UnsupportedOperationException.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testSetFeaturesError() {

    this.svgView.setFeatures(null);
  }

  /**
   * Tests that calling setShapes on a text view does nothing.
   */
  @Test
  public void testSetShapesError() {

    this.svgView.setShapes(this.shapes);
    this.svgView.toOutput(new ArrayList<>(), this.out, 10);

    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n</svg>", this.out.toString());
  }

  /**
   * Tests appending throwing an IOException.
   */
  @Test(expected = IllegalStateException.class)
  public void testAppendableException() {
    this.svgView.toOutput(new ArrayList<>(), new AppendableThrowsException(), 10);
  }

  /**
   * Tests printing an empty animation.
   */
  @Test
  public void testPrintEmptyAnimation() {
    this.svgView.toOutput(new ArrayList<>(), this.out, 10);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n</svg>", out.toString());
  }

  /**
   * Tests printing an animation with only shapes.
   */
  @Test
  public void testPrintShapeNoKeyframe() {
    List<IReadOnlyShape> noKeyframes = new ArrayList<>();
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    IModelShape ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12,
        Color.WHITE);
    noKeyframes.add(rect1);
    noKeyframes.add(ellipse1);
    this.svgView.toOutput(noKeyframes, this.out, 10);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n" +
        "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
        "0)\" visibility=\"visible\" >\n" +
        "</rect>\n" +
        "\n" +
        "<ellipse id=\"E1\" cx=\"10.0\" cy=\"12.0\" rx=\"12.0\" ry=\"12.0\" fill=\"rgb(255," +
        "255,255)\" visibility=\"visible\" >\n" +
        "</ellipse>\n" +
        "\n" +
        "</svg>", out.toString());
  }

  /**
   * Tests printing one shape.
   */
  @Test
  public void testPrintShape1() {
    List<IReadOnlyShape> noKeyframes = new ArrayList<>();
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    noKeyframes.add(rect1);
    this.svgView.toOutput(noKeyframes, this.out, 10);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n" +
        "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
        "0)\" visibility=\"visible\" >\n" +
        "</rect>\n" +
        "\n" +
        "</svg>", out.toString());
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
    this.svgView.toOutput(oneKeyframe, this.out, 10);
    assertEquals(
        "<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
            ".1\">\n" +
            "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
            "0)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"0ms\" attributeName=\"x\" "
            + "from=\"40.0\" to=\"40.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"0ms\" attributeName=\"y\" "
            + "from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"0ms\" "
            + "attributeName=\"width\" from=\"20.0\" to=\"20.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"0ms\" "
            + "attributeName=\"height\" from=\"30.0\" to=\"30.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"0ms\" attributeName=\"fill\""
            + " from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\" fill=\"freeze\" />\n\n" +
            "</rect>\n\n" +
            "</svg>", out.toString());
  }

  /**
   * Tests printing an animation with shapes and keyframes.
   */
  @Test
  public void testPrintShapesAndKeyframes() {
    this.svgView.toOutput(this.shapes, this.out, 10);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n" +
        "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
        "0)\" visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"x\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"y\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"width\"" +
        " from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" " +
        "attributeName=\"height\" from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"fill\" " +
        "from=\"rgb(0,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"x\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"y\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"width\" from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"height\" from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "</rect>\n" +
        "\n" +
        "<ellipse id=\"E1\" cx=\"10.0\" cy=\"12.0\" rx=\"12.0\" ry=\"12.0\" fill=\"rgb(255," +
        "255,255)\" visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"cx\" " +
        "from=\"20.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"cy\" " +
        "from=\"20.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"rx\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"ry\" " +
        "from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"fill\" " +
        "from=\"rgb(0,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"cx\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"cy\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"rx\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"ry\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "</ellipse>\n" +
        "\n" +
        "</svg>", out.toString());
  }

  /**
   * Tests that modifying the tick rate will change the SVG accordingly.
   */
  @Test
  public void testPrintShapesAndKeyframesDiffTick() {
    IView svgView = new SVGView();
    svgView.toOutput(this.shapes, this.out, 20);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n" +
        "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
        "0)\" visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"x\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"y\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"width\" " +
        "from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"height\"" +
        " from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"fill\" " +
        "from=\"rgb(0,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"x\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"y\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"width\"" +
        " from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" " +
        "attributeName=\"height\" from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"fill\" " +
        "from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "</rect>\n" +
        "\n" +
        "<ellipse id=\"E1\" cx=\"10.0\" cy=\"12.0\" rx=\"12.0\" ry=\"12.0\" fill=\"rgb(255," +
        "255,255)\" visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"250ms\" dur=\"250ms\" attributeName=\"cx\" " +
        "from=\"20.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"250ms\" dur=\"250ms\" attributeName=\"cy\" " +
        "from=\"20.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"250ms\" dur=\"250ms\" attributeName=\"rx\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"250ms\" dur=\"250ms\" attributeName=\"ry\" " +
        "from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"250ms\" dur=\"250ms\" attributeName=\"fill\" " +
        "from=\"rgb(0,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"cx\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"cy\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"rx\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"ry\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"fill\" " +
        "from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "</ellipse>\n" +
        "\n" +
        "</svg>", out.toString());
  }

  /**
   * Tests that outputting to a file works correctly. NOTE: Working directory must be set to
   * resources, otherwise, test will most likely not be able to find file.
   */
  @Test
  public void testToFile() throws IOException {
    Appendable writer = new FileWriter("test.txt");
    this.svgView.toOutput(this.shapes, writer, 10);
    ((Closeable) writer).close();
    Scanner reader = new Scanner(new FileReader("test.txt"));
    StringBuilder sb = new StringBuilder();
    while (reader.hasNextLine()) {
      sb.append(reader.nextLine()).append("\n");
    }
    reader.close();
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n" +
        "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
        "0)\" visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"x\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"y\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"width\"" +
        " from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" " +
        "attributeName=\"height\" from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"fill\" " +
        "from=\"rgb(0,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"x\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"y\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"width\" from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"height\" from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "</rect>\n" +
        "\n" +
        "<ellipse id=\"E1\" cx=\"10.0\" cy=\"12.0\" rx=\"12.0\" ry=\"12.0\" fill=\"rgb(255," +
        "255,255)\" visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"cx\" " +
        "from=\"20.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"cy\" " +
        "from=\"20.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"rx\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"ry\" " +
        "from=\"10.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"500ms\" attributeName=\"fill\" " +
        "from=\"rgb(0,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"cx\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"cy\" " +
        "from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"rx\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" attributeName=\"ry\" " +
        "from=\"15.0\" to=\"15.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "</ellipse>\n" +
        "\n" +
        "</svg>\n", sb.toString());
  }
}
