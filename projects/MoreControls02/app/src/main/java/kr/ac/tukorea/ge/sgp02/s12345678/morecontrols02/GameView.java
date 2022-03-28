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
        int width = getWidth();
        int height = getHeight();
        int lp = getPaddingLeft();
        int tp = getPaddingTop();
        int rp = getPaddingRight();
        int bp = getPaddingBottom();

        int cw = width - lp - rp;
        int ch = height - tp - bp;

        canvas.drawRoundRect(lp, tp, lp + cw, tp + ch, 30, 40, paint);
    }
}
