package com.example.littlejie.tabledemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class Util {

  public static int dpToPx(float dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }

  public static int pxToDp(float px) {
    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
  }

  public static Bitmap convertViewToBitmap(View view) {
    view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    view.buildDrawingCache();
    return view.getDrawingCache();
  }
}
