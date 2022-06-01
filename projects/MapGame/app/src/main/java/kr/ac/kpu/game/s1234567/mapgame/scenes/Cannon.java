package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Cannon extends Sprite {
    private int level;
    private float power, interval, shellSpeed;
    private float angle;
    private float time;
    private float range;
    private Bitmap barrelBitmap;
    private RectF barrelRect = new RectF();
    public Cannon(int level, float x, float y, float power, float interval) {
        super(x, y, TiledSprite.unit, TiledSprite.unit, R.mipmap.f_01_01);
        this.level = level;
        this.power = power;
        this.interval = interval;
        this.shellSpeed = TiledSprite.unit * 20;
        this.time = 0;
        this.range = 5 * TiledSprite.unit * level;
        if (1 < level && level <= BITMAP_IDS.length) {
            bitmap = BitmapPool.get(BITMAP_IDS[level - 1]);
        }
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        barrelRect.set(dstRect);
        barrelRect.inset(-radius, -radius);
    }
    private static int[] BITMAP_IDS = {
            R.mipmap.f_01_01,R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };
    private static int[] COSTS = {
            10, 30, 70, 72, 300, 700, 1500, 3000, 7000, 15000, 100000000
//            10, 30, 70, 150, 300, 700, 1500, 3000, 7000, 15000, 100000000
    };
    public static int getInstallCost(int level) {
        return COSTS[level - 1];
    }
    public static int getUpgradeCost(int level) {
        return Math.round((COSTS[level] - COSTS[level - 1]) * 1.1f);
    }
    public int getUpgradeCost() {
        return getUpgradeCost(level);
    }
    public static int getSellPrice(int level) {
        return COSTS[level - 1] / 2;
    }
    public int getSellPrice() {
        return getSellPrice(level);
    }

    @Override
    public void update(float frameTime) {
        time += frameTime;
        Fly fly = MainScene.get().findNearestFly(this);
        if (fly == null) {
            //angle = -90;
            return;
        }
        float dx = fly.getX() - x;
        float dy = fly.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist > 1.2 * range) {
            //angle = -90;
            return;
        }
        angle = (float)(Math.atan2(dy, dx) * 180 / Math.PI) ;
        if (dist > range) {
            return;
        }
        if (time > interval) {
            time = 0;
            fireTo(fly);
        }
    }

    private void fireTo(Fly fly) {
        boolean splash = level >= 4;
        Shell shell = Shell.get(level, x, y, fly, angle, shellSpeed, power, splash);
        MainScene.get().add(MainScene.Layer.shell.ordinal(), shell);
        //Log.d("CannonFire", "" + shell);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        super.draw(canvas);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
        canvas.restore();
    }

    public void upgrade() {
        if (level == BITMAP_IDS.length) return;

        level += 1;
        bitmap = BitmapPool.get(BITMAP_IDS[level - 1]);
        this.range = 5 * TiledSprite.unit * level;

        shellSpeed *= 1.2;
        interval *= 0.9;
        power *= 1.2;
    }
}
