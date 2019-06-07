import org.junit.Test;

import java.awt.*;

import model.IMotion;
import model.IMotionImpl;

/**
 * Tests for methods of IMotionImpl.
 */
public class IMotionImplTests {
  IMotion motion1 = new IMotionImpl(1, 20, 30, 15, 15, Color.BLACK, 10, 20, 30, 15, 15, Color.RED);
  IMotion motion2 = new IMotionImpl(20, 5, 3, -20, 100, Color.BLUE, 50, 20, 30, 100, -5,
          Color.GREEN);

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



}
