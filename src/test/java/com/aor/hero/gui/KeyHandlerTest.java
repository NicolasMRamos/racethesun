package com.aor.hero.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class KeyHandlerTest {
    private KeyHandler keyHandler;
    private Component mockComponent;

    @BeforeEach
    void setUp() {
        keyHandler = new KeyHandler();
        mockComponent = mock(Component.class); // Mock a Component
    }

    @Test
    void testDefaultAction() {
        assertEquals(GUI.ACTION.NONE, keyHandler.getNextAction());
    }

    @Test
    void testQuitAction() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_Q, 'Q'));
        assertEquals(GUI.ACTION.QUIT, keyHandler.getNextAction());
    }

    @Test
    void testUpActionWithArrowKey() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_UP, ' '));
        assertEquals(GUI.ACTION.UP, keyHandler.getNextAction());
    }

    @Test
    void testUpActionWithW() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertEquals(GUI.ACTION.UP, keyHandler.getNextAction());
    }

    @Test
    void testRightActionWithArrowKey() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_RIGHT, ' '));
        assertEquals(GUI.ACTION.RIGHT, keyHandler.getNextAction());
    }

    @Test
    void testRightActionWithD() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertEquals(GUI.ACTION.RIGHT, keyHandler.getNextAction());
    }

    @Test
    void testDownActionWithArrowKey() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_DOWN, ' '));
        assertEquals(GUI.ACTION.DOWN, keyHandler.getNextAction());
    }

    @Test
    void testDownActionWithS() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertEquals(GUI.ACTION.DOWN, keyHandler.getNextAction());
    }

    @Test
    void testLeftActionWithArrowKey() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_LEFT, ' '));
        assertEquals(GUI.ACTION.LEFT, keyHandler.getNextAction());
    }

    @Test
    void testLeftActionWithA() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertEquals(GUI.ACTION.LEFT, keyHandler.getNextAction());
    }

    @Test
    void testSelectAction() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_ENTER, '\n'));
        assertEquals(GUI.ACTION.SELECT, keyHandler.getNextAction());
    }

    @Test
    void testUnknownKeyDefaultsToNone() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_Z, 'Z'));
        assertEquals(GUI.ACTION.NONE, keyHandler.getNextAction());
    }

    @Test
    void testKeyReleasedClearsEvent() {
        keyHandler.keyPressed(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_Q, 'Q'));
        keyHandler.keyReleased(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_Q, 'Q'));
        assertEquals(GUI.ACTION.NONE, keyHandler.getNextAction());
    }

    @Test
    void testKeyTypedDoesNothing() {
        keyHandler.keyTyped(new KeyEvent(mockComponent, 0, 0, 0, KeyEvent.VK_Q, 'Q'));
        // Verify that nothing happens, getNextAction should still return NONE
        assertEquals(GUI.ACTION.NONE, keyHandler.getNextAction());
    }
}
