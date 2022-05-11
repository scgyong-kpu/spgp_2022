package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.view.GameView;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();
    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, mipmapResId);
            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }
}
