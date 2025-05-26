package com.aor.hero.model.game.arena;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Ray;
import com.aor.hero.model.game.raycaster.Raycaster;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public abstract class ArenaBuilder {

    public Arena createArena() throws IOException, URISyntaxException {
        Arena arena = new Arena(getScreenSize(), getSky(), getFloor(), getTileSize(), gethWallColor(), getvWallColor(), getWallSpawnPercentage());

        arena.setGrid(createGrid(getTileSize()));

        arena.setRaycaster(new Raycaster(createRays()));

        arena.setHero(createHero(arena.getGrid()));

        arena.setCamera(createCamera(arena));

        return arena;
    }

    protected abstract double getWallSpawnPercentage();

    protected abstract TextColor gethWallColor();

    protected abstract TextColor getvWallColor();

    protected abstract Vector2 getScreenSize();

    protected abstract Rect getSky();

    protected abstract Rect getFloor();

    protected abstract int getTileSize();

    protected abstract Grid createGrid(int tileSize);

    protected abstract Hero createHero(Grid grid) throws IOException, URISyntaxException;

    protected abstract List<Ray> createRays();

    protected abstract Camera createCamera(Arena arena);
}
