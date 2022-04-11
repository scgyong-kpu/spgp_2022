package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter extends Sprite {

    private static final String TAG = Fighter.class.getSimpleName();
    private Bitmap targetBitmap;
    private RectF targetRect = new RectF();

    private float dx;
    private float tx;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);

        targetBitmap = BitmapPool.get(R.mipmap.target);
    }

    public void update() {
        if (dx == 0)
            return;

        float frameTime = MainGame.getInstance().frameTime;
        float dx = this.dx * frameTime;
        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
            this.dx = 0;
        } else {
            x += dx;
        }
        dstRect.offset(dx, 0);
    }

    public void draw(Canvas canvas) {
//        canvas.save();
//        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
//        canvas.restore();
        if (dx != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        targetRect.set(tx - radius/2, y - radius/2,
                tx + radius/2, y + radius/2);

        dx = Metrics.size(R.dimen.fighter_speed);
        if (tx < x) {
            dx = -dx;
        }
    }

    public void fire() {
        Bullet bullet = new Bullet(x, y);
        MainGame.getInstance().add(bullet);
    }
}
