package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game.MainGame;

public class AnimSprite extends Sprite {
    protected final int imageWidth;
    protected final int imageHeight;
    protected final int frameCount;
    private final float framesPerSecond;
    private Rect srcRect = new Rect();
    private float time;

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, float framesPerSecond, int frameCount) {
        super(x, y, w, h, bitmapResId);

        imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            imageWidth = imageHeight;
            frameCount = bitmap.getWidth() / imageHeight;
        } else {
            imageWidth = bitmap.getWidth() / frameCount;
        }

        this.framesPerSecond = framesPerSecond;
        this.frameCount = frameCount;
        srcRect.set(0, 0, imageWidth, imageHeight);
    }

    @Override
    public void update() {
        time += MainGame.getInstance().frameTime;
        int frameIndex = Math.round(time * framesPerSecond) % frameCount;
        srcRect.set(frameIndex * imageWidth, 0,
                (frameIndex + 1) * imageWidth, imageHeight);
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
