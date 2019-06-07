import static org.junit.Assert.*;

import model.IModelShape;
import model.IModelShapeImpl;
import model.ShapeType;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class IModelShapeImplTest {

  @Before
  public void setUp() throws Exception {
    IModelShape rect1 = new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, Color.RED);
    IModelShape rect2 = new IModelShapeImpl("R2", ShapeType.RECTANGLE, 5, 15, 30, 30, Color.BLUE);

    IModelShape ellipse1 = new IModelShapeImpl("E1", ShapeType.ELLIPSE, 12, 12, 10, 12, Color.ORANGE);
    IModelShape ellupse2 = new IModelShapeImpl("E2", ShapeType.ELLIPSE, 3, 6, 20, 40, Color.GREEN);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} id
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullID() {
    new IModelShapeImpl(null, ShapeType.RECTANGLE, 10, 10, 0, 0, Color.BLUE);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} ShapeType
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullType() {
    new IModelShapeImpl("R1", null, 10, 10, 0, 0, Color.pink);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} Color
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullColor() {
    new IModelShapeImpl("R1", ShapeType.RECTANGLE, 10, 10, 0, 0, null);
  }

  /**
   * Tests for an exception when you try to create an IModelShapeImpl with a {@code null} id,
   * ShapeType, and Color
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeExceptionNullAll() {
    new IModelShapeImpl(null, null, 10, 10, 0, 0, null);
  }

  @Test
  public void testTypeToStringRect() {
    assertEquals("rectangle", ShapeType.typeToString(ShapeType.RECTANGLE));
  }

  @Test
  public void testTypeToStringEllipse() {
    assertEquals("ellipse", ShapeType.typeToString(ShapeType.ELLIPSE));
  }
}