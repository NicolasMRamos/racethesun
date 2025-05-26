package com.aor.hero.model.game.raycaster;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.grid.Grid;

import java.io.IOException;

public interface Ray {

    void exec(Grid grid, Vector2 initPos) throws IOException;

    void drawWall(GUI gui, Arena arena);

    void drawRays(GUI gui, Arena arena);
}
