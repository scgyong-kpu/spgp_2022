package kr.ac.tukorea.ge.sgp02.s12345678.cookierun.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.R;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.game.BaseGame;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.interfaces.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.res.Metrics;

public class MainGame extends BaseGame {
    private static final String TAG = MainGame.class.getSimpleName();
    public static MainGame get() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return (MainGame)singleton;
    }

    public enum Layer {
        bg1, bullet, enemy, player, bg2, ui, controller, COUNT
    }
    private Fighter fighter;
    Score score;


    public void init() {

        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        add(Layer.player, fighter);

//        int mipmapResId = R.mipmap.number_24x32;
//        float marginTop = Metrics.size(R.dimen.score_margin_top);
//        float marginRight = Metrics.size(R.dimen.score_margin_right);
//        float charWidth = Metrics.size(R.dimen.score_digit_width);
//        score = new Score(mipmapResId, marginTop, marginRight, charWidth);
//        score.set(123456);
        add(Layer.ui, score);

        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, Metrics.size(R.dimen.bg_speed_city)));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, Metrics.size(R.dimen.bg_speed_cloud)));

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



//    private void checkCollision() {
//    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }

    public void add(Layer layer, GameObject gameObject) {
        add(layer.ordinal(), gameObject);
    }

}
