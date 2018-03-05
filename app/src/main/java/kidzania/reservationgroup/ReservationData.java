package kidzania.reservationgroup;

import android.view.View;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Adapter.ListReservationAdapter;
import kidzania.reservationgroup.ViewHolder.WrapContentLinearLayoutManager;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_RESERVATION;

/**
 * Created by mubarik on 20/12/2017.
 */

public class ReservationData extends ParentBookAndResvData {

    @Override
    public void initialization(){
        SENDER_CLASS = "ReservationData";
        super.initialization();
        setVisibleAddBooking(View.GONE);
    }

    @Override
    public void setAdapter(){
        listReservationAdapter = new ListReservationAdapter(dataReservation);
        myRecyclerviewBooking.setAdapter(listReservationAdapter);
        WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(this);
        myRecyclerviewBooking.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshData();
    }

    @Override
    public void refreshData(){
        SENDER_CLASS = "ReservationData";
        super.refreshData();
    }

    @Override
    public void getData(){
        super.getData();
        HEADER = HEAD_GET_DATA_RESERVATION;
        if(hasConnection(ReservationData.this)) {
            MultiParamGetDataJSON getDataReservation = new MultiParamGetDataJSON();
            getDataReservation.init(APIValueParams, APIParameters, URL_GET_DATA_RESERVATION, ReservationData.this, json_Reservation, false);
        }else{
            OpenLostConnection(this);
        }
    }

    @Override
    public void HideLoading(){
        listReservationAdapter.notifyDataSetChanged();
        listReservationAdapter.setFalseLoading();
    }

    @Override
    public void clear(){
        dataReservation.clear();
        listReservationAdapter.notifyItemRangeRemoved(0, dataReservation.size());
        listReservationAdapter.notifyDataSetChanged();
        listReservationAdapter.setLoading();
    }

}
