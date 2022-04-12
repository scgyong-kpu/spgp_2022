package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.CollisionHelper;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;
    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }
    private static final int BALL_COUNT = 10;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Fighter fighter;
    public float frameTime;
    public float playTime;

    private Paint collisionPaint;
    public void init() {
        gameObjects.clear();

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        gameObjects.add(fighter);
        gameObjects.add(new EnemyGenerator());

        collisionPaint = new Paint();
        collisionPaint.setColor(Color.RED);
        collisionPaint.setStrokeWidth(1);
        collisionPaint.setStyle(Paint.Style.STROKE);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setTargetPosition(x, y);
//                if (action == MotionEvent.ACTION_DOWN) {
//                    Log.d(TAG, "Object count = " + MainGame.getInstance().objectCount());
//                }
                return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (GameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }

        for (GameObject gobj : gameObjects) {
            if (gobj instanceof BoxCollidable) {
                RectF rect = ((BoxCollidable) gobj).getBoundingRect();
                canvas.drawRect(rect, collisionPaint);
            }
        }

    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        playTime += frameTime;
        for (GameObject gobj : gameObjects) {
            gobj.update();
        }

        boolean removed = false;
        for (GameObject o1 : gameObjects) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            for (GameObject o2 : gameObjects) {
                if (!(o2 instanceof Bullet)) {
                    continue;
                }

                if (CollisionHelper.collides((BoxCollidable)o1, (BoxCollidable)o2)) {
                    Log.d(TAG, "Collision!" + o1 + " - " + o2);
                    remove(o1);
                    remove(o2);
                    removed = true;
                    break;
                }
            }
            if (removed) {
                break;
            }
        }
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.remove(gameObject);
            }
        });
    }

    public int objectCount() {
        return gameObjects.size();
    }
}
