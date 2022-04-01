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
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap soccerBitmap;
    private Rect soccerSrcRect = new Rect();
    private Rect soccerDstRect = new Rect();
    private int ballDx, ballDy;
    private long previousTimeMillis;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);

        soccerSrcRect.set(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        soccerDstRect.set(0, 0, 200, 200);

        ballDx = 10;
        ballDy = 10;

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long l) {
        long now = System.currentTimeMillis();
        int elapsed = (int) (now - previousTimeMillis);
        framesPerSecond = 1000 / elapsed;
        //Log.v(TAG, "Elapsed: " + elapsed + " FPS: " + framesPerSecond);
        previousTimeMillis = now;
        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
        soccerDstRect.offset(ballDx, ballDy);
        if (ballDx > 0) {
            if (soccerDstRect.right > getWidth()) {
                ballDx = -ballDx;
            }
        } else {
            if (soccerDstRect.left < 0) {
                ballDx = -ballDx;
            }
        }
        if (ballDy > 0) {
            if (soccerDstRect.bottom > getHeight()) {
                ballDy = -ballDy;
            }
        } else {
            if (soccerDstRect.top < 0) {
                ballDy = -ballDy;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(soccerBitmap, soccerSrcRect, soccerDstRect, null);
        canvas.drawText("FPS: " + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
//        Log.d(TAG, "onDraw()");
    }

}
