import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.TextControllerImpl;
import cs3500.animator.controller.TimerControllerImpl;
import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the textual controller.
 */
public class ControllerTests {

  private IController textController;
  private IController textController2;
  private IController emptyTextController;
  private IController emptyTextController2;
  private IController textControllerFileInput;
  private IController textControllerFileInput2;
  private AnimationBuilder<IModelImpl> builder = IModelImpl.builder();
  private AnimationBuilder<IModelImpl> builderFileInput = IModelImpl.builder();
  private AnimationBuilder<IModelImpl> emptyBuilder = IModelImpl.builder();
  private Appendable out;
  private IView svgView;

  @Before
  public void init() {
    this.out = new StringBuilder();
    this.svgView = new SVGView(out, 10);
    IView textView = new TextView(out);
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
    this.textController = new TextControllerImpl(svgView, model);
    this.textController2 = new TextControllerImpl(textView, model);
    this.textControllerFileInput = new TextControllerImpl(textView, smallDemo);
    this.textControllerFileInput2 = new TextControllerImpl(svgView, smallDemo);
    this.emptyTextController = new TextControllerImpl(svgView, emptyBuilder.build());
    this.emptyTextController2 = new TextControllerImpl(textView, emptyBuilder.build());
  }

  /**
   * Tests that setting a tickrate for a text controller is unsupported.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void textTick() {
    textController.setTickRate(20);
  }

  /**
   * Tests that passing in a null view to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new TextControllerImpl(null, new IModelImpl());
  }

  /**
   * Tests that passing in a null model to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new TextControllerImpl(svgView, null);
  }

  /**
   * Tests that passing in a null model and view to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelView() {
    new TextControllerImpl(null, null);
  }

  /**
   * Tests that passing in a null view to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullViewTimer() {
    new TimerControllerImpl(null, new IModelImpl(), 20);
  }

  /**
   * Tests that passing in a null model to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelTimer() {
    new TimerControllerImpl(svgView, null, 20);
  }

  /**
   * Tests that passing in a null model and view to the controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelViewTimer() {
    new TimerControllerImpl(null, null, 20);
  }

  /**
   * Tests that passing a negative tick rate to the timer controller throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegTickRateTimer() {
    new TimerControllerImpl(svgView, new IModelImpl(), -2);
  }

  /**
   * Tests that a controller can make a SVG view.
   */
  @Test
  public void testSVGViewController() {
    this.textController.run();
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
    this.textController2.run();
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
    this.textControllerFileInput.run();
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
    this.textControllerFileInput2.run();
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
    this.emptyTextController.run();
    assertEquals("<svg width=\"200\" height=\"200\" xmlns=\"http://www.w3.org/2000/svg\" "
        + "version=\"1.1\">\n"
        + "</svg>", this.out.toString());
  }

  /**
   * Tests that controller can make a text view with an empty model.
   */
  @Test
  public void testTextViewControllerEmpty() {
    this.emptyTextController2.run();
    assertEquals("canvas 0 0 200 200\n", this.out.toString());
  }
}
