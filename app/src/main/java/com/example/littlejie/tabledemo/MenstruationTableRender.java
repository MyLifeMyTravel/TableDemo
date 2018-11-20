package com.example.littlejie.tabledemo;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import com.example.littlejie.tabledemo.table.BasicTableRender;
import com.example.littlejie.tabledemo.table.Cell;
import com.example.littlejie.tabledemo.table.Row;

import cn.lollypop.be.model.bodystatus.MenstruationInfo;

/**
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class MenstruationTableRender extends BasicTableRender {

  public MenstruationTableRender(Context context,
                                 long startTimeInMills,
                                 long endTimeInMills) {
    super(context, startTimeInMills, endTimeInMills);
  }

  @Override
  protected void initColumns() {
    header.addCell(0, new Cell(0, 0, "日期", Util.dpToPx(48)));
    header.addCell(1, new Cell(0, 1, "周期", Util.dpToPx(48)));
    header.addCell(2, new Cell(0, 2, "经期", Util.dpToPx(140)));
    header.addCell(3, new Cell(0, 3, "经期颜色", Util.dpToPx(140)));
    header.addCell(4, new Cell(0, 4, "经期流量", Util.dpToPx(140)));
    header.addCell(5, new Cell(0, 5, "痛经", Util.dpToPx(140)));
    header.addCell(6, new Cell(0, 6, "血块", Util.dpToPx(140)));
  }

  @Override
  protected void initRow() {
    Random random = new Random();
    rows = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      MenstruationInfo menstruationInfo = new MenstruationInfo();
      menstruationInfo.setBloodClot(random.nextInt(3));
      menstruationInfo.setColor(random.nextInt(5));
      menstruationInfo.setCramps(random.nextInt(4));
      menstruationInfo.setVolume(random.nextInt(4));

      Row row = new Row();
      for (int j = 0; j < getColumnNum(); j++) {
        if (j == 0) {
          row.addCell(j, new Cell(i, j, "3/1"));
        } else if (j == 1) {
          row.addCell(j, new Cell(i, j, "1"));
        } else if (j == 2) {
          row.addCell(j, new Cell(i, j, "第1天"));
        } else {
          row.addCell(j, new Cell(i, j, "浅红"));
        }
      }
      rows.add(row);
    }
  }

  @Override
  public void drawCellBackground(Canvas canvas, Cell cell) {
    Random random = new Random();
    int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    backgroundPaint.setColor(color);
    if (cell.getColumn() == 0 || cell.getColumn() == 1) {
      backgroundPaint.setColor(colorTableBg);
    }
    canvas.drawRect(cell.getRect(), backgroundPaint);
  }
}
