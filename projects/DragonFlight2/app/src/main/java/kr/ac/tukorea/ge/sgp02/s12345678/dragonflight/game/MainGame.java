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
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Recyclable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;
    private Paint collisionPaint;

    public enum Layer {
        enemy, bullet, player, controller, COUNT
    }
    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }
    private static final int BALL_COUNT = 10;
//    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    protected ArrayList<ArrayList<GameObject>> layers;
    private Fighter fighter;
    public float frameTime;

    public static void clear() {
        singleton = null;
    }

    public void init() {
        initLayers(Layer.COUNT.ordinal());
//        gameObjects.clear();

        add(Layer.controller, new EnemyGenerator());

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        add(Layer.player, fighter);

        collisionPaint = new Paint();
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setColor(Color.RED);
    }

    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
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
//                    fighter.fire();
//                }
                return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject gobj : objects) {
                gobj.draw(canvas);
                if (gobj instanceof BoxCollidable) {
                    RectF box = ((BoxCollidable) gobj).getBoundingRect();
                    canvas.drawRect(box, collisionPaint);
                }
            }
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }

        checkCollision();
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }

    private void checkCollision() {
        ArrayList<GameObject> enemies = objectsAt(Layer.enemy);
        ArrayList<GameObject> bullets = objectsAt(Layer.bullet);
        for (GameObject o1 : enemies) {
//            if (!(o1 instanceof Enemy)) { continue; }
            Enemy enemy = (Enemy) o1;
            boolean removed = false;
            for (GameObject o2 : bullets) {
//                if (!(o2 instanceof Bullet)) { continue; }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !!");
                    remove(bullet);
                    remove(enemy);
                    removed = true;
                    break;
                }
            }
            if (removed) {
                continue;
            }
            // check enemy vs fighter
        }
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> objects: layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            RecycleBin.collect((Recyclable) gameObject);
                        }
                        break;
                    }
                }
            }
        });
    }

    public int objectCount() {
        int count = 0;
        for (ArrayList<GameObject> objects: layers) {
            count += objects.size();
        }
        return count;
    }
}
