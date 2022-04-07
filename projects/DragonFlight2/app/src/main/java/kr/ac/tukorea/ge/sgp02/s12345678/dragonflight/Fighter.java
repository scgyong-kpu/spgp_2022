package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private RectF targetRect = new RectF();

    private float angle;
    private float dx, dy;
    private float tx, ty;

    private static Bitmap targetBitmap;
//    private static Rect srcRect;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);
        angle = -(float) (Math.PI / 2);

        targetBitmap = BitmapPool.get(R.mipmap.target);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
        if (dx != 0 && dy != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void update() {
        if (dx == 0 && dy == 0)
            return;

        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
        } else {
            x += dx;
        }
        if ((dy > 0 && y + dy > ty) || (dy < 0 && y + dy < ty)) {
            dy = ty - y;
            y = ty;
        } else {
            y += dy;
        }
        dstRect.offset(dx, dy);
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        targetRect.set(tx - radius/2, ty - radius/2,
                tx + radius/2, ty + radius/2);
        angle = (float) Math.atan2(ty - y, tx - x);
        float speed = Metrics.size(R.dimen.fighter_speed);
        float dist = speed * MainGame.getInstance().frameTime;
        dx = (float) (dist * Math.cos(angle));
        dy = (float) (dist * Math.sin(angle));
    }

    public void fire() {
        Bullet bullet = new Bullet(x, y, angle);
        MainGame.getInstance().add(bullet);
    }
}
