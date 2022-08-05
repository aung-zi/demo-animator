package cs3500.animator.view.interactive;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import cs3500.animator.Duration;
import cs3500.animator.controller.KeyboardListener;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.GraphicalAnimatorView;
import cs3500.animator.view.ViewType;

/**
 * This is the Interactive Animator View which consists of buttons and labels and other useful
 * commands to improve the user experience of the Animator. The features include play/pause, speed
 * up, slow down, restart, loop on/off.
 */
public class InteractiveAnimatorView extends GraphicalAnimatorView implements AnimatorView {
  private final JButton play;
  private final JButton loop;
  private final JButton speedUp;
  private final JButton speedDown;
  private final JButton restart;
  private final JButton outline;
  private final JButton discrete;
  private final JButton edit;
  private final JTextField input;
  private final JLabel output;
  private final JLabel text;
  private final InteractiveAnimatorPanel panel;
  private final AnimatorModel model;


  /**
   * Constructor for VisualAnimatorView.
   */
  public InteractiveAnimatorView(AnimatorModel model, Duration duration) {
    super();
    this.model = model;
    this.setTitle("Animator (Interactive)");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    panel = new InteractiveAnimatorPanel(model, duration);
    panel.setPreferredSize(new Dimension(model.getBoundsWidth() * 2,
        model.getBoundsHeight() * 2));

    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
    play = new JButton("❚❚");
    play.setActionCommand("PlayOrPause");

    loop = new JButton("Loop Off");
    loop.setActionCommand("Loop");

    restart = new JButton("⟳");
    restart.setActionCommand("Restart");
    speedUp = new JButton("▶▶");
    speedUp.setActionCommand("SpeedUp");

    speedDown = new JButton("◀◀");
    speedDown.setActionCommand("SpeedDown");

    outline = new JButton("    Fill    ");
    outline.setActionCommand("OutlineOrFill");

    discrete = new JButton("Continuous");
    discrete.setActionCommand("Discrete");

    buttonPanel.add(discrete);
    buttonPanel.add(outline);
    buttonPanel.add(restart);
    buttonPanel.add(speedDown);
    buttonPanel.add(play);
    buttonPanel.add(speedUp);
    buttonPanel.add(loop);
    text = new JLabel("        Speed:" + model.getTempo(panel.getDuration()));
    buttonPanel.add(text);
    mainPanel.add(buttonPanel);

    JPanel editPanel = new JPanel();
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.LINE_AXIS));
    input = new JTextField();
    input.setMaximumSize(new Dimension(500, 100));
    editPanel.add(input);
    edit = new JButton("edit");
    edit.setActionCommand("editAnimation");
    editPanel.add(edit);
    mainPanel.add(editPanel);

    JPanel textPanel = new JPanel();
    output = new JLabel();
    textPanel.add(output);
    mainPanel.add(textPanel);

    JPanel animation = new JPanel();
    int width = model.getBoundsWidth() * 2;
    int height = model.getBoundsHeight() * 2;
    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setPreferredSize(new Dimension(width, height));
    animation.add(scrollPane);
    mainPanel.add(animation);

    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    this.pack();
    this.makeVisible();
  }

  @Override
  public void perform() {
    text.setText("        Speed:" + model.getTempo(panel.getDuration()));
    panel.perform();
  }

  @Override
  public void outlineOrFill() {
    if (outline.getText().equals("Outline")) {
      panel.fill();
      outline.setText("    Fill    ");
    } else {
      panel.fill();
      outline.setText("Outline");
    }
  }

  @Override
  public void discrete() {
    if (discrete.getText().equals("  Discrete  ")) {
      panel.setDiscreteMode(false);
      discrete.setText("Continuous");
    } else {
      panel.setDiscreteMode(true);
      discrete.setText("  Discrete  ");
    }
  }

  @Override
  public void playOrPause() {
    if (play.getText().equals("▶")) {
      panel.playOrStop(true);
      play.setText("❚❚");
    } else {
      panel.playOrStop(false);
      play.setText("▶");
    }
  }

  @Override
  public void loop() {
    if (loop.getText().equals("Loop Off")) {
      panel.isLooping(true);
      loop.setText("Loop On");
    } else {
      panel.isLooping(false);
      loop.setText("Loop Off");
    }
  }

  @Override
  public void restart() {
    panel.restart();
  }

  @Override
  public void speedUp() {
    panel.speedUp();
  }

  @Override
  public void speedDown() {
    panel.speedDown();
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    play.addActionListener(actionListener);
    loop.addActionListener(actionListener);
    restart.addActionListener(actionListener);
    speedUp.addActionListener(actionListener);
    speedDown.addActionListener(actionListener);
    outline.addActionListener(actionListener);
    discrete.addActionListener(actionListener);
    edit.addActionListener(actionListener);
  }

  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {
    play.addKeyListener(keyboardListener);
    loop.addKeyListener(keyboardListener);
    restart.addKeyListener(keyboardListener);
    speedUp.addKeyListener(keyboardListener);
    speedDown.addKeyListener(keyboardListener);
    outline.addKeyListener(keyboardListener);
    discrete.addKeyListener(keyboardListener);
  }

  @Override
  public ViewType getViewType() {
    return ViewType.INTERACTIVE;
  }

  @Override
  public void edit() {
    String[] cmd = getCommand();
    if ("slo-mo".equals(cmd[0])) {
      try {
        if (cmd.length != 4) {
          output.setText("Invalid input. " + input.getText());
          return;
        }
        model.setTweenTempo(Integer.parseInt(cmd[1]),
            Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
        output.setText(input.getText());
      } catch (IllegalArgumentException ioe) {
        output.setText("Invalid input. " + input.getText());
      }
    } else {
      output.setText("Invalid input. " + input.getText());
    }
    input.setText("");
  }

  @Override
  public int getDuration() {
    return panel.getDuration();
  }

  private String[] getCommand() {
    return input.getText().split(" ");
  }
}
