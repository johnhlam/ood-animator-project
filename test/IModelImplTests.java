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
