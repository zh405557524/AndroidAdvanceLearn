package com.soul.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soul.customview.view.recycler.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
                convertView = RecyclerViewActivity.this.getLayoutInflater().inflate(R.layout.item_table, parent);
                final TextView textView = convertView.findViewById(R.id.text1);
                textView.setText("第" + position + "行");
                return convertView;
            }

            @Override
            public View onBinderViewHolder(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int row) {
                return 0;
            }

            @Override
            public int getVIewTypeCount() {
                return 0;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });
    }


}
