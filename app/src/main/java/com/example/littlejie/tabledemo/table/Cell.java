package com.example.littlejie.tabledemo.table;

import android.graphics.Rect;

/**
 * 单元格.
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class Cell {

  private int column;
  private int row;
  private String content;
  private int width;
  private Rect rect;

  public Cell() {
  }

  public Cell(int row, int column, String content) {
    this.row = row;
    this.column = column;
    this.content = content;
  }

  public Cell(int column, int row, String content, int width) {
    this.column = column;
    this.row = row;
    this.content = content;
    this.width = width;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public Rect getRect() {
    return rect;
  }

  public void setRect(Rect rect) {
    this.rect = rect;
  }
}
