package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;

import kr.ac.kpu.game.framework.map.MapLoader;
import kr.ac.kpu.game.framework.map.TiledMap;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.view.GameView;

public class TiledSprite extends Sprite {
    public final TiledMap map;

    public TiledSprite() {
        this.map = new MapLoader(GameView.view.getContext()).loadAsset("map", "desert.tmj");
        map.setDstTileSize(100);
    }

    @Override
    public void update(float frameTime) {
        int speed = 300;
        map.x += speed * frameTime;
        map.y += speed * frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas);
    }
}
