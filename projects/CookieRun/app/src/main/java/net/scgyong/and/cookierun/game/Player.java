package net.scgyong.and.cookierun.game;

import android.graphics.Rect;
import android.graphics.RectF;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.objects.SheetSprite;

public class Player extends SheetSprite implements BoxCollidable {

    private static final float FRAMES_PER_SECOND = 8f;

    public Player(float x, float y, float w, float h) {
        super(R.mipmap.cookie, FRAMES_PER_SECOND);
        this.x = x;
        this.y = y;
        setDstRect(w, h);
        srcRects = new Rect[] {
                new Rect(72 + 0 * 272, 404, 72+140 + 0 * 272, 404+140),
                new Rect(72 + 1 * 272, 404, 72+140 + 1 * 272, 404+140),
                new Rect(72 + 2 * 272, 404, 72+140 + 2 * 272, 404+140),
                new Rect(72 + 3 * 272, 404, 72+140 + 3 * 272, 404+140),
        };
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }
}
