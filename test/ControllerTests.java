import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.controller.IController;
import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the textual controller.
 */
public class ControllerTests {

  private IController svgController;
  private IController textController;
  private IController emptyTextController;
  private IController emptyTextController2;
  private IController svgControllerFileInput;
  private IController textControllerFileInput;
  private AnimationBuilder<IModelImpl> builder = IModelImpl.builder();
  private AnimationBuilder<IModelImpl> builderFileInput = IModelImpl.builder();
  private AnimationBuilder<IModelImpl> emptyBuilder = IModelImpl.builder();
  private Appendable out;
  private IView svgView;

  @Before
  public void init() {
    this.out = new StringBuilder();
    this.svgView = new SVGView();
    IView textView = new TextView();
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0, 0, 1, 20, 20, 30, 40, 255, 0, 0);
    this.builder.addMotion("E", 1, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
    IModel model = this.builder.build();
    Readable reader = null;
    try {
      reader = new BufferedReader(new FileReader("./resources/smalldemo.txt"));
      // File path goes up a directory, and then down to resources/smalldemo.txt
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    IModel smallDemo = AnimationReader.parseFile(reader, this.builderFileInput);
    this.svgController = new ControllerImpl(svgView, model, 10, out);
    this.textController = new ControllerImpl(textView, model, 10, out);
    this.svgControllerFileInput = new ControllerImpl(svgView, smallDemo, 10, out);
    this.textControllerFileInput = new ControllerImpl(textView, smallDemo, 10, out);
    this.emptyTextController = new ControllerImpl(svgView, emptyBuilder.build(), 10, out);
    this.emptyTextController2 = new ControllerImpl(textView, emptyBuilder.build(), 20, out);
  }

  /**
   * Tests that calling renderAnimation on a text view throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRenderText() {
    new ControllerImpl(new SVGView(), new IModelImpl(), 10, out).renderAnimation();
  }

  /**
   * Tests that calling outputText on a visual view throws an exception.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testOutputVisual() {
    new ControllerImpl(new EditorView(), new IModelImpl(), 10, out).outputText();
  }

  /**
   * Tests that passing in a null view to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new ControllerImpl(null, new IModelImpl(), 10, out);
  }

  /**
   * Tests that passing in a null model to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new ControllerImpl(svgView, null, 10, out);
  }

  /**
   * Tests that passing in a null model and view to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelView() {
    new ControllerImpl(null, null, 10, out);
  }

  /**
   * Tests that passing in a negative tickrate throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullViewTimer() {
    new ControllerImpl(null, new IModelImpl(), -20, out);
  }

  /**
   * Tests that passing in a null Appendable throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelTimer() {
    new ControllerImpl(svgView, new IModelImpl(), 20, null);
  }

  /**
   * Tests that a controller can make a SVG view with toOutput.
   */
  @Test
  public void testSVGViewController() {
    this.svgController.outputText();
    assertEquals("<svg width=\"200\" height=\"200\" xmlns=\"http://www.w3.org/2000/svg\" " +
        "version=\"1.1\">\n" +
        "<rect id=\"R\" x=\"0.0\" y=\"0.0\" width=\"0.0\" height=\"0.0\" fill=\"rgb(0,0,0)\" " +
        "visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"0ms\" attributeName=\"x\" " +
        "from=\"20.0\" to=\"20.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"0ms\" attributeName=\"y\" " +
        "from=\"20.0\" to=\"20.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"0ms\" attributeName=\"width\" " +
        "from=\"30.0\" to=\"30.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"0ms\" attributeName=\"height\" " +
        "from=\"40.0\" to=\"40.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"0ms\" attributeName=\"fill\" " +
        "from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "</rect>\n" +
        "\n" +
        "<ellipse id=\"E\" cx=\"0.0\" cy=\"0.0\" rx=\"0.0\" ry=\"0.0\" fill=\"rgb(0,0,0)\" " +
        "visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"cx\" " +
        "from=\"20.0\" to=\"40.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"cy\" " +
        "from=\"20.0\" to=\"40.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"rx\" " +
        "from=\"30.0\" to=\"40.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"ry\" " +
        "from=\"40.0\" to=\"40.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"fill\" " +
        "from=\"rgb(255,0,0)\" to=\"rgb(0,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "</ellipse>\n" +
        "\n" +
        "</svg>", out.toString());
  }

  /**
   * Tests that a controller can make a text view.
   */
  @Test
  public void testTextViewController() {
    this.textController.outputText();
    assertEquals("canvas 0 0 200 200\n" +
        "shape R rectangle\n" +
        "motion R 1 20 20 30 40 255 0 0\t1 20 20 30 40 255 0 0\n" +
        "shape E ellipse\n" +
        "motion E 1 20 20 30 40 255 0 0\t10 40 40 40 40 0 0 0\n", out.toString());
  }

  /**
   * Tests that a controller can display animation from file input.
   */
  @Test
  public void testFileInputText() {
    this.textControllerFileInput.outputText();
    assertEquals("canvas 0 0 200 200\n" +
        "shape R rectangle\n" +
        "motion R 1 200 200 50 100 255 0 0\t10 200 200 50 100 255 0 0\n" +
        "motion R 10 200 200 50 100 255 0 0\t50 300 300 50 100 255 0 0\n" +
        "motion R 50 300 300 50 100 255 0 0\t51 300 300 50 100 255 0 0\n" +
        "motion R 51 300 300 50 100 255 0 0\t70 300 300 25 100 255 0 0\n" +
        "motion R 70 300 300 25 100 255 0 0\t100 200 200 25 100 255 0 0\n" +
        "shape C ellipse\n" +
        "motion C 6 440 70 120 60 0 0 255\t20 440 70 120 60 0 0 255\n" +
        "motion C 20 440 70 120 60 0 0 255\t50 440 250 120 60 0 0 255\n" +
        "motion C 50 440 250 120 60 0 0 255\t70 440 370 120 60 0 170 85\n" +
        "motion C 70 440 370 120 60 0 170 85\t80 440 370 120 60 0 255 0\n" +
        "motion C 80 440 370 120 60 0 255 0\t100 440 370 120 60 0 255 0\n", out.toString());
  }

  /**
   * Tests that a controller can display animation from file input.
   */
  @Test
  public void testFileInputSVG() {
    this.svgControllerFileInput.outputText();
    assertEquals("<svg width=\"200\" height=\"200\" xmlns=\"http://www.w3.org/2000/svg\" " +
        "version=\"1.1\">\n" +
        "<rect id=\"R\" x=\"0.0\" y=\"0.0\" width=\"0.0\" height=\"0.0\" fill=\"rgb(0,0,0)\" " +
        "visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"x\" " +
        "from=\"200.0\" to=\"200.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"y\" " +
        "from=\"200.0\" to=\"200.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"width\"" +
        " from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" " +
        "attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"fill\" " +
        "from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" attributeName=\"x\" " +
        "from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" attributeName=\"y\" " +
        "from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" " +
        "attributeName=\"width\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" " +
        "attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"100ms\" attributeName=\"x\" " +
        "from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"100ms\" attributeName=\"y\" " +
        "from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"100ms\" " +
        "attributeName=\"width\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"100ms\" " +
        "attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"100ms\" attributeName=\"fill\"" +
        " from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"5100ms\" dur=\"1900ms\" attributeName=\"x\" " +
        "from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5100ms\" dur=\"1900ms\" attributeName=\"y\" " +
        "from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5100ms\" dur=\"1900ms\" " +
        "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5100ms\" dur=\"1900ms\" " +
        "attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5100ms\" dur=\"1900ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" attributeName=\"x\" " +
        "from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" attributeName=\"y\" " +
        "from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" " +
        "attributeName=\"width\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" " +
        "attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" " +
        "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "</rect>\n" +
        "\n" +
        "<ellipse id=\"C\" cx=\"0.0\" cy=\"0.0\" rx=\"0.0\" ry=\"0.0\" fill=\"rgb(0,0,0)\" " +
        "visibility=\"visible\" >\n" +
        "<animate attributeType=\"xml\" begin=\"600ms\" dur=\"1400ms\" attributeName=\"cx\" " +
        "from=\"440.0\" to=\"440.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"600ms\" dur=\"1400ms\" attributeName=\"cy\" " +
        "from=\"70.0\" to=\"70.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"600ms\" dur=\"1400ms\" attributeName=\"rx\" " +
        "from=\"120.0\" to=\"120.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"600ms\" dur=\"1400ms\" attributeName=\"ry\" " +
        "from=\"60.0\" to=\"60.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"600ms\" dur=\"1400ms\" attributeName=\"fill\"" +
        " from=\"rgb(0,0,255)\" to=\"rgb(0,0,255)\" fill=\"freeze\" />\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"3000ms\" attributeName=\"cx\" " +
        "from=\"440.0\" to=\"440.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"3000ms\" attributeName=\"cy\" " +
        "from=\"70.0\" to=\"250.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"3000ms\" attributeName=\"rx\" " +
        "from=\"120.0\" to=\"120.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"3000ms\" attributeName=\"ry\" " +
        "from=\"60.0\" to=\"60.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"3000ms\" " +
        "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,0,255)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" attributeName=\"cx\" " +
        "from=\"440.0\" to=\"440.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" attributeName=\"cy\" " +
        "from=\"250.0\" to=\"370.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" attributeName=\"rx\" " +
        "from=\"120.0\" to=\"120.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" attributeName=\"ry\" " +
        "from=\"60.0\" to=\"60.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" " +
        "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"1000ms\" attributeName=\"cx\" " +
        "from=\"440.0\" to=\"440.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"1000ms\" attributeName=\"cy\" " +
        "from=\"370.0\" to=\"370.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"1000ms\" attributeName=\"rx\" " +
        "from=\"120.0\" to=\"120.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"1000ms\" attributeName=\"ry\" " +
        "from=\"60.0\" to=\"60.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"7000ms\" dur=\"1000ms\" " +
        "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "<animate attributeType=\"xml\" begin=\"8000ms\" dur=\"2000ms\" attributeName=\"cx\" " +
        "from=\"440.0\" to=\"440.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"8000ms\" dur=\"2000ms\" attributeName=\"cy\" " +
        "from=\"370.0\" to=\"370.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"8000ms\" dur=\"2000ms\" attributeName=\"rx\" " +
        "from=\"120.0\" to=\"120.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"8000ms\" dur=\"2000ms\" attributeName=\"ry\" " +
        "from=\"60.0\" to=\"60.0\" fill=\"freeze\" />\n" +
        "<animate attributeType=\"xml\" begin=\"8000ms\" dur=\"2000ms\" " +
        "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(0,255,0)\" fill=\"freeze\" " +
        "/>\n" +
        "\n" +
        "</ellipse>\n" +
        "\n" +
        "</svg>", out.toString());
  }

  /**
   * Tests that controller can make an svg view with an empty model.
   */
  @Test
  public void testSVGViewControllerEmpty() {
    this.emptyTextController.outputText();
    assertEquals("<svg width=\"200\" height=\"200\" xmlns=\"http://www.w3.org/2000/svg\" "
        + "version=\"1.1\">\n"
        + "</svg>", this.out.toString());
  }

  /**
   * Tests that controller can make a text view with an empty model.
   */
  @Test
  public void testTextViewControllerEmpty() {
    this.emptyTextController2.outputText();
    assertEquals("canvas 0 0 200 200\n", this.out.toString());
  }
}
