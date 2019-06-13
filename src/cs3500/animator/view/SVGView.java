package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;


//TODO: Comment about no loopback - add null check for shapetype methods
public class SVGView extends ATextualView {
  private int tickRate;

  public SVGView(Appendable ap, int tickRate) {
    super(ap);
    this.tickRate = tickRate;
  }


  @Override
  public void toOutput(int x, int y, int width, int height) throws IllegalStateException,
          UnsupportedOperationException {
    String xCoordinate = null;
    String yCoordinate = null;
    String widthAttribute = null;
    String heightAttribute = null;

    this.attemptAppend("<svg width=\"" + width + "\" height=\"" + height
            + "\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n");

    for (IReadOnlyShape shape : this.shapes) {
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


      String shapeName = this.convertTypeToSVG(shape.getType());
      StringBuilder shapeHeading = new StringBuilder();
      shapeHeading.append("<").append(shapeName).append(" id=\"").append(shape.getID()).append("\" ")
              .append(xCoordinate).append("=\"").append(shape.getX()).append("\" ")
              .append(yCoordinate).append("=\"").append(shape.getY()).append("\" ")
              .append(widthAttribute).append("=\"").append(shape.getWidth()).append("\" ")
              .append(heightAttribute).append("=\"").append(shape.getHeight()).append("\" ")
              .append("fill=\"rgb(").append(shape.getColor().getRed()).append(",").append(shape.getColor().getGreen())
              .append(",").append(shape.getColor().getBlue()).append(")\" ").append("visibility" +
              "=\"visible\" >\n");
      this.attemptAppend(shapeHeading.toString());

      this.printShapeMotionsSVG(shape, xCoordinate, yCoordinate, widthAttribute, heightAttribute);
      this.attemptAppend("</" + shapeName + ">\n\n");
    }

    this.attemptAppend("</svg>");
  }

  private void printShapeMotionsSVG(IReadOnlyShape shape, String xCoordinate, String yCoordinate, String width,
                                    String height) {

    for (IMotion motion : shape.getMotions()) {
      String startTime = Integer.toString(this.tickToMS(motion.getStartTick()));
      String duration =
              Integer.toString(this.tickToMS(motion.getEndTick() - motion.getStartTick()));
      this.svgAnimationText(xCoordinate, startTime,
              duration, Double.toString(motion.getStartX()), Double.toString(motion.getEndX()));
      this.svgAnimationText(yCoordinate, startTime,
              duration, Double.toString(motion.getStartY()), Double.toString(motion.getEndY()));
      this.svgAnimationText(width, startTime,
              duration, Double.toString(motion.getStartWidth()), Double.toString(motion.getEndWidth()));
      this.svgAnimationText(height, startTime,
              duration, Double.toString(motion.getStartHeight()), Double.toString(motion.getEndHeight()));
      StringBuilder colorStart = new StringBuilder();
      colorStart.append("rgb(").append(Integer.toString(motion.getStartColor().getRed())).append(
              ",").append(Integer.toString(motion.getStartColor().getGreen())).append(
              ",").append(Integer.toString(motion.getStartColor().getBlue())).append(")");
      StringBuilder colorEnd = new StringBuilder();
      colorEnd.append("rgb(").append(Integer.toString(motion.getEndColor().getRed())).append(
              ",").append(Integer.toString(motion.getEndColor().getGreen())).append(
              ",").append(Integer.toString(motion.getEndColor().getBlue())).append(")");
      this.svgAnimationText("fill", startTime,
              duration, colorStart.toString(), colorEnd.toString());
      this.attemptAppend("\n");
    }
  }


  private void svgAnimationText(String attributeName, String startTime, String duration,
                                String fromVal, String toVal) {
    StringBuilder animationText = new StringBuilder();
    animationText.append("<animate attributeType=\"xml\" begin=\"").append(startTime).append("\" ")
            .append("dur=\"").append(duration).append("\" ").append("attributeName=\"").append(attributeName).append("\" from" +
            "=\"")
            .append(fromVal).append("\" ").append("to=\"").append(toVal).append("\" fill=\"freeze" +
            "\" />\n");
    this.attemptAppend(animationText.toString());
  }

  private int tickToMS(int tick) {
    return (int) (((double) tick / tickRate) * 1000);
  }

  private String convertTypeToSVG(ShapeType type) {
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
