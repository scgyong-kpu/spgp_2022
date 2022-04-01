package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ball {
    private int dx, dy;
    private Rect dstRect = new Rect();

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    public Ball(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;

        dstRect.set(0, 0, 200, 200);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void update() {
        dstRect.offset(dx, dy);
        if (dx < 0) {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        } else {
            if (dstRect.right > GameView.view.getWidth()) {
                dx = -dx;
            }
        }
        if (dy < 0) {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        } else {
            if (dstRect.bottom > GameView.view.getHeight()) {
                dy = -dy;
            }
        }
    }
}
