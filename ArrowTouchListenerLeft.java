package com.newaeon.myapplication;


import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ArrowTouchListenerLeft implements View.OnTouchListener {

    private final String tag = getClass().getSimpleName();
    private final int NONE = 0;
    private final int DRAG = 1;
    private final TextView rotationLbl, rotationLblPivot;
    private int mode = NONE;
    private int fromDegree=270;

    public ArrowTouchListenerLeft(TextView rotationLbl,TextView rotationLblPivot) {
        this.rotationLbl = rotationLbl;
        this.rotationLblPivot = rotationLblPivot;
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
    }

    private void onActionMoveDrag(ImageView view, MotionEvent event) {
//        view.setPivotY((view.getY() + view.getHeight()) *0.98f);


        int targetAngle = getAngle(view.getPivotX(), view.getPivotY(), event.getX(), event.getY());
        rotationLblPivot.setText("PIVOT "+view.getPivotX()+""+ view.getPivotY());

        int maxAngel = 360;
        if (targetAngle < 0) targetAngle = 0;
        if (targetAngle >maxAngel) targetAngle = maxAngel;
        int currentRotation =270 +  targetAngle;

        RotateAnimation rotate = new RotateAnimation(fromDegree, currentRotation,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0f);


        System.out.println("Left  Part From Degree "+fromDegree +" to Degree "+currentRotation);
        rotationLbl.setText("Left  Part From Degree "+fromDegree +" to Degree "+currentRotation);
        rotate.setFillAfter(true);
        rotate.setDuration(100);

        view.startAnimation(rotate);

        fromDegree = targetAngle;

//        if(fromDegree >= 130){
//            fromDegree=0;
//        }

//        rotationLbl.setText("Left  Part From Degree "+-fromDegree +" to Degree "+-currentRotation);
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