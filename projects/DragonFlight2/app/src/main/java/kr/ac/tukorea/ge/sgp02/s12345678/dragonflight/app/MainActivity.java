package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game.MainGame;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private View tree1;
    private View tree2;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tree1 = findViewById(R.id.tree1);
        tree2 = findViewById(R.id.tree2);
        createAnimator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    @Override
    protected void onPause() {
        stopAnimation();
        super.onPause();
    }

    private void createAnimator() {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(30000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (Float)valueAnimator.getAnimatedValue();
//                Log.d(TAG, "Progerss: " + progress);
                float tx = -1 * tree1.getWidth() * progress;
                tree1.setTranslationX(tx);
                tree2.setTranslationX(tx);
            }
        });
    }

    protected void startAnimation() {
        animator.start();
    }

    protected void stopAnimation() {
        animator.end();
    }

    public void onBtnStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}