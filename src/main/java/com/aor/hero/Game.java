package com.aor.hero;

import com.aor.hero.gui.LanternaGUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.menu.MainMenu;
//import com.aor.hero.model.menu.Menu;
import com.aor.hero.states.MenuState;
import com.aor.hero.states.State;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private final LanternaGUI gui;
    private State<?> state;
    public static final double FIXED_DELTA_TIME = 0.06;
    private static Game instance;

    public static Game getInstance() throws IOException, URISyntaxException, FontFormatException {
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    private Game() throws FontFormatException, IOException, URISyntaxException {
        this.gui = new LanternaGUI(480, 400);
        this.state = new MenuState(new MainMenu());
    }

    public void setState(State<?> state) {
        this.state = state;
    }

    public State<?> getState() {
        return state;
    }

    public void start() throws IOException, URISyntaxException {

        double amountOfTicks; // FPS
        double ns; // Time per frame in nanoseconds
        double delta = 0;

        long lastTime = System.nanoTime();
        long now;
        long elapsed;

        while (this.state != null) {
            if (this.state instanceof MenuState) {
                amountOfTicks = 8;
            }
            else {
                amountOfTicks = 20;
            }

            ns = 1_000_000_000 / amountOfTicks;
            now = System.nanoTime();
            delta += (now - lastTime) / ns;

            while (delta >= 1) {
                elapsed = now - lastTime;
                if (state != null) {
                    state.step(this, gui, (double) elapsed / 1_000_000_000);
                }
                delta--;
            }

            lastTime = now;
        }
        gui.close();
    }

    public Vector2 getScreenSize()
    {
        return new Vector2(gui.getScreen().getTerminalSize().getColumns(), gui.getScreen().getTerminalSize().getRows());
    }
}
