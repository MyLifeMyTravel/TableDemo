package com.example.littlejie.tabledemo.table;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class Table extends View {

  private BasicTableRender render;

  public Table(Context context) {
    this(context, null);
  }

  public Table(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public Table(Context context, @Nullable AttributeSet attrs,
               int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(render.getMeasuredWidth(), render.getMeasuredHeight());
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (render == null) {
      return;
    }
    render.draw(canvas);
  }

  public void setRender(BasicTableRender render) {
    this.render = render;
    invalidate();
  }

}
