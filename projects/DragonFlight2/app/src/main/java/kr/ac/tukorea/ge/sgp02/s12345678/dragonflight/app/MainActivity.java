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
    private View trees;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trees = findViewById(R.id.trees);
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
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (Float)valueAnimator.getAnimatedValue();
                Log.d(TAG, "Progerss: " + progress);
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