package com.example.customeslideuppanelexampe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout linear_slide, linear_slide_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {

        linear_slide = findViewById(R.id.linear_slide);
        linear_slide_next = findViewById(R.id.linear_slide_next);

        linear_slide.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeTop() {

                TranslateAnimation animation = new TranslateAnimation(0, 0,
                        350, 0);
                animation.setDuration(150);
                animation.setFillAfter(false);
                animation.setAnimationListener(new MyAnimationListener());
                linear_slide_next.startAnimation(animation);

                linear_slide_next.setVisibility(View.VISIBLE);
                Log.v("Slide ", "onTop");
            }

            public void onSwipeBottom() {

                TranslateAnimation animation = new TranslateAnimation(0, 0,
                        -350, 0);
                animation.setDuration(150);
                animation.setFillAfter(false);
                animation.setAnimationListener(new MyAnimationListener());
                linear_slide_next.startAnimation(animation);

                linear_slide_next.setVisibility(View.GONE);
                Log.v("Slide ", "onBottom");
            }

        });


    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            linear_slide_next.clearAnimation();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(linear_slide_next.getWidth(),
                    linear_slide_next.getHeight());
            //  lp.setMargins(50, 100, 0, 0);
            linear_slide_next.setLayoutParams(lp);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }


}