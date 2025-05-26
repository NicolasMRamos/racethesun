package com.aor.hero.viewer.menu;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.menu.Menu;
import com.googlecode.lanterna.graphics.TextImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class MenuViewerTest {
    private GUI gui;
    private MenuViewer viewer;
    private TextImage selectedImage;

    @BeforeEach
    void setUp() {
        Menu menu = Mockito.mock(Menu.class);
        gui = Mockito.mock(GUI.class);
        selectedImage = Mockito.mock(TextImage.class);

        when(menu.getSelectedImage()).thenReturn(selectedImage);

        viewer = new MenuViewer(menu);
    }

    @Test
    void testDrawElements() {
        viewer.drawElements(gui);
        verify(gui, times(1)).drawImage(new Vector2(0, 0), selectedImage);
    }
}
