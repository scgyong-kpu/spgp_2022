package net.scgyong.and.taptu.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import net.scgyong.and.taptu.R;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;

public class Pret extends Sprite {
    public boolean shows() {
        return shows;
    }

    public void show(boolean shows) {
        this.shows = shows;
    }

    boolean shows, captures;
    public Pret(int lane) {
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
        } else if (action == MotionEvent.ACTION_UP) {
            captures = false;
            shows = false;
            return false;
        } else {
            if (!captures) return false;
            shows = in;
        }
        return true;
    }
}
