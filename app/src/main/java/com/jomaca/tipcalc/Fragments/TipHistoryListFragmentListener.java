package com.jomaca.tipcalc.Fragments;

import com.jomaca.tipcalc.models.TipRecord;

/**
 * Created by jorge-calle on 15/06/16.
 */

public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
