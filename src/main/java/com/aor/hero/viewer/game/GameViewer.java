package com.aor.hero.viewer.game;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Element;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.viewer.Viewer;
import com.googlecode.lanterna.TextColor;

public class GameViewer extends Viewer<Arena> {

    private final HeroViewer heroViewer;
    private final RectViewer rectViewer;
    private final int drawnTileSize;
    private final double drawFactor;

    public GameViewer(Arena arena) {
        super(arena);
        this.heroViewer = new HeroViewer();
        this.rectViewer = new RectViewer();
        Grid grid = arena.getGrid();
        int tileSize = grid.getTileSize();
        drawnTileSize = grid.getDrawnTileSize();
        drawFactor = ((double)drawnTileSize / tileSize);
    }

    @Override
    public void drawElements(GUI gui) {
        Arena arena = getModel();
        drawElement(gui, arena.getSky(), rectViewer);
        drawElement(gui, arena.getFloor(), rectViewer);
        drawWalls(gui, arena);
        drawElement(gui, arena.getHero(), heroViewer);
        Grid grid = arena.getGrid();
        drawGrid(gui, grid);
        drawGridCamera(gui, arena);
        drawRays(gui, arena);
        drawGridHero(gui, arena);
    }

    private <T extends Element> void drawElement(GUI gui, T element, ElementViewer<T> viewer) {
        viewer.draw(element, gui);
    }

    private void drawWalls(GUI gui, Arena arena) {
        arena.getRaycaster().drawWall(gui, arena);
    }

    private void drawRays(GUI gui, Arena arena) {
        arena.getRaycaster().drawRays(gui, arena);
    }

    private void drawGridHero(GUI gui, Arena arena) {

        Vector2 heroPos = arena.getHero().getPosition();

        Rect playerRect = new Rect(new Vector2((long) (heroPos.getX() * drawFactor), (long) (heroPos.getY() * drawFactor)), new Vector2(2, 2), TextColor.Factory.fromString("#00FF00"));

        drawElement(gui, playerRect, rectViewer);

    }

    private void drawGridCamera(GUI gui, Arena arena) {

        Vector2 cameraPos = arena.getCamera().getPosition();

        Rect cameraRect = new Rect(new Vector2((long) (cameraPos.getX() * drawFactor), (long) (cameraPos.getY() * drawFactor)), new Vector2(2, 2), TextColor.Factory.fromString("#FFD700"));

        drawElement(gui, cameraRect, rectViewer);

    }

    private void drawGrid(GUI gui, Grid grid) {

        boolean shouldDraw;

        for(int i = 0; i < grid.getGridNumLines(); i++) {
            for(int j = 0; j < grid.getGridNumColumns(); j++) {

                char c = grid.get(j, i);
                Rect temp;
                String color;

                if(c == '0') {
                    color = "#000000";
                    shouldDraw = true;
                }
                else if(c == '1') {
                    color = "#808080";
                    shouldDraw = true;
                }
                else {
                    color = "#000000";
                    shouldDraw = false;
                }

                if(shouldDraw) {
                    temp = new Rect(new Vector2(j * drawnTileSize + 1, i * drawnTileSize + 1), new Vector2(drawnTileSize - 1, drawnTileSize - 1), TextColor.Factory.fromString(color));
                    drawElement(gui, temp, rectViewer);
                }
            }
        }
    }
}
