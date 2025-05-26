package com.aor.hero.model.game.arena;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Raycaster;
import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;

public class ArenaTest {

    @Test
    void testGetScreenWidthAndHeight() {
        Vector2 screenSize = new Vector2(800, 600);
        Arena arena = new Arena(screenSize, mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        assertEquals(800, arena.getScreenWidth());
        assertEquals(600, arena.getScreenHeight());
    }

    @Test
    void testGetSkyAndFloor() {
        Rect sky = mock(Rect.class);
        Rect floor = mock(Rect.class);
        Arena arena = new Arena(new Vector2(800, 600), sky, floor, 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        assertSame(sky, arena.getSky());
        assertSame(floor, arena.getFloor());
    }

    @Test
    void testGetAndSetHero() {
        Hero hero = mock(Hero.class);
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        arena.setHero(hero);

        assertSame(hero, arena.getHero());
    }

    @Test
    void testGetAndSetCamera() {
        Camera camera = mock(Camera.class);
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        arena.setCamera(camera);

        assertSame(camera, arena.getCamera());
    }

    @Test
    void testGetAndSetRaycaster() {
        Raycaster raycaster = mock(Raycaster.class);
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        arena.setRaycaster(raycaster);

        assertSame(raycaster, arena.getRaycaster());
    }

    @Test
    void testGetAndSetGrid() {
        Grid grid = mock(Grid.class);
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        arena.setGrid(grid);

        assertSame(grid, arena.getGrid());
    }

    @Test
    void testGetWallProperties() {
        TextColor hWall = mock(TextColor.class);
        TextColor vWall = mock(TextColor.class);
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 2.0, hWall, vWall, 0.25);

        assertEquals(2.0, arena.getWallSize());
        assertSame(hWall, arena.gethWallColor());
        assertSame(vWall, arena.getvWallColor());
    }

    @Test
    void testGetHorizonY() {
        Rect floor = mock(Rect.class);
        when(floor.getY()).thenReturn(300.0);

        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), floor, 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        assertEquals(300.0, arena.getHorizonY());
    }

    @Test
    void testGetWallSpawnPercentage() {
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.75);

        assertEquals(0.75, arena.getWallSpawnPercentage());
    }

    @Test
    void testRandomInitialization() {
        Arena arena = new Arena(new Vector2(800, 600), mock(Rect.class), mock(Rect.class), 1.0, mock(TextColor.class), mock(TextColor.class), 0.5);

        assertNotNull(arena.getRandom());
    }
}
