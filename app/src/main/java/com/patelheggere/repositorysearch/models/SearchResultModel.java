package com.patelheggere.repositorysearch.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Talkative Parents on 1/17/2018.
 */

public class SearchResultModel {
    private Long total_count;
    private boolean incomplete_results;
    private ArrayList<ItemsModel> items;

    public Long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Long total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<ItemsModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsModel> items) {
        this.items = items;
    }
}
