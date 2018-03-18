package kidzania.reservationgroup;

import android.content.Intent;
import android.view.View;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.setDefaultVar;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_BOOKING;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.isAdmin;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_BOOKING;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_BOOKING_ADMIN;

public class BookingData extends ParentBookAndResvData {

    @Override
    public void initialization(){
        SENDER_CLASS = "BookingData";
        super.initialization();
        setVisibleAddBooking(View.VISIBLE);
        add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultVar();
                startActivity(new Intent(BookingData.this, AddBooking.class));
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshData();
    }

    @Override
    public void refreshData(){
        SENDER_CLASS = "BookingData";
        super.refreshData();
    }

    @Override
    public void getData(){
        super.getData();
        HEADER = HEAD_GET_DATA_BOOKING;
        if(hasConnection(BookingData.this)) {
            MultiParamGetDataJSON getDataBooking = new MultiParamGetDataJSON();
            if(isAdmin){
                getDataBooking.init(APIValueParams, APIParameters, URL_GET_DATA_BOOKING_ADMIN, BookingData.this, json_Booking, false);
            }else {
                getDataBooking.init(APIValueParams, APIParameters, URL_GET_DATA_BOOKING, BookingData.this, json_Booking, false);
            }
        }else{
            OpenLostConnection(this);
        }
    }


}
