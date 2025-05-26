package com.aor.hero.model.game.elements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.aor.hero.model.Vector2;
import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;

class RectTest {

    @Test
    void testConstructorAndGetters() {

        Vector2 position = new Vector2(10, 20);
        Vector2 size = new Vector2(30, 40);
        TextColor color = mock(TextColor.class);

        Rect rect = new Rect(position, size, color);

        assertEquals(10, rect.getX());
        assertEquals(20, rect.getY());
        assertEquals(30, rect.getWidth());
        assertEquals(40, rect.getHeight());
        assertEquals(color, rect.getColor());
    }

    @Test
    void testToString() {

        Vector2 position = new Vector2(10, 20);
        Vector2 size = new Vector2(30, 40);
        TextColor color = mock(TextColor.class);

        Rect rect = new Rect(position, size, color);
        String result = rect.toString();

        assertEquals("Rect{x=10.0, y=20.0, size=" + size + ", color=" + color + '}', result);
    }
}