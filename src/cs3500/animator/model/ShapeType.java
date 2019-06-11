package cs3500.animator.model;

/**
 * Represents the possible shapes this animator can draw.
 */
public enum ShapeType {
  RECTANGLE, ELLIPSE;

  /**
   * Returns the string representing what kind of shape the enum represents. (ie RECTANGLE
   * equates to "rectangle.")
   *
   * @param type the type of shape
   * @return the string name for it
   */
  public static String typeToString(ShapeType type) {
    switch (type) {
      case RECTANGLE:
        return "rectangle";
      case ELLIPSE:
        return "ellipse";
      default:
        // will never reach this default case
        return "";
    }
  }

  public static ShapeType stringToType(String name) {
    switch (name) {
      case "rectangle":
        return ShapeType.RECTANGLE;
      case "ellipse":
        return ShapeType.ELLIPSE;
      default:
        throw new IllegalArgumentException("Given name for shape type, " + name + ", is not "
            + "supported.");
    }
  }
}
