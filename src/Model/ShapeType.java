package Model;

/**
 *
 */
public enum ShapeType {
  RECTANGLE, ELLIPSE;

  public static String typeToString(ShapeType type) {
    switch (type) {
      case RECTANGLE:
        return "rectangle";
      case ELLIPSE:
        return "ellipse";
      default:
        throw new IllegalArgumentException("Given ShapeType is invalid");
    }
  }
}
