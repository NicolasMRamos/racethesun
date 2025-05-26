package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.grid.Grid;

public class CameraController extends GameController {

    final Camera camera;

    final Grid grid;

    public CameraController(Arena arena) {
        super(arena);
        grid = arena.getGrid();
        camera = arena.getCamera();
    }

    @Override
    public void step(Game game, GUI.ACTION action, double delta) {

        Arena arena = getModel();

        Vector2 prevPos = grid.getPositionGridList(arena.getCamera().getPosition() , Vector2.ZERO);

        camera.updateCameraPos(arena.getHero());

        Vector2 newPos = grid.getPositionGridList(arena.getCamera().getPosition(), Vector2.ZERO);

        if((long)prevPos.getY() != (long)newPos.getY()) {

            Hero hero = arena.getHero();

            Vector2 dir = hero.getLookingDir().negative();

            dir = dir.normalized();

            hero.setPosition(hero.getPosition().add(dir.multiplyByScalar(grid.getTileSize())));

            camera.updateCameraPos(arena.getHero());

            grid.updateGrid(arena.getRandom(), arena.getWallSpawnPercentage());

        }

    }

    @Override
    public Arena getModel() {
        return super.getModel();
    }
}
