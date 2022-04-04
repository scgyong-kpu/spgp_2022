package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ball {

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
    private int dx, dy;

    public Ball(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        dstRect.set(0, 0, 200, 200);
    }

    public static void setBitmap(Bitmap bitmap) {
        Ball.bitmap = bitmap;
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        dstRect.offset(dx, dy);
        if (dx > 0) {
            if (dstRect.right > GameView.view.getWidth()) {
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > GameView.view.getHeight()) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
