package com.aor.hero.gui;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.raycaster.Raycast;
import com.aor.hero.model.game.raycaster.Raycaster;
import com.googlecode.lanterna.graphics.TextImage;
import com.aor.hero.model.game.arena.Arena;

import java.io.IOException;

public interface GUI {
    ACTION getNextAction() throws IOException;

    void drawImage(Vector2 vector2, TextImage image);

    void drawHero(Hero hero);

    void drawWall(Raycaster ray, Arena arena);

    void drawWall(Raycast ray, Arena arena);

    void drawRays(Raycaster ray, Arena arena);

    void drawRays(Raycast ray, Arena arena);

    void drawRect(Rect rect);

    void clear();

    void refresh() throws IOException;

    enum ACTION {UP, DOWN, RIGHT, LEFT, NONE, QUIT, EXIT, SELECT}
}
