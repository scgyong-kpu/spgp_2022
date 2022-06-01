package kr.ac.kpu.game.s1234567.mapgame.scenes;

import kr.ac.kpu.game.framework.game.RecycleBin;
import kr.ac.kpu.game.framework.interfaces.Recyclable;
import kr.ac.kpu.game.framework.objects.AnimSprite;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Explosion extends AnimSprite implements Recyclable {
    public static Explosion get(float x, float y, float radius) {
        Explosion ex = (Explosion) RecycleBin.get(Explosion.class);
        if (ex == null) {
            ex = new Explosion();
        }
        ex.init(x, y, radius);
        return ex;
    }
    private Explosion() {
        super(0, 0, 0, 0, R.mipmap.explosion, 20, 0);
    }
    private void init(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        setDstRectWithRadius();
        createdOn = System.currentTimeMillis();
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        if (time > 19.0f/20.0f) {
            MainScene.get().remove(this);
        }
    }

    @Override
    public void finish() { }
}
