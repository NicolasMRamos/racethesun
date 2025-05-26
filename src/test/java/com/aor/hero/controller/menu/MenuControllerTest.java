package com.aor.hero.controller.menu;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.menu.Menu;
import com.aor.hero.states.GameState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MenuControllerTest {

    @Test
    public void testConstructor() {

        Menu menu = mock(Menu.class);

        MenuController menuController = new MenuController(menu);

        assertNotNull(menuController);
    }

    @Test
    public void testStep_Exit() throws IOException, URISyntaxException {

        Menu menu = mock(Menu.class);
        Game game = mock(Game.class);
        MenuController menuController = new MenuController(menu);

        menuController.step(game, GUI.ACTION.EXIT, 0.0);

        verify(game).setState(null);
    }

    @Test
    public void testStep_MoveUp() throws IOException, URISyntaxException {

        Menu menu = mock(Menu.class);
        Game game = mock(Game.class);
        MenuController menuController = new MenuController(menu);

        menuController.step(game, GUI.ACTION.UP, 0.0);

        verify(menu).moveUp();
    }

    @Test
    public void testStep_MoveDown() throws IOException, URISyntaxException {

        Menu menu = mock(Menu.class);
        Game game = mock(Game.class);
        MenuController menuController = new MenuController(menu);

        menuController.step(game, GUI.ACTION.DOWN, 0.0);

        verify(menu).moveDown();
    }

    @Test
    public void testStep_StartSelected() throws IOException, URISyntaxException {

        Menu menu = mock(Menu.class);
        Game game = mock(Game.class);

        when(menu.getSelectedIndex()).thenReturn(0);
        when(game.getScreenSize()).thenReturn(new Vector2(100, 50));

        MenuController menuController = new MenuController(menu);

        menuController.step(game, GUI.ACTION.SELECT, 0.0);

        verify(game).setState(any(GameState.class));
    }

    @Test
    public void testStep_ExitSelected() throws IOException, URISyntaxException {

        Menu menu = mock(Menu.class);
        Game game = mock(Game.class);

        when(menu.getSelectedIndex()).thenReturn(1);

        MenuController menuController = new MenuController(menu);

        menuController.step(game, GUI.ACTION.SELECT, 0.0);

        verify(game).setState(null);
    }

    @Test
    public void testStep_SelectInvalidOption() throws IOException, URISyntaxException {

        Menu menu = mock(Menu.class);
        Game game = mock(Game.class);

        when(menu.getSelectedIndex()).thenReturn(2);

        MenuController menuController = new MenuController(menu);

        menuController.step(game, GUI.ACTION.SELECT, 1.0);

        verify(game, never()).setState(any());
    }
}
