package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.raycaster.Ray;
import com.aor.hero.model.game.raycaster.Raycaster;

import java.io.IOException;

public class RayController extends GameController {

    final Raycaster raycaster;

    public RayController(Arena arena) {
        super(arena);
        this.raycaster = arena.getRaycaster();
    }

    @Override
    public void step(Game game, GUI.ACTION action, double delta) throws IOException {
        Arena arena = getModel();
        for(Ray ray : raycaster.getRays()){
            ray.exec(arena.getGrid(), arena.getCamera().getPosition());
        }
    }
}
