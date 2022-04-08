package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Sprite;

public class Enemy extends Sprite {
    protected float dy;
    public Enemy(float x, float y, float speed) {
        super(x, y, R.dimen.enemy_radius, R.mipmap.f_01_01);
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
