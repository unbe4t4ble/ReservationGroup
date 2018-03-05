package kidzania.reservationgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.FuncGlobal.getNormalNumber;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_QUOTA_REGISTER;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_QUOTA_RESERVATION;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_QUOTA_REGISTER;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_QUOTA_RESERVATION;

public class QuotaRegister extends AppCompatActivity {

    TextView NamaBulan, tahun,
            ToddAccess, ToddResc, ToddTotal,
            ChildAccess, ChildResc, ChildTotal,
            AdultAccess, AdultResc, AdultTotal,
            BabyAccess, BabyResc, BabyTotal,
            SeniorAccess, SeniorResc, SeniorTotal,
            HandyCapAccess, HandyCapResc, HandyCapTotal,
            TotalAccess, TotalResc, AllTotal;

    String TanggalAwal,TanggalAkhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quota_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
        CheckConnection(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_refresh, menu);
        inflater.inflate(R.menu.button_foward, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        SelectGroupRegistered();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_refresh:
                SelectGroupRegistered();
                return true;
            case R.id.btn_foward:
                openBookingMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization(){
        NamaBulan = (TextView) findViewById(R.id.NamaBulan);
        tahun = (TextView) findViewById(R.id.tahun);
        ToddAccess = (TextView) findViewById(R.id.ToddAccess);
        ToddResc = (TextView) findViewById(R.id.ToddResc);
        ToddTotal = (TextView) findViewById(R.id.ToddTotal);
        ChildAccess = (TextView) findViewById(R.id.ChildAccess);
        ChildResc = (TextView) findViewById(R.id.ChildResc);
        ChildTotal = (TextView) findViewById(R.id.ChildTotal);
        AdultAccess = (TextView) findViewById(R.id.AdultAccess);
        AdultResc = (TextView) findViewById(R.id.AdultResc);
        AdultTotal = (TextView) findViewById(R.id.AdultTotal);
        BabyAccess = (TextView) findViewById(R.id.BabyAccess);
        BabyResc = (TextView) findViewById(R.id.BabyResc);
        BabyTotal = (TextView) findViewById(R.id.BabyTotal);
        SeniorAccess = (TextView) findViewById(R.id.SeniorAccess);
        SeniorResc = (TextView) findViewById(R.id.SeniorResc);
        SeniorTotal = (TextView) findViewById(R.id.SeniorTotal);
        HandyCapAccess = (TextView) findViewById(R.id.HandyCapAccess);
        HandyCapResc = (TextView) findViewById(R.id.HandyCapResc);
        HandyCapTotal = (TextView) findViewById(R.id.HandyCapTotal);
        TotalAccess = (TextView) findViewById(R.id.TotalAccess);
        TotalResc = (TextView) findViewById(R.id.TotalResc);
        AllTotal = (TextView) findViewById(R.id.AllTotal);
    }

    private void getTanggal() throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatMM = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat formatYY = new SimpleDateFormat("yyyy", Locale.US);

        String bulan = formatMM.format(date);
        String xTahun = formatYY.format(date);
        TanggalAwal = bulan+"/01/"+xTahun;

        Calendar cal = Calendar.getInstance();
        Date convertedDate = dateFormat.parse(TanggalAwal);
        cal.setTime(convertedDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        TanggalAkhir = String.valueOf(android.text.format.DateFormat.format("MM/dd/yyyy", cal));

        String[] ArrMonth = {" ",
                getString(R.string.str_month_january),
                getString(R.string.str_month_february),
                getString(R.string.str_month_march),
                getString(R.string.str_month_april),
                getString(R.string.str_month_may),
                getString(R.string.str_month_june),
                getString(R.string.str_month_july),
                getString(R.string.str_month_august),
                getString(R.string.str_month_september),
                getString(R.string.str_month_october),
                getString(R.string.str_month_november),
                getString(R.string.str_month_december)};

        NamaBulan.setText(ArrMonth[Integer.valueOf(bulan)]);
        tahun.setText(xTahun);
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add("TanggalAwal");
        APIParameters.add("TanggalAkhir");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(TanggalAwal);
        APIValueParams.add(TanggalAkhir);
    }

    private void ParamAndValue(){
        try {
            getTanggal();
            AssignParam();
            AssignValueParam();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void SelectGroupRegistered(){
        if(hasConnection(QuotaRegister.this)){
            ParamAndValue();
            MultiParamGetDataJSON GroupRegistered = new MultiParamGetDataJSON();
            GroupRegistered.init(APIValueParams, APIParameters, URL_GET_QUOTA_REGISTER, QuotaRegister.this, json_GroupRegistered, true);
        }else{
            OpenLostConnection(this);
        }
    }

    MultiParamGetDataJSON.JSONObjectResult json_GroupRegistered = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_QUOTA_REGISTER);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    ToddAccess.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("T5")));
                    ChildAccess.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("C5")));
                    AdultAccess.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("A5")));
                    BabyAccess.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("OTH")));
                    SeniorAccess.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("SEN")));
                    HandyCapAccess.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("HAN")));
                    int TotalAll = Gr.getInt("T5") + Gr.getInt("C5") + Gr.getInt("A5") + Gr.getInt("OTH") + Gr.getInt("SEN") + Gr.getInt("HAN");
                    TotalAccess.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(TotalAll)));
                }
                SelectGroupReservation();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void SelectGroupReservation(){
        if(hasConnection(QuotaRegister.this)) {
            MultiParamGetDataJSON GroupReservation = new MultiParamGetDataJSON();
            GroupReservation.init(APIValueParams, APIParameters, URL_GET_QUOTA_RESERVATION, QuotaRegister.this, json_GroupReservation, true);
        }else{
            OpenLostConnection(this);
        }
    }

    MultiParamGetDataJSON.JSONObjectResult json_GroupReservation = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_QUOTA_RESERVATION);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    ToddTotal.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("T5")));
                    ChildTotal.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("C5")));
                    AdultTotal.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("A5")));
                    BabyTotal.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("OTH")));
                    SeniorTotal.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("SEN")));
                    HandyCapTotal.setText(getFormatedCurrency(QuotaRegister.this,Gr.getString("HAN")));
                    int TotalAll = Gr.getInt("T5") + Gr.getInt("C5") + Gr.getInt("A5") + Gr.getInt("OTH") + Gr.getInt("SEN") + Gr.getInt("HAN");
                    AllTotal.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(TotalAll)));
                    setValueGroupReservation();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void setValueGroupReservation(){
        int toddResc = Integer.valueOf(getNormalNumber(ToddTotal.getText().toString())) - Integer.valueOf(getNormalNumber(ToddAccess.getText().toString()));
        int childResc = Integer.valueOf(getNormalNumber(ChildTotal.getText().toString())) - Integer.valueOf(getNormalNumber(ChildAccess.getText().toString()));
        int adultResc = Integer.valueOf(getNormalNumber(AdultTotal.getText().toString())) - Integer.valueOf(getNormalNumber(AdultAccess.getText().toString()));
        int babyResc = Integer.valueOf(getNormalNumber(BabyTotal.getText().toString())) - Integer.valueOf(getNormalNumber(BabyAccess.getText().toString()));
        int seniorResc = Integer.valueOf(getNormalNumber(SeniorTotal.getText().toString())) - Integer.valueOf(getNormalNumber(SeniorAccess.getText().toString()));
        int handycapResc = Integer.valueOf(getNormalNumber(HandyCapTotal.getText().toString())) - Integer.valueOf(getNormalNumber(HandyCapAccess.getText().toString()));

        ToddResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(toddResc)));
        ChildResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(childResc)));
        AdultResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(adultResc)));
        BabyResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(babyResc)));
        SeniorResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(seniorResc)));
        HandyCapResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(handycapResc)));
        TotalResc.setText(getFormatedCurrency(QuotaRegister.this,String.valueOf(toddResc + childResc + adultResc + babyResc + seniorResc + handycapResc)));
    }

    private void openBookingMenu(){
        startActivity(new Intent(QuotaRegister.this, BookingMenu.class));
    }
}
