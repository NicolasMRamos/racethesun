package com.aor.hero.model.game.elements;

import com.aor.hero.model.Vector2;
import com.aor.hero.sprite.SpriteLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class Hero extends Element {

    private BufferedImage currSprite;
    private BufferedImage currShadow;

    private final BufferedImage TURNING_LEFT;
    private final BufferedImage TURNING_RIGHT;
    private final BufferedImage STRAIGHT;

    private final HashMap<BufferedImage, BufferedImage> shadows;

    private final double velocity;

    private final Vector2 screenPosition;

    private final Vector2 lookingDir;

    private final Vector2 colliderRectSize;

    private final double shadowScreenDist;


    public Hero(Vector2 position, double shadowScreenDist, Vector2 colliderRectSize, Vector2 lookingDir, Vector2 screenPosition, double velocity, String turningLeft, String turningRight, String lookingStraight, String shadowLeft, String shadowRight, String shadowStraight, SpriteLoader spriteLoader) throws IOException, URISyntaxException {
        super(position);

        this.colliderRectSize = colliderRectSize;

        this.lookingDir = lookingDir.normalized();

        this.velocity = velocity;

        this.shadowScreenDist = shadowScreenDist;

        TURNING_LEFT = spriteLoader.loadBufferedImage(turningLeft);
        TURNING_RIGHT = spriteLoader.loadBufferedImage(turningRight);
        STRAIGHT = spriteLoader.loadBufferedImage(lookingStraight);
        BufferedImage SHADOW_LEFT = spriteLoader.loadBufferedImage(shadowLeft);
        BufferedImage SHADOW_RIGHT = spriteLoader.loadBufferedImage(shadowRight);
        BufferedImage SHADOW_STRAIGHT = spriteLoader.loadBufferedImage(shadowStraight);

        this.shadows = new HashMap<>();
        shadows.put(STRAIGHT, SHADOW_STRAIGHT);
        shadows.put(TURNING_LEFT, SHADOW_LEFT);
        shadows.put(TURNING_RIGHT, SHADOW_RIGHT);

        currSprite = STRAIGHT;
        currShadow = SHADOW_STRAIGHT;

        this.screenPosition = screenPosition;

    }

    public Vector2 getColliderRectSize() {
        return colliderRectSize;
    }

    public Vector2 getLookingDir() {
        return lookingDir;
    }

    public Vector2 getScreenPosition() {
        return screenPosition;
    }

    public Vector2 getShadowScreenPosition() {
        return new Vector2(screenPosition.getX(), screenPosition.getY() + shadowScreenDist);
    }

    @Override
    public void setPosition(Vector2 vector2) {
        super.setPosition(vector2);
    }

    public double getVelocity() {
        return velocity;
    }

    public BufferedImage getCurrSprite() {
        return currSprite;
    }

    public BufferedImage getTURNING_LEFT() {
        return TURNING_LEFT;
    }

    public BufferedImage getTURNING_RIGHT() {
        return TURNING_RIGHT;
    }

    public BufferedImage getSTRAIGHT() {
        return STRAIGHT;
    }

    public void setCurrSprite(BufferedImage newSprite) {
        this.currSprite = newSprite;
        this.currShadow = shadows.get(currSprite);
    }

    public BufferedImage getCurrShadow() {
        return currShadow;
    }
}
