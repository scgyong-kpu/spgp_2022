package kr.ac.kpu.game.s1234567.mapgame.scenes;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Selector extends Sprite {
    public Selector() {
        super(0, 0, TiledSprite.unit, TiledSprite.unit, R.mipmap.selection);
    }

    public void show(int x, int y) {
        float left = x * TiledSprite.unit;
        float top = y * TiledSprite.unit;
        dstRect.set(left, top, left + TiledSprite.unit, top + TiledSprite.unit);
    }
}
