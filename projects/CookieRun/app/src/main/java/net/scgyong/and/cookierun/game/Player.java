package net.scgyong.and.cookierun.game;

import android.graphics.Rect;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.objects.SheetSprite;

public class Player extends SheetSprite {

    private static final float FRAMES_PER_SECOND = 8f;

    public Player(float x, float y, float w, float h) {
        super(R.mipmap.cookie, FRAMES_PER_SECOND);
        this.x = x;
        this.y = y;
        setDstRect(w, h);
        srcRects = new Rect[] {
                new Rect(77, 409, 197, 544),
                new Rect(77 + 272, 409, 197 + 272, 544),
                new Rect(77 + 2 * 272, 409, 197 + 2 * 272, 544),
                new Rect(77 + 3 * 272, 409, 197 + 3 * 272, 544),
        };
    }
}
