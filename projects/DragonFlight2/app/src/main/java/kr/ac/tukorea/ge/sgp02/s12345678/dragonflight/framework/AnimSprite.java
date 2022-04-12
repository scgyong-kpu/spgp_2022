package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game.MainGame;

public class AnimSprite extends Sprite {
    private final float framesPerSecond;
    private final int frameCount;
    protected Rect srcRect = new Rect();
    protected float time;
    protected int imageWidth, imageHeight;

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, float framesPerSecond, int frameCount) {
        super(x, y, w, h, bitmapResId);
        this.framesPerSecond = framesPerSecond;
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            frameCount = imageWidth / imageHeight;
            imageWidth = imageHeight;
        } else {
            imageWidth = imageWidth / frameCount;
        }
        this.frameCount = frameCount;
    }

    @Override
    public void update() {
        this.time += MainGame.getInstance().frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        int index = Math.round(time * framesPerSecond) % frameCount;
        srcRect.set(index * imageWidth, 0, (index + 1) * imageWidth, imageHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
