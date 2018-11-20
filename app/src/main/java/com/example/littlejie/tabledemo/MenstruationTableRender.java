package com.example.littlejie.tabledemo;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.StaticLayout;
import com.example.littlejie.tabledemo.table.BasicTableRender;
import com.example.littlejie.tabledemo.table.Column;
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
    columns = new ArrayList<>();
    columns.add(new Column("日期", Util.dpToPx(48)));
    columns.add(new Column("周期", Util.dpToPx(48)));
    columns.add(new Column("经期", Util.dpToPx(140)));
    columns.add(new Column("经期颜色", Util.dpToPx(140)));
    columns.add(new Column("经期流量", Util.dpToPx(140)));
    columns.add(new Column("痛经", Util.dpToPx(140)));
    columns.add(new Column("血块", Util.dpToPx(140)));
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
      for (int j = 0; j < getColumns().size(); j++) {
        Column column = getColumns().get(j);
        if (j == 0) {
          row.addCell(column.getName(), "3/1");
        } else if (j == 1) {
          row.addCell(column.getName(), "1");
        } else if (j == 2) {
          row.addCell(column.getName(), "第1天");
        } else {
          row.addCell(column.getName(), "浅红");
        }
      }
      rows.add(row);
    }
  }

  @Override
  public void drawHeader(Canvas canvas, Column column, Rect rect) {
    int height = rect.height() / 2;
    canvas.save();

    //中点坐标(xOffset + column.getWidth() / 2, 100)
    canvas.translate(rect.left + rect.width() / 2, height);

    backgroundPaint.setColor(colorHeaderBg);
    //Rect rect = new Rect(-column.getWidth() / 2, -height, column.getWidth() / 2, height);
    canvas.drawRect(rect, backgroundPaint);

    //居中绘制折行文字
    //StaticLayout是默认画在Canvas的(0,0)点
    StaticLayout staticLayout = getStaticLayout(column.hashCode());
    canvas.translate(-staticLayout.getWidth() / 2, -staticLayout.getHeight() / 2);
    staticLayout.draw(canvas);

    canvas.restore();
  }

  @Override
  public void drawRow(Canvas canvas, Row row, int height) {
    int h = getHeight(0);
    int xOffset = 0;
    for (Column column : getColumns()) {
      canvas.save();

      canvas.translate(xOffset + column.getWidth() / 2, h + height / 2);

      //居中绘制折行文字
      //StaticLayout是默认画在Canvas的(0,0)点
      StaticLayout staticLayout = getStaticLayout(row.getCellValue(column.getName()).hashCode());
      canvas.translate(-staticLayout.getWidth() / 2, -staticLayout.getHeight() / 2);
      staticLayout.draw(canvas);

      canvas.restore();

      xOffset += column.getWidth();
    }
    h += height;
  }
}
