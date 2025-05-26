package com.aor.hero.model.game.elements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.aor.hero.model.Vector2;
import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;


class LineTest {

    @Test
    void testConstructorAndGetters() {

        Vector2 start = new Vector2(10, 20);
        Vector2 end = new Vector2(30, 40);
        TextColor color = mock(TextColor.class);

        Line line = new Line(start, end, color);

        assertEquals(start, line.getStart());
        assertEquals(end, line.getEnd());
        assertEquals(color, line.getColor());
    }
}