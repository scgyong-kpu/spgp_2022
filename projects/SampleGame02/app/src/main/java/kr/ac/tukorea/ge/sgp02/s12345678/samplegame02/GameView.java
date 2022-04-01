package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

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
    private Bitmap soccerBitmap;
    private Rect soccerSrcRect = new Rect();
    private Rect soccerDstRect = new Rect();

    private Handler handler = new Handler();
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);

        soccerSrcRect.set(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        soccerDstRect.set(0, 0, 200, 200);

        updateFrame();
    }

    private void updateFrame() {
        soccerDstRect.offset(1, 1);
        invalidate();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateFrame();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(soccerBitmap, soccerSrcRect, soccerDstRect, null);
        Log.d(TAG, "onDraw()");
    }
}
