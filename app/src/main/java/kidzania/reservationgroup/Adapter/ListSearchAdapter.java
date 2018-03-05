package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import kidzania.reservationgroup.Model.SearchModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;
import kidzania.reservationgroup.ViewHolder.SearchViewHolder;

/**
 * Created by mubarik on 13/11/2017.
 */

public class ListSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static boolean loadingSearch;
    private static boolean try_againSearch;
    private static final int VIEW_TYPE_ITEM_SEARCH = 0;
    private static int VIEW_TYPE_LOADING_SEARCH = 1;

    public List<SearchModel> dataSearch = Collections.emptyList();

    public ListSearchAdapter(List<SearchModel> data) {
        this.dataSearch = data;
    }

    private int[] colors = new int[] { 0x30a2def7,0x30ffffff  };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder xView = null;
        if (viewType == VIEW_TYPE_ITEM_SEARCH) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search, parent, false);
            xView = new SearchViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING_SEARCH) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            xView =  new LoadingViewHolder(view);
        }

        return xView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchViewHolder) {
            final SearchViewHolder myHolder = (SearchViewHolder) holder;
            SearchModel current = dataSearch.get(position);
            myHolder.txtSearchID.setText(current.get_id_search());
            myHolder.txtSeacrhText.setText(current.get_text_search());
            int ColorPosition = position % colors.length;
            myHolder.myCardviewSearch.setBackgroundColor(colors[ColorPosition]);
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loadingSearch ? View.VISIBLE : View.GONE);
            loadingViewHolder.btnTryAgain.setVisibility(try_againSearch ? View.VISIBLE : View.GONE);
        }
    }

    public void setLoading(){
        loadingSearch = true;
        try_againSearch = false;
    }

    public void setHideLoadingAndTryAgain(){
        loadingSearch = false;
        try_againSearch = false;
    }

    public void setTryAgain(){
        loadingSearch = false;
        try_againSearch = true;
    }

    @Override
    public int getItemCount() {
        return dataSearch == null ? 0 : dataSearch.size() + 1;
    }

    private boolean isPositionFooter (int position) {
        return position == dataSearch.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return VIEW_TYPE_LOADING_SEARCH;
        }
        return VIEW_TYPE_ITEM_SEARCH;
    }
}
