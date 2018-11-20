package com.example.littlejie.tabledemo.table;

/**
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class Column {

  private String name;
  private int width;

  public Column(String name, int width) {
    this.name = name;
    this.width = width;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

}
