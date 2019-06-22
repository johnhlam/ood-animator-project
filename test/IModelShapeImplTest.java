import cs3500.animator.model.IModelShape;
import cs3500.animator.model.IModelShapeImpl;
import cs3500.animator.model.IKeyframe;
import cs3500.animator.model.IKeyframeImpl;
import cs3500.animator.model.IReadOnlyShape;
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

  private IModelShape rect1;
  private IModelShape ellipse1;
  private IModelShape ellipse2;
  private IKeyframe keyframe1 = new IKeyframeImpl(1, 10, 10, 0, 0, Color.BLACK);
  private IKeyframe keyframe2 = new IKeyframeImpl(10, 15, 15, 0, 0, Color.RED);
  private IKeyframe keyframe3 = new IKeyframeImpl(20, 15, 15, 0, 0, Color.RED);


  @Before
  public void setUp() {
    rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);

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
    assertEquals("shape R1 rectangle", this.rect1.printKeyframes());
  }

  /**
   * Tests the shape's constructor.
   */
  @Test
  public void testShapeConstructor2() {
    assertEquals("shape E1 ellipse", this.ellipse1.printKeyframes());
  }

  /**
   * Tests an exception is thrown if a null Color is being added.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullKeyframe() {
    this.rect1.addKeyframe(0, 0, 0, 0, 0, null);
  }

  /**
   * Tests an exception is thrown if overlapping keyframes are being added.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddOverlappingKeyframe() {
    // adds a keyframe
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    // throws exception
    this.rect1.addKeyframe(1, 10, 1, 0, 0, Color.BLACK);
  }

  /**
   * Tests an exception is thrown if disagreeing adjacent keyframes are being added (end of list).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadAdjacentKeyframes() {
    // adds a keyframe
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    this.rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    // throws exception
    this.rect1.addKeyframe(10, 10, 30, -2, 3, Color.GREEN);
  }

  /**
   * Tests an exception is thrown if disagreeing adjacent keyframes are being added (middle of the
   * list).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadAdjacentKeyframes2() {
    // adds a keyframe
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    // adds a keyframe
    this.rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    // adds a keyframe
    this.rect1.addKeyframe(30, 15, 15, 0, 0, Color.RED);
    // throws exception
    this.rect1.addKeyframe(10, 15, 12, 0, 0, Color.RED);
  }

  /**
   * Tests adding a keyframe.
   */
  @Test
  public void testAdd1Keyframe() {
    assertEquals("shape R1 rectangle", this.rect1.printKeyframes());
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape R1 rectangle\nmotion R1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.rect1.printKeyframes());
  }

  /**
   * Tests adding 3 keyframes.
   */
  @Test
  public void testAdd3Keyframes() {
    assertEquals("shape R1 rectangle", this.rect1.printKeyframes());
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape R1 rectangle\nmotion R1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.rect1.printKeyframes());

    this.rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    assertEquals("shape R1 rectangle\nmotion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());

    this.rect1.addKeyframe(30, 15, 15, 0, 0, Color.RED);
    assertEquals(
        "shape R1 rectangle\n"
            + "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n"
            + "motion R1 10 0 0 15 15 255 0 0\t30 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());
  }

  /**
   * Tests adding 3 keyframes. Includes adding identical keyframes, which should not throw an error.
   */
  @Test
  public void testAdd3Keyframes2() {
    assertEquals("shape R1 rectangle", this.rect1.printKeyframes());
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape R1 rectangle\nmotion R1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.rect1.printKeyframes());

    this.rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    assertEquals("shape R1 rectangle\nmotion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());

    this.rect1.addKeyframe(30, 15, 15, 0, 0, Color.RED);
    assertEquals(
        "shape R1 rectangle\n"
            + "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n"
            + "motion R1 10 0 0 15 15 255 0 0\t30 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());

    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    this.rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    this.rect1.addKeyframe(30, 15, 15, 0, 0, Color.RED);
    assertEquals(
        "shape R1 rectangle\n"
            + "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n"
            + "motion R1 10 0 0 15 15 255 0 0\t30 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());
  }


  /**
   * Tests adding an adjacent keyframe at the end of a list.
   */
  @Test
  public void testAddAdjKeyframe() {
    assertEquals("shape R1 rectangle", this.rect1.printKeyframes());
    this.rect1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape R1 rectangle\nmotion R1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.rect1.printKeyframes());
    this.rect1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    assertEquals("shape R1 rectangle\n"
            + "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());
    this.rect1.addKeyframe(11, 15, 15, 0, 0, Color.RED);
    assertEquals("shape R1 rectangle\n"
            + "motion R1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n"
            + "motion R1 10 0 0 15 15 255 0 0\t11 0 0 15 15 255 0 0",
        this.rect1.printKeyframes());
  }

  @Test
  public void testPrintKeyframes() {
    assertEquals("shape E1 ellipse", this.ellipse1.printKeyframes());
  }

  @Test
  public void testPrintKeyframes2() {
    assertEquals("shape E1 ellipse", this.ellipse1.printKeyframes());
    this.ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.ellipse1.printKeyframes());
  }

  @Test
  public void testPrintKeyframes3() {
    assertEquals("shape E1 ellipse", this.ellipse1.printKeyframes());
    this.ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.ellipse1.printKeyframes());

    this.ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0",
        this.ellipse1.printKeyframes());
  }

  @Test
  public void testPrintKeyframes4() {
    assertEquals("shape E1 ellipse", this.ellipse1.printKeyframes());
    this.ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t1 0 0 10 10 0 0 0",
        this.ellipse1.printKeyframes());

    this.ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0",
        this.ellipse1.printKeyframes());

    this.ellipse1.addKeyframe(40, 1, 123, 456, 0, Color.CYAN);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n"
            + "motion E1 10 0 0 15 15 255 0 0\t40 456 0 1 123 0 255 255",
        this.ellipse1.printKeyframes());

    this.ellipse1.addKeyframe(40, 1, 123, 456, 0, Color.CYAN);
    assertEquals("shape E1 ellipse\n"
            + "motion E1 1 0 0 10 10 0 0 0\t10 0 0 15 15 255 0 0\n"
            + "motion E1 10 0 0 15 15 255 0 0\t40 456 0 1 123 0 255 255",
        this.ellipse1.printKeyframes());
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
  public void testGetKeyframes() {
    assertEquals(new ArrayList<IKeyframe>(), rect1.getKeyframes());
  }

  @Test
  public void testGetKeyframes2() {
    ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    IKeyframe kf = ellipse1.getKeyframes().get(0);

    assertEquals(this.keyframe1.getTick(), kf.getTick());
    assertEquals(this.keyframe1.getWidth(), kf.getWidth(), 0.0001);
    assertEquals(this.keyframe1.getHeight(), kf.getHeight(), 0.0001);
    assertEquals(this.keyframe1.getX(), kf.getX(), 0.0001);
    assertEquals(this.keyframe1.getY(), kf.getY(), 0.0001);
    assertEquals(this.keyframe1.getColor(), kf.getColor());
  }

  @Test
  public void testGetKeyframes3() {
    ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    ellipse1.addKeyframe(20, 15, 15, 0, 0, Color.RED);

    IKeyframe kf0 = ellipse1.getKeyframes().get(0);
    IKeyframe kf1 = ellipse1.getKeyframes().get(1);
    IKeyframe kf2 = ellipse1.getKeyframes().get(2);

    assertEquals(this.keyframe1.getTick(), kf0.getTick());
    assertEquals(this.keyframe1.getWidth(), kf0.getWidth(), 0.0001);
    assertEquals(this.keyframe1.getHeight(), kf0.getHeight(), 0.0001);
    assertEquals(this.keyframe1.getX(), kf0.getX(), 0.0001);
    assertEquals(this.keyframe1.getY(), kf0.getY(), 0.0001);
    assertEquals(this.keyframe1.getColor(), kf0.getColor());

    assertEquals(this.keyframe2.getTick(), kf1.getTick());
    assertEquals(this.keyframe2.getWidth(), kf1.getWidth(), 0.0001);
    assertEquals(this.keyframe2.getHeight(), kf1.getHeight(), 0.0001);
    assertEquals(this.keyframe2.getX(), kf1.getX(), 0.0001);
    assertEquals(this.keyframe2.getY(), kf1.getY(), 0.0001);
    assertEquals(this.keyframe2.getColor(), kf1.getColor());

    assertEquals(this.keyframe3.getTick(), kf2.getTick());
    assertEquals(this.keyframe3.getWidth(), kf2.getWidth(), 0.0001);
    assertEquals(this.keyframe3.getHeight(), kf2.getHeight(), 0.0001);
    assertEquals(this.keyframe3.getX(), kf2.getX(), 0.0001);
    assertEquals(this.keyframe3.getY(), kf2.getY(), 0.0001);
    assertEquals(this.keyframe3.getColor(), kf2.getColor());
  }

  @Test
  public void testRemoveKeyframe1() {
    assertEquals(new ArrayList<IKeyframe>(), ellipse1.getKeyframes());
    ellipse1.removeKeyframe(10);
    assertEquals(new ArrayList<IKeyframe>(), ellipse1.getKeyframes());
  }

  /**
   * Test that nothing happens when start tick isn't found.
   */
  @Test
  public void testRemoveKeyframe2() {
    ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);

    ArrayList<IKeyframe> temp = new ArrayList<>();

    IKeyframe kf = ellipse1.getKeyframes().get(0);

    assertEquals(this.keyframe1.getTick(), kf.getTick());
    assertEquals(this.keyframe1.getWidth(), kf.getWidth(), 0.0001);
    assertEquals(this.keyframe1.getHeight(), kf.getHeight(), 0.0001);
    assertEquals(this.keyframe1.getX(), kf.getX(), 0.0001);
    assertEquals(this.keyframe1.getY(), kf.getY(), 0.0001);
    assertEquals(this.keyframe1.getColor(), kf.getColor());

    ellipse1.removeKeyframe(3);

    IKeyframe kf2 = ellipse1.getKeyframes().get(0);

    assertEquals(this.keyframe1.getTick(), kf2.getTick());
    assertEquals(this.keyframe1.getWidth(), kf2.getWidth(), 0.0001);
    assertEquals(this.keyframe1.getHeight(), kf2.getHeight(), 0.0001);
    assertEquals(this.keyframe1.getX(), kf2.getX(), 0.0001);
    assertEquals(this.keyframe1.getY(), kf2.getY(), 0.0001);
    assertEquals(this.keyframe1.getColor(), kf2.getColor());
  }

  @Test
  public void testRemoveKeyframe3() {
    ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);

    IKeyframe kf = ellipse1.getKeyframes().get(0);

    assertEquals(this.keyframe1.getTick(), kf.getTick());
    assertEquals(this.keyframe1.getWidth(), kf.getWidth(), 0.0001);
    assertEquals(this.keyframe1.getHeight(), kf.getHeight(), 0.0001);
    assertEquals(this.keyframe1.getX(), kf.getX(), 0.0001);
    assertEquals(this.keyframe1.getY(), kf.getY(), 0.0001);
    assertEquals(this.keyframe1.getColor(), kf.getColor());

    ellipse1.removeKeyframe(1);
    assertEquals(new ArrayList<>(), ellipse1.getKeyframes());
  }

  /**
   * Tests that an exception is thrown when getting shape at a negative tick.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeAtNegTick() {
    ellipse1.addKeyframe(1, 10, 10, 0, 0, Color.BLACK);
    ellipse1.getShapeAtTick(-2);
  }

  /**
   * Tests that getting a shape at a tick without keyframes returns what it was constructed with.
   */
  @Test
  public void testShapeNoKeyframes() {
    IReadOnlyShape shape = ellipse1.getShapeAtTick(2);
    assertEquals(shape, ellipse1);
  }

  /**
   * Tests that getting a shape at a tick before its first keyframe returns the initial state of
   * the shape.
   */
  @Test
  public void testShapeBeforeFirstKeyframe() {
    ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);

    IReadOnlyShape shape = ellipse1.getShapeAtTick(2);
    assertEquals(12, shape.getWidth(), .001);
    assertEquals(12, shape.getHeight(), .001);
    assertEquals(10, shape.getX(), .001);
    assertEquals(12, shape.getY(), .001);
    assertEquals(Color.WHITE, shape.getColor());
  }

  /**
   * Tests that getting a shape at a tick after its last keyframe returns its initial state.
   */
  @Test
  public void testShapeAfterLastKeyframe() {
    ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    IReadOnlyShape shape = ellipse1.getShapeAtTick(30);

    assertEquals(12, shape.getWidth(), .001);
    assertEquals(12, shape.getHeight(), .001);
    assertEquals(10, shape.getX(), .001);
    assertEquals(12, shape.getY(), .001);
    assertEquals(Color.WHITE, shape.getColor());
  }

  /**
   * Tests that getting a shape at a tick on a keyframe returns the correct output.
   */
  @Test
  public void testShapeAtStartKeyframe() {
    ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    IReadOnlyShape shape = ellipse1.getShapeAtTick(10);

    assertEquals(15, shape.getWidth(), .001);
    assertEquals(15, shape.getHeight(), .001);
    assertEquals(0, shape.getX(), .001);
    assertEquals(0, shape.getY(), .001);
    assertEquals(Color.red, shape.getColor());
  }

  /**
   * Tests that getting a shape at a tick between two keyframes returns the correct output.
   */
  @Test
  public void testShapeAtBetweenKeyframe() {
    ellipse1.addKeyframe(10, 15, 15, 0, 0, Color.RED);
    ellipse1.addKeyframe(20, 45, 45, 30, 30, Color.CYAN);

    IReadOnlyShape shape = ellipse1.getShapeAtTick(15);

    assertEquals(30, shape.getWidth(), .001);
    assertEquals(30, shape.getHeight(), .001);
    assertEquals(15, shape.getX(), .001);
    assertEquals(15, shape.getY(), .001);
    assertEquals(Color.GRAY, shape.getColor());
  }

  /**
   * Tests that getting a shape at a tick during a keyframe returns the correct output (including
   * with
   * negative numbers).
   */
  @Test
  public void testShapeDuringKeyframe() {
    ellipse1.addKeyframe(10, 10, 30, -2, 3, Color.GREEN);
    ellipse1.addKeyframe(40, 15, 15, 0, 0, Color.RED);

    IReadOnlyShape shape = ellipse1.getShapeAtTick(15);

    assertEquals(11, shape.getWidth(), .001);
    assertEquals(28, shape.getHeight(), .001);
    assertEquals(-2, shape.getX(), .001);
    assertEquals(3, shape.getY(), .001);
    assertEquals(new Color(43, 213, 0), shape.getColor());
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
   * Tests that stringToType returns the right type.
   */
  @Test
  public void testRectangleStringType() {
    assertEquals(ShapeType.RECTANGLE, ShapeType.stringToType("rectangle"));
  }

  /**
   * Tests that stringToType returns the right type.
   */
  @Test
  public void testEllipseStringType() {
    assertEquals(ShapeType.ELLIPSE, ShapeType.stringToType("ellipse"));
  }

  /**
   * Tests an error is thrown when the string is not supported.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadStringType() {
    ShapeType.stringToType("triangle");
  }

  /**
   * Tests an error is thrown when the string is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadStringType2() {
    ShapeType.stringToType(null);
  }
}