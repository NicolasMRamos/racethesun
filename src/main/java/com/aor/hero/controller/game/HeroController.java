package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Hero;

import java.awt.image.BufferedImage;

public class HeroController extends GameController {

    private boolean hasLost;

    public HeroController(Arena arena) {
        super(arena);
        hasLost = false;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void moveHero(Vector2 dir, double fixedDelta) {

        Arena arena = getModel();

        Hero hero = arena.getHero();

        Vector2 movement = dir.multiplyByScalar(hero.getVelocity() * fixedDelta);

        Vector2 pos = hero.getPosition().add(movement);

        if(arena.getGrid().isCollidingWithHero(hero)) {
            hasLost = true;
        }
        else {
            hero.setPosition(pos);
        }
    }

    @Override
    public void step(Game game, GUI.ACTION action, double delta) {

        Hero hero = getModel().getHero();

        Vector2 dir = Vector2.UP;

        BufferedImage currSprite = hero.getCurrSprite();

        boolean isStraightSprite = true;

        if (action == GUI.ACTION.RIGHT) {
            dir = dir.add(Vector2.RIGHT);
            currSprite = hero.getTURNING_RIGHT();
            isStraightSprite = false;
        }

        if (action == GUI.ACTION.LEFT){
            dir = dir.add(Vector2.LEFT);
            currSprite = hero.getTURNING_LEFT();
            isStraightSprite = false;
        }

        if(isStraightSprite) {
            currSprite = hero.getSTRAIGHT();
        }

        hero.setCurrSprite(currSprite);
        moveHero(dir, Game.FIXED_DELTA_TIME);
    }
}
