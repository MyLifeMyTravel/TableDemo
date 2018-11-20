package com.example.littlejie.tabledemo;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.littlejie.tabledemo.table.Column;
import com.example.littlejie.tabledemo.table.Row;
import com.example.littlejie.tabledemo.table.Table;

public class MainActivity extends AppCompatActivity {

  private Table table;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    table = (Table) findViewById(R.id.table);
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -10);
    long startTimeInMills = calendar.getTimeInMillis();
    long endTimeInMills = System.currentTimeMillis();
    MenstruationTableRender render = new MenstruationTableRender(this, startTimeInMills, endTimeInMills);
    table.setRender(render);

    Column column = new Column("测试", 60);
    Row row = new Row();
    row.addCell("测试", "测试");
    Log.d("TAG", column.getName().hashCode() + "；" + row.getCellValue("测试").hashCode());
  }
}
