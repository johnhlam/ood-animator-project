import org.junit.Test;

import java.util.ArrayList;

import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.view.IView;
import cs3500.animator.view.ViewFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the view factory. Ensures each view is created correctly.
 */
public class ViewFactoryTests {

  /**
   * Tests that a Text view is created.
   */
  @Test
  public void testText() {
    IView view = ViewFactory.makeView("text");
    Appendable out = new StringBuilder();
    view.toOutput(new ArrayList<>(), out, 10);
    assertNotNull(view);
    assertEquals("canvas 0 0 0 0\n", out.toString());
  }

  /**
   * Tests that an SVG view is created.
   */
  @Test
  public void testSVG() {
    IView view = ViewFactory.makeView("svg");
    Appendable out = new StringBuilder();
    view.toOutput(new ArrayList<>(), out, 10);
    assertNotNull(view);
    assertEquals("<svg width=\"0\" height=\"0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1" +
        ".1\">\n" +
        "</svg>", out.toString());
  }

  /**
   * Tests that a Visual view is created. Tests that render animation works, but setFeatures does
   * not
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testVisual() {
    IView view = ViewFactory.makeView("visual");
    view.render(new ArrayList<>());
    view.setFeatures(null);
    assertNotNull(view);
  }

  /**
   * Tests that an Editor view is created. Tests that setFeatures works.
   */
  @Test
  public void testEditor() {
    IView view = ViewFactory.makeView("edit");
    view.setFeatures(new ControllerImpl(view, new IModelImpl(), 10, new StringBuilder()));
    assertNotNull(view);
  }

  /**
   * Tests that a string that is not one of "text", "svg", "visual", or "edit" will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testUndef() {
    IView view = ViewFactory.makeView("undefined");
  }
}
