package net.scgyong.and.taptu.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import net.scgyong.and.taptu.R;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;

public class Pret extends Sprite {
    public interface Listener {
        public void onPret(int lane, boolean pressed);
    }
    private Listener listener;
    private int lane;
    public boolean shows() {
        return shows;
    }

    public void show(boolean shows) {
        this.shows = shows;
    }

    boolean shows, captures;
    public Pret(int lane, Listener listener) {
        this.lane = lane;
        this.listener = listener;
        bitmap = BitmapPool.get(R.mipmap.trans_50p);
        float left = NoteSprite.NOTE_WIDTH * Metrics.width;
        float width = NoteSprite.NOTE_WIDTH * Metrics.width;
        left += width * lane;
        dstRect.set(left, 0, left + width, Metrics.height);
        Log.d("Pret", "lane="+lane+" rect="+dstRect);
        shows = false;
    }

    @Override
    public void draw(Canvas canvas) {
        if (shows) {
            super.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        float x = e.getX();
        float y = e.getY();
        boolean in = dstRect.contains(x, y);
        if (action == MotionEvent.ACTION_DOWN) {
            if (!in) return false;
            captures = true;
            shows = true;
            listener.onPret(lane, true);
        } else if (action == MotionEvent.ACTION_UP) {
            shows = false;
            if (!captures) return false;
            captures = false;
            listener.onPret(lane, false);
            return true;
        } else {
            if (!captures) return false;
            shows = in;
        }
        return true;
    }
}
