package net.scgyong.and.cookierun.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.Log;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.res.Metrics;
import net.scgyong.and.cookierun.framework.util.Gauge;
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
    private final float yGauge;
    private ArrayList<String> lines;
    private int columns;
    private int rows;
    public float speed;
    public float scroll;
    public int length;
    private int current;
    private Gauge gauge;

    private MapLoader() {
        random = new Random();
        unit = MainScene.get().size(1);

        gauge = new Gauge(
                Metrics.size(R.dimen.map_gauge_fg_width), R.color.map_gauge_fg,
                Metrics.size(R.dimen.map_gauge_bg_width), R.color.map_gauge_bg,
                Metrics.width * 0.7f
        );
        yGauge = Metrics.size(R.dimen.map_gauge_y);
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
        current = 0;
        loadFromTextAsset(MAP_FILES[mapIndex]);
        speed = Metrics.size(R.dimen.map_scroll_speed);
    }

    private void loadFromTextAsset(String filename) {
        lines = new ArrayList<>();
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            InputStream is = assets.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String header = reader.readLine();
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            rows = Integer.parseInt(comps[1]);
//            Log.d(TAG, "Col=" + columns + " Row="  + rows);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
//                Log.d(TAG,  "-row=" + line);
                lines.add(line);
            }
            int pages = lines.size() / rows;
            int lastCol = lines.get(lines.size() - 1).length();
            length = (pages - 1) * columns + lastCol;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float frameTime) {
        scroll += speed * frameTime;

        MainScene game = MainScene.get();

        float left = scroll + current * unit;
        while (left < Metrics.width + unit) {
            createColumn(left / unit);
            current++;
            left += unit;
        }
    }

    private void createColumn(float leftUnit) {
//        Log.d(TAG, "current column=" + current);
        float y = 0;
        for (int row = 0; row < rows; row++) {
            char ch = getAt(current, row);
            if (ch == 0) {
//                MainGame.get().finish();
                speed = 0;
                return;
            }
//            if (ch != ' ' && ch != '-') {
//                Log.d(TAG, " row=" + row + " ch=" + ch);
//            }
            createObject(ch, leftUnit, row);
            y += unit;
        }
    }

    private char getAt(int x, int y) {
        try {
            int lineIndex = x / columns * rows + y;
            String line = lines.get(lineIndex);
            return line.charAt(x % columns);
        } catch (Exception e) {
            return 0;
        }
    }

    private void createObject(char ch, float leftUnit, float topUnit) {
        MainScene game = MainScene.get();
        if (ch >= '1' && ch <= '9') {
            JellyItem item = JellyItem.get(ch - '1', leftUnit, topUnit);
            game.add(MainScene.Layer.item.ordinal(), item);
        } else if (ch == '@') {
            JellyItem item = JellyItem.get(26, leftUnit, topUnit);
            game.add(MainScene.Layer.item.ordinal(), item);
        } else if (ch >= 'O' && ch <= 'Q') {
            Platform platform = Platform.get(Platform.Type.values()[ch - 'O'], leftUnit, topUnit);
            game.add(MainScene.Layer.platform.ordinal(), platform);
        } else if (ch >= 'X' && ch <= 'Z') {
            Obstacle obstacle = Obstacle.get(ch - 'X', leftUnit, topUnit);
            game.add(MainScene.Layer.obstacle.ordinal(), obstacle);
        } else if (ch == 'T') {
            Obstacle obstacle = Obstacle.get(Obstacle.Type.fallingFork.ordinal(), leftUnit, topUnit);
            game.add(MainScene.Layer.obstacle.ordinal(), obstacle);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        gauge.setValue((float)current / length);
        gauge.draw(canvas, Metrics.width / 2, yGauge);
    }
}
