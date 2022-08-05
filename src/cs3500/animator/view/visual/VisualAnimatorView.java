package cs3500.animator.view.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cs3500.animator.Duration;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.shape.Shape;
import cs3500.animator.view.GraphicalAnimatorView;
import cs3500.animator.view.ViewType;

/**
 * This is the AnimatorView to output graphics.
 */
public class VisualAnimatorView extends GraphicalAnimatorView {
  private final VisualAnimatorPanel animatorPanel;

  /**
   * Constructor for GraphicsAnimatorView.
   */
  public VisualAnimatorView(AnimatorModel model, Duration duration) {
    super();
    this.setTitle("Animator (Visual)");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    animatorPanel = new VisualAnimatorPanel(model, duration);
    animatorPanel.setPreferredSize(new Dimension(model.getBoundsWidth(), model.getBoundsHeight()));

    JScrollPane scrollPane = new JScrollPane(animatorPanel);
    scrollPane.setPreferredSize(new Dimension(model.getBoundsWidth(), model.getBoundsHeight()));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    this.add(scrollPane, BorderLayout.CENTER);
    this.pack();

    this.makeVisible();

  }

  @Override
  public void perform() {
    animatorPanel.perform();
  }

  @Override
  public void setShapes(List<Shape> shapes) {
    animatorPanel.setShapes(shapes);
  }

  @Override
  public ViewType getViewType() {
    return ViewType.VISUAL;
  }
}
