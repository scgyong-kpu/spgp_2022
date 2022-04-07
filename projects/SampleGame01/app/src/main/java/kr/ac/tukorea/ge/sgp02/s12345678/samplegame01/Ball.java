package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ball implements GameObject {
    private float dx, dy;
    private RectF dstRect = new RectF();

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    public Ball(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;

//        dstRect.set(0, 0, 200, 200);
        float radius = Metrics.size(R.dimen.ball_radius);
        float x = 100, y = 100;
        dstRect.set(x - radius, y - radius, x + radius, y + radius);

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
