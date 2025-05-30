package com.aor.hero.states;

import com.aor.hero.Game;
import com.aor.hero.controller.Controller;
import com.aor.hero.gui.GUI;
import com.aor.hero.viewer.Viewer;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class State<T> {
    private final T model;
    private final Controller<T> controller;
    private final Viewer<T> viewer;

    public State(T model) {
        this.model = model;
        this.viewer = getViewer();
        this.controller = getController();
    }

    protected abstract Viewer<T> getViewer();

    protected abstract Controller<T> getController();

    public T getModel() {
        return model;
    }

    public void step(Game game, GUI gui, double delta) throws IOException, URISyntaxException {
        GUI.ACTION action = gui.getNextAction();
        controller.step(game, action, delta);
        viewer.draw(gui);
    }
}
