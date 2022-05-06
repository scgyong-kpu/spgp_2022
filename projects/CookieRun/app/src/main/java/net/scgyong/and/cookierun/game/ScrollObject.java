package net.scgyong.and.cookierun.game;

import android.graphics.RectF;
import android.util.Log;

import net.scgyong.and.cookierun.framework.game.BaseGame;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.Recyclable;
import net.scgyong.and.cookierun.framework.objects.Sprite;

public class ScrollObject extends Sprite implements Recyclable, BoxCollidable {
    private static final String TAG = ScrollObject.class.getSimpleName();

    protected ScrollObject() {
        Log.d(TAG, "New:" + this);
    }

    @Override
    public void update(float frameTime) {
        float speed = -200;
        float dx = speed * frameTime;
        dstRect.offset(dx, 0);
        if (dstRect.right < 0) {
            Log.d(TAG, "Removing:" + this);
            BaseGame.getInstance().remove(this);
        }
    }

    @Override
    public void finish() {
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }
}
