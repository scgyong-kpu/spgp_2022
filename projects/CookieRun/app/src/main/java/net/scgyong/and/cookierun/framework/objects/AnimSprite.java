package net.scgyong.and.cookierun.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    private final float framesPerSecond;
    private final int frameCount;
    private final int imageWidth;
    private final int imageHeight;

    private Rect srcRect = new Rect();
//    private float time;
    private long createdOn;

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, float framesPerSecond, int frameCount) {
        super(x, y, w, h, bitmapResId);
        int imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        this.framesPerSecond = framesPerSecond;
        if (frameCount == 0) {
            frameCount = imageWidth / imageHeight;
            imageWidth = imageHeight;
        } else {
            imageWidth = imageWidth / frameCount;
        }
        this.imageWidth = imageWidth;
        this.frameCount = frameCount;

        createdOn = System.currentTimeMillis();
    }

    @Override
    public void update(float frameTime) {
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int index = Math.round(time * framesPerSecond) % frameCount;
        srcRect.set(index * imageWidth, 0, (index + 1) * imageWidth, imageHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
