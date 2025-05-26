package com.aor.hero.model.menu;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import com.aor.hero.sprite.SpriteLoader;

public class MainMenu extends Menu {

    public MainMenu() throws URISyntaxException, IOException {
        super(new ArrayList<>(List.of("Play", "Exit")),
        new ArrayList<>(List.of("sprites/mainmenuplay.png", "sprites/mainmenuexit.png")), SpriteLoader.getInstance());
    }

}