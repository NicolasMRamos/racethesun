package com.aor.hero.model.game.grid;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Hero;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GridTest {

    @Test
    void testConstructorAndGetters() {

        List<StringBuilder> grid = new ArrayList<>();
        grid.add(new StringBuilder("000"));
        grid.add(new StringBuilder("010"));
        grid.add(new StringBuilder("000"));

        int tileSize = 10;
        int drawnTileSize = 5;

        Grid gridObj = new Grid(grid, tileSize, drawnTileSize);

        assertEquals(3, gridObj.getGridNumLines());
        assertEquals(3, gridObj.getGridNumColumns());
        assertEquals(30, gridObj.getGridWidth());
        assertEquals(30, gridObj.getGridHeight());
        assertEquals(10, gridObj.getTileSize());
        assertEquals(5, gridObj.getDrawnTileSize());
    }

    @Test
    void testUpdateGrid() {

        List<StringBuilder> grid = new ArrayList<>();
        grid.add(new StringBuilder("000"));
        grid.add(new StringBuilder("000"));
        grid.add(new StringBuilder("000"));

        Random random = new Random(42);

        double wallSpawnPercentage = 0.5;
        Grid gridObj = new Grid(grid, 10, 5);

        gridObj.updateGrid(random, wallSpawnPercentage);

        String updatedRow = grid.getFirst().toString();
        assertTrue(updatedRow.matches("[01]{3}"));
    }

    @Test
    void testIsInside() {

        List<StringBuilder> grid = new ArrayList<>();
        grid.add(new StringBuilder("000"));
        grid.add(new StringBuilder("010"));
        grid.add(new StringBuilder("000"));

        Grid gridObj = new Grid(grid, 10, 5);

        assertTrue(gridObj.isInside(1, 1));
        assertFalse(gridObj.isInside(-1, 1));
        assertFalse(gridObj.isInside(3, 1));
        assertFalse(gridObj.isInside(1, 3));
    }

    @Test
    void testIsCollidingWithHero() {

        List<StringBuilder> grid = new ArrayList<>();
        grid.add(new StringBuilder("000"));
        grid.add(new StringBuilder("010"));
        grid.add(new StringBuilder("000"));

        Grid gridObj = new Grid(grid, 10, 5);

        Hero hero = mock(Hero.class);
        when(hero.getPosition()).thenReturn(new Vector2(15, 15));
        when(hero.getColliderRectSize()).thenReturn(new Vector2(5, 5));

        assertTrue(gridObj.isCollidingWithHero(hero));

        when(hero.getPosition()).thenReturn(new Vector2(25, 25));
        assertFalse(gridObj.isCollidingWithHero(hero));
    }
}