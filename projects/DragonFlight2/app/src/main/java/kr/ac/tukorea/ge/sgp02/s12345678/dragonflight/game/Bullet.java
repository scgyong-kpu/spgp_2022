package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;

public class Bullet implements GameObject, BoxCollidable {
    protected float x, y;
    protected final float length;
    protected final float dx, dy;
    protected final float ex, ey;

    protected static Paint paint;
    protected static float laserWidth;

    public Bullet(float x, float y, float angle) {
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
    }
    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        x += dx * frameTime;
        y += dy * frameTime;

        if (y < 0) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x + ex, y + ey, paint);
    }

    @Override
    public RectF getBoundingRect() {
        float hw = laserWidth / 2;
        return new RectF(x - hw, y, x + hw, y + ey);
    }
}
