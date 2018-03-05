package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kidzania.reservationgroup.Model.ReservationModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;
import kidzania.reservationgroup.ViewHolder.ReservationViewHolder;

/**
 * Created by mubarik on 06/11/2017.
 */

public class ListReservationAdapter extends ListBookAndResvAdapter {

    private ArrayList<String> idBooking = new ArrayList<>();

    public List<ReservationModel> data = Collections.emptyList();

    public ListReservationAdapter(List<ReservationModel> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation_list, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReservationViewHolder) {
            final ReservationViewHolder myHolder = (ReservationViewHolder) holder;
            ReservationModel current = data.get(position);
            myHolder.textID_BOOKING.setText(current.getid_num_reser());
            myHolder.textID_Group.setText(current.getid_num_esc());
            myHolder.textID_PROMOTOR.setText(current.getid_promotor());
            myHolder.textID_SUPPORTER.setText(current.getid_supporter());
            myHolder.textTanggalBuat.setText(current.getfecha_creacion());
            myHolder.textviewField1.setText(current.getgroup_name());
            myHolder.textviewField2.setText(current.getent_status());
            myHolder.textviewField3.setText(current.getgrade());
            myHolder.textviewField4.setText(current.gettotal());
            myHolder.textviewField5.setText(current.getsupporter());
            myHolder.textviewField6.setText(current.getpromotor());
            myHolder.textviewField7.setText(current.getalias());
            myHolder.textviewField8.setText(current.getprom_booking());
            myHolder.textviewField9.setText(current.getsch_resp());
            myHolder.textviewField10.setText(current.gettotal_due());
            myHolder.textviewField11.setText(current.getpaid());
            myHolder.textviewField12.setText("NO");
            myHolder.textviewField13.setText(current.getrsv_no());
            myHolder.textviewField14.setText(current.getbebe());
            myHolder.textviewField15.setText(current.getsenior());
            myHolder.textviewField16.setText(current.gethandicap());
            myHolder.textviewField17.setText(current.getnote());
            idBooking.add(current.getid_num_reser());
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

}
