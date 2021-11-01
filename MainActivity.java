package com.newaeon.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easystudio.rotateimageview.RotateZoomImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    ImageView left_hand, left_leg, right_hand, right_leg;
    TextView tvRotationDegree,tvPivots;
    int rightHandRotationAngel = 0, leftHandRotationAngel = 0, rightLegRotationAngel = 0, leftLegRotationAngel = 0;
    ImageView right_hand_plus, left_hand_plus, right_leg_plus, left_leg_plus;
    ImageView right_hand_minus, left_hand_minus, right_leg_minus, left_leg_minus;
    RotateZoomImageView imgTop, bottomWear, dressWear;
    ImageView ivGalleryTop, ivGalleryBottom, ivGalleryDress;
    final int SELECT_IMAGE_TOP = 1;
    final int SELECT_IMAGE_BOTTOM = 2;
    final int SELECT_IMAGE_DRESS = 3;
    final int increasesDegree = 2;
    final int maxDegree = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        left_hand = findViewById(R.id.left_hand);
        tvRotationDegree = findViewById(R.id.tvRotationDegree);
        tvPivots = findViewById(R.id.tvPivots);

        right_leg = findViewById(R.id.right_leg);
        right_hand = findViewById(R.id.right_hand);
        left_leg = findViewById(R.id.left_leg);
        right_hand_plus = findViewById(R.id.right_hand_plus);
        left_hand_plus = findViewById(R.id.left_hand_plus);
        right_leg_plus = findViewById(R.id.right_leg_plus);
        left_leg_plus = findViewById(R.id.left_leg_plus);
        imgTop = findViewById(R.id.imgTop);
        bottomWear = findViewById(R.id.bottomWear);
        dressWear = findViewById(R.id.dressWear);
        ivGalleryTop = findViewById(R.id.ivGalleryTop);
        ivGalleryBottom = findViewById(R.id.ivGalleryBottom);
        ivGalleryDress = findViewById(R.id.ivGalleryDress);
        right_hand_minus= findViewById(R.id.right_hand_minus);
        left_hand_minus= findViewById(R.id.left_hand_minus);
        right_leg_minus= findViewById(R.id.right_leg_minus);
        left_leg_minus= findViewById(R.id.left_leg_minus);

        left_leg_minus.setOnClickListener(this);
        right_leg_minus.setOnClickListener(this);
        left_hand_minus.setOnClickListener(this);
        right_hand_minus.setOnClickListener(this);

        left_leg.setOnTouchListener(new ArrowTouchListenerLeft(tvRotationDegree,tvPivots));
        left_hand.setOnTouchListener(new ArrowTouchListenerLeft(tvRotationDegree,tvPivots));
        right_hand.setOnTouchListener(new ArrowTouchListenerRight(tvRotationDegree,tvPivots,MainActivity.this));
        right_leg.setOnTouchListener(new ArrowTouchListenerRight(tvRotationDegree,tvPivots,MainActivity.this));

        left_hand_plus.setOnClickListener(this);
        right_hand_plus.setOnClickListener(this);
        right_leg_plus.setOnClickListener(this);
        left_leg_plus.setOnClickListener(this);


        ivGalleryTop.setOnClickListener(this);
        ivGalleryDress.setOnClickListener(this);
        ivGalleryBottom.setOnClickListener(this);
        dressWear.setScaleType(ImageView.ScaleType.FIT_XY);


        imgTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imgTop.getLayoutParams();
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                imgTop.setLayoutParams(lp);

                return imgTop.onTouch(v, event);
            }
        });
        bottomWear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bottomWear.getLayoutParams();
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                bottomWear.setLayoutParams(lp);

                return bottomWear.onTouch(v, event);
            }
        });

        dressWear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) dressWear.getLayoutParams();
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                dressWear.setLayoutParams(lp);

                return dressWear.onTouch(v, event);
            }
        });
    }

    private void rotate(ImageView imageView, int fromDegree,int toDegree) {
        tvRotationDegree.setText("Degree "+-fromDegree +" to Degree "+-toDegree);

        if (Math.abs(toDegree) <= maxDegree) {

            RotateAnimation rotateImage = new RotateAnimation(-fromDegree, -toDegree,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0f);

            rotateImage.setStartOffset(fromDegree);
            rotateImage.setDuration(100);
            rotateImage.setFillAfter(true);
            rotateImage.setInterpolator(MainActivity.this, android.R.anim.decelerate_interpolator);

            imageView.startAnimation(rotateImage);
        } else {
            fromDegree = 0;
            RotateAnimation rotateImage = new RotateAnimation(0, 0,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0f);
            rotateImage.setStartOffset(fromDegree);
            rotateImage.setDuration(100);
            rotateImage.setFillAfter(true);
            rotateImage.setInterpolator(MainActivity.this, android.R.anim.decelerate_interpolator);

            imageView.startAnimation(rotateImage);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_hand_plus:
                if (Math.abs(leftHandRotationAngel) <= maxDegree) {
                    leftHandRotationAngel += increasesDegree;
                } else {
                    leftHandRotationAngel = increasesDegree;
                }
                rotate(left_hand,leftHandRotationAngel-increasesDegree, leftHandRotationAngel);


                break;
            case R.id.right_hand_plus:
                if (Math.abs(rightHandRotationAngel) <= maxDegree) {
                    rightHandRotationAngel += increasesDegree;
                } else rightHandRotationAngel = increasesDegree;

                rotate(right_hand,-rightHandRotationAngel-increasesDegree, -rightHandRotationAngel);
                break;
            case R.id.right_leg_plus:
                if (Math.abs(rightLegRotationAngel) <= maxDegree)
                    rightLegRotationAngel += increasesDegree;
                else rightLegRotationAngel = increasesDegree;

                rotate(right_leg,-(rightLegRotationAngel-increasesDegree), -rightLegRotationAngel);
                break;
            case R.id.left_leg_plus:
                if (Math.abs(leftLegRotationAngel) <= maxDegree)
                    leftLegRotationAngel += increasesDegree;
                else leftLegRotationAngel = increasesDegree;

                rotate(left_leg,leftLegRotationAngel-increasesDegree, leftLegRotationAngel);
                break;
            case R.id.ivGalleryBottom:
                openGallery(SELECT_IMAGE_BOTTOM);
                break;
            case R.id.ivGalleryDress:
                openGallery(SELECT_IMAGE_DRESS);
                break;
            case R.id.ivGalleryTop:
                openGallery(SELECT_IMAGE_TOP);

                break;

            case R.id.left_hand_minus:
                if (Math.abs(leftHandRotationAngel) > 0) {
                    leftHandRotationAngel -= increasesDegree;
                } else {
                    leftHandRotationAngel = 0;
                }
                rotate(left_hand,leftHandRotationAngel-2, leftHandRotationAngel);
                break;

            case R.id.left_leg_minus:
                if (Math.abs(leftLegRotationAngel) > 0)
                    leftLegRotationAngel -= increasesDegree;
                else leftLegRotationAngel = 0;

                rotate(left_leg,leftLegRotationAngel-2, leftLegRotationAngel);
                break;

            case R.id.right_hand_minus:
                if (Math.abs(rightHandRotationAngel) > 0) {
                    rightHandRotationAngel -= increasesDegree;
                } else rightHandRotationAngel = 0;

                rotate(right_hand,-rightHandRotationAngel-2, -rightHandRotationAngel);
                break;

            case R.id.right_leg_minus:
                if (Math.abs(rightLegRotationAngel) > 0)
                    rightLegRotationAngel -= increasesDegree;
                else rightLegRotationAngel = 0;

                rotate(right_leg,-rightLegRotationAngel-2, -rightLegRotationAngel);
                break;
        }
    }

    private void openGallery(int type) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), type);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_IMAGE_BOTTOM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            bottomWear.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "try again please", Toast.LENGTH_SHORT).show();
                }
                break;
            case SELECT_IMAGE_TOP:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            imgTop.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "try again please", Toast.LENGTH_SHORT).show();
                }
                break;
            case SELECT_IMAGE_DRESS:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            dressWear.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "try again please", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.left_leg:
                    // Do left Rotate animation
                    left_leg.animate().rotation(-90).setInterpolator(new LinearInterpolator()).setDuration(500);
                    break;

//                case R.id.rightTap:
//                    // Do right Rotate animation
//                    square.animate().rotation(90).setInterpolator(new LinearInterpolator()).setDuration(500);
//                    break;

                default:
                    break;
            }
        }
        return v.onTouchEvent(event);
    }

}