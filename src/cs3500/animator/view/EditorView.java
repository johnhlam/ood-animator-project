package cs3500.animator.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IReadOnlyShape;

public class EditorView extends JFrame implements IView {
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

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    stop = new JButton("Stop");
    stop.setActionCommand("stop");
    play = new JButton("Play");
    play.setActionCommand("play");
    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    loopback = new JCheckBox("Loopback Enabled:");
    loopback.setSelected(false);
    loopback.setActionCommand("loop");
    faster = new JButton("Speed up");
    faster.setActionCommand("fast");
    slower = new JButton("Slow down");
    slower.setActionCommand("slow");
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

  @Override
  public void render(List<IReadOnlyShape> shapes) throws UnsupportedOperationException {

  }
}
