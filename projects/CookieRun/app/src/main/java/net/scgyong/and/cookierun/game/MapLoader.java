package net.scgyong.and.cookierun.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.res.Metrics;
import net.scgyong.and.cookierun.framework.view.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MapLoader implements GameObject {
    private static final String TAG = MapLoader.class.getSimpleName();
    private static MapLoader instance;
    private final Random random;
    private final float unit;
    private final ArrayList<String> lines = new ArrayList<String>();
    private int columns;
    private int rows;
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
    private static String[] MAP_FILES = {
            "stage_01.txt", "stage_02.txt",
    };
    public void init(int mapIndex) {
        scroll = 0;
        itemLeft = platformLeft = 0;
        loadFromTextAsset(MAP_FILES[mapIndex]);
//        itemPaint = new Paint();
//        itemPaint.setColor(Color.RED);
//        itemPaint.setStrokeWidth(3);
//
//        platformPaint = new Paint();
//        platformPaint.setColor(Color.BLUE);
//        platformPaint.setStrokeWidth(5);
    }

    private void loadFromTextAsset(String filename) {
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            InputStream is = assets.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String header = reader.readLine();
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            rows = Integer.parseInt(comps[1]);
            Log.d(TAG, "Col=" + columns + " Row="  + rows);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                Log.d(TAG,  "-row=" + line);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
