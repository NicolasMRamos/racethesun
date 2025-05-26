package com.aor.hero.model.game.arena;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Ray;
import com.aor.hero.model.game.raycaster.Raycast;
import com.aor.hero.sprite.SpriteLoader;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomArenaBuilder extends ArenaBuilder {

    private final Vector2 screenSize;
    private final Rect sky;
    private final Rect floor;
    private final TextColor hWall;
    private final TextColor vWall;
    private final double cameraDist;
    private final double maxRaysDistance;
    private final Vector2 gridSize;
    private final int tileSize;
    private final int drawnTileSize;
    private final double wallSpawnPercentage;

    public RandomArenaBuilder(Vector2 screenSize, Vector2 gridSize, double wallSpawnPercentage, double cameraDist, double maxRaysDistance, TextColor vWall, TextColor hWall) {

        this.screenSize = screenSize;

        double screenWidth = getScreenWidth();
        double screenHeight = getScreenHeight();

        sky = new Rect(Vector2.ZERO, new Vector2(screenWidth, screenHeight / 3), TextColor.Factory.fromString("#87CEEB"));
        floor = new Rect(new Vector2(0, screenHeight / 3), new Vector2(screenWidth, screenHeight * 2 / 3), TextColor.Factory.fromString("#F0F0F0"));

        this.cameraDist = cameraDist;

        this.maxRaysDistance = maxRaysDistance;

        this.gridSize = new Vector2((int)gridSize.getX(), (int)gridSize.getY());

        this.wallSpawnPercentage = Math.clamp(wallSpawnPercentage, 0, 1);

        tileSize = 30;

        drawnTileSize = 4;

        this.hWall = hWall;
        this.vWall = vWall;

    }

    @Override
    protected Grid createGrid(int tileSize) {

        Random random = new Random();

        String temp = "0".repeat((int)gridSize.getX());

        List<StringBuilder> list = new ArrayList<>();

        int i = 0;

        while(i < gridSize.getY() - 20){

            StringBuilder s = new StringBuilder(temp);

            s.setCharAt(0, '1');

            for(int j = 1; j < gridSize.getX() - 1; j++){

                double randomValue = random.nextDouble();

                if(randomValue < wallSpawnPercentage){
                    s.setCharAt(j, '1');
                }
            }

            s.setCharAt((int)gridSize.getX() - 1, '1');

            list.add(s);

            i++;
        }

        while(i < gridSize.getY()){

            StringBuilder s = new StringBuilder(temp);

            s.setCharAt(0, '1');

            s.setCharAt((int)gridSize.getX() - 1, '1');

            list.add(s);

            i++;
        }

        return new Grid(list, tileSize, drawnTileSize);
    }

    @Override
    public double getWallSpawnPercentage() {
        return wallSpawnPercentage;
    }

    private double getCameraDist() {
        return cameraDist;
    }

    @Override
    public TextColor gethWallColor() {
        return hWall;
    }

    @Override
    public TextColor getvWallColor() {
        return vWall;
    }

    @Override
    protected Hero createHero(Grid grid) throws IOException, URISyntaxException {

        return new Hero(new Vector2((double)grid.getGridWidth() / 2, (double)grid.getGridHeight() - grid.getTileSize() * 2), 71, new Vector2(2, 2) , new Vector2(Vector2.UP), new Vector2((double) getScreenWidth() / 2 - 24, (double) getScreenHeight() / 2 + 30), 100, "sprites/turningleft.png", "sprites/turningright.png", "sprites/straight.png", "sprites/turningleftshadow.png", "sprites/turningrightshadow.png" , "sprites/straightshadow.png", SpriteLoader.getInstance());
    }

    @Override
    protected Camera createCamera(Arena arena) {
        Camera camera = new Camera(Vector2.ZERO, getCameraDist());
        camera.updateCameraPos(arena.getHero());
        return camera;
    }

    @Override
    protected List<Ray> createRays() {

        List<Ray> rays = new ArrayList<>();

        int j = 0;

        int numRays = 401;

        double rectWidth = screenSize.getX() / numRays;

        for(double i = -40; i <= 40; i += 0.2) {

            double rectX = j * rectWidth;

            rays.add(new Raycast(Vector2.UP.rotateDeg(i),(int)rectX, numRays , maxRaysDistance, Math.toRadians(i)));
            j++;
        }

        return rays;

    }

    @Override
    public Vector2 getScreenSize() {
        return screenSize;
    }

    private int getScreenHeight() {
        return (int)screenSize.getY();
    }

    private int getScreenWidth() {
        return (int)screenSize.getX();
    }

    @Override
    public int getTileSize() {
        return tileSize;
    }

    @Override
    protected Rect getSky() {
        return sky;
    }

    @Override
    protected Rect getFloor() {
        return floor;
    }

    @Override
    public Arena createArena() throws IOException, URISyntaxException {
        return super.createArena();
    }
}