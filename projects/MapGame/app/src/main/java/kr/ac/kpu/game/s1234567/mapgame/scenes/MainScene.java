package kr.ac.kpu.game.s1234567.mapgame.scenes;

import java.util.ArrayList;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.interfaces.GameObject;

public class MainScene extends Scene {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static MainScene singleton;
    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }

    public void setMapIndex(int stageIndex) {

    }

    public Fly findNearestFly(Cannon cannon) {
        float dist = Float.MAX_VALUE;
        Fly nearest = null;
        float cx = cannon.getX();
        float cy = cannon.getY();
        ArrayList<GameObject> flies = objectsAt(Layer.enemy.ordinal());
        for (GameObject gameObject: flies) {
            if (!(gameObject instanceof Fly)) continue;
            Fly fly = (Fly) gameObject;
            float fx = fly.getX();
            float fy = fly.getY();
            float dx = cx - fx;
            if (dx > dist) continue;
            float dy = cy - fy;
            if (dy > dist) continue;
            float d = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > d) {
                dist = d;
                nearest = fly;
            }
        }
        return nearest;
    }

    public enum Layer {
        tile, cannon, enemy, shell, controller, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        TiledSprite ts = new TiledSprite();
        ts.map.wraps = true;
        add(Layer.tile.ordinal(), ts);

        add(Layer.controller.ordinal(), new FlyGen());

        add(Layer.cannon.ordinal(), new Cannon(1, 400, 400, 1, 4.0f));
        add(Layer.cannon.ordinal(), new Cannon(2, 1200, 400, 1, 3.0f));
        add(Layer.cannon.ordinal(), new Cannon(3, 2000, 500, 1, 2.0f));

//        ObjectAnimator anim = ObjectAnimator
//                .ofFloat(ts.map, "dstTileSize", 100, 200)
//                .setDuration(2000);
//        anim.setRepeatMode(ObjectAnimator.REVERSE);
//        anim.setRepeatCount(ObjectAnimator.INFINITE);
//        anim.start();
    }
}
