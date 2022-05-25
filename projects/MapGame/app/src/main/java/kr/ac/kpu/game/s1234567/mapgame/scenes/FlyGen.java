package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;

import kr.ac.kpu.game.framework.interfaces.GameObject;
import kr.ac.kpu.game.framework.res.Metrics;

public class FlyGen implements GameObject {
    private static final float GEN_INTERVAL = 3.0f;
    private float time;
    private float speed;

    public FlyGen() {
        speed = Metrics.height / 9;
    }

    @Override
    public void update(float frameTime) {
        time += frameTime;
        if (time > GEN_INTERVAL) {
            spawn();
            time -= GEN_INTERVAL;
        }
    }

    private void spawn() {
        Fly fly = Fly.get(Fly.Type.RANDOM, speed);
        MainScene.get().add(MainScene.Layer.enemy.ordinal(), fly);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
