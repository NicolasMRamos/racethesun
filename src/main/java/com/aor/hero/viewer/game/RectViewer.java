package com.aor.hero.viewer.game;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.game.elements.Rect;

public class RectViewer implements ElementViewer<Rect> {
    @Override
    public void draw(Rect element, GUI gui) {
        gui.drawRect(element);
    }
}
