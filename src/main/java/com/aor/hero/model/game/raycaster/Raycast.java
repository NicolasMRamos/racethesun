package com.aor.hero.model.game.raycaster;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.grid.Grid;

import static java.lang.Math.*;

public class Raycast implements Ray {

    private final Vector2 direction;
    private final double maxDistance;
    private double currentTraveledDistance;
    private Vector2 finalPosition;
    private boolean isHittingVertical;
    private final double angleWithUpRad;
    private boolean isColliding;
    private final int numRays;
    private final int drawWallXPosPixels;

    public Raycast(Vector2 direction, int drawWallXPosPixels, int numRays, double maxDistance, double angleWithUpRad) {
        this.direction = direction.normalized();
        this.maxDistance = maxDistance;
        currentTraveledDistance = 0;
        finalPosition = Vector2.ZERO;
        isHittingVertical = true;
        this.angleWithUpRad = angleWithUpRad;
        this.isColliding = false;
        this.numRays = numRays;
        this.drawWallXPosPixels = drawWallXPosPixels;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public int getNumRays(){
        return numRays;
    }

    public int getDrawWallXPosPixels() {
        return drawWallXPosPixels;
    }

    public double getAngleWithUpRad() {
        return angleWithUpRad;
    }

    public boolean isHittingVertical() {
        return isHittingVertical;
    }

    @Override
    public void exec(Grid grid, Vector2 initPos) {

        isColliding = false;


        initPos.setX(initPos.getX());
        initPos.setY(initPos.getY());

        int tileSize = grid.getTileSize();

        double currhDist = maxDistance;

        Vector2 inithdir = new Vector2(direction);

        if(direction.getY() != 0) {

            Vector2 hdir = new Vector2(direction);

            long initTileY = (long) abs(initPos.getY());

            long ys = initTileY % tileSize;

            if(direction.getY() > 0) {
                ys = (tileSize - ys) % tileSize;
            }

            double yDir = abs(direction.getY());

            inithdir = inithdir.multiplyByScalar((double)ys / yDir);

            hdir = hdir.multiplyByScalar((double)tileSize / yDir);

            Vector2 nexthPoint = initPos.add(inithdir);

            currhDist = initPos.dist(nexthPoint);

            while(!shouldStop(grid, nexthPoint) && currhDist <= maxDistance) {
                nexthPoint = nexthPoint.add(hdir);
                currhDist = initPos.dist(nexthPoint);
            }
        }

        double currvDist = maxDistance;

        Vector2 initvdir = new Vector2(direction);

        if(direction.getX() != 0) {

            Vector2 vdir = new Vector2(direction);

            long initTileX = (long) abs(initPos.getX());

            long xs = initTileX % tileSize;

            if(direction.getX() > 0) {
                xs = (tileSize - xs) % tileSize;
            }

            double xDir = abs(direction.getX());

            initvdir = initvdir.multiplyByScalar((double)xs / xDir);

            vdir = vdir.multiplyByScalar((double)tileSize / xDir);

            Vector2 nextvPoint = initPos.add(initvdir);

            currvDist = initPos.dist(nextvPoint);

            while(!shouldStop(grid, nextvPoint) && currvDist <= maxDistance) {
                nextvPoint = nextvPoint.add(vdir);
                currvDist = initPos.dist(nextvPoint);
            }
        }

        currentTraveledDistance = min(currhDist, currvDist);

        isHittingVertical = (currentTraveledDistance == currvDist);

        finalPosition = initPos.add(direction.multiplyByScalar(currentTraveledDistance));

    }

    public Vector2 getFinalPosition() {
        return finalPosition;
    }

    public boolean isColliding() {
        return isColliding;
    }

    @Override
    public void drawWall(GUI gui, Arena arena) {
        gui.drawWall(this, arena);
    }

    @Override
    public void drawRays(GUI gui, Arena arena) {
        gui.drawRays(this, arena);
    }

    public boolean shouldStop(Grid grid, Vector2 point) {

        Vector2 temp = grid.getPositionGridList(point, direction);

        if(grid.isInside((int)temp.getX(), (int)temp.getY())) {

            if(grid.get((int)temp.getX(), (int)temp.getY()) == '1') {
                isColliding = true;
                return true;
            }

            return false;
        }

        return true;
    }
}
