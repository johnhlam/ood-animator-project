import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import Model.IModel;
import Model.IModelImpl;
import Model.IMotion;
import Model.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the methods of IModel and IModelImpl, its implementation.
 */
public class IModelImplTests {
  IModel model1;

  @Before
  public void init() {
    model1 = new IModelImpl();
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has no IShapes in it.
   */
  @Test
  public void testPrintEmpty() {
    assertEquals("", model1.printAnimations());
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has 1 (rectangle) IModelShape in it, without
   * any motions in the shape.
   */
  @Test
  public void testPrintEmptyRect() {
    model1.addShape("R", ShapeType.RECTANGLE, 10, 20, Color.CYAN, 30, 40);

    assertEquals("shape R rectangle", model1.printAnimations());
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has 1 (ellipse) IModelShape in it, without
   * any motions in the shape.
   */
  @Test
  public void testPrintEmptyEll() {
    model1.addShape("C", ShapeType.ELLIPSE, 10, 20, Color.CYAN, 30, 40);

    assertEquals("shape C ellipse", model1.printAnimations());
  }


  @Test
  public void test() {
    model1.addShape("1", ShapeType.RECTANGLE, 10, 20, Color.BLACK, 0, 0);
    model1.addMotion("1", 0, 0, 0, 10, 20,
            Color.BLACK, 10, 30, 40, 30, 40, Color.RED);
    model1.addMotion("1", 10, 0, 0, 10, 20,
            Color.BLACK, 20, 30, 40, 30, 40, Color.RED);
    assertEquals("", this.model1.printAnimations());
  }

}
