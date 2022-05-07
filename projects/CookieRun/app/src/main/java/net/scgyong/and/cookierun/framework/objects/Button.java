package net.scgyong.and.cookierun.framework.objects;

import android.view.MotionEvent;

import net.scgyong.and.cookierun.framework.interfaces.Touchable;

public class Button extends Sprite implements Touchable {
    protected final Callback callback;

    public enum Action {
        pressed, released,
    }
    public interface Callback {
        public boolean onTouch(Action action);
    }
    public Button(float x, float y, float w, float h, int bitmapResId, Callback callback) {
        super(x, y, w, h, bitmapResId);
        this.callback = callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (!dstRect.contains(x, y)) {
            return false;
        }
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return callback.onTouch(Action.pressed);
            case MotionEvent.ACTION_UP:
                return callback.onTouch(Action.released);
        }
        return false;
    }
}
