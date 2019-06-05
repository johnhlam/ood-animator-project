import static org.junit.Assert.*;

import Model.IShape;
import Model.IShapeImpl;
import Model.ShapeType;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

public class IShapeImplTest {

  @Before
  public void setUp() throws Exception {
    IShape rect1 = new IShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    IShape rect2 = new IShapeImpl("R2", ShapeType.RECTANGLE, 5, 15, 30, 30, Color.BLUE);

    IShape ellipse1 = new IShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12, Color.ORANGE);
    IShape ellupse2 = new IShapeImpl("E2", ShapeType.ELLIPSE, 3, 6, 20, 40, Color.GREEN);
  }

  /**
   * Tests for an exception when you try to create an IShapeImpl with a {@code null} id
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullID() {
    new IShapeImpl(null, ShapeType.RECTANGLE, 10, 10, 0, 0, Color.ALICEBLUE);
  }

  /**
   * Tests for an exception when you try to create an IShapeImpl with a {@code null} ShapeType
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullType() {
    new IShapeImpl("R1", null, 10, 10, 0, 0, Color.FUCHSIA);
  }

  /**
   * Tests for an exception when you try to create an IShapeImpl with a {@code null} Color
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullColor() {
    new IShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, null);
  }

  /**
   * Tests for an exception when you try to create an IShapeImpl with a {@code null} id,
   * ShapeType, and Color
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullAll() {
    new IShapeImpl(null, null, 10, 10, 0, 0, null);
  }
}