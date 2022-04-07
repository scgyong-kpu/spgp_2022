package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ball extends Sprite {
    private float dx, dy;

    public Ball(float dx, float dy) {
        super(100, 100, R.dimen.ball_radius, R.mipmap.soccer_ball_240);
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        float dx = this.dx * frameTime;
        float dy = this.dy * frameTime;
        dstRect.offset(dx, dy);
        if (dx < 0) {
            if (dstRect.left < 0) {
                this.dx = -this.dx;
            }
        } else {
            if (dstRect.right > Metrics.width) {
                this.dx = -this.dx;
            }
        }
        if (dy < 0) {
            if (dstRect.top < 0) {
                this.dy = -this.dy;
            }
        } else {
            if (dstRect.bottom > Metrics.height) {
                this.dy = -this.dy;
            }
        }
    }
}
