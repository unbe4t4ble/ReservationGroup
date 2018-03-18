package kidzania.reservationgroup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.CanEditShift;
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.VarDate.CombineDate;
import static kidzania.reservationgroup.Misc.VarDate.DateReservDD;
import static kidzania.reservationgroup.Misc.VarDate.DateReservMM;
import static kidzania.reservationgroup.Misc.VarDate.DayInt;
import static kidzania.reservationgroup.Misc.VarDate.getNamaBulan;
import static kidzania.reservationgroup.Misc.VarDate.getNamaHariFromEnglish;
import static kidzania.reservationgroup.Misc.VarDate.isChoiceDate;
import static kidzania.reservationgroup.Misc.VarDate.setAllFormatDate;
import static kidzania.reservationgroup.Misc.VarGlobal.ADULTO;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.FECHA_VISITA;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_SPECIAL_QUOTA;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.L_AXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_CXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.NINO;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;
import static kidzania.reservationgroup.Misc.VarUrl.URL_EDIT_DATE_BOOKING;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_SPECIAL_QUOTA;

public class ChangeDateReservation extends AppCompatActivity {

    EditText TanggalReservasi, TextSesi;
    String selectedItem;
    int Shift;
    int L_RemCXQuota;
    int L_RemAXQuota;
    int L_RsvChild;
    int L_RsvAdult;
    boolean CheckingSpecialQuotaChild = true;
    boolean CheckingSpecialQuotaAdult = true;

    String DateBefore, DateAfter, ShiftBefore, ShiftAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_date_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_save_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_save_group:
                saveChangeDate();
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

    private void setBefore(){
        DateBefore = FECHA_VISITA;
        if(TURNO == 0){
            ShiftBefore = "Morning";
        }else{
            ShiftBefore = "Afternoon";
        }
    }

    private void setAfter(){
        DateAfter = FECHA_VISITA;
        if(TURNO == 0){
            ShiftAfter = "Morning";
        }else{
            ShiftAfter = "Afternoon";
        }
    }


    private void initialization(){
        TanggalReservasi = (EditText) findViewById(R.id.TanggalReservasi);
        TanggalReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCalendarShow();
            }
        });

        TextSesi = (EditText) findViewById(R.id.TextSesi);
        TextSesi.setText(getString(R.string.str_booking_morning));
        TextSesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CanEditShift(ChangeDateReservation.this)) {
                    String[] ArrayShift = new String[]{
                            getString(R.string.str_booking_morning),
                            getString(R.string.str_booking_afternoon)
                    };
                    SingleChoice(ChangeDateReservation.this, TextSesi, getString(R.string.str_booking_shift), ArrayShift);
                }
            }
        });
        setBefore();
    }

    private void saveChangeDate(){
        checkValid();
    }

    private boolean isValidDate(){
        boolean validDate = true;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(FECHA_VISITA);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (System.currentTimeMillis() > strDate.getTime()) {
            Toast.makeText(this, "Can not change reservation date to previous date.", Toast.LENGTH_SHORT).show();
            validDate = false;
        }
        return validDate;
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
        getNamaHariFromEnglish(ChangeDateReservation.this, DayFormat.format(date));
        getNamaBulan(ChangeDateReservation.this,formatMM.format(date));
        setAllFormatDate(formatDD.format(date),formatMM.format(date),formatYY.format(date) );
    }

    private void OpenCalendarShow(){
        startActivity(new Intent(ChangeDateReservation.this, CalendarShow.class));
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

    private void get_ValidateSpecialQuota() {
        if (L_CXQuota > 0) {
            clearAPIParams();
            APIParameters.add("DateReservMM");
            APIParameters.add("TURNO");
            clearAPIValueParam();
            APIValueParams.add(DateReservMM);
            APIValueParams.add(String.valueOf(TURNO));

            MultiParamGetDataJSON getSpecialQuota = new MultiParamGetDataJSON();
            getSpecialQuota.init(APIValueParams, APIParameters, URL_GET_DATA_SPECIAL_QUOTA, ChangeDateReservation.this, json_ValidateSpecialQuota, true);
        }
    }

    MultiParamGetDataJSON.JSONObjectResult json_ValidateSpecialQuota = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            L_RsvChild = Integer.valueOf(NINO);
            L_RsvAdult = Integer.valueOf(ADULTO);
            L_RemCXQuota = L_CXQuota;
            L_RemAXQuota = L_AXQuota;
            try {

                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_SPECIAL_QUOTA);
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    L_RemCXQuota = L_RemCXQuota - Gr.getInt("CHILD");
                    L_RemAXQuota = L_RemAXQuota - Gr.getInt("ADULT");
                }
                if (L_RemCXQuota < L_RsvChild) {
                    String message = "Can not save reservation to selected date. \n" +
                            "Remaining toddler/child quota for group is " + String.valueOf(L_RemCXQuota) + ". \n" +
                            "Please reduce amount of toddler/children for this reservation.";
                    Toast.makeText(ChangeDateReservation.this, message, Toast.LENGTH_LONG).show();
                    CheckingSpecialQuotaChild = false;
                }

                if (L_RemAXQuota < L_RsvAdult) {
                    String message = "Can not save reservation to selected date. \n" +
                            "Remaining adult quota for group is " + String.valueOf(L_RemAXQuota) + ". \n" +
                            "Please reduce amount of adult for this reservation.";
                    Toast.makeText(ChangeDateReservation.this, message, Toast.LENGTH_LONG).show();
                    CheckingSpecialQuotaAdult = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void checkValid(){
        get_ValidateSpecialQuota();
        FECHA_VISITA = DateReservDD; //Tanggal kunjungan
        TURNO = Shift; // shift/sesi
        if (isValidDate() && CheckingSpecialQuotaChild && CheckingSpecialQuotaAdult) {
            setAfter();
            editDataGroup();
        }
    }

    private void editDataGroup(){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add("FECHA_VISITA");
        APIParameters.add("TURNO");
        APIParameters.add("ID_NUM_RESER");
        APIParameters.add("IDUSER");
        APIParameters.add("REASON");
        APIValueParams.add(FECHA_VISITA);
        APIValueParams.add(String.valueOf(TURNO));
        APIValueParams.add(ID_NUM_RESER);
        APIValueParams.add(ID_USER);
        APIValueParams.add("CHG date/shift from "+DateBefore+" "+ShiftBefore+" to "+DateAfter+" "+ShiftAfter);
        MultiParamGetDataJSON SaveChangeDate = new MultiParamGetDataJSON();
        SaveChangeDate.init(APIValueParams, APIParameters, URL_EDIT_DATE_BOOKING, ChangeDateReservation.this, json_change_date, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_change_date = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_STATUS);
                boolean isSuccess = false;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    isSuccess = objData.getString("STATUS").equals("1");
                }
                if (isSuccess){
                    ShowDialog(getString(R.string.message_dialog_Information), getString(R.string.message_success_edit_data));
                }else{
                    SingleDialodWithOutVoid(ChangeDateReservation.this, getString(R.string.message_dialog_warning), getString(R.string.message_failed_edit_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void ShowDialog(String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(ChangeDateReservation.this);
        View mView = LayoutInflater.from(ChangeDateReservation.this).inflate(R.layout.popup_message, null);
        final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
        final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button btnOkMessage = (Button) mView.findViewById(R.id.btnOkMessage);
        txtJudul.setText(Judul);
        txtMessage.setText(Pesan);
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnOkMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

}
