package cs3500.animator;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.TextControllerImpl;
import cs3500.animator.controller.TimerControllerImpl;
import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Excellence {


  public static void main(String[] args) {

//    Readable in = null; // TODO: Delete this later
//    String view = null; // TODO: Delete this later
//    // Default output is System.out
//    Appendable out = System.out;
//    // Default speed is 1 tick/s
//    int speed = 1;
//
//    for (int i = 0; i < args.length; i++) {
//      try {
//        switch (args[i]) {
//          // TODO: Check for repeats
//          case "-in":
//            System.out.println(args[i + 1]);
//            in = new FileReader(args[i + 1]);
//            System.out.println(in);
//            i++;
//            break;
//          case "-view":
//            view = args[i + 1];
//            i++;
//            break;
//          case "-out":
//            out = new FileWriter(args[i + 1]);
//            i++;
//            break;
//          case "-speed":
//            speed = Integer.parseInt(args[i + 1]);
//            i++;
//            break;
//          default:
//            // TODO: Draw error message
//        }
//      } catch (Exception e) {
//        // TODO: Draw error message
//      }
//    }
//
//    AnimationBuilder<IModelImpl> builder =  IModelImpl.builder();
//
//    System.out.println(in);
//    IModel model = null;
//    try {
//      model = AnimationReader.parseFile(new FileReader("big-bang-big-crunch.txt"), builder);
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }
//    IController controller = null;
//    IView newView;
//
//    switch(view) {
//      case "text":
//        newView = new TextView(out);
//        controller = new TextControllerImpl(newView, model, speed);
//        break;
//      case "svg":
//        newView = new SVGView(out, speed);
//        controller = new TextControllerImpl(newView, model, speed);
//        break;
//      case "visual":
//        newView = new VisualView();
//        controller = new TimerControllerImpl(newView, model, speed);
//        break;
//      default:
//        // TODO: Draw error message
//    }


    IModel model = null;
    try {
      AnimationBuilder<IModelImpl> builder =  IModelImpl.builder();
      model = AnimationReader.parseFile(new BufferedReader(new FileReader(
          "D:\\Documents\\COLLEGE\\Summer 1 2019\\CS 3500 - Object Oriented "
              + "Design\\ood-animator-project\\src\\cs3500\\big-bang-big-crunch.txt")), builder);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    IView newView = new VisualView(model.getWidth(), model.getHeight(), model.getMaxX(), model.getMaxY());
    IController controller = new TimerControllerImpl(newView, model, 20);
    controller.run();

//D:/Documents/COLLEGE/Summer 1 2019/CS 3500 - Object Oriented "
//              + "Design/ood-animator-project/src/cs3500/
  }
}
