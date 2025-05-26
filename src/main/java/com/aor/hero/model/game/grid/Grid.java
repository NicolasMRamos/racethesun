package com.aor.hero.model.game.grid;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Hero;

import java.util.*;

import static java.lang.Math.ceil;

public class Grid {

    private final List<StringBuilder> grid;

    private final int tileSize;

    private final int drawnTileSize;

    public Grid(List<StringBuilder> grid, int tileSize, int drawnTileSize) {
        this.grid = grid;
        this.tileSize = tileSize;
        this.drawnTileSize = drawnTileSize;
    }

    public void updateGrid(Random random, double wallSpawnPercentage){

        StringBuilder stringBuilder = grid.removeLast();

        for(int i = 1; i < stringBuilder.length() - 1; i++){
            double randomDouble = random.nextDouble();

            if(randomDouble < wallSpawnPercentage){
                stringBuilder.setCharAt(i, '1');
            }
            else {
                stringBuilder.setCharAt(i, '0');
            }
        }

        grid.addFirst(stringBuilder);

    }

    public boolean isInside(int x, int y){
        return !(x < 0 || x >= getGridNumColumns() || y < 0 || y >= getGridNumLines());
    }

    public char get(int x, int y){
        return grid.get(y).charAt(x);
    }

    public long getGridNumLines(){
        return grid.size();
    }

    public long getGridNumColumns(){
        return grid.getFirst().length();
    }

    public long getGridWidth(){
        return tileSize * getGridNumColumns();
    }

    public long getGridHeight(){
        return tileSize * getGridNumLines();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getDrawnTileSize() {
        return drawnTileSize;
    }

    public boolean isCollidingWithHero(Hero hero) {

        Vector2 pos = hero.getPosition();

        Vector2 colliderRectSize = hero.getColliderRectSize();

        Set<Vector2> checkingPositions = new HashSet<>();

        List<Vector2> positions = new ArrayList<>();

        positions.add(pos.add(colliderRectSize.multiplyByScalar(-1)));
        positions.add(pos.add(new Vector2(colliderRectSize.getX(), -colliderRectSize.getY())));
        positions.add(pos.add(new Vector2(-colliderRectSize.getX(), colliderRectSize.getY())));
        positions.add(pos.add(colliderRectSize));

        for(Vector2 position : positions){

            position = position.multiplyByScalar((double) 1 / tileSize);

            if(isInside((int)position.getX(), (int)position.getY())) {
                checkingPositions.add(position);
            }
        }

        boolean isColliding = false;

        for(Vector2 position : checkingPositions) {
            if(get((int)position.getX(), (int)position.getY()) == '1'){
                isColliding = true;
                break;
            }
        }

        return isColliding;

    }

    public Vector2 getPositionGridList(Vector2 position, Vector2 direction){

            Vector2 temp = new Vector2(position);

            if(direction.getX() < 0) {
                temp.setX((double) ((long)ceil((temp.getX() - tileSize) / tileSize) * tileSize));
            }
            if(direction.getY() < 0) {
                temp.setY((double) ((long)ceil((temp.getY() - tileSize) / tileSize) * tileSize));
            }

            temp.setX( temp.getX() / tileSize);
            temp.setY( temp.getY() / tileSize);

            return temp;

    }
}
