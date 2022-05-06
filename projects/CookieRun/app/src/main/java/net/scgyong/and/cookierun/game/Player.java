package net.scgyong.and.cookierun.game;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.objects.SheetSprite;

public class Player extends SheetSprite implements BoxCollidable {

    private static final float FRAMES_PER_SECOND = 8f;
    private static final String TAG = Player.class.getSimpleName();

    private enum State {
        run, jump, COUNT;
        Rect[] srcRects() {
            return rects[this.ordinal()];
        }
        static Rect[][] rects = {
            new Rect[] {
                new Rect(72 + 0 * 272, 404, 72+140 + 0 * 272, 404+140),
                new Rect(72 + 1 * 272, 404, 72+140 + 1 * 272, 404+140),
                new Rect(72 + 2 * 272, 404, 72+140 + 2 * 272, 404+140),
                new Rect(72 + 3 * 272, 404, 72+140 + 3 * 272, 404+140)
            },
            new Rect[] {
                new Rect(72 + 7 * 272, 132, 72+140 + 7 * 272, 132+140),
                new Rect(72 + 8 * 272, 132, 72+140 + 8 * 272, 132+140),
            },
        };
    }
    private State state = State.run;

    public Player(float x, float y, float w, float h) {
        super(R.mipmap.cookie, FRAMES_PER_SECOND);
        this.x = x;
        this.y = y;
        setDstRect(w, h);
        setState(State.run);
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }

    public void jump() {
        Log.d(TAG, "Jump");
        if (state == State.run) {
            setState(State.jump);
        } else {
            setState(State.run);
        }
    }

    private void setState(State state) {
        this.state = state;
        srcRects = state.srcRects();
    }
}
