package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Ray;
import com.aor.hero.model.game.raycaster.Raycaster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RayControllerTest {


    @Test
    public void testConstructor() {

        Arena arena = mock(Arena.class);
        Raycaster raycaster = mock(Raycaster.class);

        when(arena.getRaycaster()).thenReturn(raycaster);

        RayController rayController = new RayController(arena);

        assertNotNull(rayController);
    }

    @Test
    public void testStep() throws IOException {

        Arena arena = mock(Arena.class);
        Raycaster raycaster = mock(Raycaster.class);
        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);
        Grid grid = mock(Grid.class);
        Vector2 cameraPosition = new Vector2(5, 5);
        Camera camera = mock(Camera.class);

        when(arena.getRaycaster()).thenReturn(raycaster);
        when(raycaster.getRays()).thenReturn(Arrays.asList(ray1, ray2));
        when(arena.getGrid()).thenReturn(grid);
        when(arena.getCamera()).thenReturn(camera);
        when(camera.getPosition()).thenReturn(cameraPosition);

        RayController rayController = new RayController(arena);

        rayController.step(mock(Game.class), GUI.ACTION.NONE, 0.0);

        verify(ray1).exec(grid, cameraPosition);
        verify(ray2).exec(grid, cameraPosition);
    }
}
