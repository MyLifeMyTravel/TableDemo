package com.example.littlejie.tabledemo;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.example.littlejie.tabledemo.table.Table;

public class MainActivity extends AppCompatActivity {

  private Table table;
  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    table = (Table) findViewById(R.id.table);
    imageView = (ImageView) findViewById(R.id.iv);
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -10);
    long startTimeInMills = calendar.getTimeInMillis();
    long endTimeInMills = System.currentTimeMillis();
    MenstruationTableRender render = new MenstruationTableRender(this, startTimeInMills, endTimeInMills);
    table.setRender(render);

    table.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        imageView.setImageBitmap(Util.convertViewToBitmap(table));
      }
    });
  }
}
