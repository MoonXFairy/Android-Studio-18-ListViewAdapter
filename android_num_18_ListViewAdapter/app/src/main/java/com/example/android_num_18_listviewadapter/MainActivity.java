package com.example.android_num_18_listviewadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity
{
    String[] A2J = {"1", "2", "3", "4", "5", "6", "7", "8" ,"9" ,"10" ,"11" ,"12" ,"13" ,"14" ,"15" ,"16" ,"17",
                    "18", "19", "20", "21", "22", "23" ,"24", "25", "26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32" ,"33",
                    "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
    ArrayList<String> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Generate data
        for (int i = 0; i < A2J.length; i++)
        {
            arrayList.add(A2J[i]);
        }

        //Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        //Set RecyclerView sliding event
        recyclerViewAction(recyclerView, arrayList, myAdapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView_item);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            holder.textView.setText(arrayList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return arrayList.size();
        }
    }

    private void recyclerViewAction(RecyclerView recyclerView, final ArrayList<String> choose, final MyAdapter myAdapter)
    {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback()
        {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder)
            {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                int position_dragged = viewHolder.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(choose, position_dragged, position_target);
                myAdapter.notifyItemMoved(position_dragged, position_target);

                if (choose.contains("K") && choose.indexOf("K") != choose.size() - 1)
                {
                    Collections.swap(choose, position_target, position_dragged);
                    myAdapter.notifyItemMoved(position_target, position_dragged);
                }
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                int position = viewHolder.getAdapterPosition();
                switch (direction)
                {
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        choose.remove(position);
                        myAdapter.notifyItemRemoved(position);
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
            {
                new RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MainActivity.this,android.R.color.holo_green_dark))
                        .addActionIcon(R.drawable.ic_baseline_android_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}