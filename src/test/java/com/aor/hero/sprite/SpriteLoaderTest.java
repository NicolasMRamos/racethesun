package com.aor.hero.sprite;

import com.googlecode.lanterna.graphics.BasicTextImage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SpriteLoaderTest {
    private static SpriteLoader spriteLoader;

    @BeforeAll
    static void setUp() {
        spriteLoader = SpriteLoader.getInstance();
    }

    @Test
    void testGetInstance() {
        SpriteLoader loader1 = SpriteLoader.getInstance();
        SpriteLoader loader2 = SpriteLoader.getInstance();
        assertSame(loader1, loader2, "SpriteLoader should implement a singleton pattern");
    }

    @Test
    void testLoadBasicTextImage() throws URISyntaxException, IOException {

        String testPath = "sprites/test-sprite.png";

        URL resource = getClass().getClassLoader().getResource(testPath);
        assertNotNull(resource, "Resource not found: " + testPath);

        BasicTextImage textImage = spriteLoader.loadBasicTextImage(testPath);

        assertNotNull(textImage, "The loaded BasicTextImage should not be null");
        assertTrue(textImage.getSize().getColumns() > 0, "The loaded image should have a positive width");
        assertTrue(textImage.getSize().getRows() > 0, "The loaded image should have a positive height");
    }

    @Test
    void testLoadBufferedImage() throws URISyntaxException, IOException {

        String testPath = "sprites/test-sprite.png";

        URL resource = getClass().getClassLoader().getResource(testPath);
        assertNotNull(resource, "Resource not found: " + testPath);

        BufferedImage bufferedImage = spriteLoader.loadBufferedImage(testPath);

        assertNotNull(bufferedImage, "The loaded BufferedImage should not be null");
        assertTrue(bufferedImage.getWidth() > 0, "The loaded image should have a positive width");
        assertTrue(bufferedImage.getHeight() > 0, "The loaded image should have a positive height");
    }

    @Test
    void testLoadNonExistentImage() {
        String invalidPath = "sprites/nonexistent.png";

        assertThrows(AssertionError.class, () -> spriteLoader.loadBufferedImage(invalidPath),
                "Loading a non-existent image should throw an AssertionError");
        assertThrows(AssertionError.class, () -> spriteLoader.loadBasicTextImage(invalidPath),
                "Loading a non-existent BasicTextImage should throw an AssertionError");
    }
}
