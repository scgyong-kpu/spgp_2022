package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import kr.ac.kpu.game.framework.game.RecycleBin;
import kr.ac.kpu.game.framework.interfaces.GameObject;
import kr.ac.kpu.game.framework.interfaces.Recyclable;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Shell extends Sprite implements Recyclable {
    private Rect srcRect = new Rect();
    private float dx, dy;
    private Fly target;
    private float power;
    private boolean splash;

    public static Shell get(int level, float x, float y, Fly target, float angle, float speed, float power, boolean splash) {
        Shell shell = (Shell) RecycleBin.get(Shell.class);
        if (shell == null) {
            shell = new Shell();
        }
        shell.init(level, x, y, target, angle, speed, power, splash);
        return shell;
    }

    private Shell() {
        bitmap = BitmapPool.get(R.mipmap.bullets);
    }

    private void init(int level, float x, float y, Fly target, float angle, float speed, float power, boolean splash) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int maxLevel = w / h;
        if (level < 1) level = 1;
        if (level > maxLevel) level = maxLevel;
        srcRect.set(h * (level - 1), 0, h * level, h);
        //Log.d("CannonFire", "shell rect: " + srcRect);
        this.x = x;
        this.y = y;
        this.target = target;
        double radian = angle * Math.PI / 180;
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
        this.power = power;
        this.splash = splash;
        radius = TiledSprite.unit / 8;
        if (splash) {
            radius = TiledSprite.unit / 4;
        } else {
            radius = TiledSprite.unit / 8;
        }
    }

    @Override
    public void update(float frameTime) {
        x += dx * frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        if (x < -radius || x > Metrics.width + radius ||
            y < -radius || y > Metrics.height + radius)
        {
            //Log.d("CannonFire", "Remove(" + x + "," + y + ") " + this);
            MainScene.get().remove(this);
            return;
        }

        if (target == null) return;

        float dx = x - target.getX();
        float dy = y - target.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist < target.getRadius()) {
            MainScene scene = MainScene.get();
            scene.remove(this);
            if (splash) {
                explode();
                return;
            }
            boolean dead = target.decreaseHealth(power);
            if (dead) {
                scene.remove(target);
                scene.score.add(target.score());
                this.target = null;
            }
        }
    }

    private void explode() {
        MainScene scene = MainScene.get();
        ArrayList<GameObject> flies = scene.objectsAt(MainScene.Layer.enemy.ordinal());
        double explosion_radius = TiledSprite.unit * 5;
        for (GameObject f: flies) {
            Fly fly = (Fly) f;
            float dx = x - fly.getX();
            float dy = y - fly.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < explosion_radius) {
                boolean dead = fly.decreaseHealth(power);
                if (dead) {
                    scene.remove(fly);
                    scene.score.add(fly.score());
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void finish() {
    }
}
