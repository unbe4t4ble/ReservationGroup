package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Adapter.ListBookingAdapter;
import kidzania.reservationgroup.Adapter.ListReservationAdapter;
import kidzania.reservationgroup.Model.BookingModel;
import kidzania.reservationgroup.Model.ReservationModel;
import kidzania.reservationgroup.ViewHolder.WrapContentLinearLayoutManager;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.VarDate.DateReservDD;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.RESV_EMPTY_DATA;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;

public class ParentBookAndResvData extends AppCompatActivity {

    public static ListBookingAdapter listBookingAdapter;
    public static ListReservationAdapter listReservationAdapter;
    WrapContentLinearLayoutManager linearLayoutManager;
    RecyclerView myRecyclerviewBooking;
    FloatingActionButton add_booking;

    public static List<BookingModel> dataBooking = new ArrayList<>();
    public static List<ReservationModel> dataReservation = new ArrayList<>();

    final String PARAM_TANGGAL="Tanggal";
    final String PARAM_SHIFT="Shift";
    final String PARAM_ID_USER="ID_USER";
    final String PARAM_PROMOTOR_CODE="PROMOTOR_CODE";

    public static String HEADER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_list_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CheckConnection(this);
        initialization();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageNoData, new IntentFilter(RESV_EMPTY_DATA));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageNoData);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageNoData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            HideLoading();
        }
    };

    public void HideLoading(){
        listBookingAdapter.notifyDataSetChanged();
        listBookingAdapter.setFalseLoading();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_refresh:
                refreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initialization() {
        myRecyclerviewBooking = (RecyclerView) findViewById(R.id.myRecyclerviewBooking);
        listReservationAdapter = new ListReservationAdapter(dataReservation);
        add_booking = (FloatingActionButton) findViewById(R.id.add_booking);
        setAdapter();
    }

    public void setAdapter(){
        listBookingAdapter = new ListBookingAdapter(dataBooking);
        myRecyclerviewBooking.setAdapter(listBookingAdapter);
        linearLayoutManager = new WrapContentLinearLayoutManager(ParentBookAndResvData.this);
        myRecyclerviewBooking.setLayoutManager(linearLayoutManager);
    }

    public void setVisibleAddBooking(int Visible){
        add_booking.setVisibility(Visible);
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add(PARAM_TANGGAL);
        APIParameters.add(PARAM_SHIFT);
        APIParameters.add(PARAM_ID_USER);
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(DateReservDD);
        APIValueParams.add(String.valueOf(TURNO));
        APIValueParams.add(ID_USER);
    }

    public void getData(){
        AssignParam();
        AssignValueParam();
    }

    public MultiParamGetDataJSON.JSONObjectResult json_Booking = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEADER);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    BookingModel model = new BookingModel();
                    model.setid_num_reser(objData.getString("ID_NUM_RESER"));
                    model.setid_num_esc(objData.getString("ID_NUM_ESC"));
                    model.setgroup_name(objData.getString("GROUP_NAME"));
                    model.setid_promotor(objData.getString("ID_PROMOTOR"));
                    model.setpromotor(objData.getString("PROMOTOR"));
                    model.setid_supporter(objData.getString("ID_SUPPORTER"));
                    model.setsupporter(objData.getString("SUPPORTER"));
                    model.setrsv_status(objData.getString("RSV_STATUS"));
                    model.settotal(objData.getString("TOTAL"));
                    model.setbebe(objData.getString("BEBE"));
                    model.setnino(objData.getString("NINO"));
                    model.setadulto(objData.getString("ADULTO"));
                    model.setinfante(objData.getString("INFANTE"));
                    model.setndisc(objData.getString("NDISC"));
                    model.setadisc(objData.getString("ADISC"));
                    model.setinsen(objData.getString("INSEN"));
                    model.setsenior(objData.getString("SENIOR"));
                    model.sethandicap(objData.getString("HANDICAP"));
                    model.setfecha_creacion(objData.getString("FECHA_CREACION"));
                    model.setid_resp_esc(objData.getString("ID_RESP_ESC"));

                    dataBooking.add(model);
                }
                listBookingAdapter.notifyItemInserted(dataBooking.size());
                listBookingAdapter.setFalseLoading();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public MultiParamGetDataJSON.JSONObjectResult json_Reservation = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEADER);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    ReservationModel model = new ReservationModel();
                    model.setid_num_reser(objData.getString("ID_NUM_RESER"));
                    model.setid_num_esc(objData.getString("ID_NUM_ESC"));
                    model.setgroup_name(objData.getString("GROUP_NAME"));
                    model.setid_promotor(objData.getString("ID_PROMOTOR"));
                    model.setpromotor(objData.getString("PROMOTOR"));
                    model.setid_supporter(objData.getString("ID_SUPPORTER"));
                    model.setsupporter(objData.getString("SUPPORTER"));
                    model.settotal(objData.getString("TOTAL"));
                    model.setbebe(objData.getString("BEBE"));
                    model.setsenior(objData.getString("SENIOR"));
                    model.sethandicap(objData.getString("HANDICAP"));
                    model.setfecha_creacion(objData.getString("FECHA_CREACION"));
                    model.setent_status(objData.getString("ENT_STATUS"));
                    model.setgrade(objData.getString("GRADE"));
                    model.setalias(objData.getString("ALIAS"));
                    model.setprom_booking(objData.getString("PROM_BOOKING"));
                    model.setsch_resp(objData.getString("SCH_RESP"));
                    model.settotal_due("Rp "+getFormatedCurrency(ParentBookAndResvData.this,objData.getString("TOTAL_DUE")));
                    model.setpaid("Rp "+getFormatedCurrency(ParentBookAndResvData.this,objData.getString("PAID")));
                    model.setrsv_no(objData.getString("RSV_NO"));
                    model.setnote(objData.getString("NOTE"));

                    dataReservation.add(model);
                }
                listReservationAdapter.notifyItemInserted(dataReservation.size());
                if (jsonArray.length() < (PAGE_SIZE - 1)){
                    listReservationAdapter.setFalseLoading();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void clear() {
        dataBooking.clear();
        listBookingAdapter.notifyItemRangeRemoved(0, dataBooking.size());
        listBookingAdapter.notifyDataSetChanged();
        listBookingAdapter.setLoading();
    }

    public void refreshData(){
        clear();
        getData();
    }

}
