package com.aor.hero.controller;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Controller<T> {
    private final T model;

    public Controller(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void step(Game game, GUI.ACTION action, double delta) throws IOException, URISyntaxException;
}
