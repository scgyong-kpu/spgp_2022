package kr.ac.kpu.game.s1234567.mapgame.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.game.s1234567.mapgame.framework.res.BitmapPool;

public class SheetSprite extends Sprite {
    private final float framesPerSecond;
    private final long createdOn;
    protected Rect[] srcRects;
    public SheetSprite(int mipmapResId, float framesPerSecond) {
        if (mipmapResId != 0) {
            bitmap = BitmapPool.get(mipmapResId);
        }
        this.framesPerSecond = framesPerSecond;
        createdOn = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int index = Math.round(time * framesPerSecond) % srcRects.length;
        canvas.drawBitmap(bitmap, srcRects[index], dstRect, null);
    }
}
