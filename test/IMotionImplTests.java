import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.IMotionImpl;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for methods of IMotionImpl.
 */
public class IMotionImplTests {

  private IMotion motion1 = new IMotionImpl(1, 20, 30, 15, 15,
      Color.BLACK, 10, 20, 30, 15, 15, Color.RED);
  private IMotion motion2 = new IMotionImpl(20, 5.5, 3.7, -20.2,
      100.5, Color.BLUE, 50, 20.7, 30.3, 100.1,
      -5.1, Color.GREEN);

  /**
   * Tests that passing in a null start color throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNullException1() {
    new IMotionImpl(1, 20, 30, 15, 15, null, 10, 20, 30, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in a null end color throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNullException2() {
    new IMotionImpl(1, 20, 30, 15, 15, Color.GREEN, 10, 20, 30, 15, 15, null);
  }

  /**
   * Tests that passing in a negative start tick throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNegativeTickException1() {
    new IMotionImpl(-10, 20, 30, 15, 15, Color.BLACK, 10, 20, 30, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in a negative end tick throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNegativeTickException2() {
    new IMotionImpl(1, 20, 30, 15, 15, Color.BLACK, -2, 20, 30, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in a negative start width throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNegativeSizeException1() {
    new IMotionImpl(1, -2, 30, 15, 15, Color.BLACK, 2, 20, 30, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in a negative start height throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNegativeSizeException2() {
    new IMotionImpl(1, 20, -4, 15, 15, Color.BLACK, 2, 20, 30, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in a negative end width throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNegativeSizeException3() {
    new IMotionImpl(1, 20, 40, 15, 15, Color.BLACK, 2, -2, 30, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in a negative end height throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorNegativeSizeException4() {
    new IMotionImpl(1, 20, 40, 15, 15, Color.BLACK, 2, 20, -3, 15, 15, Color.RED);
  }

  /**
   * Tests that passing in an earlier end tick throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void motionConstructorBadEndTick() {
    new IMotionImpl(40, 20, 40, 15, 15, Color.BLACK, 39, 20, 20, 15, 15, Color.RED);
  }

  /**
   * Tests the Motion implementation's constructor.
   */
  @Test
  public void motionConstructorTest() {
    assertEquals("1 15 15 20 30 0 0 0\t10 15 15 20 30 255 0 0",
        this.motion1.printMotion());
  }

  /**
   * Tests the Motion implementation's constructor.
   */
  @Test
  public void motionConstructorTest2() {
    assertEquals("20 -20 100 5 3 0 0 255\t50 100 -5 20 30 0 255 0",
        this.motion2.printMotion());
  }

  /**
   * Tests printMotion.
   */
  @Test
  public void testPrintMotion() {
    assertEquals("1 15 15 20 30 0 0 0\t10 15 15 20 30 255 0 0",
        this.motion1.printMotion());
  }

  /**
   * Tests printMotion.
   */
  @Test
  public void testPrintMotion2() {
    assertEquals("20 -20 100 5 3 0 0 255\t50 100 -5 20 30 0 255 0",
        this.motion2.printMotion());
  }

  /**
   * Tests getStartTick.
   */
  @Test
  public void testGetStartTick1() {
    assertEquals(1, this.motion1.getStartTick());
  }

  /**
   * Tests getStartTick.
   */
  @Test
  public void testGetStartTick2() {
    assertEquals(20, this.motion2.getStartTick());
  }

  /**
   * Tests getStartWidth.
   */
  @Test
  public void testGetStartWidth() {
    assertEquals(20.0, this.motion1.getStartWidth());
  }

  /**
   * Tests getStartWidth.
   */
  @Test
  public void testGetStartWidth2() {
    assertEquals(5.5, this.motion2.getStartWidth());
  }

  /**
   * Tests getStartHeight.
   */
  @Test
  public void testGetStartHeight1() {
    assertEquals(30.0, this.motion1.getStartHeight());
  }

  /**
   * Tests getStartHeight.
   */
  @Test
  public void testGetStartHeight2() {
    assertEquals(3.7, this.motion2.getStartHeight());
  }

  /**
   * Tests getStartX.
   */
  @Test
  public void testGetStartX1() {
    assertEquals(15.0, this.motion1.getStartX());
  }

  /**
   * Tests getStartX.
   */
  @Test
  public void testGetStartX2() {
    assertEquals(-20.2, this.motion2.getStartX());
  }

  /**
   * Tests getStartY.
   */
  @Test
  public void testGetStartY1() {
    assertEquals(15.0, this.motion1.getStartY());
  }

  /**
   * Tests getStartY.
   */
  @Test
  public void testGetStartY2() {
    assertEquals(100.5, this.motion2.getStartY());
  }

  /**
   * Tests getStartColor.
   */
  @Test
  public void testGetStartColor1() {
    assertEquals(Color.BLACK, this.motion1.getStartColor());
  }

  /**
   * Tests getStartColor.
   */
  @Test
  public void testGetStartColor2() {
    assertEquals(Color.BLUE, this.motion2.getStartColor());
  }

  /**
   * Tests getEndTick.
   */
  @Test
  public void testGetEndTick() {
    assertEquals(10, this.motion1.getEndTick());
  }

  /**
   * Tests getEndTick.
   */
  @Test
  public void testGetEndTick2() {
    assertEquals(50, this.motion2.getEndTick());
  }

  /**
   * Tests getEndWidth.
   */
  @Test
  public void testGetEndWidth() {
    assertEquals(20.0, this.motion1.getEndWidth());
  }

  /**
   * Tests getEndWidth.
   */
  @Test
  public void testGetEndWidth2() {
    assertEquals(20.7, this.motion2.getEndWidth());
  }

  /**
   * Tests getEndHeight.
   */
  @Test
  public void testGetEndHeight1() {
    assertEquals(30.0, this.motion1.getEndHeight());
  }

  /**
   * Tests getEndHeight.
   */
  @Test
  public void testGetEndHeight2() {
    assertEquals(30.3, this.motion2.getEndHeight());
  }

  /**
   * Tests getEndX.
   */
  @Test
  public void testGetEndX1() {
    assertEquals(15.0, this.motion1.getEndX());
  }

  /**
   * Tests getEndX.
   */
  @Test
  public void testGetEndX2() {
    assertEquals(100.1, this.motion2.getEndX());
  }

  /**
   * Tests getEndY.
   */
  @Test
  public void testGetEndY1() {
    assertEquals(15.0, this.motion1.getEndY());
  }

  /**
   * Tests getEndY.
   */
  @Test
  public void testGetEndY2() {
    assertEquals(-5.1, this.motion2.getEndY());
  }

  /**
   * Tests getEndColor.
   */
  @Test
  public void testGetEndColor1() {
    assertEquals(Color.RED, this.motion1.getEndColor());
  }

  /**
   * Tests getEndColor.
   */
  @Test
  public void testGetEndColor2() {
    assertEquals(Color.GREEN, this.motion2.getEndColor());
  }

}
