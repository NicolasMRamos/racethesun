package com.aor.hero.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class DeathMenuTest {

    @Test
    void testDeathMenuInitialization() throws URISyntaxException, IOException {

        DeathMenu deathMenu = new DeathMenu();

        List<String> expectedOptions = List.of("Play again", "Exit");
        assertEquals(expectedOptions, deathMenu.getOptions());

        assertEquals(0, deathMenu.getSelectedIndex());
    }

    @Test
    void testMenuNavigation() throws URISyntaxException, IOException {

        DeathMenu deathMenu = new DeathMenu();

        deathMenu.moveDown();
        assertEquals(1, deathMenu.getSelectedIndex());

        deathMenu.moveUp();
        assertEquals(0, deathMenu.getSelectedIndex());
    }

    @Test
    void testGetSelectedImage() throws URISyntaxException, IOException {

        DeathMenu deathMenu = new DeathMenu();

        assertNotNull(deathMenu.getSelectedImage(), "Selected image should not be null for the default index.");

        deathMenu.moveDown();
        assertNotNull(deathMenu.getSelectedImage(), "Selected image should not be null after changing selection.");

        deathMenu.moveUp();
        assertNotNull(deathMenu.getSelectedImage(), "Selected image should not be null after reverting selection.");
    }

}
