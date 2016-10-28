package com.example.li.demo.RecyclerViewDemo.CardLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.li.demo.R;

import java.util.Random;

public class CardLayoutActivity extends AppCompatActivity {

    private static final int[] COLORS = {Color.BLACK, Color.RED, Color.BLUE,
            Color.GRAY, Color.DKGRAY, Color.GREEN,
            Color.CYAN, Color.LTGRAY, Color.MAGENTA,
            Color.WHITE, Color.YELLOW, };

    private RecyclerView mRecyclerView;
    private Adapter mAdapter = new Adapter();

    private int mCount = 10;
    private int mGroupSize = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        init();
    }

    private void init() {
        mRecyclerView.setLayoutManager(new CardLayoutManager(mGroupSize, true));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void add(View view) {
        mCount += 10;
        mAdapter.notifyDataSetChanged();
    }

    public void change(View view) {
        if (mGroupSize == 3) { mGroupSize = 9;}
        else { mGroupSize = 3;}
        init();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item, parent, false);
            return new Holder(item);
        }

        @Override
        public void onBindViewHolder(final Holder holder, final int position) {
            holder.item.setCardColor(randomColor());
            holder.text.setText("菜单" + position);
            holder.item.setOnClickListener(view ->
                    Toast.makeText(CardLayoutActivity.this, holder.text.getText(), Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return mCount;
        }

        private int randomColor() {
            return COLORS[new Random().nextInt(COLORS.length)];
        }

        class Holder extends RecyclerView.ViewHolder {
            CardItemView item;
            TextView text;
            public Holder(View itemView) {
                super(itemView);
                item = (CardItemView) itemView.findViewById(R.id.item);
                text = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
