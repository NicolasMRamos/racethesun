package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Ray;
import com.aor.hero.model.game.raycaster.Raycaster;
import com.aor.hero.states.MenuState;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArenaControllerTest {

    @Test
    public void testConstructor() {

        Arena arena = mock(Arena.class);

        ArenaController arenaController = new ArenaController(arena);

        assertNotNull(arenaController);
    }

    @Test
    public void testStep_QuitAction() throws IOException, URISyntaxException {

        Arena arena = mock(Arena.class);
        Game game = mock(Game.class);

        ArenaController arenaController = new ArenaController(arena);

        arenaController.step(game, GUI.ACTION.QUIT, 0.0);

        verify(game).setState(any(MenuState.class));
    }

    @Test
    public void testStep_ExitAction() throws IOException, URISyntaxException {

        Arena arena = mock(Arena.class);
        Game game = mock(Game.class);

        ArenaController arenaController = new ArenaController(arena);

        arenaController.step(game, GUI.ACTION.EXIT, 0.0);

        verify(game).setState(null);
    }

    @Test
    public void testStep_GameLost() throws IOException, URISyntaxException {

        Arena arena = mock(Arena.class);
        Game game = mock(Game.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);
        Camera camera = mock(Camera.class);
        Raycaster raycaster = mock(Raycaster.class);
        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);
        BufferedImage sprite = mock(BufferedImage.class);
        Vector2 heroPosition = new Vector2(0, 0);
        Vector2 lookingDir = new Vector2(1, 0);
        Vector2 initialCameraPos = new Vector2(5, 5);
        Vector2 prevGridPos = new Vector2(0, 0);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getCurrSprite()).thenReturn(sprite);
        when(hero.getPosition()).thenReturn(heroPosition);
        when(hero.getVelocity()).thenReturn(1.0);
        when(hero.getLookingDir()).thenReturn(lookingDir);

        when(arena.getGrid()).thenReturn(grid);
        when(grid.getPositionGridList(initialCameraPos, Vector2.ZERO)).thenReturn(prevGridPos);
        when(arena.getCamera()).thenReturn(camera);
        when(camera.getPosition()).thenReturn(initialCameraPos);

        when(arena.getRaycaster()).thenReturn(raycaster);
        when(raycaster.getRays()).thenReturn(Arrays.asList(ray1, ray2));

        when(grid.isCollidingWithHero(hero)).thenReturn(true);

        ArenaController arenaController = new ArenaController(arena);

        arenaController.step(game, GUI.ACTION.NONE, 0.0);

        verify(ray1).exec(grid, initialCameraPos);
        verify(ray2).exec(grid, initialCameraPos);
        verify(game).setState(any(MenuState.class));
    }

    @Test
    public void testStep_GameNotLost() throws IOException, URISyntaxException {

        Arena arena = mock(Arena.class);
        Game game = mock(Game.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);
        Camera camera = mock(Camera.class);
        Raycaster raycaster = mock(Raycaster.class);
        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);
        BufferedImage sprite = mock(BufferedImage.class);
        Vector2 heroPosition = new Vector2(0, 0);
        Vector2 lookingDir = new Vector2(1, 0);
        Vector2 initialCameraPos = new Vector2(5, 5);
        Vector2 prevGridPos = new Vector2(0, 0);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getCurrSprite()).thenReturn(sprite);
        when(hero.getPosition()).thenReturn(heroPosition);
        when(hero.getVelocity()).thenReturn(1.0);
        when(hero.getLookingDir()).thenReturn(lookingDir);

        when(arena.getGrid()).thenReturn(grid);
        when(grid.getPositionGridList(initialCameraPos, Vector2.ZERO)).thenReturn(prevGridPos);
        when(arena.getCamera()).thenReturn(camera);
        when(camera.getPosition()).thenReturn(initialCameraPos);

        when(arena.getRaycaster()).thenReturn(raycaster);
        when(raycaster.getRays()).thenReturn(Arrays.asList(ray1, ray2));

        when(grid.isCollidingWithHero(hero)).thenReturn(false);

        ArenaController arenaController = new ArenaController(arena);

        arenaController.step(game, GUI.ACTION.NONE, 1.0);

        verify(ray1).exec(grid, initialCameraPos);
        verify(ray2).exec(grid, initialCameraPos);
        verify(game, never()).setState(any());
    }
}
