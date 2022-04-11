package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter extends Sprite {

    private static final String TAG = Fighter.class.getSimpleName();
    private Bitmap targetBitmap;
    private RectF targetRect = new RectF();

    private float angle;
    private float dx, dy;
    private float tx, ty;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);
        angle = -(float) (Math.PI / 2);

        targetBitmap = BitmapPool.get(R.mipmap.target);
    }

    public void update() {
        if (dx == 0 && dy == 0)
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
        float dy = this.dy * frameTime;
        if ((dy > 0 && y + dy > ty) || (dy < 0 && y + dy < ty)) {
            dy = ty - y;
            y = ty;
            this.dy = 0;
        } else {
            y += dy;
        }
//        Log.d(TAG, "x="+x+" y="+y+" dx="+dx+" dy="+dy);
        dstRect.offset(dx, dy);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
        if (dx != 0 || dy != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        targetRect.set(tx - radius/2, ty - radius/2,
                tx + radius/2, ty + radius/2);

        angle = (float) Math.atan2(ty - this.y, tx - this.x);
        float speed = Metrics.size(R.dimen.fighter_speed);
//        float dist = speed * MainGame.getInstance().frameTime;
//        Log.d(TAG, "speed=" + speed+" dist="+dist);
        dx = (float) (speed * Math.cos(angle));
        dy = (float) (speed * Math.sin(angle));
    }

    public void fire() {
        Bullet bullet = new Bullet(x, y, angle);
        MainGame.getInstance().add(bullet);
    }
}
