package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Sprite;

public class Enemy extends Sprite {
    protected float dy;
    public Enemy(float x, float speed) {
        super(x, 0, R.dimen.enemy_radius, R.mipmap.f_01_01);
        dy = speed;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

}
