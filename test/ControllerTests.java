import org.junit.Before;
import org.junit.Test;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.TextControllerImpl;
import cs3500.animator.controller.TimerControllerImpl;
import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the textual controller.
 */
public class ControllerTests {
  IController textController;
  IController textController2;
  IController timerController;
  AnimationBuilder<IModelImpl> builder = IModelImpl.builder();
  Appendable out;
  IView svgView;
  IView textView;

  @Before
  public void init() {
    out = new StringBuilder();
    svgView = new SVGView(out, 10);
    textView = new TextView(out);
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0 ,0,1, 20, 20, 30, 40, 255, 0 ,0);
    this.builder.addMotion("E", 1, 20, 20, 30, 40, 255, 0 ,0,10, 40, 40, 40, 40, 0, 0, 0);
    IModel model = this.builder.build();
    textController = new TextControllerImpl(svgView, model);
    textController2 = new TextControllerImpl(textView, model);
    timerController = new TimerControllerImpl(new VisualView(20, 20, 0 ,0), model, 10);
  }

  /**
   * Tests that setting a tickrate for a text controller is unsupported
   */
  @Test(expected = UnsupportedOperationException.class)
  public void textTick() {
    textController.setTickRate(20);
  }

  /**
   * Tests that passing in a null view to the controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new TextControllerImpl(null, new IModelImpl());
  }

  /**
   * Tests that passing in a null model to the controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new TextControllerImpl(svgView, null);
  }

  /**
   * Tests that passing in a null model and view to the controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelView() {
    new TextControllerImpl(null, null);
  }

  /**
   * Tests that passing in a null view to the controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullViewTimer() {
    new TimerControllerImpl(null, new IModelImpl(), 20);
  }

  /**
   * Tests that passing in a null model to the controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelTimer() {
    new TimerControllerImpl(svgView, null, 20);
  }

  /**
   * Tests that passing in a null model and view to the controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelViewTimer() {
    new TimerControllerImpl(null, null, 20);
  }

  /**
   * Tests that passing a negative tick rate to the timer controller throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegTickRateTimer() {
    new TimerControllerImpl(svgView, new IModelImpl(), -2);
  }

  /**
   * Tests that a controller can make a SVG view
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
   * Tests that a controller can make a text view
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
}
