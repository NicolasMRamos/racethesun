package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.menu.DeathMenu;
import com.aor.hero.model.menu.MainMenu;
//import com.aor.hero.model.menu.Menu;
import com.aor.hero.states.MenuState;

import java.io.IOException;
import java.net.URISyntaxException;

public class ArenaController extends GameController {
    private final HeroController heroController;
    private final RayController rayController;
    private final CameraController cameraController;

    public ArenaController(Arena arena) {
        super(arena);

        this.heroController = new HeroController(arena);
        this.rayController = new RayController(arena);
        this.cameraController = new CameraController(arena);
    }

    @Override
    public void step(Game game, GUI.ACTION action, double delta) throws IOException, URISyntaxException {
        if (action == GUI.ACTION.QUIT)
            game.setState(new MenuState(new MainMenu()));
        else if(action == GUI.ACTION.EXIT){
            game.setState(null);
        }
        else {

            heroController.step(game, action, delta);

            cameraController.step(game, action, delta);

            if(heroController.hasLost()) {
                game.setState(new MenuState(new DeathMenu()));
            }

            rayController.step(game, action, delta);

        }
    }
}