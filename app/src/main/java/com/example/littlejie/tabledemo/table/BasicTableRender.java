package com.example.littlejie.tabledemo.table;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.SparseArray;
import com.example.littlejie.tabledemo.R;
import com.example.littlejie.tabledemo.Util;

/**
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public abstract class BasicTableRender {

  private static final String TAG = BasicTableRender.class.getSimpleName();
  private Context context;
  protected Paint backgroundPaint;
  protected TextPaint textPaint;

  protected int colorHeaderBg;
  protected int colorHeaderText;
  protected int colorTableBg;
  protected int colorTableText;

  private long startTimeInMills;
  private long endTimeInMills;
  protected List<Column> columns;
  protected List<Row> rows;
  private SparseArray<StaticLayout> array = new SparseArray<>();
  private List<Integer> rowHeightArray = new ArrayList<>();

  public BasicTableRender(Context context, long startTimeInMills, long endTimeInMills) {
    this.context = context;
    this.startTimeInMills = startTimeInMills;
    this.endTimeInMills = endTimeInMills;

    backgroundPaint = new Paint();
    backgroundPaint.setAntiAlias(true);

    textPaint = new TextPaint();
    textPaint.setAntiAlias(true);
    colorHeaderBg = context.getResources().getColor(R.color.colorTableHeaderBg);
    colorHeaderText = Color.WHITE;
    colorTableBg = context.getResources().getColor(R.color.colorTableContentBg);
    colorTableText = context.getResources().getColor(R.color.colorTableText);

    initColumns();
    initRow();

    measureHeaderHeight();
    measureEveryRowHeight();
  }

  public void draw(Canvas canvas) {
    int xOffset = 0;
    Rect rect = new Rect();
    for (Column column : getColumns()) {
      rect.left = xOffset;
      rect.top = 0;
      rect.right = xOffset + column.getWidth();
      rect.bottom = getHeight(0);
      drawHeader(canvas, column, rect);
    }
    drawHeaderDivider(canvas);

    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.get(i);
      drawRow(canvas, row, getHeight(i + 1));
    }
    drawRowDivider(canvas);
  }

  public List<Column> getColumns() {
    if (columns == null) {
      initColumns();
    }
    return columns;
  }

  public int getMeasuredWidth() {
    int width = 0;
    for (Column column : getColumns()) {
      width += column.getWidth();
    }
    return width;
  }

  public int getMeasuredHeight() {
    int height = 0;
    for (Integer rowHeight : rowHeightArray) {
      height += rowHeight;
    }
    return height;
  }

  protected abstract void initColumns();

  protected abstract void initRow();

  public abstract void drawHeader(Canvas canvas, Column column, Rect rect);

  public abstract void drawRow(Canvas canvas, Row row, int height);

  public void drawHeaderDivider(Canvas canvas) {
    int xOffset = 0;
    backgroundPaint.setColor(Color.WHITE);
    int maxHeight = rowHeightArray.get(0);
    for (int i = 0; i < getColumns().size(); i++) {
      if (i != getColumns().size() - 1) {
        canvas.save();
        Column column = getColumns().get(i);
        canvas.translate(xOffset + column.getWidth() - 1, 0);
        canvas.drawLine(0, 0, 2, maxHeight, backgroundPaint);
        xOffset += column.getWidth();
        canvas.restore();
      }
    }
  }

  public void drawRowDivider(Canvas canvas) {

  }

  private void measureHeaderHeight() {
    int maxHeight = 0;
    textPaint.setColor(colorHeaderText);
    textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.table_header_text_size));
    for (Column column : getColumns()) {
      String name = column.getName();
      StaticLayout staticLayout = getStaticLayout(name.hashCode());
      if (staticLayout == null) {
        staticLayout = new StaticLayout(name, textPaint, column.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, false);
      }
      if (staticLayout.getHeight() > maxHeight) {
        maxHeight = staticLayout.getHeight();
      }
      array.append(column.hashCode(), staticLayout);
    }
    Log.d(TAG, "maxHeight = " + maxHeight);
    rowHeightArray.add(maxHeight + Util.dpToPx(4) * 2);
  }

  private void measureEveryRowHeight() {
    textPaint.setColor(colorTableText);
    textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.table_content_text_size));
    for (int i = 0; i < rows.size(); i++) {
      int maxHeight = 0;
      Row row = rows.get(i);
      for (Column column : getColumns()) {
        String cellValue = row.getCellValue(column.getName());
        StaticLayout staticLayout = getStaticLayout(cellValue.hashCode());
        if (staticLayout == null) {
          staticLayout = new StaticLayout(row.getCellValue(column.getName()), textPaint, column.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, false);
        }
        if (staticLayout.getHeight() > maxHeight) {
          maxHeight = staticLayout.getHeight();
        }
        array.append(row.getCellValue(column.getName()).hashCode(), staticLayout);
      }
      rowHeightArray.add(maxHeight + Util.dpToPx(4) * 2);
    }
  }

  protected StaticLayout getStaticLayout(int key) {
    return array.get(key);
  }

  protected int getHeight(int index) {
    return rowHeightArray.get(index);
  }
}
