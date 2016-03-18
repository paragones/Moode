package com.paularagones.moode.Animations;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.paularagones.moode.Adapters.StatusRecyclerAdapter;
import com.paularagones.moode.R;

/**
 * Created by Mae Anjanette Docena on 3/18/2016.
 */
public class Animator {

    public void toggleStatusCardView(final StatusRecyclerAdapter.StatusViewHolder v, Resources res, String tag) {
        if (tag.equals("collapsed")) {
            // expand cardview
            v.crdStatus.getLayoutParams().height = v.crdStatus.getMeasuredHeight();

            v.imgMood.getLayoutParams().height = (int) res.getDimension(R.dimen.mood_image_exp);
            v.imgMood.getLayoutParams().width = (int) res.getDimension(R.dimen.mood_image_exp);

            v.txtMood.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.mood_text_size_exp));

            v.imgLocation.getLayoutParams().height = (int) res.getDimension(R.dimen.info_image_exp);
            v.imgLocation.getLayoutParams().width = (int) res.getDimension(R.dimen.info_image_exp);
            v.txtLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.info_text_size_exp));

            v.imgPerson.getLayoutParams().height = (int) res.getDimension(R.dimen.info_image_exp);
            v.imgPerson.getLayoutParams().width = (int) res.getDimension(R.dimen.info_image_exp);
            v.txtPerson.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.info_text_size_exp));

            v.imgActivity.getLayoutParams().height = (int) res.getDimension(R.dimen.info_image_exp);
            v.imgActivity.getLayoutParams().width = (int) res.getDimension(R.dimen.info_image_exp);
            v.txtActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.info_text_size_exp));

            v.txtNote.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.note_text_size_exp));
            v.txtNote.setMaxLines(Integer.MAX_VALUE);

            v.layoutMisc.setVisibility(View.VISIBLE);

            v.crdStatus.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int targetHeight = v.crdStatus.getMeasuredHeight();

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.crdStatus.getLayoutParams().height = interpolatedTime == 1
                            ? ViewGroup.LayoutParams.WRAP_CONTENT
                            : (int)(targetHeight * interpolatedTime);
                    v.crdStatus.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            //a.setDuration((int)(targetHeight / v.crdStatusExp.getContext().getResources().getDisplayMetrics().density));
            a.setDuration(10000);
            v.crdStatus.startAnimation(a);}
//            ValueAnimator anim = ValueAnimator.ofInt(minHeight, maxHeight);
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    v.crdStatusExp.setVisibility(View.VISIBLE);
//                    int val = (Integer) valueAnimator.getAnimatedValue();
//                    ViewGroup.LayoutParams layoutParams = v.crdStatusExp.getLayoutParams();
//                    layoutParams.height = val;
//                    v.crdStatusExp.setLayoutParams(layoutParams);
//                }
//            });
//            anim.start();
//        } else if (tag.equals("expanded")){
//            // collapse cardview
//            ValueAnimator anim = ValueAnimator.ofInt(maxHeight, minHeight);
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    v.crdStatusExp.setVisibility(View.GONE);
//                    int val = (Integer) valueAnimator.getAnimatedValue();
//                    ViewGroup.LayoutParams layoutParams = v.crdStatus.getLayoutParams();
//                    layoutParams.height = val;
//                    v.crdStatus.setLayoutParams(layoutParams);
//                }
//            });
//            anim.start();
//        }
    }
}
