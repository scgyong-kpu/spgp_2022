package kr.ac.kpu.game.framework.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.io.InputStream;

public class TiledTileset {
    private final TiledMap map;

    //////////////////////////////////////////////////
    // from tmj json
    public int columns, tilecount;
    public int tilewidth, tileheight;
    public int imagewidth, imageheight;
    public int margin, spacing;
    public String image;
    //////////////////////////////////////////////////

    public Bitmap bitmap;

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();


    TiledTileset(TiledMap map) {
        this.map = map;
    }
    public void getRectForTile(int tile, Rect rect) {
        int x = (tile - 1) % columns;
        int y = (tile - 1) / columns;
        rect.left = x * (tilewidth + spacing) + margin;
        rect.top = y * (tileheight + spacing) + margin;
        rect.right = rect.left + tilewidth;
        rect.bottom = rect.top + tileheight;
    }

    public boolean loadAssetBitmap(AssetManager assets, String folder) {
        try {
            InputStream is = assets.open(folder + "/" + image);
            bitmap = BitmapFactory.decodeStream(is);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void draw(Canvas canvas, TiledLayer layer) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        float sx = map.x, sy = map.y;
        if (map.wraps) {
            float fullWidth = map.getFullWidth();
            sx %= fullWidth;
            if (sx < 0) {
                sx += fullWidth;
            }
            float fullHeight = map.getFullHeight();
            sy %= fullHeight;
            if (sy < 0) {
                sy += fullHeight;
            }
        }

        int dstTileSize = Math.round(map.getDstTileSize());
        int width = map.width;

        int tile_x = (int) (sx / dstTileSize);
        int tile_y = (int) (sy / dstTileSize);

        float beg_x = - (sx % dstTileSize);
        float beg_y = - (sy % dstTileSize);
        dstRect.top = beg_y;
        dstRect.bottom = beg_y + dstTileSize;
        int ty = tile_y;
        while (dstRect.top < canvasHeight) {
            if (ty >= 0) {
                dstRect.left = beg_x;
                dstRect.right = beg_x + dstTileSize;
                int tx = tile_x;
                int ti = ty * width + tx;
                while (dstRect.left < canvasWidth) {
                    if (ti >= layer.data.length) {
                        break;
                    }
                    int tile = layer.data[ti];
                    getRectForTile(tile, srcRect);
//                    Log.d(TAG, "src=" + srcRect + " dst=" + dstRect + " tx=" + tx + " ty=" + ty + " ti=" + ti);
                    canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                    dstRect.left += dstTileSize;
                    dstRect.right += dstTileSize;
                    ti++;
                    tx = (tx + 1) % layer.width;
                }
            }
            dstRect.top += dstTileSize;
            dstRect.bottom += dstTileSize;
            ty = (ty + 1) % layer.height;
        }
    }
}
