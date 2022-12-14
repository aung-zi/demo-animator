package cs3500.animator.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * This class represents a keyboard listener. It is configurable by the controller that instantiates
 * it. This listener keeps three maps, one each for key typed, key pressed and key released Each map
 * stores a key mapping. A key mapping is a pair (keystroke,code to be executed with that keystroke)
 * The latter part of that pair is actually a function object, i.e. an object of a class that
 * implements the Runnable interface This class implements the KeyListener interface, so that its
 * object can be used as a valid keylistener for Java Swing.
 */
public class KeyboardListener implements KeyListener {
  private Map<Integer, Runnable> keyPressedMap;

  /**
   * Empty default constructor.
   */
  public KeyboardListener() {
    // default
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters.
   */
  public void setKeyTypedMap(Map<Character, Runnable> map) {
    // feature not used.
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes.
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes.
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    // feature not used.
  }

  /**
   * This is called when the view detects that a key has been typed. Find if anything has been
   * mapped to this key character and if so, execute it.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // feature not used.
  }

  /**
   * This is called when the view detects that a key has been pressed. Find if anything has been
   * mapped to this key code and if so, execute it.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * This is called when the view detects that a key has been released. Find if anything has been
   * mapped to this key code and if so, execute it.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // feature not used.
  }
}
