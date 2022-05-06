package net.scgyong.and.cookierun.game;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.BaseGame;
import net.scgyong.and.cookierun.framework.objects.HorzScrollBackground;
import net.scgyong.and.cookierun.framework.objects.Sprite;
import net.scgyong.and.cookierun.framework.res.Metrics;

public class MainGame extends BaseGame {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static final String TAG = MainGame.class.getSimpleName();

    public static MainGame get() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return (MainGame)singleton;
    }
    public enum Layer {
        bg, platform, item, player, controller, COUNT
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
        showsBoxCollidables = true;

        initLayers(Layer.COUNT.ordinal());

//        Sprite player = new Sprite(
//                size(2), size(7),
//                size(2), size(2),
//                R.mipmap.cookie);
        Player player = new Player(
                size(2), size(7),
                size(2), size(2)
        );
        add(Layer.player.ordinal(), player);
        add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_1, Metrics.size(R.dimen.bg_scroll_1)));
        add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_2, Metrics.size(R.dimen.bg_scroll_2)));
        add(Layer.bg.ordinal(), new HorzScrollBackground(R.mipmap.cookie_run_bg_3, Metrics.size(R.dimen.bg_scroll_3)));

        MapLoader.get().init(mapIndex);
        add(Layer.controller.ordinal(), MapLoader.get());
//        add(Layer.platform.ordinal(), Platform.get(Platform.Type.T_10x2, 1, 7));
//        add(Layer.platform.ordinal(), Platform.get(Platform.Type.T_2x2, 11, 8));
//        add(Layer.platform.ordinal(), Platform.get(Platform.Type.T_2x2, 13, 7));
//        add(Layer.platform.ordinal(), Platform.get(Platform.Type.T_3x1, 6, 4));
//        add(Layer.platform.ordinal(), Platform.get(Platform.Type.T_3x1, 9, 4));
//
//        add(Layer.item.ordinal(), JellyItem.get(2, 5, 3));
//        add(Layer.item.ordinal(), JellyItem.get(3, 6, 3));
//        add(Layer.item.ordinal(), JellyItem.get(4, 7, 3));
//        add(Layer.item.ordinal(), JellyItem.get(5, 8, 3));
    }

}
