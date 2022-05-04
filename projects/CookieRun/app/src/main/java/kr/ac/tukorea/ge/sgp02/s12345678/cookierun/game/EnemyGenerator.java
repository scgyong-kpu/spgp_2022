package kr.ac.tukorea.ge.sgp02.s12345678.cookierun.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.R;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.game.BaseGame;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.interfaces.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.res.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private final float spawnInterval;
    private final float fallSpeed;
    private float elapsedTime;
    private int wave;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        Enemy.size = Metrics.width / 5.0f * 0.9f;
        wave = 0;
    }

    @Override
    public void update() {
        float frameTime = BaseGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        wave++;
        Random r = new Random();
        float tenth = Metrics.width / 10;
        for (int i = 1; i <= 9; i += 2) {
            int level = (wave + 15) / 10 - r.nextInt(3);
            if (level < Enemy.MIN_LEVEL) level = Enemy.MIN_LEVEL;
            if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;
            Enemy enemy = Enemy.get(level, tenth * i, fallSpeed);
            MainGame.get().add(MainGame.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
