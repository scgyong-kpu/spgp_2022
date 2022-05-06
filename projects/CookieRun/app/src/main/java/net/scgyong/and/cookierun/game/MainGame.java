package net.scgyong.and.cookierun.game;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.BaseGame;
import net.scgyong.and.cookierun.framework.objects.Sprite;
import net.scgyong.and.cookierun.framework.res.Metrics;

public class MainGame extends BaseGame {
    private static final String TAG = MainGame.class.getSimpleName();

    public static MainGame get() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return (MainGame)singleton;
    }
    public enum Layer {
        player, COUNT
    }

    public void init() {
        initLayers(Layer.COUNT.ordinal());

        add(Layer.player.ordinal(), new Sprite(Metrics.width / 2, Metrics.height / 2, 500, 500, R.mipmap.cookie));
    }

}
