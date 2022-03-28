package kr.ac.tukorea.ge.sgp02.s12345678.morecontrols02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawRoundRect(10, 20, 100, 300, 30, 40, paint);
    }
}
