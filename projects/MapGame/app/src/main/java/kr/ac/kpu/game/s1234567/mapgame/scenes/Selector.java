package kr.ac.kpu.game.s1234567.mapgame.scenes;

import java.util.HashMap;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Selector extends Sprite {
    private int xUnit, yUnit;
    private Cannon cannon;
    private HashMap<Integer, Cannon> cannons = new HashMap<>();

    public Selector() {
        super(0, 0, TiledSprite.unit, TiledSprite.unit, R.mipmap.selection);
    }
    public Cannon getCannon() {
        return cannon;
    }
    public int getUnitKey() {
        return getUnitKey(xUnit, yUnit);
    }
    public static int getUnitKey(int x, int y) {
        return x * 1000 + y;
    }

    public void show(int xUnit, int yUnit) {
        this.xUnit = xUnit;
        this.yUnit = yUnit;
        this.cannon = cannons.get(getUnitKey(xUnit, yUnit));
        float left = xUnit * TiledSprite.unit;
        float top = yUnit * TiledSprite.unit;
        dstRect.set(left, top, left + TiledSprite.unit, top + TiledSprite.unit);
        x = left + TiledSprite.unit / 2;
        y = top + TiledSprite.unit / 2;
    }

    public void install(Cannon cannon) {
        cannons.put(getUnitKey(), cannon);
    }
}
