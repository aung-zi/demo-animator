package cs3500.animator.view;

import cs3500.animator.Duration;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.interactive.InteractiveAnimatorView;
import cs3500.animator.view.visual.VisualAnimatorView;
import cs3500.animator.view.svg.SVGAnimatorView;
import cs3500.animator.view.text.TextAnimatorView;

/**
 * Represents the Factory that builds any available AnimatorView.
 */
public class AnimatorViewFactory {

  /**
   * Creates an AnimatorView on demand.
   *
   * @param view  type of view
   * @param model model used in view
   * @return an instance of AnimatorView
   */
  public static AnimatorView getView(String view, AnimatorModel model) {
    switch (view) {
      case "text":
        return new TextAnimatorView(model);
      case "visual":
        return new VisualAnimatorView(model, new Duration());
      case "svg":
        return new SVGAnimatorView(model);
      case "interactive":
        return new InteractiveAnimatorView(model, new Duration());
      default:
        throw new IllegalArgumentException(
            "The view cannot be created! The argument might be " + "invalid.");
    }
  }
}
