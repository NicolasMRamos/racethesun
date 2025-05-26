package com.aor.hero.sprite;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class SpriteLoader {
    private static SpriteLoader instance;

    public static synchronized SpriteLoader getInstance() {
        if (instance == null) {
            instance = new SpriteLoader();
        }
        return instance;
    }

    public BasicTextImage loadBasicTextImage(String path) throws URISyntaxException, IOException {
        // Load image from the given path
        URL resource = getClass().getClassLoader().getResource(path);

        if (resource == null) {
            throw new AssertionError("Resource not found: " + path);
        }

        File spriteFile = new File(resource.toURI());

        BufferedImage bufferedSprite = ImageIO.read(spriteFile);

        BasicTextImage textImage = new BasicTextImage(bufferedSprite.getWidth(), bufferedSprite.getHeight());

        for (int y = 0; y < bufferedSprite.getHeight(); y++) {
            for (int x = 0; x < bufferedSprite.getWidth(); x++) {

                int rgb = bufferedSprite.getRGB(x, y);
                // Extract RGB components (and alpha channel)
                int a = (rgb >> 24) & 0xFF;  // Alpha component
                int r = (rgb >> 16) & 0xFF;  // Red component
                int g = (rgb >> 8) & 0xFF;   // Green component
                int b = rgb & 0xFF;          // Blue component

                // Skip fully transparent pixels (alpha = 0)
                if (a != 0) {

                    TextColor color = TextColor.Indexed.fromRGB(r, g, b);

                    // Map grayscale to an ASCII character
                    TextCharacter pixelChar = TextCharacter.fromCharacter(' ', color, color)[0];

                    // Set the character in the TextImage at (x, y)
                    textImage.setCharacterAt(x, y, pixelChar);
                }
            }
        }

        return textImage;
    }

    public BufferedImage loadBufferedImage(String path) throws URISyntaxException, IOException {
        // Load image from the given path
        URL resource = getClass().getClassLoader().getResource(path);

        if (resource == null) {
            throw new AssertionError("Resource not found: " + path);
        }
        File spriteFile = new File(resource.toURI());

        return ImageIO.read(spriteFile);
    }

}
