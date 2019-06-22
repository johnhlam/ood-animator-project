import cs3500.animator.model.IKeyframe;
import cs3500.animator.model.IKeyframeImpl;
import org.junit.Test;

import java.awt.Color;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for methods of IKeyframeImpl.
 */
public class IKeyframeImplTests {

  private IKeyframe keyframe1 = new IKeyframeImpl(1, 20, 30, 15, 15, Color.BLACK);
  private IKeyframe keyframe2 = new IKeyframeImpl(20, 5.5, 3.7, -20.2, 100.5, Color.BLUE);

  /**
   * Tests that passing in a null color throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void keyframeConstructorNullException1() {
    new IKeyframeImpl(1, 20, 30, 15, 15, null);
  }

  /**
   * Tests that passing in a negative tick throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void keyframeConstructorNegativeTickException1() {
    new IKeyframeImpl(-10, 20, 30, 15, 15, Color.BLACK);
  }


  /**
   * Tests that passing in a negative width throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void keyframeConstructorNegativeSizeException1() {
    new IKeyframeImpl(1, -2, 30, 15, 15, Color.BLACK);
  }

  /**
   * Tests that passing in a negative height throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void keyframeConstructorNegativeSizeException2() {
    new IKeyframeImpl(1, 20, -4, 15, 15, Color.BLACK);
  }

  /**
   * Tests the Keyframe implementation's constructor.
   */
  @Test
  public void keyframeConstructorTest() {
    assertEquals("1 15 15 20 30 0 0 0",
        this.keyframe1.printKeyframe());
  }

  /**
   * Tests the Keyframe implementation's constructor.
   */
  @Test
  public void keyframeConstructorTest2() {
    assertEquals("20 -20 100 5 3 0 0 255",
        this.keyframe2.printKeyframe());
  }

  /**
   * Tests printKeyframe.
   */
  @Test
  public void testPrintKeyframe() {
    assertEquals("1 15 15 20 30 0 0 0",
        this.keyframe1.printKeyframe());
  }

  /**
   * Tests printKeyframe.
   */
  @Test
  public void testPrintKeyframe2() {
    assertEquals("20 -20 100 5 3 0 0 255",
        this.keyframe2.printKeyframe());
  }

  /**
   * Tests getStartTick.
   */
  @Test
  public void testGetStartTick1() {
    assertEquals(1, this.keyframe1.getTick());
  }

  /**
   * Tests getStartTick.
   */
  @Test
  public void testGetStartTick2() {
    assertEquals(20, this.keyframe2.getTick());
  }

  /**
   * Tests getStartWidth.
   */
  @Test
  public void testGetStartWidth() {
    assertEquals(20.0, this.keyframe1.getWidth());
  }

  /**
   * Tests getStartWidth.
   */
  @Test
  public void testGetStartWidth2() {
    assertEquals(5.5, this.keyframe2.getWidth());
  }

  /**
   * Tests getStartHeight.
   */
  @Test
  public void testGetStartHeight1() {
    assertEquals(30.0, this.keyframe1.getHeight());
  }

  /**
   * Tests getStartHeight.
   */
  @Test
  public void testGetStartHeight2() {
    assertEquals(3.7, this.keyframe2.getHeight());
  }

  /**
   * Tests getStartX.
   */
  @Test
  public void testGetStartX1() {
    assertEquals(15.0, this.keyframe1.getX());
  }

  /**
   * Tests getStartX.
   */
  @Test
  public void testGetStartX2() {
    assertEquals(-20.2, this.keyframe2.getX());
  }

  /**
   * Tests getStartY.
   */
  @Test
  public void testGetStartY1() {
    assertEquals(15.0, this.keyframe1.getY());
  }

  /**
   * Tests getStartY.
   */
  @Test
  public void testGetStartY2() {
    assertEquals(100.5, this.keyframe2.getY());
  }

  /**
   * Tests getStartColor.
   */
  @Test
  public void testGetStartColor1() {
    assertEquals(Color.BLACK, this.keyframe1.getColor());
  }

  /**
   * Tests getStartColor.
   */
  @Test
  public void testGetStartColor2() {
    assertEquals(Color.BLUE, this.keyframe2.getColor());
  }

  /**
   * Tests getTick.
   */
  @Test
  public void testGetTick() {
    assertEquals(1, this.keyframe1.getTick());
  }

  /**
   * Tests getTick.
   */
  @Test
  public void testGetTick2() {
    assertEquals(20, this.keyframe2.getTick());
  }

  /**
   * Tests getWidth.
   */
  @Test
  public void testGetWidth() {
    assertEquals(20.0, this.keyframe1.getWidth());
  }

  /**
   * Tests getWidth.
   */
  @Test
  public void testGetWidth2() {
    assertEquals(5.5, this.keyframe2.getWidth());
  }

  /**
   * Tests getHeight.
   */
  @Test
  public void testGetHeight1() {
    assertEquals(30.0, this.keyframe1.getHeight());
  }

  /**
   * Tests getHeight.
   */
  @Test
  public void testGetHeight2() {
    assertEquals(3.7, this.keyframe2.getHeight());
  }

  /**
   * Tests getX.
   */
  @Test
  public void testGetX1() {
    assertEquals(15.0, this.keyframe1.getX());
  }

  /**
   * Tests getX.
   */
  @Test
  public void testGetX2() {
    assertEquals(-20.2, this.keyframe2.getX());
  }

  /**
   * Tests getY.
   */
  @Test
  public void testGetY1() {
    assertEquals(15.0, this.keyframe1.getY());
  }

  /**
   * Tests getY.
   */
  @Test
  public void testGetY2() {
    assertEquals(100.5, this.keyframe2.getY());
  }

  /**
   * Tests getColor.
   */
  @Test
  public void testGetColor1() {
    assertEquals(Color.BLACK, this.keyframe1.getColor());
  }

  /**
   * Tests getColor.
   */
  @Test
  public void testGetColor2() {
    assertEquals(Color.BLUE, this.keyframe2.getColor());
  }

}
