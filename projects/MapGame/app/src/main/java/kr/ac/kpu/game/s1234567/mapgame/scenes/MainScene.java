package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.interfaces.GameObject;
import kr.ac.kpu.game.framework.objects.Score;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class MainScene extends Scene implements TowerMenu.Listener {
    public static final String PARAM_STAGE_INDEX = "stage_index";
    private static MainScene singleton;
    private TiledSprite tiledSprite;
    private Selector selector;
    private TowerMenu towerMenu;
    public Score score;

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
        tile, cannon, enemy, shell, explosion, score, selection, controller, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        tiledSprite = new TiledSprite();
        tiledSprite.map.wraps = true;
        add(Layer.tile.ordinal(), tiledSprite);

        add(Layer.controller.ordinal(), new FlyGen());

        selector = new Selector();
        selector.select(-1, -1);
        add(Layer.selection.ordinal(), selector);

        towerMenu = new TowerMenu(this);
        add(Layer.selection.ordinal(), towerMenu);

        score = new Score(R.mipmap.gold_number,
                TiledSprite.unit / 2.0f,TiledSprite.unit / 2.0f,
                TiledSprite.unit * 1.2f);
        score.set(100);
        add(Layer.score.ordinal(), score);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        if (towerMenu.onTouchEvent(event)) {
            return true;
        }
        int x = (int) (event.getX() / TiledSprite.unit);
        int y = (int) (event.getY() / TiledSprite.unit);
        int tileIndex = tiledSprite.map.getTileAt(x, y);
        //Log.d("MainScene", "("+x+","+y+")"+tileIndex);
        if (tileIndex != TiledSprite.TILEINDEX_BRICK) {
            selector.select(-1, -1);
            towerMenu.setMenu(-1, -1);
            return false;
        }
        Cannon cannon = selector.select(x, y);
        if (cannon != null) {
            towerMenu.setMenu(x, y,
                    R.mipmap.uninstall,
                    R.mipmap.upgrade);
        } else {
            towerMenu.setMenu(x, y,
                    R.mipmap.f_01_01,
                    R.mipmap.f_02_01,
                    R.mipmap.f_03_01);
        }
        return true;
    }

    @Override
    public void onMenuSelected(int menuMipmapResId) {

        Cannon cannon = selector.getCannon();
        if (cannon != null) {
            switch (menuMipmapResId) {
                case R.mipmap.upgrade:
                    int cost = cannon.getUpgradeCost();
                    if (score.get() < cost) {
                        return;
                    }
                    score.add(-cost);
                    cannon.upgrade();
                    break;
                case R.mipmap.uninstall:
                    selector.remove();
                    int price = cannon.getSellPrice();
                    score.add(price);
                    remove(cannon);
                    break;
            }
            selector.select(-1, -1);
            towerMenu.setMenu(-1, -1);
            return;
        }
        int level = 0;
        switch (menuMipmapResId) {
            case R.mipmap.f_01_01:
                level = 1;
                break;
            case R.mipmap.f_02_01:
                level = 2;
                break;
            case R.mipmap.f_03_01:
                level = 3;
                break;
            default:
                return;
        }
        int cost = Cannon.getInstallCost(level);
        if (score.get() < cost) {
            return;
        }
        score.add(-cost);
        cannon = new Cannon(level, selector.getX(), selector.getY(), 10, 2);
        selector.install(cannon);
        add(Layer.cannon.ordinal(), cannon);
        selector.select(-1, -1);
        towerMenu.setMenu(-1, -1);
    }
}
