package net.scgyong.and.cookierun.game;

import android.view.MotionEvent;

import net.scgyong.and.cookierun.BuildConfig;
import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.BaseGame;
import net.scgyong.and.cookierun.framework.objects.HorzScrollBackground;
import net.scgyong.and.cookierun.framework.objects.Sprite;
import net.scgyong.and.cookierun.framework.res.Metrics;

public class MainGame extends BaseGame {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static final String TAG = MainGame.class.getSimpleName();
    private Player player;

    public static MainGame get() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return (MainGame)singleton;
    }
    public enum Layer {
        bg, platform, item, player, ui, controller, COUNT
    }

    public float size(float unit) {
        return Metrics.height / 9.5f * unit;
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }

    protected int mapIndex;

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

//        Sprite player = new Sprite(
//                size(2), size(7),
//                size(2), size(2),
//                R.mipmap.cookie);
        player = new Player(
                size(2), size(2),
                size(2), size(2)
        );
        add(Layer.player.ordinal(), player);
        add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_1, Metrics.size(R.dimen.bg_scroll_1)));
        add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_2, Metrics.size(R.dimen.bg_scroll_2)));
        add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_3, Metrics.size(R.dimen.bg_scroll_3)));

        MapLoader mapLoader = MapLoader.get();
        mapLoader.init(mapIndex);
        add(Layer.controller.ordinal(), mapLoader);
        add(Layer.controller.ordinal(), new CollisionChecker(player));

        float btn_x = size(1.5f);
        float btn_y = size(8.75f);
        float btn_w = size(8.0f / 3.0f);
        float btn_h = size(1.0f);
        add(Layer.ui.ordinal(), new Sprite(btn_x, btn_y, btn_w, btn_h, R.mipmap.btn_jump_n));
        add(Layer.ui.ordinal(), new Sprite(Metrics.width - btn_x, btn_y, btn_w, btn_h, R.mipmap.btn_slide_n));
//        add(Layer.ui.ordinal(), new Sprite(Metrics.width - btn_x - btn_w, btn_y, btn_w, btn_h, R.mipmap.btn_fall_n));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            player.jump();
            return true;
        }
        return false;
    }
}
