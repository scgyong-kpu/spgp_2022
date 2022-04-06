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

    private float x, y;
    private float dx, dy;
    private float tx, ty;

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    public Fighter(int x, int y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = y;
        int radius_dp = 30;
        DisplayMetrics displayMetrics = GameView.view.getResources().getDisplayMetrics();
        int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius_dp, displayMetrics);
        dstRect.set(x - radius, y - radius, x + radius, y + radius);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

//    public static void setBitmap(Bitmap bitmap) {
//        Fighter.bitmap = bitmap;
//        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
//    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void update() {
        float angle = (float) Math.atan2(ty - y, tx - x);
        float speed = 1000;
        float dist = speed * MainGame.getInstance().frameTime;
        dx = (float) (dist * Math.cos(angle));
        dy = (float) (dist * Math.sin(angle));
//        Log.d(TAG, "x="+x+" y="+y+" dx="+dx+" dy="+dy);
        if (dx > 0) {
            if (x + dx > tx) {
                x = tx;
                dx = tx - x;
            } else {
                x += dx;
            }
        } else {
            if (x + dx < tx) {
                x = tx;
                dx = tx - x;
            } else {
                x += dx;
            }
        }
        if (dy > 0) {
            if (y + dy > ty) {
                y = ty;
                dy = ty - y;
            } else {
                y += dy;
            }
        } else {
            if (y + dy < ty) {
                y = ty;
                dy = ty - y;
            } else {
                y += dy;
            }
        }
//        x += dx;
//        y += dy;
        dstRect.offset(dx, dy);
    }

    public void setTargetPosition(float x, float y) {
        tx = x;
        ty = y;
        //int hw = 200 / 2, hh = 200 / 2;
//        int radius = 200 / 2;
//        dstRect.set((int)x - radius,
//                (int)y - radius,
//                (int)x + radius,
//                (int)y + radius);
    }
}
