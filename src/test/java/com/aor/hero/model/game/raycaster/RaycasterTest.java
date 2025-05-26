package com.aor.hero.model.game.raycaster;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.grid.Grid;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RaycasterTest {

    @Test
    void testExec() throws IOException {

        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);

        Grid grid = mock(Grid.class);
        Vector2 initPos = new Vector2(0, 0);

        List<Ray> rays = List.of(ray1, ray2);
        Raycaster raycaster = new Raycaster(rays);

        raycaster.exec(grid, initPos);

        verify(ray1, times(1)).exec(grid, initPos);
        verify(ray2, times(1)).exec(grid, initPos);
    }

    @Test
    void testDrawWall() {

        GUI gui = mock(GUI.class);
        Arena arena = mock(Arena.class);

        List<Ray> rays = new ArrayList<>();
        Raycaster raycaster = new Raycaster(rays);

        raycaster.drawWall(gui, arena);

        verify(gui, times(1)).drawWall(raycaster, arena);
    }

    @Test
    void testDrawRays() {

        GUI gui = mock(GUI.class);
        Arena arena = mock(Arena.class);

        List<Ray> rays = new ArrayList<>();
        Raycaster raycaster = new Raycaster(rays);

        raycaster.drawRays(gui, arena);

        verify(gui, times(1)).drawRays(raycaster, arena);
    }

    @Test
    void testGetRays() {

        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);

        List<Ray> rays = List.of(ray1, ray2);
        Raycaster raycaster = new Raycaster(rays);

        assertEquals(rays, raycaster.getRays());
    }
}