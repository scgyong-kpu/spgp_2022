package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.game.framework.interfaces.Touchable;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class TowerMenu extends Sprite implements Touchable {
    private int[] items;
    private RectF itemRect = new RectF();
    private Paint alphaPaint = new Paint();

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
        ValueAnimator animator = ValueAnimator
                .ofInt(0, 255)
                .setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                alphaPaint.setAlpha((Integer)valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.items.length == 0) return;
//        super.draw(canvas);
        canvas.drawBitmap(bitmap, null, dstRect, alphaPaint);
        itemRect.set(dstRect);
        itemRect.right = itemRect.left + TiledSprite.unit;
        for (int item: items) {
            Bitmap bitmap = BitmapPool.get(item);
            canvas.drawBitmap(bitmap, null, itemRect, alphaPaint);
            itemRect.offset(TiledSprite.unit, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (this.items.length == 0) return false;
        float x = e.getX();
        float y = e.getY();
        if (!dstRect.contains(x, y)) {
            return false;
        }
        itemRect.set(dstRect);
        itemRect.right = itemRect.left + TiledSprite.unit;
        int foundItem = 0;
        for (int item: items) {
            if (itemRect.contains(x, y)) {
                foundItem = item;
                break;
            }
            itemRect.offset(TiledSprite.unit, 0);
        }
        if (foundItem != 0) {
            Log.d("TowerMenu", "item = " + foundItem);
        }
        return true;
    }
}
