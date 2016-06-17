package com.jomaca.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jomaca.tipcalc.R;
import com.jomaca.tipcalc.models.TipRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jorge-calle on 15/06/16.
 */

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TipRecord> dataset;
    private OnItemClickListener onItemClickListener;

    public TipAdapter(Context context, ArrayList<TipRecord> dataset, OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public TipAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataset = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    // crea un objeto del tipo viewHolder que recibe un view, el cual es el item_row.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    //colocar el contenido en el viewholder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecord element = dataset.get(position);
        String strTip = String.format(context.getString(R.string.global_message_tip),element.getTip());
        holder.txtContent.setText(strTip);
        holder.count.setText(String.valueOf(dataset.indexOf(element)+1));
        holder.txtDate.setText(element.getDateFormatted());
        holder.setOnItemClickListener(element, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(TipRecord record) {
        dataset.add(0,record);
        notifyDataSetChanged();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtContent)
        TextView txtContent;
        @Bind(R.id.count)
        TextView count;
        @Bind(R.id.txtDate)
        TextView txtDate;

        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final TipRecord element, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(element);
                }
            });
        }
    }
}
