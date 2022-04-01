package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Fighter implements GameObject {
    private Rect dstRect = new Rect();

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    public Fighter() {
        dstRect.set(0, 0, 200, 200);

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
    }

    public void setPosition(float x, float y) {
        //int hw = 200 / 2, hh = 200 / 2;
        int radius = 200 / 2;
        dstRect.set((int)x - radius,
                (int)y - radius,
                (int)x + radius,
                (int)y + radius);
    }
}
