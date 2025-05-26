package com.aor.hero.model.game.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.aor.hero.model.Vector2;
import org.junit.jupiter.api.Test;

public class CameraTest {

    @Test
    void testConstructor() {

        Vector2 position = new Vector2(0, 0);
        double distFromPlayer = 5.0;

        Camera camera = new Camera(position, distFromPlayer);

        assertEquals(position, camera.getPosition());
    }

    @Test
    void testCalculateCameraPos() {

        Vector2 heroPosition = new Vector2(10, 10);
        Vector2 lookingDir = new Vector2(1, 0);
        Hero hero = mock(Hero.class);
        when(hero.getLookingDir()).thenReturn(lookingDir);
        when(hero.getPosition()).thenReturn(heroPosition);

        Camera camera = new Camera(new Vector2(0, 0), 5.0);
        Vector2 calculatedPosition = camera.calculateCameraPos(hero);

        Vector2 expectedPosition = new Vector2(10 - 5, 10);
        assertEquals(expectedPosition, calculatedPosition);
    }

    @Test
    void testUpdateCameraPos() {

        Vector2 heroPosition = new Vector2(10, 10);
        Vector2 lookingDir = new Vector2(0, -1);
        Hero hero = mock(Hero.class);
        when(hero.getLookingDir()).thenReturn(lookingDir);
        when(hero.getPosition()).thenReturn(heroPosition);

        Camera camera = new Camera(new Vector2(0, 0), 5.0);
        camera.updateCameraPos(hero);

        Vector2 expectedPosition = new Vector2(10, 10 + 5);
        assertEquals(expectedPosition, camera.getPosition());
    }
}
