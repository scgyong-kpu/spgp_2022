package net.scgyong.and.cookierun.framework.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import net.scgyong.and.cookierun.BuildConfig;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;

import java.util.ArrayList;

import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.interfaces.Recyclable;
import net.scgyong.and.cookierun.framework.interfaces.Touchable;
import net.scgyong.and.cookierun.framework.view.GameView;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    protected float frameTime, elapsedTime;

    public static Scene getTopScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex < 0) return null;
        return sceneStack.get(lastIndex);
    }

    public static void clear() {
        while (sceneStack.size() > 0) {
            Scene scene = sceneStack.remove(0);
            scene.end();
        }
        sceneStack.clear();
    }

    protected ArrayList<ArrayList<GameObject>> layers;
    protected Paint collisionPaint;

    protected static ArrayList<Scene> sceneStack = new ArrayList<>();
//    public Scene getTopScene() {
//    }
    public static void start(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in start): " + top);
            top.end();
            sceneStack.set(lastIndex, scene);
        } else {
            sceneStack.add(scene);
        }
        Log.d(TAG, "Starting(in start): " + scene);
        scene.start();
    }
    public static void push(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Pausing: " + top);
            top.pause();
        }
        sceneStack.add(scene);
        Log.d(TAG, "Starting(in push): " + scene);
        scene.start();
    }
    public static void popScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in pop): " + top);
            top.end();
        }
        lastIndex--;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Resuming: " + top);
            top.resume();
        } else {
            Log.e(TAG, "should end app in popScene()");
        }
    }

    public void init() {
        if (BuildConfig.showsCollisionBox) {
            collisionPaint = new Paint();
            collisionPaint.setStyle(Paint.Style.STROKE);
            collisionPaint.setColor(Color.RED);
        }

        elapsedTime = 0;
    }

    public boolean isTransparent() { return false; }
    public void start(){}
    public void pause(){}
    public void resume(){}
    public void end(){}

    protected void initLayers(int count) {
        layers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        elapsedTime += frameTime;
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.update(frameTime);
            }
        }
    }

    public void draw(Canvas canvas) {
        draw(canvas, sceneStack.size() - 1);
    }
    protected void draw(Canvas canvas, int index) {
        Scene scene = sceneStack.get(index);
        if (scene.isTransparent() && index > 0) {
            draw(canvas, index - 1);
        }
        ArrayList<ArrayList<GameObject>> layers = scene.layers;
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.draw(canvas);
            }
        }
        if (BuildConfig.showsCollisionBox) {
            drawBoxCollidables(canvas, layers);
        }
    }

    public void drawBoxCollidables(Canvas canvas, ArrayList<ArrayList<GameObject>> layers) {
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                if (gobj instanceof BoxCollidable) {
                    RectF box = ((BoxCollidable) gobj).getBoundingRect();
                    canvas.drawRect(box, collisionPaint);
                }
            }
        }
    }

    public void add(int layerIndex, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> gameObjects = layers.get(layerIndex);
                gameObjects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> gameObjects : layers) {
                    boolean removed = gameObjects.remove(gameObject);
                    if (!removed) continue;
                    if (gameObject instanceof Recyclable) {
                        Recyclable recyclable = (Recyclable) gameObject;
                        recyclable.finish();
                        RecycleBin.add(recyclable);
                    }
                    break;
                }
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchLayer = getTouchLayerIndex();
        if (touchLayer < 0) return false;
        ArrayList<GameObject> gameObjects = layers.get(touchLayer);
        for (GameObject gobj : gameObjects) {
            if (!(gobj instanceof Touchable)) {
                continue;
            }
            boolean processed = ((Touchable) gobj).onTouchEvent(event);
            if (processed) return true;
        }
        return false;
    }

    protected int getTouchLayerIndex() {
        return -1;
    }

    public ArrayList<GameObject> objectsAt(int layerIndex) {
        return layers.get(layerIndex);
    }

    public int objectCount() {
        if (layers == null) return 0;
        int count = 0;
        for (ArrayList<GameObject> gameObjects : layers) {
            count += gameObjects.size();
        }
        return count;
    }

    public void finish() {
        GameView.view.getActivity().finish();
    }

    public boolean handleBackKey() {
        return false;
    }
}
