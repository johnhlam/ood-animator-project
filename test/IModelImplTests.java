import model.IModelShape;
import model.IModelShapeImpl;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import model.IModel;
import model.IModelImpl;
import model.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the methods of IModel and IModelImpl, its implementation.
 */
public class IModelImplTests {
  IModel model1;
  IModelShape rect1;
  IModelShape ell1;

  @Before
  public void init() {
    this.model1 = new IModelImpl();
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has no IShapes in it.
   */
  @Test
  public void testPrintEmpty() {
    assertEquals("", this.model1.printAnimations());
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has 1 (rectangle) IModelShape in it, without
   * any motions in the shape.
   */
  @Test
  public void testPrintEmptyRect() {
    model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has 1 (ellipse) IModelShape in it, without
   * any motions in the shape.
   */
  @Test
  public void testPrintEmptyEll() {
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape C ellipse", model1.printAnimations());
  }

  // TODO: Add more print tests here

  // Tests for IModelImpl#removeShape(String)

  /**
   * Tests that attempting to remove a shape from a model with a null id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNull() {
    this.model1.removeShape(null);
  }

  /**
   * Tests that attempting to remove a shape from a model without any IShapes in it throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveEmpty() {
    this.model1.removeShape("ID");
  }

  /**
   * Tests that attempting to remove a shape from a model with a non-existent id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvID() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("ID");
  }

  /**
   * Tests that attempting to remove a shape from a model with a lowercase version of an id (R)
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLowerIDR() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("r");
  }

  /**
   * Tests that attempting to remove a shape from a model with a lowercase version of an id (C)
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLowerIDC() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("c");
  }

  /**
   * Tests that attempting to remove a shape from a model with a lowercase version of an id (S)
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLowerIDS() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("s");
  }

  /**
   * Tests that attempting to remove a shape from a model with a uppercase version of an id (r)
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUpperIDR() {
    this.model1.addShape("r", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("R");
  }

  /**
   * Tests that attempting to remove a shape from a model with a uppercase version of an id (c)
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUpperIDC() {
    this.model1.addShape("r", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("C");
  }

  /**
   * Tests that attempting to remove a shape from a model with a uppercase version of an id (s)
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUpperIDS() {
    this.model1.addShape("r", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.removeShape("S");
  }

//  /**
//   * Tests for removing the first shape in a model's list of shapes
//   */
//  @Test
//  public void testRemoveFirst() {
//    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
//    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
//    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
//    this.model1.removeShape("R");
//  }
}
