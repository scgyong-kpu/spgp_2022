package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private final Handler handler;
    private Bitmap soccerBitmap;
    private int ballDx, ballDy;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

        handler = new Handler();
        updateGame();
    }

    private void updateGame() {
        update();
        invalidate();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateGame();
            }
        });
    }

    private void initView() {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        srcRect.set(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        dstRect.set(0, 0, 100, 100);

        ballDx = 10;
        ballDy = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawBitmap(soccerBitmap, srcRect, dstRect, null);
        Log.d(TAG, "onDraw()");
    }

    private void update() {
        dstRect.offset(ballDx, ballDy);
        if (ballDx < 0) {
            if (dstRect.left < 0) {
                ballDx = -ballDx;
            }
        } else {
            if (dstRect.right > getWidth()) {
                ballDx = -ballDx;
            }
        }
        if (ballDy < 0) {
            if (dstRect.top < 0) {
                ballDy = -ballDy;
            }
        } else {
            if (dstRect.bottom > getHeight()) {
                ballDy = -ballDy;
            }
        }
    }

}
