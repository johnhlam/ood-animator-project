import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.controller.IController;
import cs3500.animator.controller.TestController;
import cs3500.animator.model.IModel;
import cs3500.animator.model.TestModel;
import cs3500.animator.view.IView;
import cs3500.animator.view.TestView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the wiring between the events being fired in the view and how the controller and model
 * respond. Uses Readables to simulate events for the view, and Appendables to get the results
 * from the model and controller. When testing the model, our real controller implementation will
 * be used.
 */
public class ListenersTest {

  private Appendable controllerAp;
  private Appendable modelAp;
  private IModel testModel;

  @Before
  public void init() {
    controllerAp = new StringBuilder();
    modelAp = new StringBuilder();
    testModel = new TestModel(modelAp);
  }

  /**
   * Tests that clicking the play button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testPlay() {
    Readable in = new StringReader("play");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Play was pressed.", controllerAp.toString());
  }

  /**
   * Tests that clicking the stop button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testStop() {
    Readable in = new StringReader("stop");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Stop was pressed.", controllerAp.toString());
  }

  /**
   * Tests that clicking the restart button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testRestart() {
    Readable in = new StringReader("restart");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Restart was pressed.", controllerAp.toString());
  }

  /**
   * Tests that clicking the speed increase button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testFaster() {
    Readable in = new StringReader("fast");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Speed increased.", controllerAp.toString());
  }

  /**
   * Tests that clicking the speed decrease button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testSlower() {
    Readable in = new StringReader("slow");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Speed decreased.", controllerAp.toString());
  }

  /**
   * Tests that checking the loopback box in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testLoopback() {
    Readable in = new StringReader("loop");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Loopback was checked.", controllerAp.toString());
  }

  /**
   * Tests that clicking the add shape button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testAddShapeController() {
    Readable in = new StringReader("addShape");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Shape added with ID shape added of type rectangle",
        controllerAp.toString());
  }

  /**
   * Tests that clicking the remove shape button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testRemoveShapeController() {
    Readable in = new StringReader("removeShape");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Shape removed with ID shape removed",
        controllerAp.toString());
  }

  /**
   * Tests that clicking the add keyframe button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testAddKeyframeController() {
    Readable in = new StringReader("addKeyframe");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Keyframe added for shape keyframe added at tick 38",
        controllerAp.toString());
  }

  /**
   * Tests that clicking the remove keyframe button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testRemoveKeyframeController() {
    Readable in = new StringReader("removeKeyframe");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Keyframe removed for shape keyframe removed at tick 12",
        controllerAp.toString());
  }

  /**
   * Tests that clicking the modify keyframe button in the view triggers the correct response in the
   * controller.
   */
  @Test
  public void testModifyKeyframeController() {
    Readable in = new StringReader("modifyKeyframe");
    IView testView = new TestView(in);
    IController test = new TestController(testView, controllerAp);

    testView.render(null);
    assertEquals("Keyframe modified for shape keyframe modified at tick 23",
        controllerAp.toString());
  }

  /**
   * Tests that clicking the add shape button modifies the model accordingly.
   */
  @Test
  public void testAddShapeModel() {
    Readable in = new StringReader("addShape");
    IView testView = new TestView(in);
    IController controller = new ControllerImpl(testView, testModel, 10, controllerAp);

    testView.render(null);
    assertEquals("Model: Shape added with ID shape added of type rectangle",
        modelAp.toString());
  }

  /**
   * Tests that clicking the remove shape button modifies the model accordingly.
   */
  @Test
  public void testRemoveShapeModel() {
    Readable in = new StringReader("removeShape");
    IView testView = new TestView(in);
    IController controller = new ControllerImpl(testView, testModel, 10, controllerAp);

    testView.render(null);
    assertEquals("Model: Shape removed with ID shape removed",
        modelAp.toString());
  }

  /**
   * Tests that clicking the add keyframe button modifies the model accordingly.
   */
  @Test
  public void testAddKeyframeModel() {
    Readable in = new StringReader("addKeyframe");
    IView testView = new TestView(in);
    IController controller = new ControllerImpl(testView, testModel, 10, controllerAp);

    testView.render(null);
    assertEquals("Model: Keyframe added for shape keyframe added at tick 38",
        modelAp.toString());
  }

  /**
   * Tests that clicking the remove keyframe button modifies the model accordingly.
   */
  @Test
  public void testRemoveKeyframeModel() {
    Readable in = new StringReader("removeKeyframe");
    IView testView = new TestView(in);
    IController controller = new ControllerImpl(testView, testModel, 10, controllerAp);

    testView.render(null);
    assertEquals("Model: Keyframe removed for shape keyframe removed at tick 12",
        modelAp.toString());
  }

  /**
   * Tests that clicking the modify keyframe button modifies the model accordingly.
   */
  @Test
  public void testModifyKeyframeModel() {
    Readable in = new StringReader("modifyKeyframe");
    IView testView = new TestView(in);
    IController controller = new ControllerImpl(testView, testModel, 10, controllerAp);

    testView.render(null);
    assertEquals("Model: Keyframe removed for shape keyframe modified at tick 23Model: " +
            "Keyframe added for shape keyframe modified at tick 23",
        modelAp.toString());
  }
}
