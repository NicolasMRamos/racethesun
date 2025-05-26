package com.aor.hero;

import com.aor.hero.gui.LanternaGUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.menu.MainMenu;
import com.aor.hero.states.MenuState;
import com.aor.hero.states.State;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    void testGetScreenSize() throws IOException, FontFormatException, URISyntaxException {

        LanternaGUI mockGui = mock(LanternaGUI.class);
        Screen mockScreen = mock(Screen.class);
        when(mockGui.getScreen()).thenReturn(mockScreen);
        when(mockScreen.getTerminalSize()).thenReturn(new TerminalSize(480, 401));

        Game game = injectMockGui(mockGui);

        Vector2 screenSize = game.getScreenSize();
        assertEquals(480, screenSize.getX());
        assertEquals(401, screenSize.getY());
    }

    @Test
    void testSetAndGetState() throws IOException, FontFormatException, URISyntaxException {
        State<?> mockState = mock(State.class);
        Game game = Game.getInstance();

        game.setState(mockState);
        assertEquals(mockState, game.getState());
    }

    @Test
    void testSetStateWithNull() throws IOException, FontFormatException, URISyntaxException {
        Game game = Game.getInstance();

        game.setState(null);
        assertNull(game.getState());
    }

    @Test
    void testGetInstanceReturnsSameInstance() throws IOException, URISyntaxException, FontFormatException {
        Game instance1 = Game.getInstance();
        Game instance2 = Game.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    @Test
    void testGetInstanceHandlesExceptions() {
        assertDoesNotThrow(Game::getInstance);
    }

    @Test
    void testGetStateWhenUnset() throws IOException, FontFormatException, URISyntaxException {
        Game game = Game.getInstance();

        State<?> result = game.getState();

        assertNotNull(result);
        assertTrue(result instanceof MenuState);
        assertTrue(((MenuState) result).getModel() instanceof MainMenu);
    }

    @Test
    void testStartWithMockState() throws IOException, URISyntaxException, FontFormatException {

        LanternaGUI mockGui = mock(LanternaGUI.class);
        State<?> mockState = mock(State.class);

        Game game = injectMockGui(mockGui);
        game.setState(mockState);

        new Thread(() -> {
            try {
                Thread.sleep(100);
                game.setState(null);
            } catch (InterruptedException e) {
                fail("Test interrupted: " + e.getMessage());
            }
        }).start();

        game.start();

        verify(mockState, atLeastOnce()).step(eq(game), eq(mockGui), anyDouble());
        verify(mockGui, times(1)).close();
    }

    private Game injectMockGui(LanternaGUI mockGui) throws IOException, FontFormatException, URISyntaxException {

        Game game = Game.getInstance();
        try {
            java.lang.reflect.Field guiField = Game.class.getDeclaredField("gui");
            guiField.setAccessible(true);
            guiField.set(game, mockGui);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to inject mock GUI: " + e.getMessage());
        }
        return game;
    }
}
