package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.grid.Grid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CameraControllerTest {

    @Test
    public void testConstructor() {

        Arena arena = mock(Arena.class);
        Grid grid = mock(Grid.class);
        Camera camera = mock(Camera.class);

        when(arena.getGrid()).thenReturn(grid);
        when(arena.getCamera()).thenReturn(camera);

        CameraController cameraController = new CameraController(arena);

        assertNotNull(cameraController);
    }

    @Test
    public void testGetModel() {

        Arena arena = mock(Arena.class);
        CameraController cameraController = new CameraController(arena);

        assertEquals(arena, cameraController.getModel());
    }

    @Test
    public void testStep_CameraMoves() {

        Arena arena = mock(Arena.class);
        Grid grid = mock(Grid.class);
        Camera camera = mock(Camera.class);
        Hero hero = mock(Hero.class);
        Vector2 initialCameraPos = new Vector2(0, 0);
        Vector2 newCameraPos = new Vector2(0, 10);
        Vector2 heroPosition = new Vector2(5, 5);
        Vector2 lookingDir = new Vector2(1, 0);

        when(arena.getGrid()).thenReturn(grid);
        when(arena.getCamera()).thenReturn(camera);
        when(arena.getHero()).thenReturn(hero);

        when(camera.getPosition()).thenReturn(initialCameraPos).thenReturn(newCameraPos);
        when(grid.getPositionGridList(initialCameraPos, Vector2.ZERO)).thenReturn(new Vector2(0, 0));
        when(grid.getPositionGridList(newCameraPos, Vector2.ZERO)).thenReturn(new Vector2(0, 10));
        when(hero.getPosition()).thenReturn(heroPosition);
        when(hero.getLookingDir()).thenReturn(lookingDir);
        when(grid.getTileSize()).thenReturn(1);

        CameraController cameraController = new CameraController(arena);

        cameraController.step(mock(Game.class), GUI.ACTION.NONE, 0.06);

        verify(hero).setPosition(heroPosition.add(lookingDir.negative().normalized().multiplyByScalar(1.0)));
        verify(camera, times(2)).updateCameraPos(hero);
        verify(grid).updateGrid(any(), anyDouble());
    }

    @Test
    public void testStep_CameraDoesNotMove() {
        Arena arena = mock(Arena.class);
        Grid grid = mock(Grid.class);
        Camera camera = mock(Camera.class);
        Hero hero = mock(Hero.class);
        Vector2 cameraPos = new Vector2(0, 0);

        when(arena.getGrid()).thenReturn(grid);
        when(arena.getCamera()).thenReturn(camera);
        when(arena.getHero()).thenReturn(hero);

        when(camera.getPosition()).thenReturn(cameraPos);
        when(grid.getPositionGridList(cameraPos, Vector2.ZERO)).thenReturn(new Vector2(0, 0));

        CameraController cameraController = new CameraController(arena);

        cameraController.step(mock(Game.class), GUI.ACTION.NONE, 0.06);

        verify(hero, never()).setPosition(any());
        verify(camera, times(1)).updateCameraPos(hero);
        verify(grid, never()).updateGrid(any(), anyDouble());
    }
}
