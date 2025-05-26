package com.aor.hero.model.game.elements;

import com.aor.hero.model.Vector2;

public class Camera extends Element {

    private final double distFromPlayer;

    public Camera(Vector2 position, double distFromPlayer) {
        super(position);
        this.distFromPlayer = distFromPlayer;
    }

    public void updateCameraPos(Hero hero) {
        setPosition(calculateCameraPos(hero));
    }

    protected Vector2 calculateCameraPos(Hero hero) {

        Vector2 cameraVec = new Vector2(hero.getLookingDir());
        cameraVec = cameraVec.multiplyByScalar(-1 * distFromPlayer);

        cameraVec = hero.getPosition().add(cameraVec);

        return cameraVec;
    }

}
