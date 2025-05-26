package com.aor.hero.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.aor.hero.gui.GUI.ACTION;

public class KeyHandler implements KeyListener {
    // Shared variable to store the most recent KeyEvent
    private KeyEvent lastKeyEvent;

    // Function to get the next action based on the most recent key event
    public ACTION getNextAction() {
        // If no key event has been recorded, return NONE
        if (lastKeyEvent == null) return ACTION.NONE;

        int keyCode = lastKeyEvent.getKeyCode();

        // Determine action based on key code
        if (keyCode == KeyEvent.VK_Q) return ACTION.QUIT;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) return ACTION.UP;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) return ACTION.RIGHT;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) return ACTION.DOWN;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) return ACTION.LEFT;
        if (keyCode == KeyEvent.VK_ENTER) return ACTION.SELECT;

        // Default action if no match
        return ACTION.NONE;
    }

    // Clear the stored key event after processing
    public void clearKeyEvent() {
        lastKeyEvent = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        lastKeyEvent = e;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        clearKeyEvent();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
