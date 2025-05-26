package com.aor.hero.model.game.arena;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Raycaster;
import com.googlecode.lanterna.TextColor;

import java.util.*;

public class Arena {

    private final Vector2 screenSize;
    private final Rect sky;
    private final Rect floor;

    private Hero hero;

    private Grid grid;

    private Camera camera;

    private Raycaster raycaster;

    private final double horizonY;

    private final double wallSize;

    private final TextColor hWall;

    private final TextColor vWall;

    private final double wallSpawnPercentage;

    private final Random random;

    public Arena(Vector2 screenSize, Rect sky, Rect floor, double wallSize, TextColor hWall, TextColor vWall, double wallSpawnPercentage) {
        this.screenSize = screenSize;
        this.sky = sky;
        this.floor = floor;
        this.horizonY = floor.getY();
        this.wallSize = wallSize;
        this.hWall = hWall;
        this.vWall = vWall;
        this.wallSpawnPercentage = wallSpawnPercentage;
        this.random = new Random();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public TextColor gethWallColor() {
        return hWall;
    }

    public TextColor getvWallColor() {
        return vWall;
    }

    public double getWallSize() {
        return wallSize;
    }

    public double getHorizonY() {
        return horizonY;
    }

    public int getScreenWidth() {
        return (int)screenSize.getX();
    }

    public int getScreenHeight() {
        return (int)screenSize.getY();
    }

    public Rect getSky() {
        return sky;
    }

    public Rect getFloor() {
        return floor;
    }

    public Hero getHero() {
        return hero;
    }

    public Raycaster getRaycaster() {
        return raycaster;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setRaycaster(Raycaster raycaster) {
        this.raycaster = raycaster;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public double getWallSpawnPercentage() {
        return wallSpawnPercentage;
    }

    public Random getRandom() {
        return random;
    }

}
