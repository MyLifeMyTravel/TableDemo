package com.example.littlejie.tabledemo.table;

import java.util.HashMap;
import java.util.Map;

/**
 * key:列名的hash值，value：列对应行的值.
 * Copyright (c) 2018, Bongmi
 * All rights reserved
 * Author: lishengjie@bongmi.com.
 */
public class Row {
  private Map<Integer, Cell> cellMap = new HashMap<>();

  public Row() {
  }

  public void addCell(int column, Cell cell) {
    cellMap.put(column, cell);
  }

  public Cell getCell(int column) {
    return cellMap.get(column);
  }

  public Map<Integer, Cell> getCells() {
    return cellMap;
  }
}
