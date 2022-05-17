package net.scgyong.and.cookierun.game;

import android.graphics.RectF;

import net.scgyong.and.cookierun.framework.game.Scene;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.Recyclable;
import net.scgyong.and.cookierun.framework.objects.Sprite;

public class MapSprite extends Sprite implements Recyclable, BoxCollidable {
    private static final String TAG = MapSprite.class.getSimpleName();

    protected MapSprite() {
//        Log.d(TAG, "New:" + this);
    }
    protected boolean valid = true;

    protected void setUnitDstRect(float unitLeft, float unitTop, float unitWidth, float unitHeight) {
        MainScene game = MainScene.get();
        float left = game.size(unitLeft);
        float top = game.size(unitTop);
        dstRect.set(left, top, left + game.size(unitWidth), top + game.size(unitHeight));
    }

    @Override
    public void update(float frameTime) {
        float speed = MapLoader.get().speed;
        float dx = speed * frameTime;
        dstRect.offset(dx, 0);
        if (dstRect.right < 0) {
//            Log.d(TAG, "Removing:" + this);
            Scene.getTopScene().remove(this);
        }
    }

    protected void init() {
        valid = true;
    }

    @Override
    public void finish() {
        valid = false;
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }
}
