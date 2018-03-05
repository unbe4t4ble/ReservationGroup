package kidzania.reservationgroup.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kidzania.reservationgroup.R;
import kidzania.reservationgroup.ViewHolder.LoadingViewHolder;

import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.ParentBookAndResvData.dataBooking;
import static kidzania.reservationgroup.ParentBookAndResvData.dataReservation;

/**
 * Created by mubarik on 06/11/2017.
 */

public class ListBookAndResvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static boolean loading;
    public static final int VIEW_TYPE_ITEM = 0;
    public static int VIEW_TYPE_LOADING = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder xView = null;
        if (viewType == VIEW_TYPE_ITEM) {
            if (SENDER_CLASS.equals("BookingData")) {
                ListBookAndResvAdapter parentView = new ListBookingAdapter(dataBooking);
                xView = parentView.onCreateViewHolder(parent, viewType);
            } else if (SENDER_CLASS.equals("ReservationData")) {
                ListBookAndResvAdapter parentView = new ListReservationAdapter(dataReservation);
                xView = parentView.onCreateViewHolder(parent, viewType);
            }
        }else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            xView =  new LoadingViewHolder(view);
        }
        return xView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (SENDER_CLASS.equals("BookingData")) {
            ListBookAndResvAdapter parent = new ListBookingAdapter(dataBooking);
            parent.onBindViewHolder(holder, position);
        }else
        if (SENDER_CLASS.equals("ReservationData")){
            ListBookAndResvAdapter parent = new ListReservationAdapter(dataReservation);
            parent.onBindViewHolder(holder, position);
        }
    }

    public void setLoading(){
        loading = true;
    }

    public void setFalseLoading(){
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (SENDER_CLASS.equals("BookingData")) {
            return dataBooking == null ? 0 : dataBooking.size();
        }else {
            return dataReservation == null ? 0 : dataReservation.size();
        }
    }

    private boolean isPositionFooter (int position) {
        if (SENDER_CLASS.equals("BookingData")) {
            return position == dataBooking.size();
        }else {
            return position == dataReservation.size();
        }
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
