package com.aor.hero.model.menu;

import com.aor.hero.sprite.SpriteLoader;
import com.googlecode.lanterna.graphics.TextImage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    protected final List<String> options;
    protected int selectedIndex = 0;
    protected final List<TextImage> images;

    public Menu(List<String> options, List<String> imagePaths, SpriteLoader spriteLoader) throws URISyntaxException, IOException {

        this.options = options;

        this.images = new ArrayList<>();

        for(String path : imagePaths) {
            images.add(spriteLoader.loadBasicTextImage(path));
        }
    }

    public List<String> getOptions() {
        return options;
    }

    public TextImage getSelectedImage(){
        return images.get(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void moveUp() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
    }

    public void moveDown() {
        selectedIndex = (selectedIndex + 1) % options.size();
    }

}