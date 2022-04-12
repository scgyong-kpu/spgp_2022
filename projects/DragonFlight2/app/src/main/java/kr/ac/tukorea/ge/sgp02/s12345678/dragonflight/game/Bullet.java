package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Recyclable;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    protected float x, y;
    protected final float length;
    protected final float dy;
    protected static Paint paint;
    protected static float width;
    protected RectF boundingRect = new RectF();
    protected Bullet(float x, float y) {
        this.x = x;
        this.y = y;
        this.length = Metrics.size(R.dimen.laser_length);
        float speed = Metrics.size(R.dimen.laser_speed);
        this.dy = -speed;

        Log.d(TAG, "Creating Bullet: " + x + "," + y);
        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            width = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(width);
        }
    }
    public static Bullet get(float x, float y) {
        Bullet bullet = (Bullet) MainGame.getInstance().get(Bullet.class);
        if (bullet == null) {
            return new Bullet(x, y);
        }
        bullet.reuse(x, y);
        return bullet;
    }

    private void reuse(float x, float y) {
        this.x = x;
        this.y = y;
//        Log.d(TAG, "Reusing Bullet: " + x + "," + y);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        y += dy * frameTime;

        if (y < 0) {
            game.remove(this);
        }
//        Log.d(TAG, "update: " + this);
        boundingRect.set(x - width / 2, y - length, x + width / 2, y);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x, y - length, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingRect;
    }

    @Override
    public void recycle() {
//        Log.d(TAG, "Recycling");
    }
}
