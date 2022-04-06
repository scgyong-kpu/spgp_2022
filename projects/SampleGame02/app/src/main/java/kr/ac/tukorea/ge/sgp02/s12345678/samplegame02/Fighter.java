package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

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
    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    private float x, y;
    private float dx, dy;
    private float tx, ty;

    public Fighter(float x, float y) {
        this.x = x;
        this.y = y;
        float radius = Metrics.size(R.dimen.fighter_radius);
        dstRect.set(x - radius, y - radius,
                x + radius, y + radius);
        tx = x;
        ty = y;

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
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
//        Log.d(TAG, "x="+x+" y="+y+" dx="+dx+" dy="+dy);
        dstRect.offset(dx, dy);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void setTargetPosition(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        float angle = (float) Math.atan2(ty - this.y, tx - this.x);
        float speed = Metrics.size(R.dimen.fighter_speed);
        float dist = speed * MainGame.getInstance().frameTime;
//        Log.d(TAG, "speed=" + speed+" dist="+dist);
        dx = (float) (dist * Math.cos(angle));
        dy = (float) (dist * Math.sin(angle));
    }
}
