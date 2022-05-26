package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Cannon extends Sprite {
    private int level;
    private float power, interval;
    private float angle;
    private float time;
    private Bitmap barrelBitmap;
    private RectF barrelRect = new RectF();
    public Cannon(int level, float x, float y, float power, float interval) {
        super(x, y, Metrics.height / 18, Metrics.height / 18, R.mipmap.f_01_01);
        this.level = level;
        this.power = power;
        this.interval = interval;
        this.time = interval;
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

    @Override
    public void update(float frameTime) {
        time += frameTime;
        Fly fly = MainScene.get().findNearestFly(this);
        if (fly == null) {
            angle = 0;
            return;
        }
        float dx = fly.getX() - x;
        float dy = fly.getY() - y;
        angle = (float)(Math.atan2(dy, dx) * 180 / Math.PI) ;
        if (time > interval) {
            time = 0;
            fire();
        }
    }

    private void fire() {
        Shell shell = Shell.get(level, x, y, angle, 1000);
        MainScene.get().add(MainScene.Layer.shell.ordinal(), shell);
        //Log.d("CannonFire", "" + shell);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
        canvas.restore();
    }
}
