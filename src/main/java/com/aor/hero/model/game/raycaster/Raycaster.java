package com.aor.hero.model.game.raycaster;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.grid.Grid;

import java.io.IOException;
import java.util.List;

public class Raycaster implements Ray {

    private final List<Ray> rays;

    public Raycaster(List<Ray> rays) {
        this.rays = rays;
    }

    @Override
    public void exec(Grid grid, Vector2 initPos) throws IOException {
        for(Ray ray : rays){
            ray.exec(grid, initPos);
        }
    }

    @Override
    public void drawWall(GUI gui, Arena arena) {
        gui.drawWall(this, arena);
    }

    @Override
    public void drawRays(GUI gui, Arena arena) {
        gui.drawRays(this, arena);
    }

    public List<Ray> getRays() {
        return rays;
    }

}
