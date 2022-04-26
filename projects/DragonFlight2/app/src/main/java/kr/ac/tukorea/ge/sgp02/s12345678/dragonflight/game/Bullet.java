package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;

public class Bullet implements GameObject, BoxCollidable {
    private static final String TAG = Bullet.class.getSimpleName();
    protected float x, y;
    protected final float length;
    protected final float dx, dy;
    protected final float ex, ey;
    protected RectF boundingBox = new RectF();

    protected static Paint paint;
    protected static float laserWidth;

    protected static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bullet get(float x, float y, float angle) {
        if (recycleBin.size() > 0) {
//            Log.d(TAG, "get(): Recycle Bin has " + recycleBin.size() + " bullets");
            Bullet bullet = recycleBin.remove(0);
            bullet.reuse(x, y, angle);
            return bullet;
        }
        return new Bullet(x, y, angle);
    }
    private void reuse(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        // angle
    }
    private Bullet(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.length = Metrics.size(R.dimen.laser_length);
        float speed = Metrics.size(R.dimen.laser_speed);
        this.dx = (float) (speed * Math.cos(angle));
        this.dy = (float) (speed * Math.sin(angle));
        this.ex = (float) (length * Math.cos(angle));
        this.ey = (float) (length * Math.sin(angle));

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }
        Log.d(TAG, "Created: " + this);
    }
    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        x += dx * frameTime;
        y += dy * frameTime;

        float hw = laserWidth / 2;
        boundingBox.set(x - hw, y, x + hw, y + ey);

        if (y < 0) {
            game.remove(this);
            recycleBin.add(this);
//            Log.d(TAG, "remove(): Recycle Bin has " + recycleBin.size() + " bullets");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x + ex, y + ey, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }
}
