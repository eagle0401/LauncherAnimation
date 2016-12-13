package com.hwm.gico.launcheranimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    private RelativeLayout mRootView;
    private View mAnimationView;

    private int mClickLeft;
    private int mClickRight;
    private int mClickTop;
    private int mClickBottom;


    private AnimatorSet mAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        Rect clickPosition = getIntent().getSourceBounds();

        if (clickPosition == null) {
            finish();
            return;
        }

        mClickLeft = clickPosition.left;
        mClickTop = clickPosition.top;
        mClickRight = clickPosition.right;
        mClickBottom = clickPosition.bottom;

        mRootView = (RelativeLayout) findViewById(R.id.activity_main);

        mAnimationView = LayoutInflater.from(this).inflate(R.layout.animation_view, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mAnimationView.setLayoutParams(layoutParams);
        mAnimationView.setAlpha(0F);

        mRootView.addView(mAnimationView);
        initAnimator();
    }

    private void initAnimator() {
        ObjectAnimator startDelayTime = new ObjectAnimator();
        startDelayTime.setProperty(View.ALPHA);
        startDelayTime.setFloatValues(0F, 0F);
        startDelayTime.setDuration(500);

        ObjectAnimator startAlpha = new ObjectAnimator();
        startAlpha.setProperty(View.ALPHA);
        startAlpha.setFloatValues(0F, 1F);
        startAlpha.setDuration(300);

        ObjectAnimator startPositionX = new ObjectAnimator();
        startPositionX.setProperty(View.TRANSLATION_X);
        startPositionX.setFloatValues(mClickLeft, mClickLeft);
        startPositionX.setDuration(300);

        ObjectAnimator startPositionY = new ObjectAnimator();
        startPositionY.setProperty(View.TRANSLATION_Y);
        startPositionY.setFloatValues(mClickTop, mClickTop);
        startPositionY.setDuration(300);

        ObjectAnimator startMovePositionX = new ObjectAnimator();
        startMovePositionX.setProperty(View.TRANSLATION_X);
        startMovePositionX.setFloatValues(mClickLeft, mClickRight);
        startMovePositionX.setDuration(1500);

        ObjectAnimator startMovePositionY = new ObjectAnimator();
        startMovePositionY.setProperty(View.TRANSLATION_Y);
        startMovePositionY.setFloatValues(mClickTop, mClickBottom);
        startMovePositionY.setDuration(1500);

        ObjectAnimator startMoveScaleX = new ObjectAnimator();
        startMoveScaleX.setProperty(View.SCALE_X);
        startMoveScaleX.setFloatValues(1F, 3F);
        startMoveScaleX.setDuration(1500);

        ObjectAnimator startMoveScaleY = new ObjectAnimator();
        startMoveScaleY.setProperty(View.SCALE_Y);
        startMoveScaleY.setFloatValues(1F, 3F);
        startMoveScaleY.setDuration(1500);

        ObjectAnimator stayAlpha = new ObjectAnimator();
        stayAlpha.setProperty(View.ALPHA);
        stayAlpha.setFloatValues(1F, 1F);
        stayAlpha.setDuration(500);

        ObjectAnimator endMovePositionX = new ObjectAnimator();
        endMovePositionX.setProperty(View.TRANSLATION_X);
        endMovePositionX.setFloatValues(mClickRight, mClickLeft);
        endMovePositionX.setDuration(1500);

        ObjectAnimator endMovePositionY = new ObjectAnimator();
        endMovePositionY.setProperty(View.TRANSLATION_Y);
        endMovePositionY.setFloatValues(mClickBottom, mClickTop);
        endMovePositionY.setDuration(1500);

        ObjectAnimator endMoveScaleX = new ObjectAnimator();
        endMoveScaleX.setProperty(View.SCALE_X);
        endMoveScaleX.setFloatValues(3F, 1F);
        endMoveScaleX.setDuration(1500);

        ObjectAnimator endMoveScaleY = new ObjectAnimator();
        endMoveScaleY.setProperty(View.SCALE_Y);
        endMoveScaleY.setFloatValues(3F, 1F);
        endMoveScaleY.setDuration(1500);

        ObjectAnimator endAlpha = new ObjectAnimator();
        endAlpha.setProperty(View.ALPHA);
        endAlpha.setFloatValues(1F, 0F);
        endAlpha.setDuration(300);

        ObjectAnimator endDelayTime = new ObjectAnimator();
        endDelayTime.setProperty(View.ALPHA);
        endDelayTime.setFloatValues(0F, 0F);
        endDelayTime.setDuration(500);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(startDelayTime).before(startAlpha);
        mAnimatorSet.play(startAlpha).with(startPositionX);
        mAnimatorSet.play(startPositionX).with(startPositionY);
        mAnimatorSet.play(startPositionY).before(startMovePositionX);
        mAnimatorSet.play(startMovePositionX).with(startMovePositionY);
        mAnimatorSet.play(startMovePositionY).with(startMoveScaleX);
        mAnimatorSet.play(startMoveScaleX).with(startMoveScaleY);
        mAnimatorSet.play(startMoveScaleY).before(stayAlpha);
        mAnimatorSet.play(stayAlpha).before(endMovePositionX);
        mAnimatorSet.play(endMovePositionX).with(endMovePositionY);
        mAnimatorSet.play(endMovePositionY).with(endMoveScaleX);
        mAnimatorSet.play(endMoveScaleX).with(endMoveScaleY);
        mAnimatorSet.play(endMoveScaleY).before(endAlpha);
        mAnimatorSet.play(endAlpha).before(endDelayTime);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {}
            @Override public void onAnimationEnd(Animator animation) {
                finish();
            }
            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimatorSet.setTarget(mAnimationView);
        mAnimatorSet.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAnimationView.setAlpha(0F);
    }
}
