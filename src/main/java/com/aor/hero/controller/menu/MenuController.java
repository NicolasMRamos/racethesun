package com.aor.hero.controller.menu;

import com.aor.hero.Game;
import com.aor.hero.controller.Controller;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.RandomArenaBuilder;
//import com.aor.hero.model.menu.Menu;
import com.aor.hero.model.menu.Menu;
import com.aor.hero.states.GameState;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;
import java.net.URISyntaxException;

public class MenuController extends Controller<Menu> {
    public MenuController(Menu menu) {
        super(menu);
    }

    @Override
    public void step(Game game, GUI.ACTION action, double delta) throws IOException, URISyntaxException {
        switch (action) {
            case EXIT:
                game.setState(null);
                break;
            case UP:
                getModel().moveUp();
                break;
            case DOWN:
                getModel().moveDown();
                break;
            case SELECT:
                if (getModel().getSelectedIndex() == 0) { // play selected
                    game.setState(
                            new GameState( new RandomArenaBuilder(
                                    game.getScreenSize(),
                                    new Vector2(27, 50),
                                    0.2,40, 10000 ,
                                    TextColor.Factory.fromString("#D3D3D3"),
                                    TextColor.Factory.fromString("#A9A9A9"))
                                    .createArena()));

                } else if (getModel().getSelectedIndex() == 1) { // exit selected
                    game.setState(null);
                }
                break;
            default:
                // No action taken
                break;
        }
    }
}
