package com.aor.hero.states;

import com.aor.hero.Game;
import com.aor.hero.controller.Controller;
import com.aor.hero.controller.game.ArenaController;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.viewer.Viewer;
import com.aor.hero.viewer.game.GameViewer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    @Test
    void testGetViewer() {
        Arena mockArena = mock(Arena.class);
        Grid mockGrid = mock(Grid.class);

        when(mockArena.getGrid()).thenReturn(mockGrid);

        GameState gameState = new GameState(mockArena);

        assertTrue(gameState.getViewer() instanceof GameViewer, "getViewer should return a GameViewer instance");
    }

    @Test
    void testGetController() {
        Arena mockArena = mock(Arena.class);
        Grid mockGrid = mock(Grid.class);

        when(mockArena.getGrid()).thenReturn(mockGrid);

        GameState gameState = new GameState(mockArena);

        assertTrue(gameState.getController() instanceof ArenaController, "getController should return an ArenaController instance");
    }

    @Test
    void testStep() throws IOException, URISyntaxException {

        Arena mockArena = mock(Arena.class);
        GUI mockGUI = mock(GUI.class);
        Game mockGame = mock(Game.class);
        Viewer<Arena> mockViewer = mock(Viewer.class);
        Controller<Arena> mockController = mock(Controller.class);

        GameState gameState = new GameState(mockArena) {
            @Override
            protected Viewer<Arena> getViewer() {
                return mockViewer;
            }

            @Override
            protected Controller<Arena> getController() {
                return mockController;
            }
        };

        when(mockGUI.getNextAction()).thenReturn(GUI.ACTION.DOWN);

        gameState.step(mockGame, mockGUI, 0.016);

        verify(mockGUI, times(1)).getNextAction();
        verify(mockController, times(1)).step(mockGame, GUI.ACTION.DOWN, 0.016);
        verify(mockViewer, times(1)).draw(mockGUI);
    }
}

