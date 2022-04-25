package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    protected final int imageWidth;
    protected final int imageHeight;
    protected final int frameCount;
    private Rect srcRect = new Rect();

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, int frameCount) {
        super(x, y, w, h, bitmapResId);

        imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            imageWidth = imageHeight;
            frameCount = bitmap.getWidth() / imageHeight;
        } else {
            imageWidth = bitmap.getWidth() / frameCount;
        }

        this.frameCount = frameCount;
        srcRect.set(0, 0, imageWidth, imageHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
