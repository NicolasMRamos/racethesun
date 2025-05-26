package com.aor.hero.model.game.raycaster;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.grid.Grid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RaycastTest {

    @Test
    void testConstructorInitialization() {

        Vector2 direction = new Vector2(1, 0);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        assertEquals(400, raycast.getNumRays());
        assertEquals(50, raycast.getDrawWallXPosPixels());
        assertEquals(Math.PI / 4, raycast.getAngleWithUpRad());
        assertEquals(1000, raycast.getMaxDistance(), 0.01);
        assertFalse(raycast.isColliding());
    }

    @Test
    void testExecNoCollision() {
        Vector2 direction = new Vector2(1, 0);
        Vector2 initPos = new Vector2(0, 0);

        List<StringBuilder> gridData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            gridData.add(new StringBuilder("0".repeat(50)));
        }

        Grid grid = new Grid(gridData, 20, 5);

        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        raycast.exec(grid, initPos);

        assertEquals(1000, raycast.getFinalPosition().dist(initPos), 0.01);
        assertFalse(raycast.isColliding());
    }

    @Test
    void testExecCollision() {

        Vector2 direction = new Vector2(1, 0);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        List<StringBuilder> gridData = new ArrayList<>();
        gridData.add(new StringBuilder("000"));
        gridData.add(new StringBuilder("010"));
        gridData.add(new StringBuilder("000"));

        Grid grid = new Grid(gridData, 10, 5);

        raycast.exec(grid, new Vector2(5, 15));

        assertTrue(raycast.isColliding());
        assertTrue(raycast.getFinalPosition().getX() > 5);
    }

    @Test
    void testExecHorizontalCollision() {
        Vector2 direction = new Vector2(0, 1);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        List<StringBuilder> gridData = new ArrayList<>();
        gridData.add(new StringBuilder("000"));
        gridData.add(new StringBuilder("010"));
        gridData.add(new StringBuilder("000"));

        Grid grid = new Grid(gridData, 10, 5);

        raycast.exec(grid, new Vector2(15, 5));

        assertTrue(raycast.isColliding());
        assertTrue(raycast.getFinalPosition().getY() > 5);
    }

    @Test
    void testDrawWall() {
        Vector2 direction = new Vector2(1, 0);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        GUI gui = mock(GUI.class);
        Arena arena = mock(Arena.class);

        raycast.drawWall(gui, arena);

        verify(gui, times(1)).drawWall(raycast, arena);
    }

    @Test
    void testDrawRays() {

        Vector2 direction = new Vector2(1, 0);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        GUI gui = mock(GUI.class);
        Arena arena = mock(Arena.class);

        raycast.drawRays(gui, arena);

        verify(gui, times(1)).drawRays(raycast, arena);
    }

    @Test
    void testShouldStop() {

        Vector2 direction = new Vector2(1, 0);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        List<StringBuilder> gridData = new ArrayList<>();
        gridData.add(new StringBuilder("000"));
        gridData.add(new StringBuilder("010"));
        gridData.add(new StringBuilder("000"));

        Grid grid = new Grid(gridData, 10, 5);

        Vector2 point = new Vector2(15, 15);
        assertTrue(raycast.shouldStop(grid, point));

        point = new Vector2(5, 5);
        assertFalse(raycast.shouldStop(grid, point));
    }

    @Test
    void testHorizontalAndVerticalIteration() {
        Vector2 direction = new Vector2(1, 1).normalized();
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        List<StringBuilder> gridData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            gridData.add(new StringBuilder("0".repeat(20)));
        }
        gridData.get(1).setCharAt(1, '1');

        Grid grid = new Grid(gridData, 10, 5);

        Vector2 initPos = new Vector2(0, 0);
        raycast.exec(grid, initPos);

        Vector2 expectedPosition = new Vector2(10, 10);

        assertEquals(expectedPosition.getX(), raycast.getFinalPosition().getX(), 0.01, "X coordinate mismatch");
        assertEquals(expectedPosition.getY(), raycast.getFinalPosition().getY(), 0.01, "Y coordinate mismatch");

        assertTrue(raycast.isColliding());
        assertEquals(new Vector2(10, 10).dist(initPos), raycast.getFinalPosition().dist(initPos), 0.01);
    }


    @Test
    void testIsHittingVertical() {

        Vector2 direction = new Vector2(1, 0);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        List<StringBuilder> gridData = new ArrayList<>();
        gridData.add(new StringBuilder("000"));
        gridData.add(new StringBuilder("010"));
        gridData.add(new StringBuilder("000"));

        Grid grid = new Grid(gridData, 10, 5);

        Vector2 initPos = new Vector2(5, 15);
        raycast.exec(grid, initPos);

        assertTrue(raycast.isHittingVertical(), "Collision should be on a vertical wall.");
        assertTrue(raycast.isColliding(), "Ray should detect a collision.");
    }

    @Test
    void testIsHittingHorizontal() {

        Vector2 direction = new Vector2(0, 1);
        Raycast raycast = new Raycast(direction, 50, 400, 1000, Math.PI / 4);

        List<StringBuilder> gridData = new ArrayList<>();
        gridData.add(new StringBuilder("000"));
        gridData.add(new StringBuilder("000"));
        gridData.add(new StringBuilder("010"));

        Grid grid = new Grid(gridData, 10, 5);

        Vector2 initPos = new Vector2(15, 5);
        raycast.exec(grid, initPos);

        assertFalse(raycast.isHittingVertical(), "Collision should be on a horizontal wall.");
        assertTrue(raycast.isColliding(), "Ray should detect a collision.");
    }
}
