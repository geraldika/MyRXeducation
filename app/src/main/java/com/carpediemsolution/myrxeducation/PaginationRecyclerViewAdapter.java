package com.carpediemsolution.myrxeducation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Юлия on 19.06.2017.
 */

public class PaginationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MAIN_VIEW = 0;
    private Context context;

    public PaginationRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    private List<Card> cardList;

    private boolean allItemsLoaded;

    private class MainViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        private MainViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public void addNewItems(List<Card> items) {
        if (items.size() == 0) {
            allItemsLoaded = true;
            return;
        }
        cardList.addAll(items);
    }

    public boolean isAllItemsLoaded() {
        return allItemsLoaded;
    }

    private Card getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MAIN_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new MainViewHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return MAIN_VIEW;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case MAIN_VIEW:
                onBindTextHolder(holder, position);
                break;
        }
    }

    private void onBindTextHolder(RecyclerView.ViewHolder holder, int position) {
        MainViewHolder mainHolder = (MainViewHolder) holder;
        mainHolder.textView.setText(getItem(position).getWord());

        mainHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //   final int position = getAdapterPosition();
                showCardDescription(position, context);
                Log.d("LOG_TAG", "long click position " +
                        " " + cardList.get(position).getWord());
                return false;
            }
        });

        mainHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainHolder.textView.setText(getItem(position).getTranslate());
            }
        });

        Log.d("LOG_TAG", "holder position " + position);
    }

    private void showCardDescription(int position, Context context) {
        Card card = cardList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(card.getWord() + " ~ " + card.getTheme())
                .setMessage(card.getTranslate() + "\n\n" + card.getDescription())
                .setPositiveButton("no operation", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}