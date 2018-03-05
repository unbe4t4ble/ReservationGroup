package kidzania.reservationgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarDate.DateReservDD;
import static kidzania.reservationgroup.Misc.VarDate.StripReservDD;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_POPULASI_FAMILY1;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_POPULASI_FAMILY2;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_POPULASI_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_POPULASI_PARTY;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_POPULASI_FAMILY1;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_POPULASI_FAMILY2;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_POPULASI_GROUP;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_POPULASI_PARTY;

public class ReservationMenu extends AppCompatActivity {

    TextView Pop_Child_Family, Pop_Other_Family;
    TextView Pop_Child_Group, Pop_Other_Group;
    TextView Pop_Child_Party, Pop_Other_Party, Total_Populasi;

    Button btnBookingMenu, btnReservationMenu;

    int Populasi_Child_Family, Populasi_Other_Family;
    int Populasi_Child_Group, Populasi_Other_Group;
    int Populasi_Child_Party, Populasi_Other_Party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
        CheckConnection(this);
        getPopulation();
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
                getPopulation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization(){
        Pop_Child_Family = (TextView) findViewById(R.id.Pop_Child_Family);
        Pop_Other_Family = (TextView) findViewById(R.id.Pop_Other_Family);
        Pop_Child_Group = (TextView) findViewById(R.id.Pop_Child_Group);
        Pop_Other_Group = (TextView) findViewById(R.id.Pop_Other_Group);
        Pop_Child_Party = (TextView) findViewById(R.id.Pop_Child_Party);
        Pop_Other_Party = (TextView) findViewById(R.id.Pop_Other_Party);
        //Total_Populasi = (TextView) findViewById(R.id.Total_Populasi);
        btnBookingMenu = (Button) findViewById(R.id.btnBookingMenu);
        btnBookingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBookingList();
            }
        });
        btnReservationMenu = (Button) findViewById(R.id.btnReservationMenu);
        btnReservationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenReservationList();
            }
        });
    }

    private void ParamStripDate(){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add("StripReservDD");
        APIValueParams.add(StripReservDD);
        APIParameters.add("SHIFT");
        APIValueParams.add(String.valueOf(TURNO));
    }

    private void ParamDate(){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add("DateReservDD");
        APIValueParams.add(DateReservDD);
        APIParameters.add("SHIFT");
        APIValueParams.add(String.valueOf(TURNO));
    }

    private void getPopulation(){
        if(hasConnection(ReservationMenu.this)){
            getPopulasiFamily1();
        }else{
            OpenLostConnection(this);
        }
    }

    private void getPopulasiFamily1(){
        ParamStripDate();
        MultiParamGetDataJSON PopulasiFamily = new MultiParamGetDataJSON();
        PopulasiFamily.init(APIValueParams, APIParameters, URL_GET_POPULASI_FAMILY1, ReservationMenu.this, json_PopFamily1, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_PopFamily1 = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(final JSONObject jsonObject) {
            int Child = 0;
            int Other = 0;
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_POPULASI_FAMILY1);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    if ((Gr.getInt("ID_TICKTYPE") == 2) || (Gr.getInt("ID_TICKTYPE") == 3)) {
                        Child = Child + Gr.getInt("COUNT");
                    } else {
                        Other = Other + Gr.getInt("COUNT");
                    }
                }
                Populasi_Child_Family = Child;
                Populasi_Other_Family = Other;

                getPopulasiFamily2();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getPopulasiFamily2(){
        ParamDate();
        MultiParamGetDataJSON PopulasiFamily2 = new MultiParamGetDataJSON();
        PopulasiFamily2.init(APIValueParams, APIParameters, URL_GET_POPULASI_FAMILY2, ReservationMenu.this, json_PopFamily2, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_PopFamily2 = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(final JSONObject jsonObject) {
            int Child = 0;
            int Other = 0;

            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_POPULASI_FAMILY2);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    Child = Child + Gr.getInt("f") + Gr.getInt("g");
                    Other = Other + Gr.getInt("a") + Gr.getInt("b") + Gr.getInt("c") + Gr.getInt("d") + Gr.getInt("e");
                }
                Populasi_Child_Family = Populasi_Child_Family + Child;
                Populasi_Other_Family = Populasi_Other_Family + Other;

                Pop_Child_Family.setText("Child : " + String.valueOf(Populasi_Child_Family));
                Pop_Other_Family.setText("Other : " + String.valueOf(Populasi_Other_Family));

                getPopulasiGroup();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getPopulasiGroup(){
        ParamDate();
        MultiParamGetDataJSON PopulasiGroup = new MultiParamGetDataJSON();
        PopulasiGroup.init(APIValueParams, APIParameters, URL_GET_POPULASI_GROUP, ReservationMenu.this, json_PopGroup, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_PopGroup = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(final JSONObject jsonObject) {
            int Child = 0;
            int Other = 0;

            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_POPULASI_GROUP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    Child = Child + Gr.getInt("f") + Gr.getInt("g");
                    Other = Other + Gr.getInt("a") + Gr.getInt("b") + Gr.getInt("c") + Gr.getInt("d") + Gr.getInt("e");
                }
                Populasi_Child_Group = Child;
                Populasi_Other_Group = Other;

                Pop_Child_Group.setText("Child : " + String.valueOf(Populasi_Child_Group));
                Pop_Other_Group.setText("Other : " + String.valueOf(Populasi_Other_Group));
                getPopulasiParty();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getPopulasiParty(){
        ParamStripDate();
        MultiParamGetDataJSON PopulasiParty = new MultiParamGetDataJSON();
        PopulasiParty.init(APIValueParams, APIParameters, URL_GET_POPULASI_PARTY, ReservationMenu.this, json_PopParty, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_PopParty = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(final JSONObject jsonObject) {
            int Child = 0;
            int Other = 0;

            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_POPULASI_PARTY);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    if ((Gr.getInt("TYP_PER") == 2) || (Gr.getInt("TYP_PER") == 3)) {
                        Child = Child + Gr.getInt("COUNT");
                    } else {
                        Other = Other + Gr.getInt("COUNT");
                    }
                }
                Populasi_Child_Party = Child;
                Populasi_Other_Party = Other;

                Pop_Child_Party.setText("Child : " + String.valueOf(Populasi_Child_Party));
                Pop_Other_Party.setText("Other : " + String.valueOf(Populasi_Other_Party));

                //int CapacityChild = 1200;
                //int CapacityOther = 800;
                //int SisaQuotaChild = CapacityChild - (Populasi_Child_Family + Populasi_Child_Group + Populasi_Child_Party);
                //int SisaQuotaOther = CapacityOther - (Populasi_Other_Family + Populasi_Other_Group + Populasi_Other_Party);
                //Total_Populasi.setText("Quota Avalaible : Child = " + String.valueOf(SisaQuotaChild) + ", Other = " + String.valueOf(SisaQuotaOther));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void OpenBookingList(){
        startActivity(new Intent(ReservationMenu.this, BookingData.class));
    }

    private void OpenReservationList(){
        startActivity(new Intent(ReservationMenu.this, ReservationData.class));
    }
}
