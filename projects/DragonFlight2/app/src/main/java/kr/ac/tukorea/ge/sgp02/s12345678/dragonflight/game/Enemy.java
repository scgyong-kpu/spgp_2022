package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {
    public static float size;
    protected float dy;
    protected RectF boundingBox = new RectF();
    public Enemy(float x, float speed) {
        super(x, -size, size, size, R.mipmap.f_01_01);
//        y -= radius;
//        setDstRectWithRadius();
        dy = speed;
    }

    @Override
    public void update() {
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
