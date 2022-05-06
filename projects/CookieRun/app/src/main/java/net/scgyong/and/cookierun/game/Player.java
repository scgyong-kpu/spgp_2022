package net.scgyong.and.cookierun.game;

import android.graphics.Rect;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.objects.SheetSprite;

public class Player extends SheetSprite {

    private static final float FRAMES_PER_SECOND = 5f;

    public Player(float x, float y, float w, float h) {
        super(R.mipmap.jelly, FRAMES_PER_SECOND);
        this.x = x;
        this.y = y;
        setDstRect(w, h);
        srcRects = new Rect[] {
                new Rect(2 + 2 * 68, 2, 3 * 68, 68),
                new Rect(2 + 3 * 68, 2, 4 * 68, 68),
                new Rect(2 + 4 * 68, 2, 5 * 68, 68),
                new Rect(2 + 5 * 68, 2, 6 * 68, 68),
                new Rect(2 + 6 * 68, 2, 7 * 68, 68),
        };
    }
}
