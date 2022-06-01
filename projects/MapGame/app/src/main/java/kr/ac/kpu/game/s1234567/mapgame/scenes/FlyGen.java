package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.game.framework.interfaces.GameObject;

public class FlyGen implements GameObject {
    private static final float GEN_INTERVAL = 5.0f;
    private static final float MIN_INTERVAL = 0.1f;
    private static final float WAVE_INTERVAL = 30.0f;
    private float time, interval;
    private float waveTime, waveInteral;
    private float speed;
    private static Random random = new Random();
    private int wave;
    private boolean normalPhase;

    public FlyGen() {
        speed = 2 * TiledSprite.unit;
        interval = GEN_INTERVAL;
        waveInteral = WAVE_INTERVAL;
        wave = 0;
        normalPhase = true;
    }

    @Override
    public void update(float frameTime) {
        waveTime += frameTime;
        if (normalPhase) {
            time += frameTime;
            if (time > interval) {
                spawn(false);
                time -= interval;
                interval *= 0.995;
                if (interval < MIN_INTERVAL) interval = MIN_INTERVAL;
            }
            if (waveTime > waveInteral) {
                spawn(true);
                waveTime = 0;
                normalPhase = false;
            }
        } else {
            final int layerIndex = MainScene.Layer.enemy.ordinal();
            if (waveTime > waveInteral || MainScene.get().objectsAt(layerIndex).size() == 0) {
                waveTime = 0;
                normalPhase = true;
            }
        }
    }

    private void spawn(boolean boss) {
        float size = (float) (random.nextDouble() * 0.3 + 0.7);
        float speed = (float) (this.speed * (random.nextDouble() * 0.2 + 0.9));
        Fly.Type type = Fly.Type.boss;
        if (boss) {
            size *= 1.5;
        } else {
            type = Fly.Type.RANDOM;
        }
        Fly fly = Fly.get(type, speed, size);
        MainScene.get().add(MainScene.Layer.enemy.ordinal(), fly);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
