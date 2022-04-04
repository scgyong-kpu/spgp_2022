package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
//    Ball ball1, ball2;
    private ArrayList<Ball> balls = new ArrayList<>();

    private long previousTimeNanos;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();

    public static GameView view;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        view = this;

        Resources res = getResources();
        Bitmap soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        Ball.setBitmap(soccerBitmap);

        Ball ball1 = new Ball(10, 10);
        Ball ball2 = new Ball(7, 15);
        balls.add(ball1);
        balls.add(ball2);

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        long now = currentTimeNanos;
//        long now = System.currentTimeMillis();
        int elapsed = (int) (now - previousTimeNanos);
        framesPerSecond = 1_000_000_000 / elapsed;
        //Log.v(TAG, "Elapsed: " + elapsed + " FPS: " + framesPerSecond);
        previousTimeNanos = now;
        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
//        ball1.update();
//        ball2.update();
        for (Ball ball : balls) {
            ball.update();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Ball ball : balls) {
            ball.draw(canvas);
        }
//        ball1.draw(canvas);
//        ball2.draw(canvas);
        canvas.drawText("FPS: " + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
//        Log.d(TAG, "onDraw()");
    }

}
