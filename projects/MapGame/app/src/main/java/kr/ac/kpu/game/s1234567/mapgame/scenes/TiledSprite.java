package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;

import kr.ac.kpu.game.framework.map.MapLoader;
import kr.ac.kpu.game.framework.map.TiledMap;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.view.GameView;

public class TiledSprite extends Sprite {
    private final TiledMap map;

    public TiledSprite() {
        this.map = new MapLoader(GameView.view.getContext()).loadAsset("map", "desert.tmj");
        map.setDstTileSize(100);
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas);
    }
}
