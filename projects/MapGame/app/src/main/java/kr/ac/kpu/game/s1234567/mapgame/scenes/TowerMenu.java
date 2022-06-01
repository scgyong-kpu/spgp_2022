package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class TowerMenu extends Sprite {
    private int[] items;
    private RectF itemRect = new RectF();

    public TowerMenu() {
        bitmap = BitmapPool.get(R.mipmap.menu_bg);
        items = new int[] {};
    }
    public void setMenu(float leftUnit, float topUnit, int... items) {
        float left = (leftUnit + 1) * TiledSprite.unit;
        float top = topUnit * TiledSprite.unit;
        this.items = items;
        dstRect.set(left, top, left + items.length * TiledSprite.unit, top + TiledSprite.unit);
        if (dstRect.right > Metrics.width) {
            dstRect.offset(-(items.length + 1) * TiledSprite.unit, 0);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.items.length == 0) return;
        super.draw(canvas);
        itemRect.set(dstRect);
        itemRect.right = itemRect.left + TiledSprite.unit;
        for (int item: items) {
            Bitmap bitmap = BitmapPool.get(item);
            canvas.drawBitmap(bitmap, null, itemRect, null);
            itemRect.offset(TiledSprite.unit, 0);
        }
    }
}
