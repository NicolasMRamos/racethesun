package com.aor.hero.viewer.game;

import com.aor.hero.gui.GUI;
import com.aor.hero.model.Vector2;
import com.aor.hero.model.game.arena.Arena;
import com.aor.hero.model.game.elements.Camera;
import com.aor.hero.model.game.elements.Hero;
import com.aor.hero.model.game.grid.Grid;
import com.aor.hero.model.game.raycaster.Raycaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class GameViewerTest {
    private GameViewer gameViewer;
    private GUI guiMock;
    private Arena arenaMock;
    private Grid gridMock;
    private Raycaster raycasterMock;

    @BeforeEach
    void setUp() {
        guiMock = mock(GUI.class);
        arenaMock = mock(Arena.class);
        gridMock = mock(Grid.class);
        raycasterMock = mock(Raycaster.class);
        Hero heroMock = mock(Hero.class);
        Camera cameraMock = mock(Camera.class);

        when(arenaMock.getGrid()).thenReturn(gridMock);
        when(arenaMock.getRaycaster()).thenReturn(raycasterMock);
        when(arenaMock.getHero()).thenReturn(heroMock);
        when(arenaMock.getCamera()).thenReturn(cameraMock);

        when(gridMock.getTileSize()).thenReturn(10);
        when(gridMock.getDrawnTileSize()).thenReturn(20);

        when(heroMock.getPosition()).thenReturn(new Vector2(10, 10));
        when(cameraMock.getPosition()).thenReturn(new Vector2(5, 5));

        gameViewer = new GameViewer(arenaMock);
    }

    @Test
    void testDrawCallsGUIProperly() throws IOException {

        gameViewer.draw(guiMock);

        verify(guiMock).clear();
        verify(guiMock).refresh();

        verify(raycasterMock).drawWall(guiMock, arenaMock);
        verify(raycasterMock).drawRays(guiMock, arenaMock);
    }

    @Test
    void testDrawElementsInvokesGridDrawing() throws IOException {

        when(gridMock.getGridNumLines()).thenReturn(3L);
        when(gridMock.getGridNumColumns()).thenReturn(3L);
        when(gridMock.get(anyInt(), anyInt())).thenReturn('1');

        gameViewer.draw(guiMock);

        verify(gridMock, atLeastOnce()).getGridNumLines();
        verify(gridMock, atLeastOnce()).getGridNumColumns();
        verify(gridMock, atLeastOnce()).get(anyInt(), anyInt());

        verify(guiMock, atLeastOnce()).drawRect(any());
    }

    @Test
    void testDrawHeroAndCamera() throws IOException {
        gameViewer.draw(guiMock);
        verify(guiMock, atLeastOnce()).drawRect(any());
    }

}
