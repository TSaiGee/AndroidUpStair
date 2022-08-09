package com.example.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final List list = new ArrayList();
    private final ItemClickListener mItemClickListener = (position) -> Toast.makeText(this, list.get(position).toString(), Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
        for (int i = 0; i < 100; i++) {
            list.add((int) (Math.random() * 500) + 100);
        }
    }

    private void initRecyclerView() {
        Adapter adapter = new Adapter(list, this, mItemClickListener);

        //拖动排序监听
        ItemTouchListener itemTouchListener = new ItemTouchListener();
        itemTouchListener.setOnItemTouchListener(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchListener);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);

        //设置布局
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}