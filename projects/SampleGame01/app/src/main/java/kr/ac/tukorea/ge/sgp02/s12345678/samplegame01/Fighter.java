package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class Fighter implements GameObject {
    private static final String TAG = Fighter.class.getSimpleName();
    private RectF dstRect = new RectF();
    private RectF targetRect = new RectF();

    private float radius;
    private float angle;
    private float x, y;
    private float dx, dy;
    private float tx, ty;

    private static Bitmap bitmap;
    private static Bitmap targetBitmap;
//    private static Rect srcRect;

    public Fighter(float x, float y) {
        this.x = x;
        this.y = y;
//        Resources res = GameView.view.getResources();
        radius = Metrics.size(R.dimen.fighter_radius);
        dstRect.set(x - radius, y - radius, x + radius, y + radius);

        this.tx = x;
        this.ty = y;
        targetRect.set(dstRect);
        angle = -(float) (Math.PI / 2);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
//            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
            targetBitmap = BitmapFactory.decodeResource(res, R.mipmap.target);
        }
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
        if (dx != 0 && dy != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void update() {
        if (dx == 0 && dy == 0)
            return;

        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
        } else {
            x += dx;
        }
        if ((dy > 0 && y + dy > ty) || (dy < 0 && y + dy < ty)) {
            dy = ty - y;
            y = ty;
        } else {
            y += dy;
        }
        dstRect.offset(dx, dy);
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        targetRect.set(tx - radius/2, ty - radius/2,
                tx + radius/2, ty + radius/2);
        angle = (float) Math.atan2(ty - y, tx - x);
        float speed = Metrics.size(R.dimen.fighter_speed);
        float dist = speed * MainGame.getInstance().frameTime;
        dx = (float) (dist * Math.cos(angle));
        dy = (float) (dist * Math.sin(angle));
    }
}
