package kr.ac.kpu.game.framework.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class TiledMap {
    private static final String TAG = TiledMap.class.getSimpleName();
    int x;
    int y;
    boolean wraps;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void scrollTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private float dstTileSize;
    public float getDstTileSize() {
        return dstTileSize;
    }
    public void setDstTileSize(float dstTileSize) {
        this.dstTileSize = dstTileSize;
    }

    public float getFullWidth() {
        return dstTileSize * width;
    }
    public float getFullHeight() {
        return dstTileSize * height;
    }

    ArrayList<TiledTileset> tilesets;
    ArrayList<TiledLayer> layers;

    ////////////////////////////////////////////////////////////
    // from tmj
    public int width, height;
    public int tilewidth, tileheight;
    ////////////////////////////////////////////////////////////

    public void draw(Canvas canvas) {
        draw(canvas, 0, 0);
    }
    public void draw(Canvas canvas, int tilesetIndex, int layerIndex) {
        try {
            TiledLayer layer = layers.get(layerIndex);
            TiledTileset tileset = tilesets.get(tilesetIndex);
            tileset.draw(canvas, layer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
