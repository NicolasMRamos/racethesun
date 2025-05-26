package com.aor.hero.controller.game;

import com.aor.hero.Game;
import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.grid.Grid;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HeroControllerTest {

    @Test
    public void testConstructor() {
        Arena arena = mock(Arena.class);
        HeroController heroController = new HeroController(arena);

        assertNotNull(heroController);
        assertFalse(heroController.hasLost());
    }

    @Test
    public void testHasLostInitialState() {
        Arena arena = mock(Arena.class);
        HeroController heroController = new HeroController(arena);

        assertFalse(heroController.hasLost());
    }

    @Test
    public void testMoveHeroNoCollision() {

        Arena arena = mock(Arena.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getPosition()).thenReturn(new Vector2(0, 0));
        when(hero.getVelocity()).thenReturn(1.0);
        when(arena.getGrid()).thenReturn(grid);
        when(grid.isCollidingWithHero(hero)).thenReturn(false);

        HeroController heroController = new HeroController(arena);

        heroController.moveHero(Vector2.RIGHT, 0.06);

        verify(hero).setPosition(new Vector2(0.06, 0));
        assertFalse(heroController.hasLost());
    }

    @Test
    public void testMoveHeroCollision() {

        Arena arena = mock(Arena.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getPosition()).thenReturn(new Vector2(0, 0));
        when(arena.getGrid()).thenReturn(grid);
        when(grid.isCollidingWithHero(hero)).thenReturn(true);

        HeroController heroController = new HeroController(arena);

        heroController.moveHero(Vector2.RIGHT, 0.06);

        verify(hero, never()).setPosition(any(Vector2.class));
        assertTrue(heroController.hasLost());
    }

    @Test
    public void testStep_MoveRight() {

        Arena arena = mock(Arena.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);
        BufferedImage turningRightSprite = mock(BufferedImage.class);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getPosition()).thenReturn(new Vector2(0, 0));
        when(hero.getVelocity()).thenReturn(1.0);
        when(hero.getTURNING_RIGHT()).thenReturn(turningRightSprite);
        when(hero.getSTRAIGHT()).thenReturn(mock(BufferedImage.class));
        when(arena.getGrid()).thenReturn(grid);
        when(grid.isCollidingWithHero(hero)).thenReturn(false);

        HeroController heroController = new HeroController(arena);

        heroController.step(mock(Game.class), GUI.ACTION.RIGHT, 0.06);

        verify(hero).setCurrSprite(turningRightSprite);
        verify(hero).setPosition(new Vector2(0.06, -0.06));
    }

    @Test
    public void testStep_MoveLeft() {

        Arena arena = mock(Arena.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);
        BufferedImage turningLeftSprite = mock(BufferedImage.class);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getPosition()).thenReturn(new Vector2(0, 0));
        when(hero.getVelocity()).thenReturn(1.0);
        when(hero.getTURNING_LEFT()).thenReturn(turningLeftSprite);
        when(hero.getSTRAIGHT()).thenReturn(mock(BufferedImage.class));
        when(arena.getGrid()).thenReturn(grid);
        when(grid.isCollidingWithHero(hero)).thenReturn(false);

        HeroController heroController = new HeroController(arena);

        heroController.step(mock(Game.class), GUI.ACTION.LEFT, 0.06);

        verify(hero).setCurrSprite(turningLeftSprite);
        verify(hero).setPosition(new Vector2(-0.06, -0.06));
    }

    @Test
    public void testStep_Straight() {

        Arena arena = mock(Arena.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);
        BufferedImage straightSprite = mock(BufferedImage.class);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getPosition()).thenReturn(new Vector2(0, 0));
        when(hero.getVelocity()).thenReturn(1.0);
        when(hero.getSTRAIGHT()).thenReturn(straightSprite);
        when(arena.getGrid()).thenReturn(grid);
        when(grid.isCollidingWithHero(hero)).thenReturn(false);

        HeroController heroController = new HeroController(arena);

        heroController.step(mock(Game.class), GUI.ACTION.NONE, 0.06);

        verify(hero).setCurrSprite(straightSprite);
    }

    @Test
    public void testStep_WithCollision() {

        Arena arena = mock(Arena.class);
        Hero hero = mock(Hero.class);
        Grid grid = mock(Grid.class);
        BufferedImage turningRightSprite = mock(BufferedImage.class);

        when(arena.getHero()).thenReturn(hero);
        when(hero.getPosition()).thenReturn(new Vector2(0, 0));
        when(hero.getVelocity()).thenReturn(1.0);
        when(hero.getTURNING_RIGHT()).thenReturn(turningRightSprite);
        when(hero.getSTRAIGHT()).thenReturn(mock(BufferedImage.class));
        when(arena.getGrid()).thenReturn(grid);
        when(grid.isCollidingWithHero(hero)).thenReturn(true);

        HeroController heroController = new HeroController(arena);

        heroController.step(mock(Game.class), GUI.ACTION.RIGHT, 0.06);

        verify(hero).setCurrSprite(turningRightSprite);
        verify(hero, never()).setPosition(any(Vector2.class));
        assertTrue(heroController.hasLost());
    }
}