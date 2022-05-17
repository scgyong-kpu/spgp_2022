package net.scgyong.and.cookierun.game;

import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.BounceInterpolator;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.RecycleBin;
import net.scgyong.and.cookierun.framework.res.BitmapPool;
import net.scgyong.and.cookierun.framework.res.Metrics;

import java.util.Random;

public class Obstacle extends MapSprite {
    private static final String TAG = Obstacle.class.getSimpleName();
    protected Modifier modifier;
    protected long createdOn;
    protected RectF collisionBox = new RectF();
    protected ValueAnimator animator;
    protected static Random random = new Random();

    public enum Type {
        shortSingle, shortTriple, longDouble, fallingFork, COUNT
    }

    public static Obstacle get(int index, float unitLeft, float unitTop) {
        Obstacle obs = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obs == null) {
            obs = new Obstacle();
        }
        obs.init(index, unitLeft, unitTop);
        return obs;
    }
    private static class Modifier {
        protected final float width, height;
        protected int mipmapResId;
        public Modifier(float widthUnit, float heightUnit) {
            this.width = widthUnit;
            this.height = heightUnit;
        }
        public Modifier(float widthUnit, float heightUnit, int mipmapResId) {
            this.width = widthUnit;
            this.height = heightUnit;
            this.mipmapResId = mipmapResId;
        }
        public void update(Obstacle obstacle, float frameTime) {
            obstacle.collisionBox.set(obstacle.dstRect);
        }
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            unitTop -= this.height - 1;
            Log.d(TAG, "Height=" + this.height + " :" + this);
            obstacle.setUnitDstRect(unitLeft, unitTop, width, height);
            if (mipmapResId != 0) {
                obstacle.bitmap = BitmapPool.get(mipmapResId);
            }
        }
    }
    private static class AnimModifier extends Modifier {
        private final int[] mipmapResIds;
        private final float topTransparent;
        public AnimModifier(float widthUnit, float heightUnit, int[] mipmapResIds, float topTransparent) {
            super(widthUnit, heightUnit);
            this.mipmapResIds = mipmapResIds;
            this.topTransparent = topTransparent;
        }

        @Override
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            super.init(obstacle, unitLeft, unitTop);
            obstacle.bitmap = BitmapPool.get(mipmapResIds[0]);
            obstacle.createdOn = System.currentTimeMillis();
        }

        @Override
        public void update(Obstacle obstacle, float frameTime) {
            RectF rect = obstacle.dstRect;
            float height = rect.height();
            float trans = height * topTransparent;
            obstacle.collisionBox.set(
                    rect.left,
                    rect.top + trans,
                    rect.right,
                    rect.bottom
            );

            float elapsed = (System.currentTimeMillis() - obstacle.createdOn) / 1000.f;
            final float start = 2.0f;
            if (elapsed > start) {
                final float fps = 8.0f;
                int index = Math.round((elapsed - start) * fps);
                if (index < mipmapResIds.length) {
                    obstacle.bitmap = BitmapPool.get(mipmapResIds[index]);
                }
            }
        }
    }
    private static class MoveModifier extends Modifier {
        public MoveModifier(float widthUnit, float heightUnit, int mipmapResId) {
            super(widthUnit, heightUnit, mipmapResId);
        }

        @Override
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            super.init(obstacle, unitLeft, unitTop);
            float height = obstacle.dstHeight();
            float bottom = obstacle.dstRect.bottom;
            obstacle.dstRect.offset(0, -bottom);
//            obstacle.dstRect.offset(0, -obstacle.dstRect.bottom);
//            obstacle.collisionBox.set(obstacle.dstRect);
            ValueAnimator animator = ValueAnimator.ofFloat(0, bottom);
            animator.setDuration(random.nextInt(800) + 600);
            animator.setInterpolator(new BounceInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator va) {
                    float value = (Float)va.getAnimatedValue();
                    obstacle.dstRect.set(
                            obstacle.dstRect.left,
                            value - height,
                            obstacle.dstRect.right,
                            value
                    );
                    obstacle.collisionBox.set(obstacle.dstRect);
                }
            });
            animator.setStartDelay(random.nextInt(800) + 2000);
            animator.start();
            obstacle.animator = animator;
        }
    }

    private static Modifier[] MODIFIERS = {
            new Modifier(.5f, .5f*99/63f, R.mipmap.epn01_tm01_jp1a),
            new AnimModifier(1.2f, 131/81f, new int[] {
                    R.mipmap.trans_00p,
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            }, 64/131f),
            new AnimModifier(1, 222/87f, new int[] {
                    R.mipmap.trans_00p,
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            }, 68/222f),
            new MoveModifier(1.5f, 1.5f*482/86f, R.mipmap.epn01_tm01_sda),
    };

    private void init(int index, float unitLeft, float unitTop) {
        super.init();
        animator = null;
        bitmap = null;
        modifier = MODIFIERS[index];
        modifier.init(this, unitLeft, unitTop);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);
        modifier.update(this, frameTime);
    }

    @Override
    public RectF getBoundingRect() {
        return collisionBox;
    }

    @Override
    public void finish() {
        super.finish();
        if (animator != null) {
            animator.cancel();
        }
    }
}