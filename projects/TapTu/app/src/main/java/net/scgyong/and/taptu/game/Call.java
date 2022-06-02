package net.scgyong.and.taptu.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import net.scgyong.and.taptu.R;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.Metrics;

public class Call extends Sprite {
    private Rect srcRect = new Rect();
    private int height;
    private long setOn;
    private float originalX, originalY;
    public Call() {
        super(Metrics.width / 2, Metrics.height / 3,
                Metrics.width / 3, Metrics.width / 15,
                R.mipmap.calls);
        height = bitmap.getHeight() / 5;
        srcRect.set(0, 0, bitmap.getWidth(), height);
        originalX = dstRect.left;
        originalY = dstRect.top;
    }
    public enum Type {
        perfect, great, good, bad, miss
    }
    public void set(Type type) {
        int index = type.ordinal();
        srcRect.offsetTo(0, index * height);
        //Log.d("Call", "type=" + type + " index=" + index + " rect=" + srcRect);
        setOn = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        int elapsed = (int) (System.currentTimeMillis() - setOn);
        if (elapsed < 1000) {
            if (elapsed > 500) elapsed = 500;
            float y = originalY - elapsed * dstRect.height() / 1000.0f;
            dstRect.offsetTo(originalX, y);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }
    }
}
