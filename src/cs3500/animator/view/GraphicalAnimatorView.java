package cs3500.animator.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This is the abstract class which consists of methods that every Graphical AnimatorView should
 * contain.
 */
public abstract class GraphicalAnimatorView extends JFrame implements AnimatorView {
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    this.repaint();
  }
}
