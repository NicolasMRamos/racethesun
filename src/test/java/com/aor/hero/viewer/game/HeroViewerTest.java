package com.aor.hero.viewer.game;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.elements.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

class HeroViewerTest {
    private Hero hero;
    private HeroViewer viewer;
    private GUI gui;

    @BeforeEach
    void setUp() {
        hero = Mockito.mock(Hero.class);
        when(hero.getPosition()).thenReturn(new Vector2(100.0, 100.0));
        viewer = new HeroViewer();
        gui = Mockito.mock(GUI.class);
    }

    @Test
    void drawElement() {
        viewer.draw(hero, gui);
        Mockito.verify(gui, Mockito.times(1)).drawHero(hero);
    }
}