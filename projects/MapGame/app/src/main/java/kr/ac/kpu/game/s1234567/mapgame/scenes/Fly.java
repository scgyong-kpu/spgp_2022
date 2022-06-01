package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.game.framework.game.RecycleBin;
import kr.ac.kpu.game.framework.interfaces.Recyclable;
import kr.ac.kpu.game.framework.objects.SheetSprite;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.framework.util.Gauge;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Fly extends SheetSprite implements Recyclable {

    private Type type;
    private float dist;
    private float speed;
    private float angle;
    private float dx, dy;
    private float health, maxHealth;
    private Gauge gauge;

    private static Random random = new Random();
    //private static Path path;
    private static PathMeasure pathMeasure;
    public static void setPath(Path path) {
        //Fly.path = path;
        Fly.pathMeasure = new PathMeasure(path, false);
    }

    public boolean decreaseHealth(float power) {
        health -= power;
        return health < 0;
    }

    public enum Type {
        boss, red, blue, cyan, dragon, COUNT, RANDOM;
        float getMaxHealth() {
            return HEALTHS[ordinal()];
        }
        static float[] HEALTHS = { 150, 50, 30, 20, 10 };
        static int[] POSSIBILITIES = { 0, 10, 20, 30, 40 };
        static int POSSIBILITY_SUM;
        static {
            POSSIBILITY_SUM = 0;
            for (int p : POSSIBILITIES) {
                POSSIBILITY_SUM += p;
            }
        }
    }
    public static Fly get(Type type, float speed, float size) {
        Fly fly = (Fly) RecycleBin.get(Fly.class);
        if (fly == null) {
            fly = new Fly();
        }
        fly.init(type, speed, size);
        return fly;
    }

    private Fly() {
        super(R.mipmap.galaga_flies, 2.0f);
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
        gauge = new Gauge(
                Metrics.size(R.dimen.fly_gauge_thickness_fg),
                R.color.fly_gauge_fg,
                Metrics.size(R.dimen.fly_gauge_thickness_bg),
                R.color.fly_gauge_bg,
                TiledSprite.unit
        );
    }

    private Rect[][] rects_array;
    private void init(Type type, float speed, float size) {
        if (type == Type.RANDOM) {
            int value = random.nextInt(Type.POSSIBILITY_SUM);
            Log.d("Fly", "value=" + value);
            for (int i = 0; i < Type.POSSIBILITIES.length; i++) {
                value -= Type.POSSIBILITIES[i];
                if (value < 0) {
                    type = Type.values()[i];
                    Log.d("Fly", "type=" + type + " i=" + i);
                    break;
                }
            }
        }
        this.type = type;
        srcRects = rects_array[type.ordinal()];
        this.speed = speed;
        radius = TiledSprite.unit * size;
        dist = 0;
        dx = dy = 0;
        health = maxHealth = type.getMaxHealth() * size;
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

        dx += (2 * radius * random.nextFloat() - radius) * frameTime;
        if (dx < -radius) dx = -radius;
        else if (dx > radius) dx = radius;
        dy += (2 * radius * random.nextFloat() - radius) * frameTime;
        if (dy < -radius) dy = -radius;
        else if (dy > radius) dy = radius;

        pathMeasure.getPosTan(dist, pos, tan);
        x = pos[0] + dx;
        y = pos[1] + dy;
        angle = (float)(Math.atan2(tan[1], tan[0]) * 180 / Math.PI) ;
        setDstRectWithRadius();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();
        gauge.setValue(health / maxHealth);
        gauge.draw(canvas, x, y + radius);
    }

    @Override
    public void finish() {
    }
}
