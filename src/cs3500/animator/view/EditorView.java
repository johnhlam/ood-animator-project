package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IKeyframe;
import cs3500.animator.model.IReadOnlyShape;
import cs3500.animator.model.ShapeType;

/**
 * Represents an interactive view that displays an animation. The user can modify the animation
 * through adding and removing shapes, and adding/removing/modifying keyframes. The user can also
 * control how the animation can played with the click of buttons. The view acts as its own action
 * listener and transmits events to the controller, or its "features."
 */
public class EditorView extends JFrame implements IView, ActionListener {
  Features features;
  private List<IReadOnlyShape> shapesToRender = new ArrayList<>();

  // panel for the animation and its buttons
  private final AnimationPanel animationPanel;
  private JScrollPane scrollableAnimationPanel;
  private JPanel videoPanel;
  private JPanel buttonPanel;
  private JButton stop;
  private JButton play;
  private JButton restart;
  private JCheckBox loopback;
  private JButton faster;
  private JButton slower;

  // panel for the shape interactions
  private JPanel shapePanel;
  private DefaultListModel<String> shapeListModel = new DefaultListModel();
  private JList<String> shapeList;
  private JTextField shapeIDField;
  private JRadioButton rectangleRadio;
  private JRadioButton ellipseRadio;
  ButtonGroup shapeSelections;

  // panel for the keyframe interactions
  private JPanel keyframePanel;
  private DefaultListModel<String> keyframeListModel = new DefaultListModel();
  private JList<String> keyframeList;
  private JTextField tick;
  private JTextField xCoor;
  private JTextField yCoor;
  private JTextField height;
  private JTextField width;
  private JTextField rVal;
  private JTextField gVal;
  private JTextField bVal;

  public EditorView() {

    animationPanel = new AnimationPanel();
    scrollableAnimationPanel = new JScrollPane(animationPanel);

    this.configureButtons();
    scrollableAnimationPanel.setPreferredSize(new Dimension(buttonPanel.getWidth(), 500));

    this.configureVideoPanel();
    this.configureShapePanel();
    this.configureKeyframePanel();

    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(shapePanel, BorderLayout.WEST);
    this.add(videoPanel, BorderLayout.CENTER);
    this.add(keyframePanel, BorderLayout.EAST);

    // arbitrary minimum size so the animation can still be seen
    this.setMinimumSize(new Dimension(750, 400));
    this.setVisible(true);

    this.pack();
  }

  /**
   * Configures the JPanel that contains the animation panel and its associated buttons.
   */
  private void configureVideoPanel() {
    videoPanel = new JPanel();
    videoPanel.setLayout(new BorderLayout());
    videoPanel.add(scrollableAnimationPanel, BorderLayout.CENTER);
    videoPanel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Configures the JPanel that contains the all the keyframe interactions, including input text
   * fields, buttons, and the list of keyframes for a shape.
   */
  private void configureKeyframePanel() {
    keyframePanel = new JPanel();
    keyframeList = new JList<String>(this.keyframeListModel);
    keyframeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    keyframeList.setFixedCellWidth(shapePanel.getWidth());
    keyframeList.addListSelectionListener((e -> {
      if (keyframeList.getSelectedIndex() == -1 || this.shapeList.getSelectedIndex() == -1) {
        return;
      }
      IReadOnlyShape curShape = this.shapesToRender.get(shapeList.getSelectedIndex());
      IKeyframe curFrame = curShape.getKeyframes().get(keyframeList.getSelectedIndex());
      tick.setText(Integer.toString(curFrame.getTick()));
      xCoor.setText(Integer.toString((int) curFrame.getX()));
      yCoor.setText(Integer.toString((int) curFrame.getY()));
      width.setText(Integer.toString((int) curFrame.getWidth()));
      height.setText(Integer.toString((int) curFrame.getHeight()));
      rVal.setText(Integer.toString(curFrame.getColor().getRed()));
      gVal.setText(Integer.toString(curFrame.getColor().getGreen()));
      bVal.setText(Integer.toString(curFrame.getColor().getBlue()));
    }));
    JScrollPane scrolledKeyframeList = new JScrollPane(keyframeList);

    scrolledKeyframeList.setPreferredSize(new Dimension(keyframePanel.getWidth(), 250));

    JLabel keyframeListHeader = new JLabel("Keyframes (in order)");
    JLabel keyframeFormat = new JLabel("'Tick X Y Width Height R G B'");

    JPanel keyframeText = new JPanel(new BorderLayout());
    keyframeText.add(keyframeListHeader, BorderLayout.NORTH);
    keyframeText.add(keyframeFormat, BorderLayout.CENTER);

    keyframePanel.setLayout(new BorderLayout());
    keyframePanel.setPreferredSize(new Dimension(300, 550));
    keyframePanel.add(keyframeText, BorderLayout.NORTH);
    keyframePanel.add(scrolledKeyframeList, BorderLayout.CENTER);

    // construct panel for adding/removing/editing keyframes

    JPanel keyframeInteraction = new JPanel();
    keyframeInteraction.setPreferredSize(new Dimension(200, 300));
    keyframeInteraction.setLayout(new BoxLayout(keyframeInteraction, BoxLayout.LINE_AXIS));

    JPanel keyframeInputs1 = new JPanel(new FlowLayout());
    JPanel keyframeInputs2 = new JPanel(new FlowLayout());
    JPanel keyframeInputs3 = new JPanel(new FlowLayout());
    JPanel keyframeInputs4 = new JPanel(new FlowLayout());
    JPanel keyframeInputs5 = new JPanel(new FlowLayout());
    JPanel keyframeInputs6 = new JPanel(new FlowLayout());
    JPanel keyframeInputs7 = new JPanel(new FlowLayout());
    JPanel keyframeInputs8 = new JPanel(new FlowLayout());

    JPanel keyframeInputs = new JPanel();
    keyframeInputs.setLayout(new GridLayout(8, 0));
    JLabel tickLabel = new JLabel("Tick:");
    JLabel xLabel = new JLabel("X-Coor:");
    JLabel yLabel = new JLabel("Y-Coor:");
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

    keyframeInputs1.add(tickLabel);
    keyframeInputs1.add(tick);
    keyframeInputs2.add(xLabel);
    keyframeInputs2.add(xCoor);
    keyframeInputs3.add(yLabel);
    keyframeInputs3.add(yCoor);
    keyframeInputs4.add(widthLabel);
    keyframeInputs4.add(width);
    keyframeInputs5.add(heightLabel);
    keyframeInputs5.add(height);
    keyframeInputs6.add(rLabel);
    keyframeInputs6.add(rVal);
    keyframeInputs7.add(gLabel);
    keyframeInputs7.add(gVal);
    keyframeInputs8.add(bLabel);
    keyframeInputs8.add(bVal);
    keyframeInputs.add(keyframeInputs1);
    keyframeInputs.add(keyframeInputs2);
    keyframeInputs.add(keyframeInputs3);
    keyframeInputs.add(keyframeInputs4);
    keyframeInputs.add(keyframeInputs5);
    keyframeInputs.add(keyframeInputs6);
    keyframeInputs.add(keyframeInputs7);
    keyframeInputs.add(keyframeInputs8);

    keyframeInteraction.add(keyframeInputs);

    JPanel keyframeButtons = new JPanel();
    keyframeButtons.setLayout(new BoxLayout(keyframeButtons, BoxLayout.Y_AXIS));

    JButton add = new JButton("Add");
    add.setActionCommand("addKeyframe");
    add.addActionListener(this);
    keyframeButtons.add(add);
    JButton remove = new JButton("Remove");
    remove.setActionCommand("removeKeyframe");
    remove.addActionListener(this);
    keyframeButtons.add(remove);
    JButton modify = new JButton("Modify");
    modify.addActionListener(this);
    modify.setActionCommand("modifyKeyframe");
    keyframeButtons.add(modify);

    keyframeInteraction.add(keyframeButtons);

    keyframePanel.add(keyframeInteraction, BorderLayout.SOUTH);
  }

  /**
   * Configures the JPanel that contains the all the shape interactions, including input text
   * fields, buttons, and the list of shapes.
   */
  private void configureShapePanel() {
    // construct overall shape panel
    shapePanel = new JPanel();
    shapeList = new JList<String>(this.shapeListModel);
    shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shapeList.addListSelectionListener((e -> {
      if (shapeList.getSelectedIndex() == -1) {
        return;
      }
      IReadOnlyShape shape = this.shapesToRender.get(shapeList.getSelectedIndex());
      keyframeListModel.clear();
      for (IKeyframe keyframe : shape.getKeyframes()) {
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
    rectangleRadio.setActionCommand("rectangle");
    ellipseRadio = new JRadioButton("Ellipse");
    ellipseRadio.setActionCommand("ellipse");

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

  /**
   * Configures the button panel to be placed in the main video panel. Sets all the listeners and
   * commands.
   */
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

    loopback = new JCheckBox("Loopback Enabled");
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

  /**
   * In addition the the functionality specified in the interface, this method also updates the
   * shape list to be drawn.
   *
   * @param shapes a list of shapes belonging to the animation
   */
  @Override
  public void setShapes(List<IReadOnlyShape> shapes) {
    this.shapesToRender = shapes;
    this.shapeListModel.clear();
    for (int i = 0; i < this.shapesToRender.size(); i++) {
      this.shapeListModel.addElement(this.shapesToRender.get(i).getID());
    }
  }

  /**
   * Holds the logic for deciding which button was pressed. Calls the appropriate feature.
   *
   * @param e the button clicked
   */
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
        break;
      case "addShape":
        this.addShape();
        break;
      case "removeShape":
        this.removeShape();
        break;
      case "addKeyframe":
        this.addKeyframe();
        break;
      case "removeKeyframe":
        this.removeKeyframe();
        break;
      case "modifyKeyframe":
        this.modifyKeyframe();
        break;
      default:
    }
  }

  /**
   * Parses through user input to modify the currently selected keyframe. Calls the appropriate
   * method on Features and passes in the information for the keyframe of interest. If unsuccessful,
   * an error will pop up.
   */
  private void modifyKeyframe() {
    if (keyframeList.isSelectionEmpty()) {
      return;
    }
    if (shapeList.isSelectionEmpty()) {
      this.errorPopup("Please have a shape selected.");
      return;
    }

    int tickVal = 0;
    int x = 0;
    int y = 0;
    int heightVal = 0;
    int widthVal = 0;
    int r = 0;
    int g = 0;
    int b = 0;

    if (tick.getText().isEmpty() || xCoor.getText().isEmpty() || yCoor.getText().isEmpty()
            || width.getText().isEmpty() || height.getText().isEmpty() || rVal.getText().isEmpty()
            || gVal.getText().isEmpty() || bVal.getText().isEmpty()) {
      this.errorPopup("Please fill in all the fields.");
      return;
    }
    try {
      tickVal = Integer.parseInt(this.tick.getText());
      x = Integer.parseInt(this.xCoor.getText());
      y = Integer.parseInt(this.yCoor.getText());
      widthVal = Integer.parseInt(this.width.getText());
      heightVal = Integer.parseInt(this.height.getText());
      r = Integer.parseInt(this.rVal.getText());
      b = Integer.parseInt(this.bVal.getText());
      g = Integer.parseInt(this.gVal.getText());
    } catch (NumberFormatException e) {
      this.errorPopup("Inputs for a new keyframe must be numbers!");
      return;
    }

    try {
      this.features.modifyKeyframe(shapeList.getSelectedValue(), tickVal, widthVal, heightVal, x, y,
              new Color(r, g, b));
    } catch (Exception e) {
      this.errorPopup(e.getMessage());
      return;
    }

    this.keyframeListModel.clear();
  }

  /**
   * Removes the currently selected keyframe by calling the appropriate Feature method. If
   * unsuccessful, an error will pop up.
   */
  private void removeKeyframe() {
    if (keyframeList.isSelectionEmpty()) {
      return;
    }
    if (shapeList.isSelectionEmpty()) {
      this.errorPopup("Please have a shape selected.");
      return;
    }
    IReadOnlyShape curShape = this.shapesToRender.get(shapeList.getSelectedIndex());
    IKeyframe curFrame = curShape.getKeyframes().get(keyframeList.getSelectedIndex());
    try {
      this.keyframeListModel.remove(this.keyframeList.getSelectedIndex());
      this.features.removeKeyframe(curShape.getID(), curFrame.getTick());
    } catch (Exception e) {
      this.errorPopup(e.getMessage());
      e.printStackTrace();
      return;
    }

    this.keyframeListModel.clear();
  }

  /**
   * Parses through user input to add the desired keyframe to the currently selected shape by
   * calling the appropriate method on Feature. If unsuccessful, an error will pop up.
   */
  private void addKeyframe() {
    if (shapeList.isSelectionEmpty()) {
      this.errorPopup("Please have a shape selected.");
      return;
    }
    int tickVal = 0;
    int x = 0;
    int y = 0;
    int heightVal = 0;
    int widthVal = 0;
    int r = 0;
    int g = 0;
    int b = 0;

    if (tick.getText().isEmpty() || xCoor.getText().isEmpty() || yCoor.getText().isEmpty()
            || width.getText().isEmpty() || height.getText().isEmpty() || rVal.getText().isEmpty()
            || gVal.getText().isEmpty() || bVal.getText().isEmpty()) {
      this.errorPopup("Please fill in all the fields.");
      return;
    }

    if (shapeList.isSelectionEmpty()) {
      this.errorPopup("Select a shape to add the keyframe to!");
      return;
    }
    try {
      tickVal = Integer.parseInt(this.tick.getText());
      x = Integer.parseInt(this.xCoor.getText());
      y = Integer.parseInt(this.yCoor.getText());
      widthVal = Integer.parseInt(this.width.getText());
      heightVal = Integer.parseInt(this.height.getText());
      r = Integer.parseInt(this.rVal.getText());
      b = Integer.parseInt(this.bVal.getText());
      g = Integer.parseInt(this.gVal.getText());
    } catch (NumberFormatException e) {
      this.errorPopup("Inputs for a new keyframe must be numbers!");
      return;
    }

    try {
      this.features.addKeyframe(shapeList.getSelectedValue(), tickVal, widthVal, heightVal, x, y,
              new Color(r, g, b));
    } catch (Exception e) {
      this.errorPopup(e.getMessage());
      e.printStackTrace();
      return;
    }

    this.keyframeListModel.clear();
  }

  /**
   * Removes the currently selected shape. If unsuccessful, an error will pop up.
   */
  private void removeShape() {
    if (shapeList.isSelectionEmpty()) {
      return;
    }
    String id = shapeList.getSelectedValue();
    try {
      this.shapeListModel.remove(shapeList.getSelectedIndex());
      this.features.removeShape(id);
    } catch (Exception e) {
      errorPopup(e.getMessage());
      e.printStackTrace();
      return;
    }
  }

  /**
   * Parses user input and radio button selection to add a shape. If unsuccessful, an error will pop
   * up.
   */
  private void addShape() {
    if (shapeIDField.getText().isEmpty() || shapeSelections.getSelection() == null) {
      return;
    }
    ShapeType type = ShapeType.stringToType(shapeSelections.getSelection().getActionCommand());
    try {
      this.features.addShape(shapeIDField.getText(), type);
    } catch (Exception e) {
      errorPopup(e.getMessage());
      e.printStackTrace();
      return;
    }
  }

  /**
   * Pop up an error message with the given message.
   *
   * @param errorMessage the message to display
   */
  private void errorPopup(String errorMessage) {
    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
