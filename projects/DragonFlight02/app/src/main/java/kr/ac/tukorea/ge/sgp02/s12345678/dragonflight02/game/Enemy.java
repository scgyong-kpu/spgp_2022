package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {
    private final int level;
    protected float dy;
    protected RectF boundingRect = new RectF();

    protected static int[] BITMAP_IDS = {
            R.mipmap.f_01_01,R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,
            R.mipmap.f_05_01,R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,
            R.mipmap.f_09_01,R.mipmap.f_10_01,
    };
    public static final int MAX_LEVEL = BITMAP_IDS.length;

    public Enemy(int level, float x, float speed) {
//        super(x, 0, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, -size/2, size, size, BITMAP_IDS[level - 1]);
        this.level = level;
        dy = speed;
    }

    private static float size, inset;
    public static void setSize(float size) {
        Enemy.size = size;
        Enemy.inset = size / 16;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public RectF getBoundingRect() {
        return boundingRect;
    }
}
