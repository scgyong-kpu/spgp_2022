package kr.ac.kpu.game.s1234567.mapgame.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.game.framework.game.RecycleBin;
import kr.ac.kpu.game.framework.interfaces.Recyclable;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;
import kr.ac.kpu.game.s1234567.mapgame.R;

public class Shell extends Sprite implements Recyclable {
    private Rect srcRect;
    private float dx, dy;
    private Fly target;

    public static Shell get(int level, float x, float y, Fly target, float angle, float speed) {
        Shell shell = (Shell) RecycleBin.get(Shell.class);
        if (shell == null) {
            shell = new Shell();
        }
        shell.init(level, x, y, target, angle, speed);
        return shell;
    }

    private Shell() {
        bitmap = BitmapPool.get(R.mipmap.bullets);
        radius = TiledSprite.unit / 8;
    }

    private void init(int level, float x, float y, Fly target, float angle, float speed) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int maxLevel = w / h;
        if (level < 1) level = 1;
        if (level > maxLevel) level = maxLevel;
        srcRect = new Rect(h * (level - 1), 0, h * level, h);
        //Log.d("CannonFire", "shell rect: " + srcRect);
        this.x = x;
        this.y = y;
        this.target = target;
        double radian = angle * Math.PI / 180;
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
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
            scene.remove(target);
            this.target = null;
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
