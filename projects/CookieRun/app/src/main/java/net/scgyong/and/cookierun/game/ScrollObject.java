package net.scgyong.and.cookierun.game;

import android.graphics.RectF;

import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.Recyclable;
import net.scgyong.and.cookierun.framework.objects.Sprite;

public class ScrollObject extends Sprite implements Recyclable, BoxCollidable {
    protected ScrollObject() {
    }

    @Override
    public void finish() {
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }
}
