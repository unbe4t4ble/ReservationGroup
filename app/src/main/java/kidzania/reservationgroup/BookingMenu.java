package kidzania.reservationgroup;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.CanEditShift;
import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.showTimePicker;
import static kidzania.reservationgroup.Misc.VarDate.CombineDate;
import static kidzania.reservationgroup.Misc.VarDate.DateReservDD;
import static kidzania.reservationgroup.Misc.VarDate.DateReservMM;
import static kidzania.reservationgroup.Misc.VarDate.DayInt;
import static kidzania.reservationgroup.Misc.VarDate.getNamaBulan;
import static kidzania.reservationgroup.Misc.VarDate.getNamaHariFromEnglish;
import static kidzania.reservationgroup.Misc.VarDate.isChoiceDate;
import static kidzania.reservationgroup.Misc.VarDate.setAllFormatDate;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.ARR_TIME;
import static kidzania.reservationgroup.Misc.VarGlobal.FECHA_VISITA;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MPSChild;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MPopChild;
import static kidzania.reservationgroup.Misc.VarGlobal.G_RsvAQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.G_RsvCQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.G_SQuotaApply;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_ALL_QUOTA;
import static kidzania.reservationgroup.Misc.VarGlobal.HORA_SALIDA;
import static kidzania.reservationgroup.Misc.VarGlobal.L_AXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_CXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_TAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.L_TChild;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_ALL_QUOTA;

public class BookingMenu extends AppCompatActivity {

    EditText TanggalReservasi, TextJamMulai, TextJamAkhir, TextSesi;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String selectedItem;
    int Shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
        ShowCalendar();
        CheckConnection(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_booking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_booking:
                OpenListBooking();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        TanggalReservasi.setText(getStrDateNow());
    }

    private void initialization(){
        TanggalReservasi = (EditText) findViewById(R.id.TanggalReservasi);
        TanggalReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (Build.VERSION.SDK_INT >= 23) {
                    if (!checkPermission()) {
                        requestPermission();
                    }else{
                        OpenCalendarShow();
                    }
                //}
            }
        });
        TextJamMulai = (EditText) findViewById(R.id.TextJamMulai);
        TextJamMulai.setText("09:00");
        TextJamMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(BookingMenu.this,TextJamMulai);
            }
        });
        TextJamAkhir = (EditText) findViewById(R.id.TextJamAkhir);
        TextJamAkhir.setText("14:00");
        TextJamAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(BookingMenu.this, TextJamAkhir);
            }
        });
        TextSesi = (EditText) findViewById(R.id.TextSesi);
        TextSesi.setText(getString(R.string.str_booking_morning));
        TextSesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CanEditShift(BookingMenu.this)) {
                    String[] ArrayShift = new String[]{
                            getString(R.string.str_booking_morning),
                            getString(R.string.str_booking_afternoon)
                    };
                    SingleChoice(BookingMenu.this, TextSesi, getString(R.string.str_booking_shift), ArrayShift);
                }
            }
        });
    }

    private void SingleChoice(Context context, final EditText editText, String Tag_Name, final String[] ArrayList){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Tag_Name);
        builder.setSingleChoiceItems(ArrayList, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedItem = Arrays.asList(ArrayList).get(i);
                        Shift = i;
                    }
                });
        //}
        builder.setPositiveButton(getString(R.string.btn_ok_message), null);
        builder.setNegativeButton(getString(R.string.btn_delete_message), null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button btnOk = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText(selectedItem);
                        dialog.dismiss();
                    }
                });
                Button btnDelete = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText("");
                        dialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    private String getStrDateNow(){
        Date date = new Date();
        String hasil;
        if (isChoiceDate) {
            hasil = CombineDate;
        }else{
            GetDate(date);
            hasil = CombineDate;
        }
        return hasil;
    }

    private void GetDate(Date date){
        SimpleDateFormat formatDD = new SimpleDateFormat("dd", Locale.US);
        SimpleDateFormat formatMM = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat formatYY = new SimpleDateFormat("yyyy", Locale.US);
        SimpleDateFormat DayFormat = new SimpleDateFormat("EEEE", Locale.US);
        DayInt = Integer.valueOf(formatDD.format(date));
        getNamaHariFromEnglish(BookingMenu.this, DayFormat.format(date));
        getNamaBulan(BookingMenu.this,formatMM.format(date));
        setAllFormatDate(formatDD.format(date),formatMM.format(date),formatYY.format(date) );
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(BookingMenu.this, Manifest.permission.READ_CALENDAR);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(BookingMenu.this, android.Manifest.permission.READ_CALENDAR)) {
            Toast.makeText(BookingMenu.this, getString(R.string.message_failed_permission_calendar), Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(BookingMenu.this, new String[]{android.Manifest.permission.READ_CALENDAR}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    private void ShowCalendar(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkPermission()) {
                requestPermission();
            }
        }
    }

    private void AssignToParam(){
        FECHA_VISITA = DateReservDD; //Tanggal kunjungan
        ARR_TIME = TextJamMulai.getText().toString(); //jam kedatangan
        HORA_SALIDA = TextJamAkhir.getText().toString(); //jam pulang
        TURNO = Shift; // shift/sesi
        get_AllQuota();
    }

    private void OpenListBooking(){
        if(hasConnection(BookingMenu.this)) {
            AssignToParam();
        }else{
            OpenLostConnection(this);
        }
    }

    private void get_AllQuota(){
        setDefault();
        clearAPIValueParam();
        clearAPIParams();
        APIParameters.add("DateReservMM");
        APIParameters.add("SHIFT");
        APIValueParams.add(DateReservMM);
        APIValueParams.add(String.valueOf(TURNO));
        MultiParamGetDataJSON AssignSpecialQuota = new MultiParamGetDataJSON();
        AssignSpecialQuota.init(APIValueParams,APIParameters, URL_GET_ALL_QUOTA, BookingMenu.this,json_Quota, true);
    }

    private void setDefault(){
        G_RsvCQuota = 1200;
        G_RsvAQuota = 400;
        G_SQuotaApply = 1;

        L_TChild = 1200;
        L_TAdult = 400;

        L_CXQuota = 1200;
        L_AXQuota = 400;

        G_MPopChild = 1200;
        G_MPSChild = 1200;
        G_MAdult = 400;
    }

    MultiParamGetDataJSON.JSONObjectResult json_Quota = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_ALL_QUOTA);
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    G_RsvCQuota = Gr.getInt("RSV_CHILD");
                    G_RsvAQuota = Gr.getInt("RSV_ADULT");
                    G_SQuotaApply = Gr.getInt("APPLY");

                    L_TChild =  Gr.getInt("TOTAL_KIDS");
                    L_TAdult =  Gr.getInt("TOTAL_ADULTS");

                    L_CXQuota =  Gr.getInt("CHILD");
                    L_AXQuota =  Gr.getInt("ADULT");

                }
                OpenReservationMenu();
            } catch (JSONException e) {
                //e.printStackTrace();
                OpenReservationMenu();
            }
        }
    };

    private void OpenCalendarShow(){
        startActivity(new Intent(BookingMenu.this, CalendarShow.class));
    }

    private void OpenReservationMenu(){
        startActivity(new Intent(BookingMenu.this, ReservationMenu.class));
    }

}
