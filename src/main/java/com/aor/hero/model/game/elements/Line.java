package com.aor.hero.model.game.elements;

import com.aor.hero.model.Vector2;
import com.googlecode.lanterna.TextColor;

public class Line extends Element {
    private final Vector2 end;
    private final TextColor color;

    public Line(Vector2 position, Vector2 end, TextColor color) {
        super(position);
        this.end = end;
        this.color = color;
    }

    public Vector2 getStart() {
        return getPosition();
    }

    public Vector2 getEnd() {
        return end;
    }

    public TextColor getColor() {
        return color;
    }
}
