package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Recyclable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.RecycleBin;

public class MainGame extends BaseGame {
    private static final String TAG = MainGame.class.getSimpleName();
    Score score;

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

    public void init() {

        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        add(Layer.player, fighter);

//        score = new Score();
        int mipmapResId = R.mipmap.number_24x32;
        float marginTop = Metrics.size(R.dimen.score_margin_top);
        float marginRight = Metrics.size(R.dimen.score_margin_right);
        float charWidth = Metrics.size(R.dimen.score_digit_width);
        score = new Score(mipmapResId, marginTop, marginRight, charWidth);
//        score.set(123456);
        add(Layer.ui, score);

        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, Metrics.size(R.dimen.bg_speed_city)));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, Metrics.size(R.dimen.bg_speed_cloud)));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    public void add(Layer layer, GameObject gameObject) {
        add(layer.ordinal(), gameObject);
    }
}
