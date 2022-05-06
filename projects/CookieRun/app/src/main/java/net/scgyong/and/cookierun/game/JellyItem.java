package net.scgyong.and.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.RecycleBin;
import net.scgyong.and.cookierun.framework.res.BitmapPool;

public class JellyItem extends MapSprite {
    public static final int JELLY_COUNT = 60;
    private static final int SIZE = 66;
    private static final int BORDER = 2;
    private static final int ITEMS_IN_A_ROW = 30;
    private static final String TAG = JellyItem.class.getSimpleName();
    protected Rect srcRect = new Rect();
    public static JellyItem get(int index, float unitLeft, float unitTop) {
        JellyItem item = (JellyItem) RecycleBin.get(JellyItem.class);
        if (item == null) {
            item = new JellyItem();
        }
        item.init(index, unitLeft, unitTop);
        return item;
    }

    private void init(int index, float unitLeft, float unitTop) {
        MainGame game = MainGame.get();
        int srcLeft = BORDER + (index % ITEMS_IN_A_ROW) * (SIZE + BORDER);
        int srcTop = BORDER + (index / ITEMS_IN_A_ROW) * (SIZE + BORDER);
        srcRect.set(srcLeft, srcTop, srcLeft + SIZE, srcTop + SIZE);
//        Log.d(TAG, "index=" + index + " rect=" + srcRect);
        float left = game.size(unitLeft);
        float top = game.size(unitTop);
        float unit = game.size(1);
        dstRect.set(left, top, left + unit, top + unit);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    private JellyItem() {
        bitmap = BitmapPool.get(R.mipmap.jelly);
    }
}
