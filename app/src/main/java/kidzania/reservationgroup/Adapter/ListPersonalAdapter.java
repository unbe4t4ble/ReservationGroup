package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import kidzania.reservationgroup.Model.PersonalModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;
import kidzania.reservationgroup.ViewHolder.PersonalViewHolder;

/**
 * Created by mubarik on 20/02/2018.
 */

public class ListPersonalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static boolean loading;
    public static boolean try_again;
    public static final int VIEW_TYPE_ITEM = 0;
    public static int VIEW_TYPE_LOADING = 1;

    public List<PersonalModel> data = Collections.emptyList();

    public ListPersonalAdapter(List<PersonalModel> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder xView = null;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_personal, parent, false);
            xView = new PersonalViewHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            xView =  new LoadingViewHolder(view);
        }
        return xView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PersonalViewHolder) {
            final PersonalViewHolder myHolder = (PersonalViewHolder) holder;
            PersonalModel current = data.get(position);
            myHolder.textviewField1.setText(current.getname());
            myHolder.textviewField2.setText(current.getid_card());
            myHolder.textviewField3.setText(current.getaddress());
            myHolder.textviewField4.setText(current.getarea());
            myHolder.textviewField5.setText(current.getdistrict());
            myHolder.textviewField6.setText(current.getcity());
            myHolder.textviewField7.setText(current.getprovince());
            myHolder.textviewField8.setText(current.getzipcode());
            myHolder.textviewField9.setText(current.getemail());
            myHolder.textviewField10.setText(current.getfax());
            myHolder.textviewField11.setText(current.getphone());
            myHolder.textViewID.setText(current.getid());
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() + 1;
    }

    public void setLoading(){
        loading = true;
        try_again = false;
    }

    public void setHideLoadingAndTryAgain(){
        loading = false;
        try_again = false;
    }

    public void setTryAgain(){
        loading = false;
        try_again = true;
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
