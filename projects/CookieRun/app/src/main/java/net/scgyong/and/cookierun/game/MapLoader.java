package net.scgyong.and.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.res.Metrics;

import java.util.Random;

public class MapLoader implements GameObject {
    private static final String TAG = MapLoader.class.getSimpleName();
    private static MapLoader instance;
    private final Random random;
    private final float unit;
    public float speed = -200;
    public float scroll, itemLeft, platformLeft;
//    private Paint itemPaint, platformPaint;

    private MapLoader() {
        random = new Random();
        unit = MainGame.get().size(1);
    }
    public static MapLoader get() {
        if (instance == null) {
            instance = new MapLoader();
        }
        return instance;
    }
    public void init() {
        scroll = 0;
        itemLeft = platformLeft = 0;
//        itemPaint = new Paint();
//        itemPaint.setColor(Color.RED);
//        itemPaint.setStrokeWidth(3);
//
//        platformPaint = new Paint();
//        platformPaint.setColor(Color.BLUE);
//        platformPaint.setStrokeWidth(5);
    }
    @Override
    public void update(float frameTime) {
        scroll += speed * frameTime;

        MainGame game = MainGame.get();

        while (true) {
            float left = scroll + itemLeft;
            if (left > Metrics.width + unit) {
                break;
            }
            int index = random.nextInt(JellyItem.JELLY_COUNT);
            float unitTop = random.nextInt(8);
            JellyItem item = JellyItem.get(index, (left / unit), unitTop);
            game.add(MainGame.Layer.item.ordinal(), item);
//            Log.d(TAG, "itemLeft=" + itemLeft + " left=" + left + " w=" + item.dstWidth());
            itemLeft += item.dstWidth();
//            break;
        }

        while (true) {
            float left = scroll + platformLeft;
            if (left > Metrics.width + unit) {
                break;
            }
            Platform.Type type = Platform.Type.random(random);
            Platform platform = Platform.get(type, (left / unit), 8);
            game.add(MainGame.Layer.platform.ordinal(), platform);
//            Log.d(TAG, "platformLeft=" + platformLeft + " left=" + left + " w=" + platform.dstWidth());
            platformLeft += platform.dstWidth();
//            break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
//        canvas.drawLine(scroll + platformLeft, 0, scroll + platformLeft, Metrics.height, platformPaint);
//        canvas.drawLine(scroll + itemLeft, 0, scroll + itemLeft, Metrics.height, itemPaint);
    }
}
