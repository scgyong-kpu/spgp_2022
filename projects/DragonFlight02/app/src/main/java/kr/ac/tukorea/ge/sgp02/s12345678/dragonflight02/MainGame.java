package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class MainGame {
    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    private MainGame() {
    }

    private static MainGame singleton;

    private static final int BALL_COUNT = 10;
    private ArrayList<GameObject> objects = new ArrayList<>();
    private Fighter fighter;

    public void init() {
//        Random random = new Random();
//        float min = Metrics.size(R.dimen.ball_speed_min);
//        float max = Metrics.size(R.dimen.ball_speed_max);
//        float diff = max - min;
//        for (int i = 0; i < BALL_COUNT; i++) {
//            float dx = random.nextFloat() * diff + min;
//            float dy = random.nextFloat() * diff + min;
//            Ball ball = new Ball(dx, dy);
//            objects.add(ball);
//        }

        fighter = new Fighter(Metrics.width / 2, Metrics.height / 2);
        objects.add(fighter);
    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        for (GameObject gobj : objects) {
            gobj.update();
        }
    }

    public void draw(Canvas canvas) {
        for (GameObject gobj : objects) {
            gobj.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);
                if (action == MotionEvent.ACTION_DOWN) {
                    fighter.fire();
                }
                return true;
        }
        return false;
    }

    public void add(GameObject gameObject) {
        objects.add(gameObject);
    }
}
