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
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final int BALL_COUNT = 10;
    public static GameView view;
    private static final String TAG = GameView.class.getSimpleName();
//    private Bitmap soccerBitmap;
//    private int ballDx, ballDy;
//    private int ball2Dx, ball2Dy;
//    private Rect srcRect = new Rect();
//    private Rect dstRect = new Rect();
//    private Rect dstRect2 = new Rect();
    private Paint fpsPaint = new Paint();
    private long lastTimeNanos;
    private int framesPerSecond;

    private ArrayList<Ball> balls = new ArrayList<>();
    private Fighter fighter;

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

        Random random = new Random();
        for (int i = 0; i < BALL_COUNT; i++) {
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            Ball ball = new Ball(dx, dy);
            balls.add(ball);
        }

        Bitmap fighterBitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
        Fighter.setBitmap(fighterBitmap);
        fighter = new Fighter();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        for (Ball ball : balls) {
            ball.draw(canvas);
        }
        fighter.draw(canvas);
        canvas.drawText(String.valueOf(framesPerSecond), 0, 100, fpsPaint);
        Log.d(TAG, "onDraw()");
    }

    private void update() {
        for (Ball ball : balls) {
            ball.update();
        }
        fighter.update();
    }

}
