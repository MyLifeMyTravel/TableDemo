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
  private Map<String, String> cells = new HashMap<>();

  public Row() {
  }

  public Row(Map<String, String> cells) {
    this.cells = cells;
  }

  public Map<String, String> getCells() {
    return cells;
  }

  public void setCells(Map<String, String> cells) {
    this.cells = cells;
  }

  public void addCell(String key, String value) {
    cells.put(key, value);
  }

  public String getCellValue(String key) {
    return cells.get(key);
  }
}
