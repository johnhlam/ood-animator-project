// TODO: Test for clashing keyframes
// TODO: Get rid of printStackTraces
// TODO: Fix line wrapping: File -> Settings -> Code Style -> Java -> Wrapping and Braces -> Keep
//  when reformatting -> Line breaks OFF;; Toggle back on when done
// TODO: Add tests for making sure that no error occurs when adding a keyframe in with the exact
//  same params as an existing one

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.IModelShape;
import cs3500.animator.model.IKeyframe;
import cs3500.animator.model.ShapeType;
import cs3500.animator.util.AnimationBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the methods of IModel and IModelImpl, its implementation.
 */
public class IModelImplTests {

  private IModel model1;
  private AnimationBuilder<IModelImpl> builder;

  /**
   * Sets up a blank model without any shapes.
   */
  @Before
  public void setUp() {
    this.model1 = new IModelImpl();
    builder = IModelImpl.builder();
  }

  /**
   * Tests that a model cannot be constructed with negative width and height.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructorNegative() {
    new IModelImpl(0, 0, -20, -30, 0, 0, new ArrayList<>());
  }

  // Tests for IModelImpl#printAnimations()

  /**
   * Tests IModelImpl#printAnimations() with a model that has no IShapes in it.
   */
  @Test
  public void testPrintEmpty() {
    assertEquals("", this.model1.printAnimations());
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has 1 (rectangle) IModelShape in it,
   * without any keyframes in the shape.
   */
  @Test
  public void testPrintEmptyRect() {
    model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());
  }

  /**
   * Tests IModelImpl#printAnimations() with a model that has 1 (ellipse) IModelShape in it, without
   * any keyframes in the shape.
   */
  @Test
  public void testPrintEmptyEll() {
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape C ellipse", model1.printAnimations());
  }

  /**
   * Tests for printing the animation of a model with 1 shape in it (no keyframes).
   */
  @Test
  public void testPrintWith1Shape() {
    assertEquals("", this.model1.printAnimations());
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    assertEquals("shape R rectangle", this.model1.printAnimations());
  }

  /**
   * Tests for printing the animation of a model with 2 shapes in it (no keyframes).
   */
  @Test
  public void testPrintWith2Shapes() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle\n\n" + "shape C ellipse", this.model1.printAnimations());
  }

  /**
   * Tests for printing the animation of a model with 3 shapes in it (no keyframes).
   */
  @Test
  public void testPrintWith3Shapes() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);

    assertEquals("shape R rectangle\n\n" + "shape C ellipse\n\n" + "shape S rectangle",
        this.model1.printAnimations());
  }

  /**
   * Tests to make sure the correct output is generated when you add 2 keyframes into a model with 3
   * shapes (none of which have keyframes).
   */
  @Test
  public void testPrintAdd2KF() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t1 30 40 10 20 0 255 255\n\n"
            + "shape C ellipse\n\n" + "shape S rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n\n"
            + "shape C ellipse\n\n" + "shape S rectangle", this.model1.printAnimations());
  }

  /**
   * Tests to make sure the correct output is generated when you add 4 keyframes into a model with 3
   * shapes (none of which have keyframes).
   */
  @Test
  public void testPrintAdd4KF() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);

    this.model1.addKeyframe("C", 20, 30, 40, 0, 0, Color.ORANGE);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n\n"
            + "shape C ellipse\n" + "motion C 20 30 40 0 0 255 200 0\t20 30 40 0 0 255 200 0\n\n"
            + "shape S rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n\n"
            + "shape C ellipse\n" + "motion C 20 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n\n"
            + "shape S rectangle", this.model1.printAnimations());
  }

  /**
   * Tests to make sure the correct output is generated when you add 3 keyframes into a model with 3
   * shapes (none of which have keyframes).
   */
  @Test
  public void testPrintAdd5KF() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("C", 20, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n"
            + "motion R 10 30 40 40 60 0 255 255\t30 50 60 80 120 0 255 0\n" + "\n"
            + "shape C ellipse\n" + "motion C 20 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n"
            + "\n" + "shape S rectangle", this.model1.printAnimations());
  }

  /**
   * Tests to make sure the correct output is generated when you add all of the shapes first, and
   * then the keyframes.
   */
  @Test
  public void testPrintShapesThenKeyframes() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);
    this.model1.addKeyframe("S", 35, 15, 20, 6, 7, Color.RED);
    this.model1.addKeyframe("S", 45, 15, 20, 6, 7, Color.YELLOW);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n"
            + "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n"
            + "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0\n" + "\n"
            + "shape C ellipse\n" + "motion C 1 30 40 10 20 0 255 255\t10 30 40 10 20 255 200 0\n"
            + "motion C 10 30 40 10 20 255 200 0\t15 30 40 0 0 255 200 0\n"
            + "motion C 15 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n" + "\n"
            + "shape S rectangle\n" + "motion S 10 30 40 10 10 255 0 0\t25 15 20 10 20 255 0 0\n"
            + "motion S 25 15 20 10 20 255 0 0\t35 15 20 6 7 255 0 0\n"
            + "motion S 35 15 20 6 7 255 0 0\t45 15 20 6 7 255 255 0",
        this.model1.printAnimations());
  }

  /**
   * Tests that an the correct output is generated when keyframes are added out of order. Same
   * keyframes are added as above test, but in a different order.
   */
  public void testPrintShapesThenKeyframes1() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("S", 30, 15, 20, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 35, 15, 20, 6, 7, Color.RED);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("C", 20, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("R", 20, 50, 60, 80, 120, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);
    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);
    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("S", 45, 15, 20, 6, 7, Color.YELLOW);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n"
            + "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n"
            + "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0\n" + "\n"
            + "shape C ellipse\n" + "motion C 1 30 40 10 20 0 255 255\t10 30 40 10 20 255 200 0\n"
            + "motion C 10 30 40 10 20 255 200 0\t15 30 40 0 0 255 200 0\n"
            + "motion C 15 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n" + "\n"
            + "shape S rectangle\n" + "motion S 10 30 40 10 10 255 0 0\t25 15 20 10 20 255 0 0\n"
            + "motion S 25 15 20 10 20 255 0 0\t35 15 20 6 7 255 0 0\n"
            + "motion S 35 15 20 6 7 255 0 0\t45 15 20 6 7 255 255 0",
        this.model1.printAnimations());
  }

  /**
   * Tests to make sure the correct output is generated when you add all of the keyframes for each
   * shape before adding the next shape.
   */
  @Test
  public void testPrintShapesKeyframesTogether() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);
    this.model1.addKeyframe("S", 35, 15, 20, 6, 7, Color.RED);
    this.model1.addKeyframe("S", 45, 15, 20, 6, 7, Color.YELLOW);
    assertEquals(
        "shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0\n" +
            "\n" +
            "shape C ellipse\n" +
            "motion C 1 30 40 10 20 0 255 255\t10 30 40 10 20 255 200 0\n" +
            "motion C 10 30 40 10 20 255 200 0\t15 30 40 0 0 255 200 0\n" +
            "motion C 15 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n" +
            "\n" +
            "shape S rectangle\n" +
            "motion S 10 30 40 10 10 255 0 0\t25 15 20 10 20 255 0 0\n" +
            "motion S 25 15 20 10 20 255 0 0\t35 15 20 6 7 255 0 0\n" +
            "motion S 35 15 20 6 7 255 0 0\t45 15 20 6 7 255 255 0",
        this.model1.printAnimations());
  }

  // Tests for IModelImpl#addKeyframe(String, int, double, double, double, double, Color,
  // int, double, double, double, double, Color)

  /**
   * Tests that attempting to add a keyframe with a {@code null} id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeNullID() {
    this.model1.addKeyframe(null, 1, 1, 1, 1, 1, Color.PINK);
  }

  /**
   * Tests that attempting to add a keyframe with a {@code null} color throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeSColor() {
    this.model1.addKeyframe("ID", 1, 1, 1, 1, 1, null);
  }

  /**
   * Tests that attempting to add a keyframe with a negative tick throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeNegSTick() {
    this.model1.addKeyframe("ID", -1, 1, 1, 1, 1, Color.GREEN);
  }

  /**
   * Tests that attempting to add a keyframe with a negative width throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeNegSWidth() {
    this.model1.addKeyframe("ID", 1, 1, 1, -1, 1, Color.GREEN);
  }

  /**
   * Tests that attempting to add a keyframe with a negative height throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeNegSHeight() {
    this.model1.addKeyframe("ID", 1, 1, 1, 1, -1, Color.GREEN);
  }
  
  /**
   * Tests that attempting to add a keyframe with a negative tick, width, and height throws an 
   * error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeNegAll() {
    this.model1.addKeyframe("ID", -1, 1, 1, -1, -1, Color.GREEN);
  }


  /**
   * Tests that attempting to add a keyframe to a shape for a model without any IShapes in it
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeEmpty() {
    this.model1.addKeyframe("ID", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a non-existent id
   * throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeInvID() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("ID", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a lowercase version of
   * an id (R) throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeLowerIDR() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("r", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a lowercase version of
   * an id (C) throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeLowerIDC() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("c", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a lowercase version of
   * an id (S) throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeLowerIDS() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("s", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a uppercase version of
   * an id (r) throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeUpperIDR() {
    this.model1.addShape("r", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("R", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a uppercase version of
   * an id (c) throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeUpperIDC() {
    this.model1.addShape("r", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("C", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe to a shape for a model with a uppercase version of
   * an id (s) throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeUpperIDS() {
    this.model1.addShape("r", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("c", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("s", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);
    this.model1.addKeyframe("S", 1, 0, 0, 20, 20, Color.ORANGE);
  }

  /**
   * Tests that attempting to add a keyframe that overlaps with the first
   * keyframe of the first shape in the model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeOverlapFirst() {

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addKeyframe("R", 0, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 10, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 20, 10, 40, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 35, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 40, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R",45, 20, 40, 60, 120, Color.DARK_GRAY);

    // This call should throw an error
    this.model1.addKeyframe("R", 0, 0, 20, 30, 40, Color.CYAN);
  }

  /**
   * Tests that attempting to add a keyframe that overlaps with the second
   * keyframe of the first shape in the model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeOverlapSecond() {

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addKeyframe("R", 10, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 20, 10, 40, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 35, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 40, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 45, 20, 40, 60, 120, Color.DARK_GRAY);

    // This call should throw an error
    this.model1.addKeyframe("R", 20, 0, 40, 30, 40, Color.CYAN);
  }

  /**
   * Tests that attempting to add a keyframe that overlaps with the third
   * keyframe of the first shape in the model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeOverlapThird() {

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addKeyframe("R", 10, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 20, 10, 40, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 35, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 40, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 45, 20, 40, 60, 120, Color.DARK_GRAY);

    // This call should throw an error
    this.model1.addKeyframe("R", 35, 1, 40, 60, 120, Color.CYAN);
  }

  /**
   * Tests that attempting to add a keyframe that overlaps with the last keyframe of the first
   shape in
   * the model throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeOverlapLast() {

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addKeyframe("R", 10, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 20, 10, 40, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 35, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 40, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 45, 20, 40, 60, 120, Color.DARK_GRAY);

    // This call should throw an error
    this.model1.addKeyframe("R", 45, 0, 40, 60, 120, Color.DARK_GRAY);
  }

  /**
   * Tests that attempting to add a keyframe whose state doesn't match up with the existing
   * keyframe's state (in a given shape's list of keyframes) will throw an error (which throw
   * an error simply because of the same tick).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddKeyframeBadStart() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    this.model1.addKeyframe("R", 10, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 20, 10, 40, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 35, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 40, 10, 40, 60, 120, Color.CYAN);
    this.model1.addKeyframe("R", 45, 20, 40, 60, 120, Color.DARK_GRAY);

    this.model1.addKeyframe("R", 35, 5, 10, 15, 20, Color.GREEN);
  }


  /**
   * Tests for adding 2 keyframes into a model with one shape in it that has no keyframes. I
   */
  @Test
  public void testAddKeyframe1ShapeEmpty() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t1 30 40 10 20 0 255 255",
        this.model1.printAnimations());

    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255",
        this.model1.printAnimations());
  }

  /**
   * Tests for adding a keyframe into a model with one shape in it that has 1 keyframe.
   */
  @Test
  public void testAddKeyframe1Shape1() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255",
        this.model1.printAnimations());

    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);

    assertEquals(
        "shape R rectangle\n" + "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n"
            + "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255",
        this.model1.printAnimations());
  }

  /**
   * Tests for adding a keyframe into a model with one shape in it that has three keyframes.
   * The keyframe should be added to the beginning of the shape's keyframe list.
   */
  @Test
  public void testAddKeyframe1Shape3KFBeg() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    assertEquals(
        "shape R rectangle\n" + "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n"
            + "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0",
        this.model1.printAnimations());

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0",
        this.model1.printAnimations());
  }

  /**
   * Tests for adding a keyframe into a model with one shape in it that has three keyframes.
   * The keyframe should be added to the beginning of the shape's keyframe list. Includes adding
   * multiple identical keyframes, which should not throw an error.
   */
  @Test
  public void testAddKeyframe1Shape3KFBeg2() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    // Since these keyframes are identical to the ones above, this should not throw and error
    // (and should not have any effect on the shapes or model).
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    assertEquals(
        "shape R rectangle\n" + "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n"
            + "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0",
        this.model1.printAnimations());

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0",
        this.model1.printAnimations());
  }


  /**
   * Tests for adding in a keyframe to a model with one shape, where the keyframe will have all
   * fields of 0.
   */
  @Test
  public void testAddKeyframeZero() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());

    this.model1.addKeyframe("R", 0, 0, 0, 0, 0, Color.BLACK);

    assertEquals("shape R rectangle\n"
            + "motion R 0 0 0 0 0 0 0 0\t0 0 0 0 0 0 0 0",
        this.model1.printAnimations());
  }

  // Tests for IModelImpl#addShape(String, ShapeType, double, double, double, double, Color)

  /**
   * Tests that attempting to add a shape with a {@code null} id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullID() {
    this.model1.addShape(null, ShapeType.RECTANGLE, 4.5, 6.4, 3.4, 7, Color.WHITE);
  }

  /**
   * Tests that attempting to add a shape with a {@code null} ShapeType throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullType() {
    this.model1.addShape("R", null, 4.5, 6.4, 3.4, 7, Color.WHITE);
  }

  /**
   * Tests that attempting to add a shape with a {@code null} Color throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullColor() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 4.5, 6.4, 3.4, 7, null);
  }

  /**
   * Tests that attempting to add a shape with a {@code null} id, ShapeType, and Color throws an
   * error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullAll() {
    this.model1.addShape(null, null, 4.5, 6.4, 3.4, 7, null);
  }

  /**
   * Tests that attempting to add a shape with a negative width throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNegWidth() {
    this.model1.addShape("R", ShapeType.RECTANGLE, -10, 20, 30, 40, Color.CYAN);
  }

  /**
   * Tests that attempting to add a shape with a negative height throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNegHeight() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, -20, 30, 40, Color.CYAN);
  }

  /**
   * Tests that attempting to add a shape with a negative height throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNegBoth() {
    this.model1.addShape("R", ShapeType.RECTANGLE, -10, -20, 30, 40, Color.CYAN);
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
            + "shape C ellipse",
        this.model1.printAnimations());
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
            + "shape S rectangle",
        this.model1.printAnimations());
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
            + "shape r rectangle",
        this.model1.printAnimations());
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
            + "shape c ellipse",
        this.model1.printAnimations());
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
            + "shape s rectangle",
        this.model1.printAnimations());
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
            + "shape R rectangle",
        this.model1.printAnimations());
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
            + "shape C ellipse",
        this.model1.printAnimations());
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
            + "shape S rectangle",
        this.model1.printAnimations());
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

  /**
   * Tests that adding a shape with a width and height of 0 adds the shape in normally (with no
   * errors).
   */
  @Test
  public void testAddShapeWidthHeight0() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 0, 0, 30.7, 40.9, Color.CYAN);

    assertEquals("shape R rectangle", this.model1.printAnimations());
  }

  // Tests for IModelImpl#removeShape(String)

  /**
   * Tests that attempting to remove a shape from a model with a {@code null} id throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNull() {
    this.model1.removeShape(null);
  }

  /**
   * Tests that attempting to remove a shape from a model without any IShapes in it throws an
   * error.
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
   * Tests for removing the first shape in a model's list of shapes (without any keyframes added).
   */
  @Test
  public void testRemoveFirstNoKF() {
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
   * Tests for removing the second shape in a model's list of shapes (without any keyframes
   added).
   */
  @Test
  public void testRemoveSecondNoKF() {
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
   * Tests for removing the third shape in a model's list of shapes (without any keyframes added).
   */
  @Test
  public void testRemoveThirdNoKF() {
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
   * Tests for removing the first shape in a model's list of shapes (with keyframes added).
   */
  @Test
  public void testRemoveFirstWithKF() {
    assertEquals("", this.model1.printAnimations());

    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 45, 60, 40, 60, Color.GREEN);

    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);
    this.model1.addKeyframe("S", 35, 15, 20, 6, 7, Color.RED);
    this.model1.addKeyframe("S", 45, 15, 20, 6, 7, Color.YELLOW);

    assertEquals(
        "shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0\n" +
            "\n" +
            "shape C ellipse\n" +
            "motion C 1 30 40 10 20 0 255 255\t10 30 40 10 20 255 200 0\n" +
            "motion C 10 30 40 10 20 255 200 0\t15 30 40 0 0 255 200 0\n" +
            "motion C 15 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n" +
            "\n" +
            "shape S rectangle\n" +
            "motion S 10 30 40 10 10 255 0 0\t25 15 20 10 20 255 0 0\n" +
            "motion S 25 15 20 10 20 255 0 0\t35 15 20 6 7 255 0 0\n" +
            "motion S 35 15 20 6 7 255 0 0\t45 15 20 6 7 255 255 0",
        this.model1.printAnimations());

    this.model1.removeShape("R");

    assertEquals(
        "shape C ellipse\n" +
            "motion C 1 30 40 10 20 0 255 255\t10 30 40 10 20 255 200 0\n" +
            "motion C 10 30 40 10 20 255 200 0\t15 30 40 0 0 255 200 0\n" +
            "motion C 15 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n" +
            "\n" +
            "shape S rectangle\n" +
            "motion S 10 30 40 10 10 255 0 0\t25 15 20 10 20 255 0 0\n" +
            "motion S 25 15 20 10 20 255 0 0\t35 15 20 6 7 255 0 0\n" +
            "motion S 35 15 20 6 7 255 0 0\t45 15 20 6 7 255 255 0",
        this.model1.printAnimations());
  }

  @Test
  public void testGetX() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
  }

  @Test
  public void testGetY() {
    IModel model = this.builder.build();
    assertEquals(0, model.getY());
  }

  @Test
  public void testGetWidth() {
    IModel model = this.builder.build();
    assertEquals(200, model.getWidth());
  }

  @Test
  public void testGetHeight() {
    IModel model = this.builder.build();
    assertEquals(200, model.getHeight());
  }

  @Test
  public void testGetMaxX() {
    IModel model = this.builder.build();
    assertEquals(0, model.getMaxX());
  }

  @Test
  public void testGetMaxY() {
    IModel model = this.builder.build();
    assertEquals(0, model.getMaxX());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveKeyframeNullID() {
    this.model1.removeKeyframe(null, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveKeyframeNegTick() {
    this.model1.removeKeyframe("R", -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetKeyframesNegTick() {
    this.model1.getKeyframesAtTick(-2);
  }

  /**
   * Tests that a keyframe can be removed from the beginning of the animation.
   */
  @Test
  public void testRemoveKeyframeBeg() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 45, 60, 40, 60, Color.GREEN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());

    this.model1.removeKeyframe("R", 1);

    assertEquals("shape R rectangle\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());
  }

  /**
   * Tests that a keyframe can be removed from the middle of the animation.
   */
  @Test
  public void testRemoveKeyframeMid() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 45, 60, 40, 60, Color.GREEN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());

    this.model1.removeKeyframe("R", 15);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());
  }

  /**
   * Tests that a keyframe can be removed from the end of the animation.
   */
  @Test
  public void testRemoveKeyframeEnd() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 45, 60, 40, 60, Color.GREEN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());

    this.model1.removeKeyframe("R", 30);

    assertEquals("shape R rectangle\n" +
        "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
        "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255",
        model1.printAnimations());
  }

  /**
   * Tests that nothing happens if the ID cannot be found.
   */
  @Test
  public void testRemoveKeyframe2() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 45, 60, 40, 60, Color.GREEN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());

    this.model1.removeKeyframe("E", 15);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());
  }

  /**
   * Tests that nothing happens if the tick cannot be found.
   */
  @Test
  public void testRemoveKeyframe3() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 45, 60, 40, 60, Color.GREEN);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());

    this.model1.removeKeyframe("R", 2);

    assertEquals("shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 45 60 40 60 0 255 0",
        model1.printAnimations());
  }

  /**
   * Tests that removing from an empty animation does nothing.
   */
  @Test
  public void testRemoveKeyframe4() {
    assertEquals("", model1.printAnimations());

    this.model1.removeKeyframe("E", 2);

    assertEquals("", model1.printAnimations());
  }

  @Test
  public void testGetKeyframesAtTickEmpty() {
    assertEquals(new ArrayList<IKeyframe>(), model1.getKeyframesAtTick(0));
  }

  /**
   * Tests that getting keyframes at a given tick across shapes works.
   */
  @Test
  public void testGetKeyframesAtTick() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);

    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);

    ArrayList<IKeyframe> res = new ArrayList<>();
    res.add(model1.getShapes().get(0).getKeyframes().get(1));
    res.add(model1.getShapes().get(2).getKeyframes().get(0));

    assertEquals(res, model1.getKeyframesAtTick(10));
  }

  /**
   * Tests getFinalTick on a model without shapes.
   */
  @Test
  public void testGetFinalTickEmpty() {
    assertEquals(0, this.model1.getFinalTick());
  }

  /**
   * Tests getFinalTick on a model with 3 shapes, but no keyframes.
   */
  @Test
  public void testGetFinalTick3Shapes() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    assertEquals(0, this.model1.getFinalTick());

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    assertEquals(0, this.model1.getFinalTick());

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);
    assertEquals(0, this.model1.getFinalTick());
  }

  /**
   * Tests getFinalTick on a model with 3 shapes, but only the first shape has 1 keyframe.
   */
  @Test
  public void testGetFinalTick3Shapes1KF() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    this.model1.addKeyframe("R", 1, 0, 0, 0, 0, new Color(0, 0, 0));
    assertEquals(1, this.model1.getFinalTick());

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    assertEquals(1, this.model1.getFinalTick());

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);
    assertEquals(1, this.model1.getFinalTick());
  }

  /**
   * Tests getFinalTick on a model with 3 shapes, but only the second shape has 1 keyframe.
   */
  @Test
  public void testGetFinalTick3Shapes1KF2() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    assertEquals(0, this.model1.getFinalTick());

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addKeyframe("C", 1, 0, 0, 0, 0, new Color(0, 0, 0));
    assertEquals(1, this.model1.getFinalTick());

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);
    assertEquals(1, this.model1.getFinalTick());
  }

  /**
   * Tests getFinalTick on a model with 3 shapes, but only the third shape has 1 keyframe.
   */
  @Test
  public void testGetFinalTick3Shapes1KF3() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10.3, 20.5, 30.7, 40.9, Color.CYAN);
    assertEquals(0, this.model1.getFinalTick());

    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    assertEquals(0, this.model1.getFinalTick());

    this.model1.addShape("S", ShapeType.RECTANGLE, 4, 5.9, 6.8, 1000, Color.RED);
    this.model1.addKeyframe("S", 1, 0, 0, 0, 0, new Color(0, 0, 0));
    assertEquals(1, this.model1.getFinalTick());
  }

  /**
   * Tests that calling getFinalTick on a model with 3 shapes, each with their own keyframes,
   * returns the correct result.
   */
  @Test
  public void testGetFinalTickKF1() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);
    this.model1.addKeyframe("S", 35, 15, 20, 6, 7, Color.RED);
    this.model1.addKeyframe("S", 45, 15, 20, 6, 7, Color.YELLOW);

    assertEquals(45, this.model1.getFinalTick());
  }

  /**
   * Tests that calling getFinalTick on a model with 3 shapes, each with their own keyframes,
   * returns the correct result.
   */
  @Test
  public void testGetFinalTickKF2() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 36, 45, 60, 0, 0, Color.ORANGE);

    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);

    assertEquals(36, this.model1.getFinalTick());
  }

  /**
   * Tests that calling getFinalTick on a model with 3 shapes, each with their own keyframes,
   * returns the correct result.
   */
  @Test
  public void testGetFinalTickKF3() {
    this.model1.addShape("R", ShapeType.RECTANGLE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("C", ShapeType.ELLIPSE, 10, 20, 30, 40, Color.CYAN);
    this.model1.addShape("S", ShapeType.RECTANGLE, 10, 10, 30, 40, Color.RED);

    this.model1.addKeyframe("R", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("S", 35, 15, 20, 6, 7, Color.RED);
    this.model1.addKeyframe("C", 1, 30, 40, 10, 20, Color.CYAN);
    this.model1.addKeyframe("C", 10, 30, 40, 10, 20, Color.ORANGE);
    this.model1.addKeyframe("R", 10, 30, 40, 40, 60, Color.CYAN);
    this.model1.addKeyframe("S", 45, 15, 20, 6, 7, Color.YELLOW);
    this.model1.addKeyframe("R", 15, 45, 60, 40, 60, Color.CYAN);
    this.model1.addKeyframe("S", 10, 30, 40, 10, 10, Color.RED);
    this.model1.addKeyframe("C", 15, 30, 40, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("C", 30, 45, 60, 0, 0, Color.ORANGE);
    this.model1.addKeyframe("S", 25, 15, 20, 10, 20, Color.RED);
    this.model1.addKeyframe("R", 30, 50, 60, 80, 120, Color.GREEN);

    assertEquals(45, this.model1.getFinalTick());
  }


  /**
   * Tests that getting shapes at a negative tick throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapesAtNegTick() {
    IModel model = this.builder.build();
    model.getShapesAtTick(-2);
  }

  /**
   * Tests that getting shapes at a given tick in an empty animation returns nothing.
   */
  @Test
  public void testNoShapesTween() {
    IModel model = this.builder.build();
    assertEquals(new ArrayList<IReadOnlyShape>(), model.getShapesAtTick(1));
  }

  /**
   * Tests that getting shapes at a given tick with no keyframes inside returns the same shapes.
   */
  @Test
  public void testShapesNoKeyframesTween() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    IModel model = this.builder.build();
    List<IReadOnlyShape> res = new ArrayList<>();
    res.add(model.getShapes().get(0));
    res.add(model.getShapes().get(1));
    assertEquals(res, model.getShapesAtTick(1));
  }

  /**
   * Tests that tweening a shape at a tick before its first keyframe returns the start state of
   * the first keyframe.
   */
  @Test
  public void testShapesBeforeFirstKeyframe() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 10, 0, 0, 15, 15, 255, 0, 0,
        20, 15, 15, 0, 0, 255, 0, 0);
    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(3);
    IReadOnlyShape shape = shapes.get(0);

    assertEquals(0, shape.getWidth(), .001);
    assertEquals(0, shape.getHeight(), .001);
    assertEquals(0, shape.getX(), .001);
    assertEquals(0, shape.getY(), .001);
    assertEquals(Color.BLACK, shape.getColor());
  }

  /**
   * Tests that tweening a shape at a tick after its last keyframe returns the end state of the
   * last keyframe.
   */
  @Test
  public void testShapesAfterLastKeyframe() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 10, 0, 0, 15, 15, 255, 0, 0,
        20, 15, 15, 0, 0, 255, 0, 0);
    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(40);
    IReadOnlyShape shape = shapes.get(0);

    assertEquals(0, shape.getWidth(), .001);
    assertEquals(0, shape.getHeight(), .001);
    assertEquals(0, shape.getX(), .001);
    assertEquals(0, shape.getY(), .001);
    assertEquals(Color.BLACK, shape.getColor());
  }

  /**
   * Tests that tweening a shape during a keyframe returns the correct output.
   */
  @Test
  public void testShapesDuringKeyframe() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 10, 0, 0, 15, 15, 255, 0, 0,
        20, 15, 15, 0, 0, 255, 0, 0);
    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(10);
    IReadOnlyShape shape = shapes.get(0);

    assertEquals(15, shape.getWidth(), .001);
    assertEquals(15, shape.getHeight(), .001);
    assertEquals(0, shape.getX(), .001);
    assertEquals(0, shape.getY(), .001);
    assertEquals(Color.RED, shape.getColor());
  }

  /**
   * Tests that tweening a shape during a keyframe returns the correct output.
   */
  @Test
  public void testShapesDuringKeyframe2() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 10, 0, 0, 15, 15, 255, 0, 0,
        20, 15, 15, 0, 0, 255, 0, 0);
    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(20);
    IReadOnlyShape shape = shapes.get(0);

    assertEquals(0, shape.getWidth(), .001);
    assertEquals(0, shape.getHeight(), .001);
    assertEquals(15, shape.getX(), .001);
    assertEquals(15, shape.getY(), .001);
    assertEquals(Color.RED, shape.getColor());
  }

  /**
   * Tests that tweening a shape during a keyframe returns the correct output (including with
   * negative numbers).
   */
  @Test
  public void testShapesDuringKeyframe3() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 10, -2, 3, 10, 30, 0, 255, 0,
        40, 0, 0, 15, 15, 255, 0, 0);
    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(15);
    IReadOnlyShape shape = shapes.get(0);

    assertEquals(11, shape.getWidth(), .001);
    assertEquals(28, shape.getHeight(), .001);
    assertEquals(-2, shape.getX(), .001);
    assertEquals(3, shape.getY(), .001);
    assertEquals(new Color(43, 213, 0), shape.getColor());
  }

  /**
   * Tests that tweening an animation with multiple shapes functions as expected.
   */
  @Test
  public void testShapesDuringKeyframe4() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("E", 10, 0, 0, 15, 15, 255, 0, 0,
        20, 15, 15, 0, 0, 255, 0, 0);
    this.builder.addMotion("R", 10, -2, 3, 10, 30, 0, 255, 0,
        40, 0, 0, 15, 15, 255, 0, 0);
    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(20);
    IReadOnlyShape shape = shapes.get(0);
    IReadOnlyShape shape2 = shapes.get(1);

    assertEquals(12, shape.getWidth(), .001);
    assertEquals(25, shape.getHeight(), .001);
    assertEquals(-1, shape.getX(), .001);
    assertEquals(2, shape.getY(), .001);
    assertEquals(new Color(85, 170, 0), shape.getColor());

    assertEquals(0, shape2.getWidth(), .001);
    assertEquals(0, shape2.getHeight(), .001);
    assertEquals(15, shape2.getX(), .001);
    assertEquals(15, shape2.getY(), .001);
    assertEquals(Color.RED, shape2.getColor());
  }

  /**
   * Tests that tweening an animation with multiple shapes and keyframes functions as expected.
   */
  @Test
  public void testShapesDuringKeyframe5() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("E", 5, 100, 200, 30, 30, 200, 0, 3,
        10, 0, 0, 15, 15, 255, 0, 0);
    this.builder.addMotion("E", 10, 0, 0, 15, 15, 255, 0, 0,
        20, 15, 15, 0, 0, 255, 0, 0);
    this.builder.addMotion("R", 10, -2, 3, 10, 30, 0, 255, 0,
        40, 0, 0, 15, 15, 255, 0, 0);

    IModel model = this.builder.build();
    List<IReadOnlyShape> shapes = model.getShapesAtTick(10);
    IReadOnlyShape shape = shapes.get(1);
    IReadOnlyShape shape2 = shapes.get(0);

    assertEquals(15, shape.getWidth(), .001);
    assertEquals(15, shape.getHeight(), .001);
    assertEquals(0, shape.getX(), .001);
    assertEquals(0, shape.getY(), .001);
    assertEquals(new Color(255, 0, 0), shape.getColor());

    assertEquals(10, shape2.getWidth(), .001);
    assertEquals(30, shape2.getHeight(), .001);
    assertEquals(-2, shape2.getX(), .001);
    assertEquals(3, shape2.getY(), .001);
    assertEquals(Color.green, shape2.getColor());
  }

  /**
   * Tests a builder constructs a model with default values when being called with build.
   */
  @Test
  public void testBuilder() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
  }

  /**
   * Tests that building a model with negative width and height throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buildNegModel() {
    this.builder.setBounds(0, 0, -2, -3);
    this.builder.build();
  }

  /**
   * Tests that setBounds updates the builders fields.
   */
  @Test
  public void testSetBounds() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());

    this.builder.setBounds(20, 20, 300, 300);
    model = this.builder.build();
    assertEquals(20, model.getX());
    assertEquals(20, model.getY());
    assertEquals(300, model.getWidth());
    assertEquals(300, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
  }

  /**
   * Tests that an exception is thrown given a null shape type.
   */
  @Test(expected = IllegalArgumentException.class)
  public void declareShapeNullShape() {
    this.builder.declareShape("B", null);
  }

  /**
   * Tests that an exception is thrown given a null ID.
   */
  @Test(expected = IllegalArgumentException.class)
  public void declareShapeNullID() {
    this.builder.declareShape(null, "rectangle");
  }

  /**
   * Tests that an exception is thrown given a null ID and null shape type.
   */
  @Test(expected = IllegalArgumentException.class)
  public void declareShapeNullIDNullShape() {
    this.builder.declareShape(null, null);
  }

  /**
   * Tests that an exception is thrown given an invalid shape type string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void declareShapeBadString() {
    this.builder.declareShape("R", "triangle");
  }

  /**
   * Tests that declaring a shape actually adds it to the model to build.
   */
  @Test
  public void declareShape() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
    this.builder.declareShape("R", "rectangle");
    model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(ShapeType.RECTANGLE, model.getShapes().get(0).getType());
    assertEquals("R", model.getShapes().get(0).getID());
  }

  /**
   * Tests that declaring multiple shapes.
   */
  @Test
  public void declareShape2() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    this.builder.declareShape("R2", "rectangle");
    model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(ShapeType.RECTANGLE, model.getShapes().get(0).getType());
    assertEquals("R", model.getShapes().get(0).getID());
    assertEquals(ShapeType.ELLIPSE, model.getShapes().get(1).getType());
    assertEquals("E", model.getShapes().get(1).getID());
    assertEquals(ShapeType.RECTANGLE, model.getShapes().get(2).getType());
    assertEquals("R2", model.getShapes().get(2).getID());
  }

  /**
   * Tests that an exception is thrown when addMotion is given a null ID.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullMotionID() {
    this.builder.addMotion(null, 1, 0, 0, 0, 0, 0, 0, 0,
        20, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that an exception is thrown when adding motion to an empty model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionEmptyModel() {
    this.builder.addMotion("R", 1, 0, 0, 0, 0, 0, 0, 0,
        20, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that an exception is thrown when adding motion to a non-existent ID.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNoID() {
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("R", 1, 0, 0, 0, 0, 0, 0, 0,
        20, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that an exception is thrown when motion has negative start values.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNegativeStart() {
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("E", -4, -2, -3, -2, -2, -2,
        2, -2, 0, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that an exception is thrown when motion has negative end values.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNegativeEnd() {
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("E", 1, 2, 2, 0, 0, 0, 0, 0,
        -10, 2, 3, -2, -2, -2, -2, -2);
  }

  /**
   * Tests that adding an overlapping motion throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuilderOverlapMotion() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
    this.builder.addMotion("R", 4, 20, 20, 30, 40, 255, 0, 0, 10, 0, 40, 40, 40, 0, 0, 0);
  }


  /**
   * Tests that adding two motions with adjacent ticks but disagreeing states throws an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuilderBadAdjMotions() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
    this.builder.addMotion("R", 10, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
  }

  /**
   * Tests that adding a motion with end tick before start tick throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuilderBadEndTick() {
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 10, 20, 20, 30, 40, 255, 0, 0, 4, 40, 40, 40, 40, 0, 0, 0);

  }

  /**
   * Tests that a motion can be added to an existing shape in the model builder successfully (ie
   * added and max x and y coordinates updated).
   */
  @Test
  public void testAddMotionBuilder() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
    model = this.builder.build();
    IKeyframe startKeyframe= model.getShapes().get(0).getKeyframes().get(0);
    IKeyframe endKeyframe= model.getShapes().get(0).getKeyframes().get(1);
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(80, model.getMaxX());
    assertEquals(80, model.getMaxY());
    assertEquals(ShapeType.RECTANGLE, model.getShapes().get(0).getType());
    assertEquals("R", model.getShapes().get(0).getID());
    assertEquals(1, startKeyframe.getTick());
    assertEquals(20, startKeyframe.getX(), .001);
    assertEquals(20, startKeyframe.getY(), .001);
    assertEquals(30, startKeyframe.getWidth(), .001);
    assertEquals(40, startKeyframe.getHeight(), .001);
    assertEquals(new Color(255, 0, 0), startKeyframe.getColor());

    assertEquals(10, endKeyframe.getTick());
    assertEquals(40, endKeyframe.getX(), .001);
    assertEquals(40, endKeyframe.getY(), .001);
    assertEquals(40, endKeyframe.getWidth(), .001);
    assertEquals(40, endKeyframe.getHeight(), .001);
    assertEquals(new Color(0, 0, 0), endKeyframe.getColor());
  }

  /**
   * Tests that multiple motions can be added to an existing shape in the model builder
   * successfully (ie added and max x and y coordinates updated).
   */
  @Test
  public void testAddMotionBuilder2() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0, 0, 2, 20, 20, 30, 40, 255, 0, 0);
    this.builder.addMotion("R", 2, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
    model = this.builder.build();
    IKeyframe keyframeAdded = model.getShapes().get(0).getKeyframes().get(0);
    IKeyframe keyframeAdded2 = model.getShapes().get(0).getKeyframes().get(1);
    IKeyframe keyframeAdded3 = model.getShapes().get(0).getKeyframes().get(2);
    
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(80, model.getMaxX());
    assertEquals(80, model.getMaxY());
    assertEquals(ShapeType.RECTANGLE, model.getShapes().get(0).getType());
    assertEquals("R", model.getShapes().get(0).getID());
    assertEquals(1, keyframeAdded.getTick());
    assertEquals(20, keyframeAdded.getX(), .001);
    assertEquals(20, keyframeAdded.getY(), .001);
    assertEquals(30, keyframeAdded.getWidth(), .001);
    assertEquals(40, keyframeAdded.getHeight(), .001);
    assertEquals(new Color(255, 0, 0), keyframeAdded.getColor());
    
    assertEquals(2, keyframeAdded2.getTick());
    assertEquals(20, keyframeAdded2.getX(), .001);
    assertEquals(20, keyframeAdded2.getY(), .001);
    assertEquals(30, keyframeAdded2.getWidth(), .001);
    assertEquals(40, keyframeAdded2.getHeight(), .001);
    assertEquals(new Color(255, 0, 0), keyframeAdded2.getColor());

    assertEquals(10, keyframeAdded3.getTick());
    assertEquals(40, keyframeAdded3.getX(), .001);
    assertEquals(40, keyframeAdded3.getY(), .001);
    assertEquals(40, keyframeAdded3.getWidth(), .001);
    assertEquals(40, keyframeAdded3.getHeight(), .001);
    assertEquals(new Color(0, 0, 0), keyframeAdded3.getColor());
  }

  /**
   * Tests that motions can be added to different shapes in the model.
   */
  @Test
  public void testAddMotionBuilder3() {
    IModel model = this.builder.build();
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(0, model.getMaxX());
    assertEquals(0, model.getMaxY());
    assertEquals(new ArrayList<IModelShape>(), model.getShapes());
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("E", "ellipse");
    this.builder.addMotion("R", 1, 20, 20, 30, 40, 255, 0, 0, 2, 20, 20, 30, 40, 255, 0, 0);
    this.builder.addMotion("E", 1, 20, 20, 30, 40, 255, 0, 0, 10, 40, 40, 40, 40, 0, 0, 0);
    model = this.builder.build();
    IKeyframe keyframeAdded = model.getShapes().get(0).getKeyframes().get(0);
    IKeyframe keyframeAdded2 = model.getShapes().get(0).getKeyframes().get(1);
    IKeyframe keyframeAdded3 = model.getShapes().get(1).getKeyframes().get(0);
    IKeyframe keyframeAdded4 = model.getShapes().get(1).getKeyframes().get(1);
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(200, model.getWidth());
    assertEquals(200, model.getHeight());
    assertEquals(80, model.getMaxX());
    assertEquals(80, model.getMaxY());
    assertEquals(ShapeType.RECTANGLE, model.getShapes().get(0).getType());
    assertEquals("R", model.getShapes().get(0).getID());
    assertEquals(1, keyframeAdded.getTick());
    assertEquals(20, keyframeAdded.getX(), .001);
    assertEquals(20, keyframeAdded.getY(), .001);
    assertEquals(30, keyframeAdded.getWidth(), .001);
    assertEquals(40, keyframeAdded.getHeight(), .001);
    assertEquals(new Color(255, 0, 0), keyframeAdded.getColor());

    assertEquals(2, keyframeAdded2.getTick());
    assertEquals(20, keyframeAdded2.getX(), .001);
    assertEquals(20, keyframeAdded2.getY(), .001);
    assertEquals(30, keyframeAdded2.getWidth(), .001);
    assertEquals(40, keyframeAdded2.getHeight(), .001);
    assertEquals(new Color(255, 0, 0), keyframeAdded2.getColor());

    assertEquals(1, keyframeAdded3.getTick());
    assertEquals(20, keyframeAdded3.getX(), .001);
    assertEquals(20, keyframeAdded3.getY(), .001);
    assertEquals(30, keyframeAdded3.getWidth(), .001);
    assertEquals(40, keyframeAdded3.getHeight(), .001);
    assertEquals(new Color(255, 0, 0), keyframeAdded3.getColor());

    assertEquals(10, keyframeAdded4.getTick());
    assertEquals(40, keyframeAdded4.getX(), .001);
    assertEquals(40, keyframeAdded4.getY(), .001);
    assertEquals(40, keyframeAdded4.getWidth(), .001);
    assertEquals(40, keyframeAdded4.getHeight(), .001);
    assertEquals(new Color(0, 0, 0), keyframeAdded4.getColor());
  }

  /**
   * Tests that adding a keyframe with a null ID (via Builder) will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeNullID() {
    this.builder.addKeyframe(null, 10, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that adding a keyframe with a negative tick (via Builder) will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeNegTick() {
    this.builder.addKeyframe("NULL", -1, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that adding a keyframe with a negative width (via Builder) will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeNegWidth() {
    this.builder.addKeyframe("NULL", 1, 0, 0, -1, 0, 0, 0, 0);
  }

  /**
   * Tests that adding a keyframe with a negative height (via Builder) will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeNegHeight() {
    this.builder.addKeyframe("NULL", 1, 0, 0, 0, -1, 0, 0, 0);
  }

  /**
   * Tests that adding a keyframe with an ID for a shape that doesn't exist will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeNoShape() {
    this.builder.addKeyframe("R", 1, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that adding a keyframe with an ID for a shape that doesn't exist will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeNoShape2() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("NULL", 1, 0, 0, 0, 0, 0, 0, 0);
  }

  /**
   * Tests that adding a keyframe twice for the same shape will throw an error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildAddKeyframeTwice() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("R", 1, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("R", 1, 0, 0, 0, 0, 0, 0, 1);
  }

  /**
   * Tests that adding a keyframe works properly when the given keyframe is valid.
   */
  @Test
  public void testAddKeyframeBuild() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("R", 10, 0, 0, 0, 0, 0, 0, 0);

    IModel model = this.builder.build();

    assertEquals("shape R rectangle\n"
            + "motion R 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle",
        model.printAnimations());
  }

  /**
   * Tests that adding a keyframe works properly when the given keyframe is valid.
   */
  @Test
  public void testAddKeyframeBuild2() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("C", 10, 0, 0, 0, 0, 0, 0, 0);

    IModel model = this.builder.build();

    assertEquals("shape R rectangle\n\n"
            + "shape C ellipse\n"
            + "motion C 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0\n\n"
            + "shape S rectangle",
        model.printAnimations());
  }

  /**
   * Tests that adding a keyframe works properly when the given keyframe is valid.
   */
  @Test
  public void testAddKeyframeBuild3() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("S", 10, 0, 0, 0, 0, 0, 0, 0);

    IModel model = this.builder.build();

    assertEquals("shape R rectangle\n\n"
            + "shape C ellipse\n\n"
            + "shape S rectangle\n"
            + "motion S 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0",
        model.printAnimations());
  }

  /**
   * Tests that adding keyframes works properly when the given keyframes is valid.
   */
  @Test
  public void testAddKeyframeBuild4() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("R", 10, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("C", 10, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("S", 10, 0, 0, 0, 0, 0, 0, 0);

    IModel model = this.builder.build();

    assertEquals("shape R rectangle\n"
            + "motion R 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0\n\n"
            + "shape C ellipse\n"
            + "motion C 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0",
        model.printAnimations());
  }

  /**
   * Tests that adding keyframes works properly when the given keyframes is valid. Includes
   * adding identical keyframes, which should not throw an error.
   */
  @Test
  public void testAddKeyframeBuild5() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("R", 10, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("C", 10, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("S", 10, 0, 0, 0, 0, 0, 0, 0);
    // Since these keyframes are identical to the ones above, it should not throw an error.
    this.builder.addKeyframe("R", 10, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("C", 10, 0, 0, 0, 0, 0, 0, 0);
    this.builder.addKeyframe("S", 10, 0, 0, 0, 0, 0, 0, 0);

    IModel model = this.builder.build();

    assertEquals("shape R rectangle\n"
            + "motion R 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0\n\n"
            + "shape C ellipse\n"
            + "motion C 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0\n\n"
            + "shape S rectangle\n"
            + "motion S 10 0 0 0 0 0 0 0\t10 0 0 0 0 0 0 0",
        model.printAnimations());
  }

  /**
   * Tests that adding keyframes works properly when the given keyframes is valid.
   */
  @Test
  public void testAddKeyframeBuild6() {
    this.builder.declareShape("R", "rectangle");
    this.builder.declareShape("C", "ellipse");
    this.builder.declareShape("S", "rectangle");

    this.builder.addKeyframe("R", 1, 30, 40, 10, 20, 0, 255, 255);
    this.builder.addKeyframe("R", 10, 30, 40, 40, 60, 0, 255, 255);
    this.builder.addKeyframe("R", 15, 45, 60, 40, 60, 0, 255, 255);
    this.builder.addKeyframe("R", 30, 50, 60, 80, 120, 0, 255, 0);

    this.builder.addKeyframe("C", 1, 30, 40, 10, 20, 0, 255, 255);
    this.builder.addKeyframe("C", 10, 30, 40, 10, 20, 255, 200, 0);
    this.builder.addKeyframe("C", 15, 30, 40, 0, 0, 255, 200, 0);
    this.builder.addKeyframe("C", 30, 45, 60, 0, 0, 255, 200, 0);

    this.builder.addKeyframe("S", 10, 30, 40, 10, 10, 255, 0, 0);
    this.builder.addKeyframe("S", 25, 15, 20, 10, 20, 255, 0, 0);
    this.builder.addKeyframe("S", 35, 15, 20, 6, 7, 255, 0, 0);
    this.builder.addKeyframe("S", 45, 15, 20, 6, 7, 255, 255, 0);
    
    IModel model = this.builder.build();
    
    assertEquals(
        "shape R rectangle\n" +
            "motion R 1 30 40 10 20 0 255 255\t10 30 40 40 60 0 255 255\n" +
            "motion R 10 30 40 40 60 0 255 255\t15 45 60 40 60 0 255 255\n" +
            "motion R 15 45 60 40 60 0 255 255\t30 50 60 80 120 0 255 0\n" +
            "\n" +
            "shape C ellipse\n" +
            "motion C 1 30 40 10 20 0 255 255\t10 30 40 10 20 255 200 0\n" +
            "motion C 10 30 40 10 20 255 200 0\t15 30 40 0 0 255 200 0\n" +
            "motion C 15 30 40 0 0 255 200 0\t30 45 60 0 0 255 200 0\n" +
            "\n" +
            "shape S rectangle\n" +
            "motion S 10 30 40 10 10 255 0 0\t25 15 20 10 20 255 0 0\n" +
            "motion S 25 15 20 10 20 255 0 0\t35 15 20 6 7 255 0 0\n" +
            "motion S 35 15 20 6 7 255 0 0\t45 15 20 6 7 255 255 0",
        model.printAnimations());
  }
  
}
