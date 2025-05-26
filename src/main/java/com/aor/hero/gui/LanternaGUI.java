package com.aor.hero.gui;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Line;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.raycaster.Ray;
import com.aor.hero.model.game.raycaster.Raycast;
import com.aor.hero.model.game.raycaster.Raycaster;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class LanternaGUI implements GUI {
    private final Screen screen;

    TextGraphics tg;
    private KeyHandler keyHandler;

    public LanternaGUI(Screen screen) {
        this.screen = screen;
    }

    public LanternaGUI(int width, int height) throws IOException, FontFormatException, URISyntaxException {
        AWTTerminalFontConfiguration fontConfig = loadSquareFont();
        Terminal terminal = createTerminal(width, height, fontConfig);
        this.screen = createScreen(terminal);
        this.tg = screen.newTextGraphics();

    }

    private Screen createScreen(Terminal terminal) throws IOException {
        final Screen screen;
        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    private Terminal createTerminal(int width, int height, AWTTerminalFontConfiguration fontConfig) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height + 1);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        Terminal terminal = terminalFactory.createTerminal();

        ((AWTTerminalFrame) terminal).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });

        this.keyHandler = new KeyHandler();

        ((AWTTerminalFrame) terminal).getComponent(0).addKeyListener(keyHandler);

        return terminal;

    }

    private AWTTerminalFontConfiguration loadSquareFont() throws URISyntaxException, FontFormatException, IOException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        assert resource != null;
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font loadedFont = font.deriveFont(Font.PLAIN, 2);
        return AWTTerminalFontConfiguration.newInstance(loadedFont);
    }

    @Override
    public ACTION getNextAction() throws IOException {
        KeyStroke keyStroke = screen.pollInput();

        if (keyStroke != null && keyStroke.getKeyType() == KeyType.EOF) return ACTION.EXIT;

        return keyHandler.getNextAction();
    }

    @Override
    public void drawWall(Raycaster raycaster, Arena arena) {
        for (Ray ray : raycaster.getRays()) {
            ray.drawWall(this, arena);
        }
    }

    @Override
    public void drawRays(Raycaster raycaster, Arena arena) {
        for(Ray ray : raycaster.getRays()) {
            ray.drawRays(this, arena);
        }
    }

    @Override
    public void drawRays(Raycast raycast, Arena arena) {
        Vector2 tempPos = arena.getCamera().getPosition();

        double drawFactor = (double)arena.getGrid().getDrawnTileSize() / arena.getGrid().getTileSize();

        Line line = new Line(new Vector2(tempPos.getX() * drawFactor, tempPos.getY() * drawFactor), raycast.getFinalPosition().multiplyByScalar(drawFactor), TextColor.Factory.fromString("#FF0000"));

        drawLine(line);
    }

    @Override
    public void drawWall(Raycast raycast, Arena arena) {

        double rectWidth = (double) arena.getScreenWidth() / raycast.getNumRays();

        if(raycast.isColliding()) {

            double dist = arena.getCamera().getPosition().dist(raycast.getFinalPosition());

            dist *= Math.cos(raycast.getAngleWithUpRad());

            double rectX = raycast.getDrawWallXPosPixels();

            double rectHeight = arena.getWallSize() * arena.getScreenHeight() / dist;

            rectHeight = min(rectHeight, 2 * arena.getFloor().getHeight());

            double rectY = arena.getHorizonY() - rectHeight / 2;

            TextColor color;

            if (raycast.isHittingVertical()) {
                color = arena.getvWallColor();
            } else {
                color = arena.gethWallColor();
            }

            Rect tempRect = new Rect(new Vector2((long) rectX, (long) rectY), new Vector2((int) ceil(rectWidth), (int) ceil(rectHeight)), color);
            drawRect(tempRect);
        }
    }

    private void drawImage(Vector2 vector2, BufferedImage image) {

        for (int x = 0; x < image.getWidth(); x++){
            for (int y = 0; y < image.getHeight(); y++){
                int a = image.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 255;
                int green = (a >> 8) & 255;
                int blue = a & 255;

                if (alpha != 0) {
                    TextCharacter c = new TextCharacter(' ', new TextColor.RGB(red, green, blue), new TextColor.RGB(red, green, blue));
                    tg.setCharacter((int) vector2.getX() + x, (int) vector2.getY() + y, c);

                }
            }
        }
    }

    @Override
    public void drawImage(Vector2 vector2, TextImage image) {
        tg.drawImage(new TerminalPosition((int)vector2.getX(), (int)vector2.getY()), image);
    }

    @Override
    public void drawHero(Hero hero) {

        drawImage(hero.getShadowScreenPosition(), hero.getCurrShadow());
        drawImage(hero.getScreenPosition(), hero.getCurrSprite());
    }

    @Override
    public void drawRect(Rect rect) {
        tg.setBackgroundColor(rect.getColor());
        tg.fillRectangle(new TerminalPosition((int)rect.getX(), (int)rect.getY()), new TerminalSize((int)rect.getWidth(), (int)rect.getHeight()) , ' ');
    }

    void drawLine(Line line) {
        tg.setBackgroundColor(line.getColor());
        tg.drawLine((int)line.getStart().getX(),(int) line.getStart().getY(), (int)line.getEnd().getX(),(int) line.getEnd().getY(), ' ');
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh(Screen.RefreshType.DELTA);
    }

    public void close() throws IOException {
        screen.close();
    }

    public Screen getScreen() {
        return screen;
    }
}
