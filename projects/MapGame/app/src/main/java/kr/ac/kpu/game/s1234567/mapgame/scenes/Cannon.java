package kr.ac.kpu.game.s1234567.mapgame.scenes;

import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Cannon extends Sprite {
    private int level;
    private float power, interval;
    public Cannon(int level, float x, float y, float power, float interval) {
        super(x, y, Metrics.height / 18, Metrics.height / 18, R.mipmap.f_01_01);
        this.level = level;
        this.power = power;
        this.interval = interval;
        if (1 < level && level <= BITMAP_IDS.length) {
            bitmap = BitmapPool.get(BITMAP_IDS[level - 1]);
        }
    }
    private static int[] BITMAP_IDS = {
            R.mipmap.f_01_01,R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };
}
