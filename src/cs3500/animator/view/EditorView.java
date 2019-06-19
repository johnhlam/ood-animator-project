package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IReadOnlyShape;

public class EditorView extends JFrame implements IView, ActionListener {
  Features features;
  private final AnimationPanel animationPanel;
  private final JPanel videoPanel;
  private final JPanel buttonPanel;
  //private final JPanel shapePanel;
  //private final JPanel keyframePanel;
  private final JButton stop;
  private final JButton play;
  private final JButton restart;
  private final JCheckBox loopback;
  private final JButton faster;
  private final JButton slower;


  public EditorView() {
    animationPanel = new AnimationPanel();
    this.setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    videoPanel = new JPanel();
    videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.PAGE_AXIS));

    //TODO: Put these in helpers
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    stop = new JButton("Stop");
    stop.setActionCommand("stop");
    stop.addActionListener(this);
    play = new JButton("Play");
    play.setActionCommand("play");
    play.addActionListener(this);
    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    loopback = new JCheckBox("Loopback Enabled:");
    loopback.setSelected(false);
    loopback.setActionCommand("loop");
    loopback.addActionListener(this);
    faster = new JButton("Speed up");
    faster.setActionCommand("fast");
    faster.addActionListener(this);
    slower = new JButton("Slow down");
    slower.setActionCommand("slow");
    slower.addActionListener(this);
    buttonPanel.add(stop);
    buttonPanel.add(play);
    buttonPanel.add(restart);
    buttonPanel.add(faster);
    buttonPanel.add(slower);
    buttonPanel.add(loopback);

    JScrollPane scrolledAnimation = new JScrollPane(animationPanel);

    scrolledAnimation.setPreferredSize(new Dimension(buttonPanel.getWidth(), 500));

    videoPanel.add(scrolledAnimation);
    videoPanel.add(buttonPanel);
    this.add(videoPanel);
    this.setVisible(true);


    this.pack();
  }

  @Override
  public void setCanvas(int x, int y, int width, int height, int maxX, int maxY) throws IllegalArgumentException {
    animationPanel.setDrawPanelSize(width, height, maxX, maxY);
  }

  @Override
  public void toOutput(List<IReadOnlyShape> shapes, Appendable ap, int tickRate) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Editor view cannot output to text.");
  }

  /**
   * Renders the given list of shapes on the panel (the window) that the animation is being played
   * on. Details of how each shape is drawn is left to the panel. This method should be called on
   * every tick/frame of the animation.
   *
   * @param shapes is the List of IReadOnlyShapes that this IView will display.
   * @throws IllegalArgumentException if the given list of shapes is null, or the panel is null
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("Given shapes to setShapes is null");
    }

    this.animationPanel.draw(shapes);
  }

  @Override
  public void setFeatures(Features features) {
    if (features != null) {
      this.features = features;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "play":
        this.features.play();
        break;
      case "stop":
        this.features.stop();
        break;
      case "restart":
        this.features.restart();
        break;
      case "fast":
        this.features.faster();
        break;
      case "slow":
        this.features.slower();
        break;
      case "loop":
        this.features.toggleLoopback();
      default:
    }
  }
}
