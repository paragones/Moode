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

    int minHeight = 0;

    public void toggleStatusCardView(final StatusRecyclerAdapter.StatusViewHolder v, Resources res, String tag) {
        if (tag.equals("collapsed")) {
            // expand cardview
            minHeight = v.crdStatus.getHeight();

//            v.imgMood.getLayoutParams().height = (int) res.getDimension(R.dimen.mood_image_exp);
//            v.imgMood.getLayoutParams().width = (int) res.getDimension(R.dimen.mood_image_exp);
//
//            v.txtMood.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.mood_text_size_exp));
//
//            v.imgLocation.getLayoutParams().height = (int) res.getDimension(R.dimen.info_image_exp);
//            v.imgLocation.getLayoutParams().width = (int) res.getDimension(R.dimen.info_image_exp);
//            v.txtLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.info_text_size_exp));
//
//            v.imgPerson.getLayoutParams().height = (int) res.getDimension(R.dimen.info_image_exp);
//            v.imgPerson.getLayoutParams().width = (int) res.getDimension(R.dimen.info_image_exp);
//            v.txtPerson.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.info_text_size_exp));
//
//            v.imgActivity.getLayoutParams().height = (int) res.getDimension(R.dimen.info_image_exp);
//            v.imgActivity.getLayoutParams().width = (int) res.getDimension(R.dimen.info_image_exp);
//            v.txtActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.info_text_size_exp));
//
//            v.txtNote.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.note_text_size_exp));
//            v.txtNote.setMaxLines(Integer.MAX_VALUE);
//
//            v.layoutMisc.setVisibility(View.VISIBLE);

            v.crdStatus.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int crdMaxHeight = v.crdStatus.getHeight();

            final int imgMoodMinParams = (int) res.getDimension(R.dimen.mood_image);
            final int imgMoodMaxParams = (int) res.getDimension(R.dimen.mood_image_exp) - imgMoodMinParams;

            final int txtMoodMinTextSize = (int) res.getDimension(R.dimen.mood_text_size);
            final int txtMoodMaxTextSize = (int) res.getDimension(R.dimen.mood_text_size_exp) - txtMoodMinTextSize;

            final int infoMinParams = (int) res.getDimension(R.dimen.info_image);
            final int infoMaxParams = (int) res.getDimension(R.dimen.info_image_exp) - infoMinParams;

            final int infoMinTextSize = (int) res.getDimension(R.dimen.info_text_size);
            final int infoMaxTextSize = (int) res.getDimension(R.dimen.info_text_size_exp) - infoMinTextSize;

            final int noteMinTextSize = (int) res.getDimension(R.dimen.note_text_size);
            final int noteMaxTextSize = (int) res.getDimension(R.dimen.note_text_size_exp) - noteMinTextSize;

            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.crdStatus.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    } else {
                        v.crdStatus.getLayoutParams().height = (int) (minHeight + (crdMaxHeight * interpolatedTime));

                        v.imgMood.getLayoutParams().height = (int) (imgMoodMinParams + (imgMoodMaxParams * interpolatedTime));
                        v.imgMood.getLayoutParams().width = (int) (imgMoodMinParams + (imgMoodMaxParams * interpolatedTime));
                        v.txtMood.setTextSize(TypedValue.COMPLEX_UNIT_PX, (txtMoodMinTextSize + (txtMoodMaxTextSize * interpolatedTime)));

                        v.imgLocation.getLayoutParams().height = (int) (infoMinParams + (infoMaxParams * interpolatedTime));
                        v.imgLocation.getLayoutParams().width = (int) (infoMinParams + (infoMaxParams * interpolatedTime));
                        v.txtLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (infoMinTextSize + (infoMaxTextSize * interpolatedTime)));

                        v.imgPerson.getLayoutParams().height = (int) (infoMinParams + (infoMaxParams * interpolatedTime));
                        v.imgPerson.getLayoutParams().width = (int) (infoMinParams + (infoMaxParams * interpolatedTime));
                        v.txtPerson.setTextSize(TypedValue.COMPLEX_UNIT_PX, (infoMinTextSize + (infoMaxTextSize * interpolatedTime)));

                        v.imgActivity.getLayoutParams().height = (int) (infoMinParams + (infoMaxParams * interpolatedTime));
                        v.imgActivity.getLayoutParams().width = (int) (infoMinParams + (infoMaxParams * interpolatedTime));
                        v.txtActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, (infoMinTextSize + (infoMaxTextSize * interpolatedTime)));

                        v.txtNote.setTextSize(TypedValue.COMPLEX_UNIT_PX, (noteMinTextSize + (noteMaxTextSize * interpolatedTime)));
                        v.txtNote.setMaxLines(Integer.MAX_VALUE);

                        v.layoutMisc.setVisibility(View.VISIBLE);
                    }
                    v.crdStatus.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            a.setDuration(500);
            v.crdStatus.startAnimation(a);
        }
    }
}
