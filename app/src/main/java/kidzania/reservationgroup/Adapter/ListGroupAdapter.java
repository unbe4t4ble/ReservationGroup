package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import kidzania.reservationgroup.Model.GroupModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;

import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;

/**
 * Created by mubarik on 06/11/2017.
 */

public class ListGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static boolean loading;
    public static boolean try_again;
    public static final int VIEW_TYPE_ITEM = 0;
    public static int VIEW_TYPE_LOADING = 1;

    public List<GroupModel> data = Collections.emptyList();

    public ListGroupAdapter(List<GroupModel> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder xView = null;
        if (viewType == VIEW_TYPE_ITEM) {
            switch (SENDER_CLASS) {
                case "GroupData":
                case "GroupAvailGrp": {
                    ListGroupAdapter parentView = new ListGroupDataAdapter(data);
                    xView = parentView.onCreateViewHolder(parent, viewType);
                    break;
                }
                case "GroupDraft": {
                    ListGroupAdapter parentView = new ListGroupDraftAdapter(data);
                    xView = parentView.onCreateViewHolder(parent, viewType);
                    break;
                }
                case "GroupOwner": {
                    ListGroupAdapter parentView = new ListGroupOwnerAdapter(data);
                    xView = parentView.onCreateViewHolder(parent, viewType);
                    break;
                }
            }
        }else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            xView =  new LoadingViewHolder(view);
        }
        return xView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (SENDER_CLASS) {
            case "GroupData":
            case "GroupAvailGrp": {
                ListGroupAdapter parent = new ListGroupDataAdapter(data);
                parent.onBindViewHolder(holder, position);
                break;
            }
            case "GroupDraft": {
                ListGroupAdapter parent = new ListGroupDraftAdapter(data);
                parent.onBindViewHolder(holder, position);
                break;
            }
            case "GroupOwner": {
                ListGroupAdapter parent = new ListGroupOwnerAdapter(data);
                parent.onBindViewHolder(holder, position);
                break;
            }
        }
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

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() + 1;
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
