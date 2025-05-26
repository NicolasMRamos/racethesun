package com.aor.hero.gui;

import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.elements.Line;
import com.aor.hero.model.game.elements.Rect;
import com.aor.hero.model.game.grid.Grid;
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
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LanternaGUITest {

    private Screen screenMock;
    private TextGraphics textGraphicsMock;
    private LanternaGUI gui;

    @BeforeEach
    void setUp() {
        screenMock = mock(Screen.class);
        textGraphicsMock = mock(TextGraphics.class);
        gui = new LanternaGUI(screenMock);
        gui.tg = textGraphicsMock;
    }

    @Test
    void testClear() {
        gui.clear();
        verify(screenMock, times(1)).clear();
    }

    @Test
    void testRefresh() throws IOException {
        gui.refresh();
        verify(screenMock, times(1)).refresh(Screen.RefreshType.DELTA);
    }

    @Test
    void testDrawRect() {
        Rect rect = new Rect(new Vector2(5, 5), new Vector2(10, 15), TextColor.Factory.fromString("#FF0000"));

        gui.drawRect(rect);

        verify(textGraphicsMock, times(1)).setBackgroundColor(rect.getColor());
        verify(textGraphicsMock, times(1)).fillRectangle(
                new TerminalPosition((int) rect.getX(), (int) rect.getY()),
                new TerminalSize((int) rect.getWidth(), (int) rect.getHeight()),
                ' '
        );
    }

    @Test
    void testDrawHero() {
        Hero hero = mock(Hero.class);
        Vector2 shadowPosition = new Vector2(1, 1);
        Vector2 heroPosition = new Vector2(2, 2);

        BufferedImage shadowImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        BufferedImage heroImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);

        shadowImage.setRGB(0, 0, 0xFF0000FF);
        heroImage.setRGB(1, 1, 0xFFFF0000);

        when(hero.getShadowScreenPosition()).thenReturn(shadowPosition);
        when(hero.getCurrShadow()).thenReturn(shadowImage);
        when(hero.getScreenPosition()).thenReturn(heroPosition);
        when(hero.getCurrSprite()).thenReturn(heroImage);

        gui.drawHero(hero);

        verify(textGraphicsMock, times(1)).setCharacter(1, 1,
                new TextCharacter(' ', new TextColor.RGB(0, 0, 255), new TextColor.RGB(0, 0, 255)));
        verify(textGraphicsMock, times(1)).setCharacter(3, 3,
                new TextCharacter(' ', new TextColor.RGB(255, 0, 0), new TextColor.RGB(255, 0, 0)));
    }

    @Test
    void testClose() throws IOException {
        gui.close();
        verify(screenMock, times(1)).close();
    }

    @Test
    void testGetNextAction() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(screenMock.pollInput()).thenReturn(keyStroke);
        when(keyStroke.getKeyType()).thenReturn(KeyType.EOF);

        GUI.ACTION action = gui.getNextAction();

        assertEquals(GUI.ACTION.EXIT, action);
    }

    @Test
    void testDrawImageWithTextImage() {
        TextImage image = mock(TextImage.class);
        Vector2 position = new Vector2(5, 5);

        gui.drawImage(position, image);

        verify(textGraphicsMock, times(1)).drawImage(
                new TerminalPosition((int) position.getX(), (int) position.getY()), image
        );
    }

    @Test
    void testDrawWallWithRaycast() {
        Raycast raycast = mock(Raycast.class);
        Arena arena = mock(Arena.class);
        Camera camera = mock(Camera.class);
        Rect floor = mock(Rect.class);

        when(arena.getCamera()).thenReturn(camera);
        when(camera.getPosition()).thenReturn(new Vector2(10, 10));

        when(arena.getFloor()).thenReturn(floor);
        when(floor.getHeight()).thenReturn(40.0);

        when(raycast.getNumRays()).thenReturn(100);
        when(raycast.isColliding()).thenReturn(true);
        when(raycast.getFinalPosition()).thenReturn(new Vector2(20, 20));
        when(raycast.getAngleWithUpRad()).thenReturn(Math.toRadians(45));
        when(raycast.getDrawWallXPosPixels()).thenReturn(50);
        when(raycast.isHittingVertical()).thenReturn(true);

        when(arena.getvWallColor()).thenReturn(TextColor.Factory.fromString("#FF0000"));
        when(arena.gethWallColor()).thenReturn(TextColor.Factory.fromString("#00FF00"));
        when(arena.getScreenWidth()).thenReturn(800);
        when(arena.getScreenHeight()).thenReturn(600);
        when(arena.getWallSize()).thenReturn(10.0);
        when(arena.getHorizonY()).thenReturn(300.0);

        gui.drawWall(raycast, arena);

        verify(textGraphicsMock, atLeastOnce()).setBackgroundColor(any(TextColor.class));
        verify(textGraphicsMock, atLeastOnce()).fillRectangle(any(TerminalPosition.class), any(TerminalSize.class), eq(' '));
    }

    @Test
    void testDrawRaysWithRaycast() {
        Raycast raycast = mock(Raycast.class);
        Arena arena = mock(Arena.class);
        Camera camera = mock(Camera.class);
        Grid grid = mock(Grid.class);

        when(arena.getCamera()).thenReturn(camera);
        when(camera.getPosition()).thenReturn(new Vector2(10, 10));
        when(arena.getGrid()).thenReturn(grid);
        when(grid.getTileSize()).thenReturn(10);
        when(grid.getDrawnTileSize()).thenReturn(20);

        when(raycast.getFinalPosition()).thenReturn(new Vector2(20, 20));
        gui.drawRays(raycast, arena);

        verify(textGraphicsMock, atLeastOnce()).drawLine(anyInt(), anyInt(), anyInt(), anyInt(), eq(' '));
    }

    @Test
    void testDrawLine() {
        Line line = new Line(new Vector2(0, 0), new Vector2(5, 5), TextColor.Factory.fromString("#00FF00"));

        gui.drawLine(line);

        verify(textGraphicsMock, times(1)).setBackgroundColor(line.getColor());
        verify(textGraphicsMock, times(1)).drawLine(
                (int) line.getStart().getX(),
                (int) line.getStart().getY(),
                (int) line.getEnd().getX(),
                (int) line.getEnd().getY(),
                ' '
        );
    }

    @Test
    void testDrawWall() {
        Raycaster raycaster = mock(Raycaster.class);
        Arena arena = mock(Arena.class);
        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);
        List<Ray> rays = Arrays.asList(ray1, ray2);

        when(raycaster.getRays()).thenReturn(rays);

        gui.drawWall(raycaster, arena);

        verify(ray1, times(1)).drawWall(gui, arena);
        verify(ray2, times(1)).drawWall(gui, arena);
    }

    @Test
    void testDrawRays() {
        Raycaster raycaster = mock(Raycaster.class);
        Arena arena = mock(Arena.class);
        Ray ray1 = mock(Ray.class);
        Ray ray2 = mock(Ray.class);
        List<Ray> rays = Arrays.asList(ray1, ray2);

        when(raycaster.getRays()).thenReturn(rays);

        gui.drawRays(raycaster, arena);

        verify(ray1, times(1)).drawRays(gui, arena);
        verify(ray2, times(1)).drawRays(gui, arena);
    }
}

