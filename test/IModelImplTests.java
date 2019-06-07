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

  /**
   * Sets up a blank model without any shapes.
   */
  @Before
  public void setUp() {
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
   * Tests IModelImpl#printAnimations() with a model that has 1 (rectangle) IModelShape in it,
   * without any motions in the shape.
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
  
  // Tests for IModelImpl#addMotion(String, int, double, double, double, double, Color,
  // int, double, double, double, double, Color)

  /**
   * Tests that attempting to add a motion will a null id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNullID() {
    this.model1.addMotion(null, 1, 1, 1, 1, 1, Color.PINK,
        10, 10, 10, 10, 10, Color.PINK);
  }

  /**
   * Tests that attempting to add a motion will a null starting color throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionSColor() {
    this.model1.addMotion("ID", 1, 1, 1, 1, 1, null,
        10, 10, 10, 10, 10, Color.PINK);
  }

  /**
   * Tests that attempting to add a motion will a null ending color throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionEColor() {
    this.model1.addMotion("ID", 1, 1, 1, 1, 1, Color.PINK,
        10, 10, 10, 10, 10, null);
  }

  // Tests for IModelImpl#addShape(String, ShapeType, double, double, double, double, Color)

  /**
   * Tests that attempting to add a shape with a null id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullID() {
    this.model1.addShape(null, ShapeType.RECTANGLE, 4.5, 6.4, 3.4, 7, Color.WHITE);
  }

  /**
   * Tests that attempting to add a shape with a null ShapeType throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullType() {
    this.model1.addShape("R", null, 4.5, 6.4, 3.4, 7, Color.WHITE);
  }

  /**
   * Tests that attempting to add a shape with a null Color throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullColor() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 4.5, 6.4, 3.4, 7, null);
  }

  /**
   * Tests that attempting to add a shape with a null id, ShapeType, and Color throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullAll() {
    this.model1.addShape(null, null, 4.5, 6.4, 3.4, 7, null);
  }

  /**
   * Tests that attempting to add a shape with an id that is the same as the first shape in the
   * model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeSameFirstID() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    // This call should throw an error
    this.model1.addShape("R", ShapeType.ELLIPSE, 1.2, 3.4, 5.6, 7.8, Color.BLACK);
  }

  /**
   * Tests that attempting to add a shape with an id that is the same as the second shape in the
   * model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeSameSecondID() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    // This call should throw an error
    this.model1.addShape("C", ShapeType.RECTANGLE, 1.2, 3.4, 5.6, 7.8, Color.BLACK);
  }

  /**
   * Tests that attempting to add a shape with an id that is the same as the third shape in the
   * model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeSameThirdID() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    // This call should throw an error
    this.model1.addShape("S", ShapeType.ELLIPSE, 1.2, 3.4, 5.6, 7.8, Color.BLACK);
  }

  /**
   * Tests for adding a shape to a model without any shapes in it.
   */
  @Test
  public void testAddShapeEmpty() {
    assertEquals("", this.model1.printAnimations());
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    assertEquals("shape R rectangle", this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape to a model with 1 shape in it.
   */
  @Test
  public void testAddShapeWith1() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddEmpty
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    assertEquals(
        "shape R rectangle\n\n"
            + "shape C ellipse"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape to a model with 2 shapes in it.
   */
  @Test
  public void testAddShapeWith2() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith1
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    assertEquals(
        "shape R rectangle\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with the same id as the first shape in the model, but in lowercase.
   */
  @Test
  public void testAddShapeSameIDLower1() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("r", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);

    assertEquals(
        "shape R rectangle\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle\n\n"
            + "shape r rectangle"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with the same id as the second shape in the model, but in lowercase.
   */
  @Test
  public void testAddShapeSameIDLower2() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    assertEquals(
        "shape R rectangle\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle\n\n"
            + "shape c ellipse"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with the same id as the second shape in the model, but in lowercase.
   */
  @Test
  public void testAddShapeSameIDLower3() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("s", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    assertEquals(
        "shape R rectangle\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle\n\n"
            + "shape s rectangle"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with the same id as the first shape in the model, but in uppercase.
   */
  @Test
  public void testAddShapeSameIDUpper1() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("r", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);

    assertEquals(
        "shape r rectangle\n\n"
            + "shape c ellipse\n\n"
            + "shape s rectangle\n\n"
            + "shape R rectangle"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with the same id as the second shape in the model, but in uppercase.
   */
  @Test
  public void testAddShapeSameIDUpper2() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("r", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    assertEquals(
        "shape r rectangle\n\n"
            + "shape c ellipse\n\n"
            + "shape s rectangle\n\n"
            + "shape C ellipse"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with the same id as the third shape in the model, but in uppercase.
   */
  @Test
  public void testAddShapeSameIDUpper3() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("r", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    assertEquals(
        "shape r rectangle\n\n"
            + "shape c ellipse\n\n"
            + "shape s rectangle\n\n"
            + "shape S rectangle"
        , this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with an id with multiple characters in an model.
   */
  @Test
  public void testAddShapeMultCharIDEmpty() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("hdsafhufhf17483%^$*&ihfh346&*&$./';]",
        ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);

    assertEquals("shape hdsafhufhf17483%^$*&ihfh346&*&$./';] rectangle",
        this.model1.printAnimations());
  }

  /**
   * Tests for adding a shape with an id with multiple characters in an non-empty model.
   */
  @Test
  public void testAddShapeMultCharIDNonEmpty() {
    assertEquals("", this.model1.printAnimations());

    // Tested in testAddWith2
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    this.model1.addShape("hdsafhufhf17483%^$*&ihfh346&*&$./';]",
        ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);

    assertEquals(
        "shape R rectangle\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle\n\n"
            + "shape hdsafhufhf17483%^$*&ihfh346&*&$./';] rectangle",
        this.model1.printAnimations());
  }

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

  /**
   * Tests for removing the first shape in a model's list of shapes (without any motions added)
   */
  @Test
  public void testRemoveFirstNoMot() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    assertEquals("shape R rectangle\n\n"
        + "shape C ellipse\n\n"
        + "shape S rectangle", this.model1.printAnimations());
    this.model1.removeShape("R");
    assertEquals("shape C ellipse\n\n"
        + "shape S rectangle", this.model1.printAnimations());
  }

  /**
   * Tests for removing the second shape in a model's list of shapes (without any motions added)
   */
  @Test
  public void testRemoveSecondNoMot() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    assertEquals("shape R rectangle\n\n"
        + "shape C ellipse\n\n"
        + "shape S rectangle", this.model1.printAnimations());
    this.model1.removeShape("C");
    assertEquals("shape R rectangle\n\n"
        + "shape S rectangle", this.model1.printAnimations());
  }

  /**
   * Tests for removing the third shape in a model's list of shapes (without any motions added)
   */
  @Test
  public void testRemoveThirdNoMot() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    assertEquals("shape R rectangle\n\n"
        + "shape C ellipse\n\n"
        + "shape S rectangle", this.model1.printAnimations());
    this.model1.removeShape("S");
    assertEquals("shape R rectangle\n\n"
        + "shape C ellipse", this.model1.printAnimations());
  }

  /**
   * Tests for removing the first shape in a model's list of shapes (with motions added)
   */
  @Test
  public void testRemoveFirstWithMot() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addMotion("R",
        1, 30, 40, 10, 20, Color.CYAN,
        10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addMotion("R",
        10, 30, 40, 40, 60, Color.CYAN,
        15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addMotion("R",
        20, 45, 60, 40, 60, Color.CYAN,
        30, 45, 60, 40, 60, Color.GREEN);

    this.model1.addMotion("C",
        1, 30, 40, 10, 20, Color.CYAN,
        10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addMotion("C",
        10, 30, 40, 10, 20, Color.ORANGE,
        15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addMotion("C",
        20, 30, 40, 0, 0, Color.ORANGE,
        30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addMotion("S",
        10, 30, 40, 10, 10, Color.RED,
        25, 15, 20, 10, 20, Color.RED);
    this.model1.addMotion("S",
        30, 15, 20, 10, 10, Color.RED,
        35, 15, 20, 6, 7, Color.RED);
    this.model1.addMotion("S",
        35, 15, 20, 6, 7, Color.RED,
        45, 15, 20, 6, 7, Color.YELLOW);

    assertEquals(
        "shape R rectangle\n"
            + "motion R 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 40.0 60.0 0 255 255\n"
            + "motion R 10 30.0 40.0 40.0 60.0 0 255 255\t15 45.0 60.0 40.0 60.0 0 255 255\n"
            + "motion R 20 45.0 60.0 40.0 60.0 0 255 255\t30 45.0 60.0 40.0 60.0 0 255 0\n\n"
            + "shape C ellipse\n"
            + "motion C 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 10.0 20.0 255 200 0\n"
            + "motion C 10 30.0 40.0 10.0 20.0 255 200 0\t15 30.0 40.0 0.0 0.0 255 200 0\n"
            + "motion C 20 30.0 40.0 0.0 0.0 255 200 0\t30 45.0 60.0 0.0 0.0 255 200 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 30.0 40.0 10.0 10.0 255 0 0\t25 15.0 20.0 10.0 20.0 255 0 0\n"
            + "motion S 30 15.0 20.0 10.0 10.0 255 0 0\t35 15.0 20.0 6.0 7.0 255 0 0\n"
            + "motion S 35 15.0 20.0 6.0 7.0 255 0 0\t45 15.0 20.0 6.0 7.0 255 255 0",
        this.model1.printAnimations());
    this.model1.removeShape("R");
    assertEquals(
        "shape C ellipse\n"
            + "motion C 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 10.0 20.0 255 200 0\n"
            + "motion C 10 30.0 40.0 10.0 20.0 255 200 0\t15 30.0 40.0 0.0 0.0 255 200 0\n"
            + "motion C 20 30.0 40.0 0.0 0.0 255 200 0\t30 45.0 60.0 0.0 0.0 255 200 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 30.0 40.0 10.0 10.0 255 0 0\t25 15.0 20.0 10.0 20.0 255 0 0\n"
            + "motion S 30 15.0 20.0 10.0 10.0 255 0 0\t35 15.0 20.0 6.0 7.0 255 0 0\n"
            + "motion S 35 15.0 20.0 6.0 7.0 255 0 0\t45 15.0 20.0 6.0 7.0 255 255 0",
        this.model1.printAnimations());
  }

  /**
   * Tests for removing the second shape in a model's list of shapes (with motions added)
   */
  @Test
  public void testRemoveSecondWithMot() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addMotion("R",
        1, 30, 40, 10, 20, Color.CYAN,
        10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addMotion("R",
        10, 30, 40, 40, 60, Color.CYAN,
        15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addMotion("R",
        20, 45, 60, 40, 60, Color.CYAN,
        30, 45, 60, 40, 60, Color.GREEN);

    this.model1.addMotion("C",
        1, 30, 40, 10, 20, Color.CYAN,
        10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addMotion("C",
        10, 30, 40, 10, 20, Color.ORANGE,
        15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addMotion("C",
        20, 30, 40, 0, 0, Color.ORANGE,
        30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addMotion("S",
        10, 30, 40, 10, 10, Color.RED,
        25, 15, 20, 10, 20, Color.RED);
    this.model1.addMotion("S",
        30, 15, 20, 10, 10, Color.RED,
        35, 15, 20, 6, 7, Color.RED);
    this.model1.addMotion("S",
        35, 15, 20, 6, 7, Color.RED,
        45, 15, 20, 6, 7, Color.YELLOW);

    assertEquals(
        "shape R rectangle\n"
            + "motion R 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 40.0 60.0 0 255 255\n"
            + "motion R 10 30.0 40.0 40.0 60.0 0 255 255\t15 45.0 60.0 40.0 60.0 0 255 255\n"
            + "motion R 20 45.0 60.0 40.0 60.0 0 255 255\t30 45.0 60.0 40.0 60.0 0 255 0\n\n"
            + "shape C ellipse\n"
            + "motion C 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 10.0 20.0 255 200 0\n"
            + "motion C 10 30.0 40.0 10.0 20.0 255 200 0\t15 30.0 40.0 0.0 0.0 255 200 0\n"
            + "motion C 20 30.0 40.0 0.0 0.0 255 200 0\t30 45.0 60.0 0.0 0.0 255 200 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 30.0 40.0 10.0 10.0 255 0 0\t25 15.0 20.0 10.0 20.0 255 0 0\n"
            + "motion S 30 15.0 20.0 10.0 10.0 255 0 0\t35 15.0 20.0 6.0 7.0 255 0 0\n"
            + "motion S 35 15.0 20.0 6.0 7.0 255 0 0\t45 15.0 20.0 6.0 7.0 255 255 0",
        this.model1.printAnimations());
    this.model1.removeShape("C");
    assertEquals(
        "shape R rectangle\n"
            + "motion R 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 40.0 60.0 0 255 255\n"
            + "motion R 10 30.0 40.0 40.0 60.0 0 255 255\t15 45.0 60.0 40.0 60.0 0 255 255\n"
            + "motion R 20 45.0 60.0 40.0 60.0 0 255 255\t30 45.0 60.0 40.0 60.0 0 255 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 30.0 40.0 10.0 10.0 255 0 0\t25 15.0 20.0 10.0 20.0 255 0 0\n"
            + "motion S 30 15.0 20.0 10.0 10.0 255 0 0\t35 15.0 20.0 6.0 7.0 255 0 0\n"
            + "motion S 35 15.0 20.0 6.0 7.0 255 0 0\t45 15.0 20.0 6.0 7.0 255 255 0",
        this.model1.printAnimations());
  }

  /**
   * Tests for removing the third shape in a model's list of shapes (with motions added)
   */
  @Test
  public void testRemoveThirdWithMot() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addMotion("R",
        1, 30, 40, 10, 20, Color.CYAN,
        10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addMotion("R",
        10, 30, 40, 40, 60, Color.CYAN,
        15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addMotion("R",
        20, 45, 60, 40, 60, Color.CYAN,
        30, 45, 60, 40, 60, Color.GREEN);

    this.model1.addMotion("C",
        1, 30, 40, 10, 20, Color.CYAN,
        10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addMotion("C",
        10, 30, 40, 10, 20, Color.ORANGE,
        15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addMotion("C",
        20, 30, 40, 0, 0, Color.ORANGE,
        30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addMotion("S",
        10, 30, 40, 10, 10, Color.RED,
        25, 15, 20, 10, 20, Color.RED);
    this.model1.addMotion("S",
        30, 15, 20, 10, 10, Color.RED,
        35, 15, 20, 6, 7, Color.RED);
    this.model1.addMotion("S",
        35, 15, 20, 6, 7, Color.RED,
        45, 15, 20, 6, 7, Color.YELLOW);

    assertEquals(
        "shape R rectangle\n"
            + "motion R 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 40.0 60.0 0 255 255\n"
            + "motion R 10 30.0 40.0 40.0 60.0 0 255 255\t15 45.0 60.0 40.0 60.0 0 255 255\n"
            + "motion R 20 45.0 60.0 40.0 60.0 0 255 255\t30 45.0 60.0 40.0 60.0 0 255 0\n\n"
            + "shape C ellipse\n"
            + "motion C 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 10.0 20.0 255 200 0\n"
            + "motion C 10 30.0 40.0 10.0 20.0 255 200 0\t15 30.0 40.0 0.0 0.0 255 200 0\n"
            + "motion C 20 30.0 40.0 0.0 0.0 255 200 0\t30 45.0 60.0 0.0 0.0 255 200 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 30.0 40.0 10.0 10.0 255 0 0\t25 15.0 20.0 10.0 20.0 255 0 0\n"
            + "motion S 30 15.0 20.0 10.0 10.0 255 0 0\t35 15.0 20.0 6.0 7.0 255 0 0\n"
            + "motion S 35 15.0 20.0 6.0 7.0 255 0 0\t45 15.0 20.0 6.0 7.0 255 255 0",
        this.model1.printAnimations());
    this.model1.removeShape("S");
    assertEquals(
        "shape R rectangle\n"
            + "motion R 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 40.0 60.0 0 255 255\n"
            + "motion R 10 30.0 40.0 40.0 60.0 0 255 255\t15 45.0 60.0 40.0 60.0 0 255 255\n"
            + "motion R 20 45.0 60.0 40.0 60.0 0 255 255\t30 45.0 60.0 40.0 60.0 0 255 0\n\n"
            + "shape C ellipse\n"
            + "motion C 1 30.0 40.0 10.0 20.0 0 255 255\t10 30.0 40.0 10.0 20.0 255 200 0\n"
            + "motion C 10 30.0 40.0 10.0 20.0 255 200 0\t15 30.0 40.0 0.0 0.0 255 200 0\n"
            + "motion C 20 30.0 40.0 0.0 0.0 255 200 0\t30 45.0 60.0 0.0 0.0 255 200 0",
        this.model1.printAnimations());
  }
}
