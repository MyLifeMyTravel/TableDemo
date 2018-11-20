package com.example.littlejie.tabledemo;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class CustomHorizontalScrollView extends View {

  private Paint paint;

  public CustomHorizontalScrollView(Context context) {
    this(context, null);
  }

  public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomHorizontalScrollView(Context context, AttributeSet attrs,
                                    int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    paint = new Paint();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(5000, getMeasuredHeight());
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
      paint.setColor(color);
      int width = 300;
      Rect rect = new Rect(i * width, 0, (i + 1) * width, width);
      canvas.drawRect(rect, paint);
    }
  }
}
