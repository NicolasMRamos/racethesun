package com.aor.hero.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class MainMenuTest {

    @Test
    void testMainMenuInitialization() throws URISyntaxException, IOException {

        MainMenu mainMenu = new MainMenu();

        List<String> expectedOptions = List.of("Play", "Exit");
        assertEquals(expectedOptions, mainMenu.getOptions());

        assertEquals(0, mainMenu.getSelectedIndex());
    }

    @Test
    void testMenuNavigation() throws URISyntaxException, IOException {

        MainMenu mainMenu = new MainMenu();

        mainMenu.moveDown();
        assertEquals(1, mainMenu.getSelectedIndex());
    }

    @Test
    void testGetSelectedImage() throws URISyntaxException, IOException {

        MainMenu mainMenu = new MainMenu();

        assertNotNull(mainMenu.getSelectedImage(), "Selected image should not be null for the default index.");

        mainMenu.moveDown();
        assertNotNull(mainMenu.getSelectedImage(), "Selected image should not be null after changing selection.");
    }
}