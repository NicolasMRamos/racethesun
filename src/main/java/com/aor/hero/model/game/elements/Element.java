package com.aor.hero.model.game.elements;

import com.aor.hero.model.Vector2;

public class Element {

    private Vector2 position;

    public Element(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    protected void setPosition(Vector2 newPos) { this.position = newPos; }
}
