package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kidzania.reservationgroup.Model.BookingModel;
import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.BookingViewHolder;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;

/**
 * Created by mubarik on 06/11/2017.
 */

public class ListBookingAdapter extends ListBookAndResvAdapter {

    private ArrayList<String> idBooking = new ArrayList<>();
    public List<BookingModel> data = Collections.emptyList();

    public ListBookingAdapter(List<BookingModel> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_list, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookingViewHolder) {
            final BookingViewHolder myHolder = (BookingViewHolder) holder;
            BookingModel current = data.get(position);
            myHolder.textID_BOOKING.setText(current.getid_num_reser());
            myHolder.textID_Group.setText(current.getid_num_esc());
            myHolder.textID_PROMOTOR.setText(current.getid_promotor());
            myHolder.textID_SUPPORTER.setText(current.getid_supporter());
            myHolder.textID_RESP_ESC.setText(current.getid_resp_esc());
            myHolder.textTanggalBuat.setText(current.getfecha_creacion());
            myHolder.textviewField1.setText(current.getgroup_name());
            myHolder.textviewField2.setText(current.getsupporter());
            myHolder.textviewField3.setText(current.getpromotor());
            myHolder.textviewField4.setText(current.getrsv_status());
            myHolder.textviewField5.setText(current.gettotal());
            myHolder.textviewField6.setText(current.getbebe());
            myHolder.textviewField7.setText(current.getnino());
            myHolder.textviewField8.setText(current.getadulto());
            myHolder.textviewField9.setText(current.getinfante());
            myHolder.textviewField10.setText(current.getndisc());
            myHolder.textviewField11.setText(current.getadisc());
            myHolder.textviewField12.setText(current.getinsen());
            myHolder.textviewField13.setText(current.getsenior());
            myHolder.textviewField14.setText(current.gethandicap());
            idBooking.add(current.getid_num_reser());
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

}
