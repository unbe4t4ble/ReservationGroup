package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import kidzania.reservationgroup.Model.HistoryModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.HistoryViewHolder;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;

public class ListHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static boolean loading;
    private static final int VIEW_TYPE_ITEM = 0;
    private static int VIEW_TYPE_LOADING = 1;

    public List<HistoryModel> data = Collections.emptyList();

    public ListHistoryAdapter(List<HistoryModel> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder xView = null;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
            xView = new HistoryViewHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            xView = new HistoryViewHolder(view);
        }
        return xView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HistoryViewHolder) {
            final HistoryViewHolder myHolder = (HistoryViewHolder) holder;
            HistoryModel current = data.get(position);
            myHolder.textviewField1.setText(current.getDataHist());
            myHolder.textviewField2.setText(current.getUserHist());
            myHolder.textviewField3.setText(current.getNoteHist());
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setLoading(){
        loading = true;
    }

    public void setFalseLoading(){
        loading = false;
    }


    private boolean isPositionFooter (int position) {
        return position == data.size();
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
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }
}
