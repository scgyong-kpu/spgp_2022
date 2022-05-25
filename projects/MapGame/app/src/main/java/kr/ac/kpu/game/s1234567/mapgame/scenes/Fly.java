package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;

import java.util.Random;

import kr.ac.kpu.game.framework.game.RecycleBin;
import kr.ac.kpu.game.framework.interfaces.Recyclable;
import kr.ac.kpu.game.framework.objects.SheetSprite;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Fly extends SheetSprite implements Recyclable {

    private Type type;
    private float dist;
    private float speed;

    private static Random random = new Random();
    private static Path path;
    private static PathMeasure pathMeasure;
    public static void setPath(Path path) {
        Fly.path = path;
        Fly.pathMeasure = new PathMeasure(path, false);
    }

    public enum Type {
        boss, red, blue, cyan, dragon, COUNT, RANDOM
    }
    public static Fly get(Type type, float speed) {
        Fly fly = (Fly) RecycleBin.get(Fly.class);
        if (fly == null) {
            fly = new Fly();
        }
        fly.init(type, speed);
        return fly;
    }

    private Fly() {
        super(R.mipmap.galaga_flies, 2.0f);
        radius = Metrics.height / 18;
        if (rects_array == null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            rects_array = new Rect[Type.COUNT.ordinal()][];
            int x = 0;
            for (int i = 0; i < Type.COUNT.ordinal(); i++) {
                rects_array[i] = new Rect[2];
                for (int j = 0; j < 2; j++) {
                    rects_array[i][j] = new Rect(x, 0, x+h, h);
                    x += h;
                }
            }
        }
    }

    private Rect[][] rects_array;
    private void init(Type type, float speed) {
        if (type == Type.RANDOM) {
            type = Type.values()[random.nextInt(Type.COUNT.ordinal())];
        }
        this.type = type;
        srcRects = rects_array[type.ordinal()];
        this.speed = speed;
        dist = 0;
    }

    private float[] pos = new float[2];
    private float[] tan = new float[2];
    @Override
    public void update(float frameTime) {
        dist += speed * frameTime;
        if (dist > pathMeasure.getLength()) {
            MainScene.get().remove(this);
            return;
        }
        pathMeasure.getPosTan(dist, pos, tan);
        x = pos[0];
        y = pos[1];
        setDstRectWithRadius();
    }

    @Override
    public void finish() {
    }
}
