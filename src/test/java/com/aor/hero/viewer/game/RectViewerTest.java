package com.aor.hero.viewer.game;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.game.elements.Rect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RectViewerTest {
    private Rect rect;
    private RectViewer viewer;
    private GUI gui;

    @BeforeEach
    void setUp() {
        rect = Mockito.mock(Rect.class);
        viewer = new RectViewer();
        gui = Mockito.mock(GUI.class);
    }

    @Test
    void drawElement() {
        viewer.draw(rect, gui);
        Mockito.verify(gui, Mockito.times(1)).drawRect(rect);
    }
}
