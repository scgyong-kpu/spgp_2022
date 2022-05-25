package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.AnticipateOvershootInterpolator;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.res.Metrics;

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

    public enum Layer {
        tile, enemy, controller, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        TiledSprite ts = new TiledSprite();
        ts.map.wraps = true;
        add(Layer.tile.ordinal(), ts);

        add(Layer.controller.ordinal(), new FlyGen());

//        ObjectAnimator anim = ObjectAnimator
//                .ofFloat(ts.map, "dstTileSize", 100, 200)
//                .setDuration(2000);
//        anim.setRepeatMode(ObjectAnimator.REVERSE);
//        anim.setRepeatCount(ObjectAnimator.INFINITE);
//        anim.start();
    }
}
