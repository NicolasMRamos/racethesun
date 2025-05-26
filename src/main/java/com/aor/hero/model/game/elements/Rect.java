package com.aor.hero.model.game.elements;

import com.aor.hero.model.Vector2;
import com.googlecode.lanterna.TextColor;

public class Rect extends Element {

    private final Vector2 size;

    private final TextColor color;

    public Rect(Vector2 position, Vector2 size, TextColor color) {
        super(position);
        this.size = size;
        this.color = color;
    }

    public double getX() {
        return getPosition().getX();
    }

    public double getY() {
        return getPosition().getY();
    }

    public double getWidth() {
        return size.getX();
    }

    public double getHeight() {
        return size.getY();
    }

    public TextColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Rect{" + "x=" + getX() + ", y=" + getY() + ", size=" + size + ", color=" + color + '}';
    }
}
