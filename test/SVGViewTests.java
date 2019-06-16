import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IModelShape;
import cs3500.animator.model.IModelShapeImpl;
import cs3500.animator.model.IMotion;
import cs3500.animator.model.IMotionImpl;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for methods of SVGView.
 */
public class SVGViewTests {
  Appendable out;
  IView svgView;
  IModelShape rect1;
  IModelShape ellipse1;
  IMotion motion1 = new IMotionImpl(1, 10, 10, 0, 0, Color.BLACK, 10, 15, 15, 0, 0, Color.RED);
  IMotion motion2 = new IMotionImpl(5, 15, 10, 20, 20, Color.BLACK, 10, 15, 15, 0, 0, Color.RED);
  IMotion motion3 = new IMotionImpl(10, 15, 15, 0, 0, Color.RED, 20, 15, 15, 0, 0, Color.RED);
  java.util.List<IReadOnlyShape> shapes;

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
    rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);

    ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12, Color.WHITE);

    out = new StringBuilder();
    this.svgView = new SVGView(out, 10);
    this.rect1.addMotion(motion1);
    this.rect1.addMotion(motion3);

    this.ellipse1.addMotion(motion2);
    this.ellipse1.addMotion(motion3);

    shapes = new ArrayList<>();
    shapes.add(rect1);
    shapes.add(ellipse1);
  }

  /**
   * Tests that passing a null appendable throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullTextView() {
    new SVGView(null, 20);
  }

  /**
   * Tests that passing a negative tick rate throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegSpeed() {
    new SVGView(out, -20);
  }

  /**
   * Tests that setting the text view's max window sizes throws an unsupported operation exception
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testSetMaxWindowSize() {
    this.svgView.setMaxWindowSize(100, 100);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas1() {
    this.svgView.setCanvas(0, 0, -20, 30);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas2() {
    this.svgView.setCanvas(0, 0, 20, -30);
  }

  /**
   * Tests setting bad canvas sizes (negative or 0) throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadCanvas3() {
    this.svgView.setCanvas(0, 0, 0, 0);
  }

  /**
   * Tests setting the canvas changes the canvas fields
   */
  @Test
  public void testSetCanvas() {
    this.svgView.setCanvas(100, 100, 360, 360);
    this.svgView.play(new ArrayList<>());
    assertEquals("<svg width=\"460\" height=\"460\" xmlns=\"http://www.w3.org/2000/svg\" " +
            "version=\"1.1\">\n</svg>", out.toString());
  }

  /**
   * Tests playing a null input throws exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayingNull() {
    this.svgView.play(null);
  }

  /**
   * Tests appending throwing an IOException
   */
  @Test(expected = IllegalStateException.class)
  public void testAppendableException() {
    IView view = new TextView(new AppendableThrowsException());
    view.play(new ArrayList<>());
  }

  /**
   * Tests printing an empty animation
   */
  @Test
  public void testPrintEmptyAnimation() {
    this.svgView.play(new ArrayList<>());
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
            ".1\">\n</svg>", out.toString());
  }

  /**
   * Tests printing an animation with only shapes
   */
  @Test
  public void testPrintShapeNoMotion() {
    List<IReadOnlyShape> noMotions = new ArrayList<>();
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    IModelShape ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12,
            Color.WHITE);
    noMotions.add(rect1);
    noMotions.add(ellipse1);
    this.svgView.play(noMotions);
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
   * Tests printing one shape
   */
  @Test
  public void testPrintShape1() {
    List<IReadOnlyShape> noMotions = new ArrayList<>();
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    noMotions.add(rect1);
    this.svgView.play(noMotions);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
            ".1\">\n" +
            "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
            "0)\" visibility=\"visible\" >\n" +
            "</rect>\n" +
            "\n" +
            "</svg>", out.toString());
  }

  /**
   * Tests printing an animation with shapes and motions
   */
  @Test
  public void testPrintShapesAndMotions() {
    this.svgView.play(this.shapes);
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
   * Tests that modifying the tick rate will change the SVG accordingly
   */
  @Test
  public void testPrintShapesAndMotionsDiffTick() {
    IView svgView = new SVGView(out, 20);
    svgView.play(this.shapes);
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
}
