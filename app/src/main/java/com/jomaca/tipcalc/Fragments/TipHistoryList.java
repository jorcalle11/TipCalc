package com.jomaca.tipcalc.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jomaca.tipcalc.R;
import com.jomaca.tipcalc.TipDetailActivity;
import com.jomaca.tipcalc.adapters.OnItemClickListener;
import com.jomaca.tipcalc.adapters.TipAdapter;
import com.jomaca.tipcalc.models.TipRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipHistoryList extends Fragment implements TipHistoryListFragmentListener, OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private TipAdapter adapter;

    public TipHistoryList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tip_history_list, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        initRecyclerView();
        return view;
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new TipAdapter(getActivity().getApplicationContext(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addToList(TipRecord record) {
        adapter.add(record);
    }

    @Override
    public void clearList() {
        adapter.clear();
    }

    @Override
    public void OnItemClick(TipRecord record) {
        Toast.makeText(getActivity(), record.getDateFormatted(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), TipDetailActivity.class);
        intent.putExtra(TipDetailActivity.BILL_TOTAL_KEY, record.getBill());
        intent.putExtra(TipDetailActivity.TIP_KEY, record.getTip());
        intent.putExtra(TipDetailActivity.DATE_KEY, record.getDateFormatted());

        startActivity(intent);
    }
}
