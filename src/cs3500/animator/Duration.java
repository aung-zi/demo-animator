package cs3500.animator;

/**
 * Represents the internal clock inside the timer.
 */
public class Duration {
  private int duration = 0;

  /**
   * Customize the duration with the given tick.
   */
  public void setDuration(int tick) {
    duration = tick;
  }

  /**
   * Counts the duration to the next tick.
   */
  public void addDuration() {
    duration++;
  }

  /**
   * Gets the latest tick.
   *
   * @return the latest tick
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Reset the timer.
   */
  public void restart() {
    duration = 0;
  }
}
