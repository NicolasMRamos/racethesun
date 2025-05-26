package com.aor.hero.states;

import com.aor.hero.Game;
import com.aor.hero.controller.Controller;
import com.aor.hero.controller.menu.MenuController;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.menu.Menu;
import com.aor.hero.viewer.Viewer;
import com.aor.hero.viewer.menu.MenuViewer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuStateTest {
    @Test
    void testGetViewer() {
        Menu mockMenu = mock(Menu.class);
        MenuState menuState = new MenuState(mockMenu);
        assertTrue(menuState.getViewer() instanceof MenuViewer, "getViewer should return a MenuViewer instance");
    }

    @Test
    void testGetController() {
        Menu mockMenu = mock(Menu.class);
        MenuState menuState = new MenuState(mockMenu);
        assertTrue(menuState.getController() instanceof MenuController, "getController should return a MenuController instance");
    }

    @Test
    void testStep() throws IOException, URISyntaxException {

        Menu mockMenu = mock(Menu.class);
        GUI mockGUI = mock(GUI.class);
        Game mockGame = mock(Game.class);
        Viewer<Menu> mockViewer = mock(Viewer.class);
        Controller<Menu> mockController = mock(Controller.class);

        MenuState menuState = new MenuState(mockMenu) {
            @Override
            protected Viewer<Menu> getViewer() {
                return mockViewer;
            }

            @Override
            protected Controller<Menu> getController() {
                return mockController;
            }
        };

        when(mockGUI.getNextAction()).thenReturn(GUI.ACTION.UP);

        menuState.step(mockGame, mockGUI, 0.016);

        verify(mockGUI, times(1)).getNextAction();
        verify(mockController, times(1)).step(mockGame, GUI.ACTION.UP, 0.016);
        verify(mockViewer, times(1)).draw(mockGUI);
    }

}
