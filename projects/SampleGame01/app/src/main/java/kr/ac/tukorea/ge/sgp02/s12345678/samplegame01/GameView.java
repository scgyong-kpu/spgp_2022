package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private static final String TAG = GameView.class.getSimpleName();
//    private Bitmap soccerBitmap;
//    private int ballDx, ballDy;
//    private int ball2Dx, ball2Dy;
//    private Rect srcRect = new Rect();
//    private Rect dstRect = new Rect();
//    private Rect dstRect2 = new Rect();
    private Ball ball1;
    private Ball ball2;
    private Paint fpsPaint = new Paint();
    private long lastTimeNanos;
    private int framesPerSecond;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
        initView();
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        long now = currentTimeNanos;
        int elapsed = (int) (now - lastTimeNanos);
        framesPerSecond = 1_000_000_000 / elapsed;
        lastTimeNanos = now;
        update();
        invalidate();
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                updateGame();
//            }
//        }, 30);
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        Resources res = getResources();
        Bitmap soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        Ball.setBitmap(soccerBitmap);

        ball1 = new Ball(10, 10);
        ball2 = new Ball(7, 15);
//        srcRect.set(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
//        dstRect.set(0, 0, 100, 100);
//
//        ballDx = 10;
//        ballDy = 10;
//
//        ball2Dx = 7;
//        ball2Dy = 15;
//        dstRect2.set(0, 0, 100, 100);

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        ball1.draw(canvas);
        ball2.draw(canvas);
//        canvas.drawBitmap(soccerBitmap, srcRect, dstRect, null);
//        canvas.drawBitmap(soccerBitmap, srcRect, dstRect2, null);
//        canvas.drawText("" + framesPerSecond, 0, 0, fpsPaint);
        canvas.drawText(String.valueOf(framesPerSecond), 0, 100, fpsPaint);
        Log.d(TAG, "onDraw()");
    }

    private void update() {
        ball1.update();
        ball2.update();
//        dstRect.offset(ballDx, ballDy);
//        if (ballDx < 0) {
//            if (dstRect.left < 0) {
//                ballDx = -ballDx;
//            }
//        } else {
//            if (dstRect.right > getWidth()) {
//                ballDx = -ballDx;
//            }
//        }
//        if (ballDy < 0) {
//            if (dstRect.top < 0) {
//                ballDy = -ballDy;
//            }
//        } else {
//            if (dstRect.bottom > getHeight()) {
//                ballDy = -ballDy;
//            }
//        }
//        dstRect2.offset(ball2Dx, ball2Dy);
//        if (ball2Dx < 0) {
//            if (dstRect.left < 0) {
//                ball2Dx = -ball2Dx;
//            }
//        } else {
//            if (dstRect2.right > getWidth()) {
//                ball2Dx = -ball2Dx;
//            }
//        }
//        if (ball2Dy < 0) {
//            if (dstRect2.top < 0) {
//                ball2Dy = -ball2Dy;
//            }
//        } else {
//            if (dstRect2.bottom > getHeight()) {
//                ball2Dy = -ball2Dy;
//            }
//        }
    }

}
