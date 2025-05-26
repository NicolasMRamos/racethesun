package com.aor.hero.model.game.arena;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Ray;
import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class RandomArenaBuilderTest {

    @Test
    void testConstructorInitialization() {
        Vector2 screenSize = new Vector2(800, 600);
        Vector2 gridSize = new Vector2(10, 20);
        TextColor hWall = mock(TextColor.class);
        TextColor vWall = mock(TextColor.class);

        RandomArenaBuilder builder = new RandomArenaBuilder(screenSize, gridSize, 0.5, 40, 1000, vWall, hWall);

        assertEquals(screenSize, builder.getScreenSize());
        assertEquals(30, builder.getTileSize());
        assertNotNull(builder.getSky());
        assertNotNull(builder.getFloor());
        assertEquals(0.5, builder.getWallSpawnPercentage());
    }

    @Test
    void testCreateGrid() {
        Vector2 gridSize = new Vector2(10, 20);
        Vector2 screenSize = new Vector2(800, 600);

        RandomArenaBuilder builder = new RandomArenaBuilder(screenSize, gridSize, 0.5, 40, 1000, TextColor.Factory.fromString("#FFFFFF"), TextColor.Factory.fromString("#000000"));

        Grid grid = builder.createGrid(30);

        assertNotNull(grid);
        assertEquals(10, grid.getGridWidth() / grid.getTileSize());
        assertEquals(20, grid.getGridHeight() / grid.getTileSize());
    }

    @Test
    void testCreateHero() throws IOException, URISyntaxException {
        Vector2 screenSize = new Vector2(800, 600);
        Vector2 gridSize = new Vector2(10, 20);

        RandomArenaBuilder builder = new RandomArenaBuilder(screenSize, gridSize, 0.5, 40, 1000, mock(TextColor.class), mock(TextColor.class));
        Grid grid = mock(Grid.class);
        when(grid.getGridWidth()).thenReturn(300L);
        when(grid.getGridHeight()).thenReturn(600L);
        when(grid.getTileSize()).thenReturn(30);

        Hero hero = builder.createHero(grid);

        assertNotNull(hero);
        assertEquals(new Vector2(150, 540), hero.getPosition());
    }

    @Test
    void testCreateCamera() throws IOException, URISyntaxException {
        Vector2 screenSize = new Vector2(800, 600);
        Vector2 gridSize = new Vector2(10, 20);

        RandomArenaBuilder builder = new RandomArenaBuilder(screenSize, gridSize, 0.5, 40, 1000, mock(TextColor.class), mock(TextColor.class));
        Grid grid = builder.createGrid(30);

        Hero hero = builder.createHero(grid);
        Arena arena = mock(Arena.class);
        when(arena.getHero()).thenReturn(hero);

        assertNotNull(builder.createCamera(arena));
    }

    @Test
    void testCreateRays() {
        Vector2 screenSize = new Vector2(800, 600);
        Vector2 gridSize = new Vector2(10, 20);

        RandomArenaBuilder builder = new RandomArenaBuilder(screenSize, gridSize, 0.5, 40, 1000, TextColor.Factory.fromString("#FFFFFF"), TextColor.Factory.fromString("#000000"));

        List<Ray> rays = builder.createRays();

        int expectedRays = 0;
        for (double i = -40; i <= 40; i += 0.2) {
            expectedRays++;
        }

        assertEquals(expectedRays, rays.size());
    }

    @Test
    void testWallSpawnPercentageClamping() {
        Vector2 screenSize = new Vector2(800, 600);
        Vector2 gridSize = new Vector2(10, 20);
        TextColor hWall = mock(TextColor.class);
        TextColor vWall = mock(TextColor.class);

        RandomArenaBuilder builder = new RandomArenaBuilder(screenSize, gridSize, 1.5, 40, 1000, vWall, hWall);

        assertEquals(1.0, builder.getWallSpawnPercentage());
    }
}