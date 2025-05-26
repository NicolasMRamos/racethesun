package com.aor.hero.model.game.elements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aor.hero.model.Vector2;
import com.aor.hero.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

class HeroTest {

    private Hero hero;
    private BufferedImage mockImage;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        spriteLoader = mock(SpriteLoader.class);
        mockImage = mock(BufferedImage.class);

        when(spriteLoader.loadBufferedImage(anyString())).thenReturn(mockImage);

        hero = new Hero(
                new Vector2(10, 20),
                10,
                new Vector2(5, 5),
                new Vector2(1, 0),
                new Vector2(100, 200),
                2.0,
                "turningLeft.png",
                "turningRight.png",
                "straight.png",
                "shadowLeft.png",
                "shadowRight.png",
                "shadowStraight.png",
                spriteLoader
        );
    }

    @Test
    void testConstructorInitialization() {
        assertNotNull(hero);
        assertEquals(new Vector2(10, 20), hero.getPosition());
        assertEquals(new Vector2(5, 5), hero.getColliderRectSize());
        assertEquals(new Vector2(1, 0), hero.getLookingDir());
        assertEquals(2.0, hero.getVelocity());
        assertEquals(new Vector2(100, 200), hero.getScreenPosition());
        assertEquals(new Vector2(100, 210), hero.getShadowScreenPosition());
        assertNotNull(hero.getTURNING_LEFT());
        assertNotNull(hero.getTURNING_RIGHT());
        assertNotNull(hero.getSTRAIGHT());
        assertEquals(hero.getSTRAIGHT(), hero.getCurrSprite());
    }

    @Test
    void testSetCurrSprite() {
        hero.setCurrSprite(hero.getTURNING_LEFT());
        assertEquals(hero.getTURNING_LEFT(), hero.getCurrSprite());
        assertEquals(mockImage, hero.getCurrShadow());

        hero.setCurrSprite(hero.getTURNING_RIGHT());
        assertEquals(hero.getTURNING_RIGHT(), hero.getCurrSprite());
        assertEquals(mockImage, hero.getCurrShadow());
    }

    @Test
    void testGetShadowScreenPosition() {
        assertEquals(new Vector2(100, 210), hero.getShadowScreenPosition());
    }

    @Test
    void testSetPosition() {
        Vector2 newPosition = new Vector2(30, 40);
        hero.setPosition(newPosition);
        assertEquals(newPosition, hero.getPosition());
    }
}