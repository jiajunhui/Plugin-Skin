package solid.ren.skinlibrary.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.view.View;


public class AnimatorUtils {
    public static Animator animViewFadeIn(View paramView) {
        return animViewFadeIn(paramView, 200L, null);
    }

    public static Animator animViewFadeIn(View paramView, long paramLong, Animator.AnimatorListener paramAnimatorListener) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[]{0.0F, 1.0F});
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static Animator animViewFadeOut(View paramView) {
        return animViewFadeOut(paramView, 200L, null);
    }

    public static Animator animViewFadeOut(View paramView, long paramLong, Animator.AnimatorListener paramAnimatorListener) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[]{1.0F, 0.0F});
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    /**
     * Changes the background color of the View, making the background color
     * to a more harmonious transition animation
     *
     * @param view
     * @param preColor  pre color
     * @param currColor current color
     * @param duration  animation duration
     */
    public static void showBackgroundColorAnimation(View view, int preColor, int currColor, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", new int[]{preColor, currColor});
        animator.setDuration(duration);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

}