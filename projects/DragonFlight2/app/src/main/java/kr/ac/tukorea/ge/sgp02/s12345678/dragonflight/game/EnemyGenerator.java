package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private float elapsedTime;
    private float spawnInterval;
    private float fallSpeed;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        Enemy.size = Metrics.width / 5f * 0.8f;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
            Log.d(TAG, "Object count = " + MainGame.getInstance().objectCount());
        }
    }

    private void spawn() {
        float tenth = Metrics.width / 10f;
        for (int i = 1; i <= 9; i += 2) {
            Enemy enemy = new Enemy(i * tenth, 0, fallSpeed);
            MainGame.getInstance().add(enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
