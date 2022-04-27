package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.AnimSprite;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Sprite;

public class Enemy extends AnimSprite implements BoxCollidable {
    public static final float FRAMES_PER_SECOND = 10.0f;
    public static float size;
    protected final int level;
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

    public static Enemy get(int level, float x, float speed) {
        return new Enemy(level, x, speed);
    }
    private Enemy(int level, float x, float speed) {
        super(x, -size, size, size, bitmapIds[level - 1], FRAMES_PER_SECOND, 0);
        this.level = level;
//        y -= radius;
//        setDstRectWithRadius();
        dy = speed;
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
        }
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }
}
