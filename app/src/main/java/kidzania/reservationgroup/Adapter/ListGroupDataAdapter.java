package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kidzania.reservationgroup.Model.GroupModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.GroupViewDataHolder;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;

/**
 * Created by mubarik on 08/11/2017.
 */

public class ListGroupDataAdapter extends ListGroupAdapter{

    private ArrayList<String> idNumEscAdapter = new ArrayList<>();

    ListGroupDataAdapter(List<GroupModel> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_list_data, parent, false);
        return new GroupViewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupViewDataHolder) {
            final GroupViewDataHolder myHolder = (GroupViewDataHolder) holder;
            GroupModel current = data.get(position);
            myHolder.txtID_NUM_ESC.setText(current.getid_num_esc());
            myHolder.txtIDUSR_OWN.setText(current.getidusr_own());
            myHolder.textviewField1.setText(current.getgrpname());
            myHolder.textviewField2.setText(current.getgrade());
            myHolder.textviewField3.setText(current.getgrade_typ());
            myHolder.textviewField4.setText(current.getaddr());
            myHolder.textviewField5.setText(current.getarea());
            myHolder.textviewField6.setText(current.getdistrict());
            myHolder.textviewField7.setText(current.getcity());
            myHolder.textviewField8.setText(current.getprovince());
            myHolder.textviewField9.setText(current.getphone());
            myHolder.textviewField10.setText(current.getemail());
            myHolder.textviewField11.setText(current.getprincipal());
            myHolder.textviewField12.setText(current.getprinc_hp());
            myHolder.textviewField13.setText(current.getpic());
            myHolder.textviewField14.setText(current.getno_hp());
            myHolder.textviewField15.setText(current.getulogin());
            myHolder.textviewField16.setText(current.getfecha_alta());
            idNumEscAdapter.add(current.getid_num_esc());
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            loadingViewHolder.btnTryAgain.setVisibility(try_again ? View.VISIBLE : View.GONE);
        }
    }
}
