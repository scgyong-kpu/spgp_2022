package kr.ac.kpu.game.framework.map;

import android.graphics.Canvas;

public class TiledLayer {
    private final TiledMap map;

    ////////////////////////////////////////////////////////////
    // from tmj
    public int x, y, width, height;
    public int[] data;
    ////////////////////////////////////////////////////////////

    TiledLayer(TiledMap map) {
        this.map = map;
    }

}
