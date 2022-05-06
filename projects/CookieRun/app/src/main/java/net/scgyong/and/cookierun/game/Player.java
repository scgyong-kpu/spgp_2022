package net.scgyong.and.cookierun.game;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.objects.SheetSprite;

import java.util.ArrayList;

public class Player extends SheetSprite implements BoxCollidable {

    private static final float FRAMES_PER_SECOND = 8f;
    private static final String TAG = Player.class.getSimpleName();

    static {
        State.initRects();
    }
    private enum State {
        run, jump, COUNT;
        Rect[] srcRects() {
            return rects[this.ordinal()];
        }
        static Rect[][] rects;
//        = {
//            new Rect[] {
//                new Rect(72 + 0 * 272, 404, 72+140 + 0 * 272, 404+140),
//                new Rect(72 + 1 * 272, 404, 72+140 + 1 * 272, 404+140),
//                new Rect(72 + 2 * 272, 404, 72+140 + 2 * 272, 404+140),
//                new Rect(72 + 3 * 272, 404, 72+140 + 3 * 272, 404+140)
//            },
//            new Rect[] {
//                new Rect(72 + 7 * 272, 132, 72+140 + 7 * 272, 132+140),
//                new Rect(72 + 8 * 272, 132, 72+140 + 8 * 272, 132+140),
//            },
//        };
        static void initRects() {
            int[][] indices = {
                    new int[] { 100, 101, 102, 103 }, // run
                    new int[] { 7, 8 }, // jump
            };
            ArrayList<Rect[]> rectsList = new ArrayList<>();
            for (int[] ints : indices) {
                Rect[] rects = new Rect[ints.length];
                for (int i = 0; i < ints.length; i++) {
                    int idx = ints[i];
                    int l = 72 + (idx % 100) * 272;
                    int t = 132 + (idx / 100) * 272;
                    Rect rect = new Rect(l, t, l + 140, t + 140);
                    rects[i] = rect;
                }
                rectsList.add(rects);
            }
            rects = rectsList.toArray(new Rect[rectsList.size()][]);
        }
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
