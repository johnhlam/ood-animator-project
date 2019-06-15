package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;

/**
 * SVGView is a class that extends the abstract class ATextualView. It provides a way to convert
 * a list of shapes into a textual representation, formatted based on the popular SVG file format
 * (See <a>href="https://www.w3.org/TR/SVG11/"</a> for details).
 * SVGView stores an integer tickRate along with the fields in ATextualView for reference when
 * converting ticks to milliseconds (as the SVG format requires).
 *
 * <p>The SVGView class currently does not support loopback, and animations will freeze on their end
 * states upon completion.</p>
 */
public class SVGView extends ATextualView {

  private int tickRate;

  /**
   * Constructs an instance of SVGView with the given Appendable and tickRate.
   *
   * @param ap       is the Appendable that the text output will be appended to.
   * @param tickRate is the tickRate that the animation runs at
   * @throws IllegalArgumentException if the given Appendable is null, or if the given tickRate
   *                                  is not positive
   */
  public SVGView(Appendable ap, int tickRate) throws IllegalArgumentException {
    super(ap);

    if (tickRate <= 0) {
      throw new IllegalArgumentException("Given tickRate to SVGView is negative.");
    }

    this.tickRate = tickRate;
  }

  /**
   * Turns this view into a textual representation and appends the textual output to this.ap.
   * The textual representation is formatted based on the SVG file format documentation.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will display.
   * @throws IllegalStateException if this.ap is unable to be appended to, or is unable to
   *                               transmit output.
   */
  @Override
  public void play(List<IReadOnlyShape> shapes) throws IllegalStateException {
    String xCoordinate = null;
    String yCoordinate = null;
    String widthAttribute = null;
    String heightAttribute = null;

    this.attemptAppend("<svg width=\"" + (width + x) + "\" height=\"" + (height + y)
        + "\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n");

    for (IReadOnlyShape shape : shapes) {

      // Checks what shape's ShapeType is and sets the attribute names (as String variables) to
      // their appropriate values.
      switch (shape.getType()) {
        case RECTANGLE:
          xCoordinate = "x";
          yCoordinate = "y";
          widthAttribute = "width";
          heightAttribute = "height";
          break;
        case ELLIPSE:
          xCoordinate = "cx";
          yCoordinate = "cy";
          widthAttribute = "rx";
          heightAttribute = "ry";
          break;
        default:
      }

      // Converts shape into a String formatted by SVG
      String shapeName = this.convertTypeToSVGName(shape.getType());
      StringBuilder shapeHeading = new StringBuilder();
      shapeHeading
          .append("<").append(shapeName).append(" id=\"").append(shape.getID()).append("\" ")
          .append(xCoordinate).append("=\"").append(shape.getX()).append("\" ")
          .append(yCoordinate).append("=\"").append(shape.getY()).append("\" ")
          .append(widthAttribute).append("=\"").append(shape.getWidth()).append("\" ")
          .append(heightAttribute).append("=\"").append(shape.getHeight()).append("\" ")
          .append("fill=\"rgb(").append(shape.getColor().getRed()).append(",")
          .append(shape.getColor().getGreen()).append(",").append(shape.getColor().getBlue())
          .append(")\" ").append("visibility=\"visible\" >\n");

      this.attemptAppend(shapeHeading.toString());

      // Converts shape's motions into 'animate' elements
      this.printShapeMotionsSVG(shape, xCoordinate, yCoordinate, widthAttribute, heightAttribute);
      this.attemptAppend("</" + shapeName + ">\n\n");
    }

    this.attemptAppend("</svg>");
  }

  /**
   * Convert's the given shape's motions into 'animate' elements and appends them on to this.ap.
   *
   * @param shape       is the shape whose motions are to be turned into 'animate' elements
   * @param xCoordinate is the attribute name of the x value of the shape
   * @param yCoordinate is the attribute name of the y value of the shape
   * @param width       is the attribute name of the width of the shape
   * @param height      is the attribute name of the height of the shape
   */
  private void printShapeMotionsSVG(IReadOnlyShape shape, String xCoordinate, String yCoordinate,
      String width, String height) {

    for (IMotion motion : shape.getMotions()) {
      String startTime = Integer.toString(this.tickToMS(motion.getStartTick()));
      String duration =
          Integer.toString(this.tickToMS(motion.getEndTick() - motion.getStartTick()));

      // Adds the 'animate' element for each attribute of the shape
      this.svgAnimationText(xCoordinate, startTime,
          duration, Double.toString(motion.getStartX()), Double.toString(motion.getEndX()));
      this.svgAnimationText(yCoordinate, startTime,
          duration, Double.toString(motion.getStartY()), Double.toString(motion.getEndY()));
      this.svgAnimationText(width, startTime,
          duration, Double.toString(motion.getStartWidth()), Double.toString(motion.getEndWidth()));
      this.svgAnimationText(height, startTime,
          duration, Double.toString(motion.getStartHeight()),
          Double.toString(motion.getEndHeight()));

      StringBuilder colorStart = new StringBuilder();
      colorStart.append("rgb(").append(motion.getStartColor().getRed()).append(
          ",").append(motion.getStartColor().getGreen()).append(
          ",").append(motion.getStartColor().getBlue()).append(")");

      StringBuilder colorEnd = new StringBuilder();
      colorEnd.append("rgb(").append(motion.getEndColor().getRed()).append(
          ",").append(motion.getEndColor().getGreen()).append(
          ",").append(motion.getEndColor().getBlue()).append(")");

      this.svgAnimationText("fill", startTime,
          duration, colorStart.toString(), colorEnd.toString());
      this.attemptAppend("\n");
    }
  }

  /**
   * Converts a series of Strings into an SVG animate element. It is formatted as the following:
   * <br>
   * &lt;animate attributeType="xml" begin="{@code startTime}" dur="{@code duration}" from="
   * {@code fromVal}" to="{@code toVal}" fill="freeze" /&gt;
   *
   * @param attributeName is the attribute name of the animation.
   * @param startTime     is the start time (in milliseconds) of the animation.
   * @param duration      is the duration (in milliseconds) of the animation.
   * @param fromVal       is the initial value of the attributeName to be animated (as in the value
   *                      of the attribute at startTime).
   * @param toVal         is the final value of the attributeName to be animated (as in the value of
   *                      the attribute at the end of the animation).
   */
  private void svgAnimationText(String attributeName, String startTime, String duration,
      String fromVal, String toVal) {
    StringBuilder animationText = new StringBuilder();
    animationText.append("<animate attributeType=\"xml\" begin=\"").append(startTime)
        .append("ms\" ")
        .append("dur=\"").append(duration).append("ms\" ")
        .append("attributeName=\"").append(attributeName).append("\" from=\"")
        .append(fromVal).append("\" ").append("to=\"").append(toVal).append("\" fill=\"freeze" +
        "\" />\n");
    this.attemptAppend(animationText.toString());
  }

  /**
   * Converts an integer tick value in to milliseconds using this.tickRate.
   *
   * @param tick is the tick value to be converted into milliseconds.
   * @return is the given tick value in milliseconds.
   */
  private int tickToMS(int tick) {
    return (int) (((double) tick / this.tickRate) * 1000);
  }

  /**
   * Converts a given ShapeType to its equivalent form in SVG (as a String).
   *
   * @param type is the ShapeType to be converted into a String.
   * @return the String containing the SVG equivalent of the given ShapeType.
   */
  private String convertTypeToSVGName(ShapeType type) {
    switch (type) {
      case RECTANGLE:
        return "rect";
      case ELLIPSE:
        return "ellipse";
      default:
        // will never reach this default case
        return "";
    }
  }

}
