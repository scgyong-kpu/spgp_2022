package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game.MainGame;

public class AnimSprite extends Sprite {
    private final float framesPerSecond;
    private final int frameCount;
    private final int imageWidth;
    private final int imageHeight;

    private Rect srcRect = new Rect();
    private float time;

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
    }

    @Override
    public void update() {
        this.time += MainGame.getInstance().frameTime;
        int index = Math.round(time * framesPerSecond) % frameCount;
        srcRect.set(index * imageWidth, 0, (index + 1) * imageWidth, imageHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
