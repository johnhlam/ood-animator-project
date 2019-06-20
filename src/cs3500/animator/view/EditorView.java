package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IKeyframe;
import cs3500.animator.model.IReadOnlyKeyframe;
import cs3500.animator.model.IReadOnlyShape;

public class EditorView extends JFrame implements IView, ActionListener {
  Features features;
  private List<IReadOnlyShape> shapesToRender = new ArrayList<>();

  private final AnimationPanel animationPanel;
  private final JPanel videoPanel;
  private JPanel buttonPanel;

  private JPanel shapePanel;
  private DefaultListModel shapeListModel = new DefaultListModel();
  private JList<String> shapeList;
  private JTextField shapeIDField;
  private JRadioButton rectangleRadio;
  private JRadioButton ellipseRadio;
  ButtonGroup shapeSelections;

  private JPanel keyframePanel;
  private DefaultListModel keyframeListModel = new DefaultListModel();
  private JList<String> keyframeList;
  private JTextField tick;
  private JTextField xCoor;
  private JTextField yCoor;
  private JTextField height;
  private JTextField width;
  private JTextField rVal;
  private JTextField gVal;
  private JTextField bVal;

  private JButton stop;
  private JButton play;
  private JButton restart;
  private JCheckBox loopback;
  private JButton faster;
  private JButton slower;




  public EditorView() {
    animationPanel = new AnimationPanel();
    videoPanel = new JPanel();
    videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.PAGE_AXIS));

    JScrollPane scrolledAnimation = new JScrollPane(animationPanel);

    this.configureButtons();

    scrolledAnimation.setPreferredSize(new Dimension(buttonPanel.getWidth(), 500));

    videoPanel.add(scrolledAnimation);
    videoPanel.add(buttonPanel);

    this.configureShapePanel();
    this.configureKeyframePanel();

    this.setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(shapePanel, BorderLayout.WEST);
    this.add(videoPanel, BorderLayout.CENTER);
    this.add(keyframePanel, BorderLayout.EAST);

    this.setVisible(true);

    this.pack();
  }

  //TODO: Add deselect option

  private void configureKeyframePanel() {
    keyframePanel = new JPanel();
    keyframeList = new JList<>(this.keyframeListModel);
    keyframeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    keyframeList.setFixedCellWidth(shapePanel.getWidth());
    keyframeList.addListSelectionListener((e -> {
      IReadOnlyShape curShape = this.shapesToRender.get(shapeList.getSelectedIndex());
      IReadOnlyKeyframe curFrame = curShape.getKeyframes().get(keyframeList.getSelectedIndex());
      tick.setText(Integer.toString(curFrame.getTick()));
      xCoor.setText(Integer.toString((int)curFrame.getX()));
      yCoor.setText(Integer.toString((int)curFrame.getY()));
      width.setText(Integer.toString((int)curFrame.getWidth()));
      height.setText(Integer.toString((int)curFrame.getHeight()));
      rVal.setText(Integer.toString(curFrame.getColor().getRed()));
      gVal.setText(Integer.toString(curFrame.getColor().getGreen()));
      bVal.setText(Integer.toString(curFrame.getColor().getBlue()));
    }));
    JScrollPane scrolledKeyframeList = new JScrollPane(keyframeList);

    scrolledKeyframeList.setPreferredSize(new Dimension(keyframePanel.getWidth(), 250));

    JLabel keyframeListHeader = new JLabel("Keyframes (in order)");

    keyframePanel.setLayout(new BoxLayout(keyframePanel, BoxLayout.Y_AXIS));
    keyframePanel.setPreferredSize(new Dimension(300, 500));
    keyframePanel.add(keyframeListHeader);
    keyframePanel.add(scrolledKeyframeList);

    // construct panel for adding/removing/editing keyframes

    JPanel keyframeInteraction = new JPanel();
    keyframeInteraction.setPreferredSize(new Dimension(200, 250));
    keyframeInteraction.setLayout(new BoxLayout(keyframeInteraction, BoxLayout.PAGE_AXIS));

    JPanel keyframeInputs = new JPanel(new FlowLayout());
    JLabel tickLabel = new JLabel("Tick:");
    JLabel xLabel = new JLabel("X Coordinate:");
    JLabel yLabel = new JLabel("Y Coordinate:");
    JLabel widthLabel = new JLabel("Width:");
    JLabel heightLabel = new JLabel("Height:");
    JLabel rLabel = new JLabel("R Value:");
    JLabel gLabel = new JLabel("G Value:");
    JLabel bLabel = new JLabel("B Value:");
    tick = new JTextField(4);
    xCoor = new JTextField(3);
    yCoor = new JTextField(3);
    width = new JTextField(3);
    height = new JTextField(3);
    rVal = new JTextField(3);
    gVal = new JTextField(3);
    bVal = new JTextField(3);

    keyframeInputs.add(tickLabel);
    keyframeInputs.add(tick);
    keyframeInputs.add(xLabel);
    keyframeInputs.add(xCoor);
    keyframeInputs.add(yLabel);
    keyframeInputs.add(yCoor);
    keyframeInputs.add(widthLabel);
    keyframeInputs.add(width);
    keyframeInputs.add(heightLabel);
    keyframeInputs.add(height);
    keyframeInputs.add(rLabel);
    keyframeInputs.add(rVal);
    keyframeInputs.add(gLabel);
    keyframeInputs.add(gVal);
    keyframeInputs.add(bLabel);
    keyframeInputs.add(bVal);

    keyframeInteraction.add(keyframeInputs);

    JPanel keyframeButtons = new JPanel(new FlowLayout());

    JButton add = new JButton("Add");
    add.setActionCommand("addKeyframe");
    add.addActionListener(this);
    keyframeButtons.add(add);
    JButton remove = new JButton("Remove");
    remove.addActionListener(this);
    remove.setActionCommand("removeKeyframe");
    keyframeButtons.add(remove);
    JButton modify = new JButton("Modify");
    remove.addActionListener(this);
    remove.setActionCommand("modifyKeyframe");
    keyframeButtons.add(modify);

    keyframeInteraction.add(keyframeButtons);

    keyframePanel.add(keyframeInteraction);


  }

  private void configureShapePanel() {
    // construct overall shape panel
    shapePanel = new JPanel();
    shapeList = new JList<>(this.shapeListModel);
    shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shapeList.addListSelectionListener((e -> {
      IReadOnlyShape shape = this.shapesToRender.get(shapeList.getSelectedIndex());
      keyframeListModel.clear();
      for (IReadOnlyKeyframe keyframe : shape.getKeyframes()) {
        keyframeListModel.addElement(keyframe.printKeyframe());
      }
    }));
    JScrollPane scrolledShapeList = new JScrollPane(shapeList);

    // add list of shapes

    JLabel shapeListHeader = new JLabel("Shapes");

    shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.Y_AXIS));
    shapePanel.setPreferredSize(new Dimension(200, 500));
    shapePanel.add(shapeListHeader);
    shapePanel.add(scrolledShapeList);

    shapeList.setFixedCellWidth(shapePanel.getWidth());
    scrolledShapeList.setPreferredSize(new Dimension(shapePanel.getWidth(), 250));

    // construct panel for adding/removing shapes

    JPanel shapeInteraction = new JPanel();
    shapeInteraction.setLayout(new BoxLayout(shapeInteraction, BoxLayout.PAGE_AXIS));

    JPanel shapeInputs = new JPanel(new FlowLayout());
    JLabel id = new JLabel("Shape ID:");
    shapeIDField = new JTextField(5);
    shapeSelections = new ButtonGroup();

    rectangleRadio = new JRadioButton("Rectangle");
    ellipseRadio = new JRadioButton("Ellipse");

    shapeSelections.add(rectangleRadio);
    shapeSelections.add(ellipseRadio);

    shapeInputs.add(rectangleRadio);
    shapeInputs.add(ellipseRadio);
    shapeInputs.add(id);
    shapeInputs.add(shapeIDField);

    shapeInteraction.add(shapeInputs);

    JPanel shapeButtons = new JPanel(new FlowLayout());

    JButton add = new JButton("Add");
    add.setActionCommand("addShape");
    add.addActionListener(this);
    shapeButtons.add(add);
    JButton remove = new JButton("Remove");
    remove.addActionListener(this);
    remove.setActionCommand("removeShape");
    shapeButtons.add(remove);

    shapeInteraction.add(shapeButtons);

    shapePanel.add(shapeInteraction);
  }

  private void configureButtons() {
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
  public void setShapes(List<IReadOnlyShape> shapes) {
    this.shapesToRender = shapes;
    this.shapeListModel.clear();
    for (int i = 0; i < this.shapesToRender.size(); i++) {
      this.shapeListModel.addElement(this.shapesToRender.get(i).getID());
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
