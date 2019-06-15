import cs3500.animator.model.IModelShape;
import cs3500.animator.model.IModelShapeImpl;
import cs3500.animator.model.IMotion;
import cs3500.animator.model.IMotionImpl;
import cs3500.animator.model.ShapeType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Tests the methods of IModelShapeImpl (as well as the methods of ShapeType).
 */
public class IModelShapeImplTest {

  IModelShape rect1;
  IModelShape rect2;
  IModelShape ellipse1;
  IModelShape ellipse2;
  IMotion motion1 = new IMotionImpl(1, 10, 10, 0, 0, Color.BLACK, 10, 15, 15, 0, 0, Color.RED);
  IMotion motion2 = new IMotionImpl(5, 15, 10, 20, 20, Color.BLACK, 10, 15, 15, 0, 0, Color.RED);
  IMotion motion3 = new IMotionImpl(10, 15, 15, 0, 0, Color.RED, 20, 15, 15, 0, 0, Color.RED);
  IMotion motion4 = new IMotionImpl(30, 15, 15, 0, 0, Color.RED, 40, 15, 15, 0, 0, Color.RED);
  IMotion motion5 = new IMotionImpl(50, 15.3, 15.6, 58.4, 34.2, Color.ORANGE, 100, 18.34, 30.2,
      100.2, 200, Color.RED);
  IMotion motionBadOverlap = new IMotionImpl(10, 10, 30, -2, 3, Color.GREEN, 40, 15, 15, 0, 0,
      Color.RED);

  @Before
  public void setUp() {
    rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    rect2 = new IModelShapeImpl("R2", ShapeType.RECTANGLE, 5.3, 15.5, -30.2, 30.5,
        Color.BLUE);

    ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12, Color.WHITE);
    ellipse2 = new IModelShapeImpl("E2", ShapeType.ELLIPSE, 3.5, 6.6, 20.2, -40.5,
        Color.GREEN);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} id.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullID() {
    new IModelShapeImpl(null, ShapeType.RECTANGLE, 10, 10, 0, 0, Color.BLUE);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null}
   * ShapeType.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullType() {
    new IModelShapeImpl("R1", null, 10, 10, 0, 0, Color.pink);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} Color.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullColor() {
    new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, null);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} id,
   * ShapeType, and Color.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullAll() {
    new IModelShapeImpl(null, null, 10, 10, 0, 0, null);
  }


  /**
   * Tests that constructing a shape with a negative width and height throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeNegativeWidth() {
    new IModelShapeImpl("ID", ShapeType.RECTANGLE, -2, 2, 3, 3, Color.BLACK);
  }

  /**
   * Tests that constructing a shape with a negative width and height throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeNegativeHeight() {
    new IModelShapeImpl("ID", ShapeType.RECTANGLE, 2, -2, 3, 3, Color.BLACK);
  }

  /**
   * Tests that constructing a shape with a negative width and height throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeNegativeWidthHeight() {
    new IModelShapeImpl("ID", ShapeType.RECTANGLE, -22, -2, 3, 3, Color.BLACK);
  }

  /**
   * Tests the shape's constructor.
   */
  @Test
  public void testShapeConstructor() {
    assertEquals("shape R1 rectangle", this.rect1.printMotions());
  }

  /**
   * Tests the shape's constructor.
   */
  @Test
  public void testShapeConstructor2() {
    assertEquals("shape E1 ellipse", this.ellipse1.printMotions());
  }

  /**
   * Tests an exception is thrown if null motion is being added.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullMotion() {
    this.rect1.addMotion(null);
  }

  /**
   * Tests an exception is thrown if overlapping motions are being added.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddOverlappingMotion() {
    // adds a motion
    this.rect1.addMotion(motion1);
    // throws exception
    this.rect1.addMotion(motion2);
  }

  /**
   * Tests an exception is thrown if disagreeing adjacent motions are being added (end of list).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadAdjacentMotions() {
    // adds a motion
    this.rect1.addMotion(motion1);
    // throws exception
    this.rect1.addMotion(motionBadOverlap);
  }

  /**
   * Tests an exception is thrown if disagreeing adjacent motions are being added (middle of the
   * list).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadAdjacentMotions2() {
    // adds a motion
    this.rect1.addMotion(motion1);
    // adds a motion
    this.rect1.addMotion(motion4);
    // throws exception
    this.rect1.addMotion(motionBadOverlap);
  }

  /**
   * Tests not being able to leave gap in motions
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion2() {
    assertEquals("shape R1 rectangle", this.rect1.printMotions());
    // does not throw exception
    this.rect1.addMotion(this.motion1);
    assertEquals("shape R1 rectangle\nmotion R1 1 0.0 0.0 10.0 10.0 0 0 0\t10 0.0 0.0 " +
            "15.0 15.0 255 0 0", this.rect1.printMotions());
    // throws exception
    this.rect1.addMotion(this.motion4);
    assertEquals("shape R1 rectangle\nmotion R1 1 0.0 0.0 10.0 10.0 0 0 0\t10 0.0 0.0 " +
                    "15.0 15.0 255 0 0\nmotion R1 10 0.0 0.0 15.0 15.0 255 0 0\t20 0.0 0.0 15.0 " +
                    "15.0 255 0 0\nmotion R1 30 0.0 0.0 15.0 15.0 255 0 0\t40 0.0 0.0 15.0 15.0 255 0 0",
            this.rect1.printMotions());
  }

  /**
   * Tests adding a motion.
   */
  @Test
  public void testAddMotion1() {
    assertEquals("shape R1 rectangle", this.rect1.printMotions());
    this.rect1.addMotion(this.motion1);
    assertEquals("shape R1 rectangle\nmotion R1 1 0.0 0.0 10.0 10.0 0 0 0\t10 0.0 0.0 " +
        "15.0 15.0 255 0 0", this.rect1.printMotions());
  }

  /**
   * Tests adding an adjacent motion at the end of a list.
   */
  @Test
  public void testAddAdjMotion() {
    assertEquals("shape R1 rectangle", this.rect1.printMotions());
    this.rect1.addMotion(this.motion1);
    assertEquals("shape R1 rectangle\nmotion R1 1 0.0 0.0 10.0 10.0 0 0 0\t10 0.0 0.0 " +
        "15.0 15.0 255 0 0", this.rect1.printMotions());
    this.rect1.addMotion(this.motion3);
    assertEquals("shape R1 rectangle\nmotion R1 1 0.0 0.0 10.0 10.0 0 0 0\t10 0.0 0.0 " +
        "15.0 15.0 255 0 0\nmotion R1 10 0.0 0.0 15.0 15.0 255 0 0\t20 0.0 0.0 15.0 " +
        "15.0 255 0 0", this.rect1.printMotions());
  }

  @Test
  public void testPrintMotions() {
    assertEquals("shape E1 ellipse", this.ellipse1.printMotions());
  }

  @Test
  public void testPrintMotions2() {
    assertEquals("shape E1 ellipse", this.ellipse1.printMotions());
    this.ellipse1.addMotion(this.motion1);
    assertEquals("shape E1 ellipse\nmotion E1 1 0.0 0.0 10.0 10.0 0 0 0\t10 0.0 0.0 " +
        "15.0 15.0 255 0 0", this.ellipse1.printMotions());
  }

  @Test
  public void testGetID() {
    assertEquals("R1", this.rect1.getID());
  }

  @Test
  public void testGetID2() {
    assertEquals("E2", this.ellipse2.getID());
  }

  @Test
  public void testGetWidth() {
    assertEquals(10, this.rect1.getWidth(), .001);
  }

  @Test
  public void testGetWidth2() {
    assertEquals(3.5, this.ellipse2.getWidth(), .001);
  }

  @Test
  public void testGetHeight() {
    assertEquals(10, this.rect1.getHeight(), .001);
  }

  @Test
  public void testGetHeight2() {
    assertEquals(6.6, this.ellipse2.getHeight(), .001);
  }

  @Test
  public void testGetX() {
    assertEquals(0, this.rect1.getX(), .001);
  }

  @Test
  public void testGetX2() {
    assertEquals(20.2, this.ellipse2.getX(), .001);
  }

  @Test
  public void testGetY() {
    assertEquals(0, this.rect1.getY(), .001);
  }

  @Test
  public void testGetY2() {
    assertEquals(-40.5, this.ellipse2.getY(), .001);
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.RED, this.rect1.getColor());
  }

  @Test
  public void testGetColor2() {
    assertEquals(Color.GREEN, this.ellipse2.getColor());
  }

  @Test
  public void testGetShapeType() {
    assertEquals(ShapeType.RECTANGLE, this.rect1.getType());
  }

  @Test
  public void testGetShapeType2() {
    assertEquals(ShapeType.ELLIPSE, this.ellipse2.getType());
  }

  @Test
  public void testGetMotions() {
    assertEquals(new ArrayList<IMotion>(), rect1.getMotions());
  }

  @Test
  public void testGetMotions2() {
    ellipse1.addMotion(this.motion1);
    ArrayList<IMotion> res = new ArrayList<IMotion>();
    res.add(this.motion1);
    assertEquals(res, ellipse1.getMotions());
  }

  @Test
  public void testRemoveMotion1() {
    assertEquals(new ArrayList<IMotion>(), ellipse1.getMotions());
    ellipse1.removeMotion(10);
    assertEquals(new ArrayList<IMotion>(), ellipse1.getMotions());
  }

  /**
   * Test that nothing happens when start tick isn't found
   */
  @Test
  public void testRemoveMotion2() {
    ellipse1.addMotion(this.motion1);
    ArrayList<IMotion> temp = new ArrayList<IMotion>();
    temp.add(this.motion1);
    assertEquals(temp, ellipse1.getMotions());
    ellipse1.removeMotion(3);
    assertEquals(temp, ellipse1.getMotions());
  }

  @Test
  public void testRemoveMotion3() {
    ellipse1.addMotion(this.motion1);
    ArrayList<IMotion> temp = new ArrayList<IMotion>();
    temp.add(this.motion1);
    assertEquals(temp, ellipse1.getMotions());
    ellipse1.removeMotion(1);
    assertEquals(new ArrayList<>(), ellipse1.getMotions());
  }

  /**
   * Tests to make sure that calling typeToString on an RECTANGLE returns "rectangle".
   */
  @Test
  public void testTypeToStringRect() {
    assertEquals("rectangle", ShapeType.typeToString(ShapeType.RECTANGLE));
  }

  /**
   * Tests to make sure that calling typeToString on an ELLIPSE returns "ellipse".
   */
  @Test
  public void testTypeToStringEllipse() {
    assertEquals("ellipse", ShapeType.typeToString(ShapeType.ELLIPSE));
  }

  /**
   * Tests that stringToType returns the right type
   */
  @Test
  public void testRectangleStringType() {
    assertEquals(ShapeType.RECTANGLE, ShapeType.stringToType("rectangle"));
  }

  /**
   * Tests that stringToType returns the right type
   */
  @Test
  public void testEllipseStringType() {
    assertEquals(ShapeType.ELLIPSE, ShapeType.stringToType("ellipse"));
  }

  /**
   * Tests an error is thrown when the string is not supported
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadStringType() {
    ShapeType.stringToType("triangle");
  }

  /**
   * Tests an error is thrown when the string is null
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadStringType2() {
    ShapeType.stringToType(null);
  }
}