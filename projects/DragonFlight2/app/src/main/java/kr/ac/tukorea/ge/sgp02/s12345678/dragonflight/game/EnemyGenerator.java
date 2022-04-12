package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private float elapsedTime;
    private float spawnInterval;
    private float fallSpeed;
    private int wave;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        Enemy.size = Metrics.width / 5f * 0.8f;
        wave = 0;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime * 10;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        wave++;
        float tenth = Metrics.width / 10f;
        Random r = new Random();
        for (int i = 1; i <= 9; i += 2) {
//            int level = r.nextInt(10);
            int level = wave / 10 - r.nextInt(3);
            if (level < Enemy.MIN_LEVEL) level = Enemy.MIN_LEVEL;
            if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;
            Enemy enemy = new Enemy(level, i * tenth, 0, fallSpeed);
            MainGame.getInstance().add(enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
