package com.aor.hero.viewer.menu;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.menu.Menu;
import com.aor.hero.viewer.Viewer;

public class MenuViewer extends Viewer<Menu> {

    public MenuViewer(Menu menu) {
        super(menu);
    }

    @Override
    public void drawElements(GUI gui) {
        gui.drawImage(new Vector2(0, 0), getModel().getSelectedImage());
    }
}
