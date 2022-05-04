package kr.ac.tukorea.ge.sgp02.s12345678.cookierun.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.R;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.AnimSprite;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.BitmapPool;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.Recyclable;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.RecycleBin;


public class Enemy extends AnimSprite implements BoxCollidable, Recyclable {
    public static final float FRAMES_PER_SECOND = 10.0f;
    private static final String TAG = Enemy.class.getSimpleName();
    public static float size;
    protected int level;
    protected float life, maxLife;
    protected Gauge gauge;
    protected float dy;
    protected RectF boundingBox = new RectF();
    protected static int[] bitmapIds = {
            R.mipmap.enemy_01,R.mipmap.enemy_02,R.mipmap.enemy_03,R.mipmap.enemy_04,R.mipmap.enemy_05,
            R.mipmap.enemy_06,R.mipmap.enemy_07,R.mipmap.enemy_08,R.mipmap.enemy_09,R.mipmap.enemy_10,
            R.mipmap.enemy_11,R.mipmap.enemy_12,R.mipmap.enemy_13,R.mipmap.enemy_14,R.mipmap.enemy_15,
            R.mipmap.enemy_16,R.mipmap.enemy_17,R.mipmap.enemy_18,R.mipmap.enemy_19,R.mipmap.enemy_20,
    };
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = bitmapIds.length;

//    protected static ArrayList<Enemy> recycleBin = new ArrayList<>();
    public static Enemy get(int level, float x, float speed) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
//            Enemy enemy = recycleBin.remove(0);
            enemy.set(level, x, speed);
            return enemy;
        }
        return new Enemy(level, x, speed);
    }

    private void set(int level, float x, float speed) {
        bitmap = BitmapPool.get(bitmapIds[level - 1]);
        this.x = x;
        this.y = -size;
        this.dy = speed;
        this.level = level;
        life = maxLife = level * 10;
        gauge.setValue(1.0f);
    }
    private Enemy(int level, float x, float speed) {
        super(x, -size, size, size, bitmapIds[level - 1], FRAMES_PER_SECOND, 0);
        this.level = level;
//        y -= radius;
//        setDstRectWithRadius();
        dy = speed;
        life = maxLife = level * 10;
        gauge = new Gauge(
                Metrics.size(R.dimen.enemy_gauge_fg_width), R.color.enemy_gauge_fg,
                Metrics.size(R.dimen.enemy_gauge_bg_width), R.color.enemy_gauge_bg,
                size * 0.9f
        );
        gauge.setValue(1.0f);
        Log.d(TAG, "Created: " + this);
    }

    @Override
    public void update() {
//        super.update();

        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        boundingBox.set(dstRect);
        boundingBox.inset(size/16, size/16);
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
            //recycleBin.add(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gauge.draw(canvas, x, y + size * 0.5f);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }

    @Override
    public void finish() {

    }

    public int getScore() {
        return level * level * 100;
    }

    public boolean decreaseLife(float power) {
        life -= power;
        if (life <= 0) return true;

        gauge.setValue((float)life / maxLife);
        return false;
    }
}
