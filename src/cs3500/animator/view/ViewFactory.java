package cs3500.animator.view;

/**
 * Represents a factory class that can produce views. It contains a single static method that
 * determines what view should be created based on a given string.
 */
public class ViewFactory {

  /**
   * Given a type of view to construct, returns an instance of the appropriate type. If the given
   * string is invalid, throws an exception.
   *
   * @param type the type of view to construct
   * @return an instance of the appropriate view
   * @throws IllegalArgumentException if the input is null or an unsupported view type
   */
  public static IView makeView(String type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Type of view cannot be null.");
    }
    IView res;
    switch (type) {
      case "text":
        res = new TextView();
        break;
      case "svg":
        res = new SVGView();
        break;
      case "visual":
        res = new VisualView();
        break;
      case "edit":
        res = new EditorView();
        break;
      default:
        throw new IllegalArgumentException("Given view type is not supported");
    }
    return res;
  }
}
