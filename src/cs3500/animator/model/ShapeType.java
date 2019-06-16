package cs3500.animator.model;

/**
 * Represents the possible shapes this animator can draw.
 */
public enum ShapeType {
  RECTANGLE, ELLIPSE;

  /**
   * Returns the string representing what kind of shape the enum represents. (e.g. RECTANGLE equates
   * to "rectangle.")
   *
   * @param type the type of shape
   * @return the string name for it
   * @throws IllegalArgumentException is the given ShapeType is null.
   */
  public static String typeToString(ShapeType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Given ShapeType for typeToString cannot be null");
    }

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

  /**
   * Converts the given String to a ShapeType. "rectangle" corresponds to ShapeType.RECTANGLE, and
   * "ellipse" corresponds to ShapeType.ELLIPSE.
   *
   * @param name is the String to be converted into a ShapeType.
   * @return the ShapeType that the given String corresponds to.
   * @throws IllegalArgumentException if the given String is null, or if is not one of "rectangle"
   *                                  or "ellipse"
   */
  public static ShapeType stringToType(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Given name for stringToType cannot be null");
    }

    switch (name) {
      case "rectangle":
        return ShapeType.RECTANGLE;
      case "ellipse":
        return ShapeType.ELLIPSE;
      default:
        throw new IllegalArgumentException("Given name for shape type, " + name
                + ", is not supported.");
    }
  }
}
