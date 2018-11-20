package com.example.littlejie.tabledemo;

import android.content.res.Resources;

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
}
