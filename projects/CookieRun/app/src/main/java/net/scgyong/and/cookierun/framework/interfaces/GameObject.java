package net.scgyong.and.cookierun.framework.interfaces;

import android.graphics.Canvas;

public interface GameObject {
    public void update(float frameTime);
    public void draw(Canvas canvas);
}
