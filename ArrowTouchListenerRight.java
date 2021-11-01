package com.newaeon.myapplication;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ArrowTouchListenerRight implements View.OnTouchListener {

    private final String tag = getClass().getSimpleName();
    private final int NONE = 0;
    private final int DRAG = 1;
    private final TextView rotationLbl;
    private int mode = NONE;
    public int fromDegree =270;
    public int offsetDegree =270;
    int maxDegree = 180;

    Context context;
    private final TextView rotationLblPivot;

    public ArrowTouchListenerRight(TextView rotationLbl,TextView rotationLblPivot, Context context) {
        this.rotationLbl = rotationLbl;
        this.rotationLblPivot = rotationLblPivot;
        this.context = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event);
                break;
            case MotionEvent.ACTION_UP:
                onActionUp(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(event);
                break;
            case MotionEvent.ACTION_MOVE:
                switch (mode) {
                    case DRAG:
                        onActionMoveDrag(view, event);
                        break;
                }
                break;
        }

        return true;
    }

    private void onActionDown(MotionEvent event) {
        mode = DRAG;
    }

    private void onActionUp(MotionEvent event) {
        mode = NONE;
    }

    private void onActionMoveDrag(ImageView view, MotionEvent event) {
        view.setPivotY((view.getY() + view.getHeight()) * .98f);
        int targetAngle = offsetDegree - getAngle(view.getPivotX(), view.getPivotY(), event.getX(), event.getY());
        rotationLblPivot.setText("PIVOT "+view.getPivotX()+""+ view.getPivotY());

        if (targetAngle > offsetDegree) targetAngle = offsetDegree;
        if(targetAngle<maxDegree) targetAngle =maxDegree;


        RotateAnimation rotate = new RotateAnimation(fromDegree, targetAngle,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0f);

        rotate.setFillAfter(true);
        rotate.setDuration(100);
//        rotate.setInterpolator(context, android.R.anim.linear_interpolator);

        rotationLbl.setText("Right  Part From Degree "+fromDegree +" to Degree "+targetAngle);


        view.startAnimation(rotate);

        fromDegree = targetAngle;
//        if(fromDegree >= 110){
//            fromDegree=0;
//        }
//        rotationLbl.setText(targetAngle + "*");
//        rotationLbl.setText("Right  Part From Degree "+fromDegree +" to Degree "+toDegree);

    }

    private void onPointerUp(MotionEvent event) {
        mode = NONE;
    }

    private int getAngle(float xt, float yt, float x, float y) {
        float dx = x - xt;
        float dy = yt - y;

        double inRads = Math.atan2(dy, dx);

        return (int) Math.toDegrees(inRads);
    }
}