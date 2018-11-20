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
  private static final float HALF_DIVIDER_WIDTH = Util.dpToPx(0.25f);
  private Context context;
  protected Paint backgroundPaint;
  protected TextPaint textPaint;
  private TextPaint headerPaint;

  protected int colorHeaderBg;
  protected int colorHeaderText;
  protected int colorTableBg;
  protected int colorTableText;

  private long startTimeInMills;
  private long endTimeInMills;
  protected Row header = new Row();
  protected List<Row> rows;
  private SparseArray<StaticLayout> array = new SparseArray<>();
  private List<Integer> rowHeightArray = new ArrayList<>();

  public BasicTableRender(Context context, long startTimeInMills, long endTimeInMills) {
    this.context = context;
    this.startTimeInMills = startTimeInMills;
    this.endTimeInMills = endTimeInMills;

    colorHeaderBg = context.getResources().getColor(R.color.colorTableHeaderBg);
    colorHeaderText = context.getResources().getColor(android.R.color.white);
    colorTableBg = context.getResources().getColor(R.color.colorTableContentBg);
    colorTableText = context.getResources().getColor(R.color.colorTableText);
    initPaint();
    initColumns();
    initRow();
    updateCellWidth();

    measureHeaderHeight();
    measureEveryRowHeight();
  }

  private void initPaint() {
    backgroundPaint = new Paint();
    backgroundPaint.setAntiAlias(true);
    textPaint = new TextPaint();
    textPaint.setAntiAlias(true);
    textPaint.setColor(colorTableText);
    textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.table_content_text_size));
    headerPaint = new TextPaint();
    headerPaint.setAntiAlias(true);
    headerPaint.setColor(colorHeaderText);
    headerPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.table_header_text_size));
  }

  private void updateCellWidth() {
    for (Row row : rows) {
      for (int i = 0; i < getColumnNum(); i++) {
        Cell cell = header.getCell(i);
        row.getCell(i).setWidth(cell.getWidth());
      }
    }
  }

  public void draw(Canvas canvas) {
    int xOffset = 0;
    Rect rect = new Rect();
    //绘制表头
    for (int i = 0; i < getColumnNum(); i++) {
      Cell cell = header.getCell(i);
      rect.left = xOffset;
      rect.top = 0;
      rect.right = xOffset + cell.getWidth();
      rect.bottom = getHeight(0);
      cell.setRect(rect);
      drawHeader(canvas, cell);
      drawHeaderBorder(canvas, cell);
      xOffset += cell.getWidth();
    }

    //绘制表格数据
    int yOffset = getHeight(0);
    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.get(i);
      int rowHeight = getHeight(i + 1);
      xOffset = 0;
      for (int j = 0; j < getColumnNum(); j++) {
        Cell cell = row.getCell(j);
        rect.left = xOffset;
        rect.top = yOffset;
        rect.right = xOffset + cell.getWidth();
        rect.bottom = yOffset + rowHeight;
        cell.setRect(rect);
        drawCellBackground(canvas, cell);
        drawCell(canvas, cell);
        drawCellBorder(canvas, cell);
        xOffset += cell.getWidth();
      }
      yOffset += rowHeight;
    }

  }

  protected int getColumnNum() {
    return header.getCells().size();
  }

  public int getMeasuredWidth() {
    int width = 0;
    for (Cell cell : header.getCells().values()) {
      width += cell.getWidth();
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

  /**
   * 绘制表头.
   *
   * @param canvas
   * @param cell   列
   */
  protected void drawHeader(Canvas canvas, Cell cell) {
    Rect rect = cell.getRect();
    int height = rect.height() / 2;
    //中点坐标(xOffset + column.getWidth() / 2, 100)
    backgroundPaint.setColor(colorHeaderBg);
    canvas.drawRect(rect, backgroundPaint);

    //居中绘制折行文字
    //StaticLayout是默认画在Canvas的(0,0)点
    canvas.save();
    canvas.translate(rect.left + rect.width() / 2, height);
    StaticLayout staticLayout = getStaticLayout(cell.hashCode());
    canvas.translate(-staticLayout.getWidth() / 2, -staticLayout.getHeight() / 2);
    staticLayout.draw(canvas);
    canvas.restore();
  }

  /**
   * 绘制表格单元格.
   *
   * @param canvas
   * @param cell
   */
  protected void drawCell(Canvas canvas, Cell cell) {
    canvas.save();
    Rect rect = cell.getRect();
    canvas.translate(rect.left + cell.getWidth() / 2, rect.top + rect.height() / 2);
    //居中绘制折行文字
    //StaticLayout是默认画在Canvas的(0,0)点
    StaticLayout staticLayout = getStaticLayout(cell.hashCode());
    canvas.translate(-staticLayout.getWidth() / 2, -staticLayout.getHeight() / 2);
    staticLayout.draw(canvas);
    canvas.restore();
  }

  /**
   * 绘制单元格背景.
   *
   * @param canvas
   * @param cell
   */
  public void drawCellBackground(Canvas canvas, Cell cell) {

  }

  protected void drawHeaderBorder(Canvas canvas, Cell cell) {
    backgroundPaint.setColor(Color.WHITE);
    Rect rect = cell.getRect();
    if (cell.getColumn() != getColumnNum() - 1) {
      canvas.drawRect(rect.right - HALF_DIVIDER_WIDTH, rect.top,
          rect.right + HALF_DIVIDER_WIDTH, rect.bottom, backgroundPaint);
    }
  }

  public void drawCellBorder(Canvas canvas, Cell cell) {
    backgroundPaint.setColor(Color.BLACK);
    Rect rect = cell.getRect();
    if (cell.getColumn() == 0) {
      canvas.drawRect(rect.left, rect.top,
          rect.left + HALF_DIVIDER_WIDTH * 2, rect.bottom, backgroundPaint);
    }
    canvas.drawRect(rect.right - HALF_DIVIDER_WIDTH, rect.top,
        rect.right + HALF_DIVIDER_WIDTH, rect.bottom, backgroundPaint);
    canvas.drawRect(rect.left, rect.bottom - HALF_DIVIDER_WIDTH * 2,
        rect.right, rect.bottom, backgroundPaint);
  }

  private void measureHeaderHeight() {
    int maxHeight = 0;
    for (Cell cell : header.getCells().values()) {
      String name = cell.getContent();
      StaticLayout staticLayout = getStaticLayout(name.hashCode());
      if (staticLayout == null) {
        staticLayout = new StaticLayout(name, headerPaint, cell.getWidth(),
            Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, false);
      }
      if (staticLayout.getHeight() > maxHeight) {
        maxHeight = staticLayout.getHeight();
      }
      array.append(cell.hashCode(), staticLayout);
    }
    Log.d(TAG, "maxHeight = " + maxHeight);
    rowHeightArray.add(maxHeight + Util.dpToPx(4) * 2);
  }

  private void measureEveryRowHeight() {
    for (int i = 0; i < rows.size(); i++) {
      int maxHeight = 0;
      Row row = rows.get(i);
      for (Cell cell : row.getCells().values()) {
        String value = cell.getContent();
        StaticLayout staticLayout = getStaticLayout(cell.hashCode());
        if (staticLayout == null) {
          staticLayout = new StaticLayout(value, textPaint, cell.getWidth(),
              Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, false);
        }
        if (staticLayout.getHeight() > maxHeight) {
          maxHeight = staticLayout.getHeight();
        }
        array.append(cell.hashCode(), staticLayout);
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
