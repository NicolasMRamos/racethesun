package com.aor.hero.model.menu;

import com.aor.hero.sprite.SpriteLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class DeathMenu extends Menu {

    public DeathMenu() throws URISyntaxException, IOException {
        super(new ArrayList<>(Arrays.asList("Play again", "Exit")),
                new ArrayList<>(Arrays.asList("sprites/deathmenuplayagain.png", "sprites/deathmenuexit.png")), SpriteLoader.getInstance());
    }

}