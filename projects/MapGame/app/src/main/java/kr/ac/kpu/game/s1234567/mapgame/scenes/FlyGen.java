package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.game.framework.interfaces.GameObject;
import kr.ac.kpu.game.framework.res.Metrics;

public class FlyGen implements GameObject {
    private static final float GEN_INTERVAL = 2.0f;
    private static final float MIN_INTERVAL = 0.1f;
    private float interval;
    private float time;
    private float speed;
    private static Random random = new Random();

    public FlyGen() {
        speed = Metrics.height / 9;
        interval = GEN_INTERVAL;
    }

    @Override
    public void update(float frameTime) {
        time += frameTime;
        if (time > interval) {
            spawn();
            time -= interval;
            interval *= 0.995;
            if (interval < MIN_INTERVAL) interval = MIN_INTERVAL;
        }
    }

    private void spawn() {
        float size = (float) (random.nextDouble() * 0.3 + 0.7);
        float speed = (float) (this.speed * (random.nextDouble() * 0.2 + 0.9));
        Fly fly = Fly.get(Fly.Type.RANDOM, speed, size);
        MainScene.get().add(MainScene.Layer.enemy.ordinal(), fly);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
