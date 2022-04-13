package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private final float spawnInterval;
    private final float fallSpeed;
    private float elapsedTime;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        Enemy enemy = new Enemy(Metrics.width / 2, fallSpeed);
        MainGame.getInstance().add(enemy);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
