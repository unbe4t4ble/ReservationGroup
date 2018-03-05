package kidzania.reservationgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Misc.CalcTicket;

import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.isValidDate;
import static kidzania.reservationgroup.Misc.VarDate.DateReservMM;
import static kidzania.reservationgroup.Misc.VarGlobal.ADULTO;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.ARR_TIME;
import static kidzania.reservationgroup.Misc.VarGlobal.AmountAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.AmountChild;
import static kidzania.reservationgroup.Misc.VarGlobal.AmountTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.BEBE;
import static kidzania.reservationgroup.Misc.VarGlobal.FECHA_VISITA;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MPopChild;
import static kidzania.reservationgroup.Misc.VarGlobal.G_RsvAQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.G_RsvCQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_SPECIAL_QUOTA;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.HORA_SALIDA;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.L_AXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_CXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_TAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.L_TChild;
import static kidzania.reservationgroup.Misc.VarGlobal.NINO;
import static kidzania.reservationgroup.Misc.VarGlobal.NOTE;
import static kidzania.reservationgroup.Misc.VarGlobal.PROMOTOR_CODE;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_USUARIO_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;
import static kidzania.reservationgroup.Misc.VarGlobal.USUARIO_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.U_LOGIN;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_SPECIAL_QUOTA;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_DATA_BOOKING;

public class AddBooking extends AppCompatActivity {

    TextView ViewHargaTodd, ViewHargaChild, ViewHargaAdult;
    EditText edtGroup, edtSupporter, edtSales, edtTickPack, EditTodd, EditChild, EditAdult, edtNote;
    int L_RemCXQuota;
    int L_RemAXQuota;
    int L_RsvChild;
    int L_RsvAdult;
    boolean CheckingSpecialQuotaChild = true;
    boolean CheckingSpecialQuotaAdult = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
    }

    @Override
    public void onResume(){
        super.onResume();
        setTextFromSearch();
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
                SaveBooking();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization(){
        edtGroup = (EditText)findViewById(R.id.edtGroup);
        edtSupporter = (EditText) findViewById(R.id.edtSupporter);
        edtSales = (EditText) findViewById(R.id.edtSales);
        edtTickPack = (EditText) findViewById(R.id.edtTickPack);
        edtNote = (EditText) findViewById(R.id.edtNote);
        EditTodd = (EditText) findViewById(R.id.EditTodd);
        EditChild = (EditText) findViewById(R.id.EditChild);
        EditAdult = (EditText) findViewById(R.id.EditAdult);
        ViewHargaTodd = (TextView) findViewById(R.id.ViewHargaTodd);
        ViewHargaChild = (TextView) findViewById(R.id.ViewHargaChild);
        ViewHargaAdult = (TextView) findViewById(R.id.ViewHargaAdult);

        edtGroup.setOnTouchListener(showGroupAvail);
        edtSupporter.setOnTouchListener(showSearchSupporter);
        edtTickPack.setOnTouchListener(showSearchTicketPack);
    }

    private void setTextFromSearch() {
        if (SENDER_CLASS.equals("GroupOwner")) {
            edtGroup.setText(GRPNAME);
        }else
        if (SENDER_CLASS.equals("SearchSupporter")){
            edtSupporter.setText(STR_USUARIO_ALTA);
        }else
        if (SENDER_CLASS.equals("SearchMainTicketPack")){
            edtTickPack.setText(STR_ID_PAQ);
            ViewHargaTodd.setText(PriceTodd);
            ViewHargaChild.setText(PriceChild);
            ViewHargaAdult.setText(PriceAdult);
            clearEditTextMain();
            setOnChangeEditTextMain();
        }
        edtSales.setText(U_LOGIN);
    }

    View.OnTouchListener showGroupAvail = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            startActivity(new Intent(AddBooking.this, GroupOwner.class));
            return false;
        }
    };

    View.OnTouchListener showSearchSupporter = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            startActivity(new Intent(AddBooking.this, SearchSupporter.class));
            return false;
        }
    };

    View.OnTouchListener showSearchTicketPack = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(AddBooking.this, SearchMainTicketPack.class));
            return false;
        }
    };

    private void clearEditTextMain() {
        //EditText Main
        EditTodd.setText(null);
        EditChild.setText(null);
        EditAdult.setText(null);
    }

    private void setOnChangeEditTextMain() {
        CalcTicket EditToddChange = new CalcTicket(this, EditTodd, ViewHargaTodd, PriceTodd);
        //EditToddChange.harga = PriceTodd;
        EditTodd.addTextChangedListener(EditToddChange);

        CalcTicket EditChildChange = new CalcTicket(this,EditChild, ViewHargaChild, PriceChild);
        //EditChildChange.harga = PriceChild;
        EditChild.addTextChangedListener(EditChildChange);

        CalcTicket EditAdultChange = new CalcTicket(this,EditAdult, ViewHargaAdult, PriceAdult);
        //EditAdultChange.harga = PriceAdult;
        EditAdult.addTextChangedListener(EditAdultChange);
    }

    private boolean CheckingCBSupport() {
        if (TextUtils.isEmpty(edtSupporter.getText().toString())) {
            Toast.makeText(this, getString(R.string.message_warning_booking_supporter), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingCBPromotor() {
        if (TextUtils.isEmpty(edtSales.getText().toString())) {
            Toast.makeText(this, getString(R.string.message_warning_booking_promoter), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingCBGroup() {
        if (TextUtils.isEmpty(edtGroup.getText().toString())) {
            Toast.makeText(this, getString(R.string.message_invalid_group_name), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingCBTicket() {
        if (TextUtils.isEmpty(edtTickPack.getText().toString())) {
            Toast.makeText(this, getString(R.string.message_warning_booking_ticket), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingTextHarga() {
        if (TextUtils.isEmpty(this.EditTodd.getText().toString()) &&
                TextUtils.isEmpty(this.EditChild.getText().toString()) &&
                TextUtils.isEmpty(this.EditAdult.getText().toString())) {
            Toast.makeText(this, getString(R.string.message_warning_booking_amount_visitor), Toast.LENGTH_LONG).show();
            return false;
        } else {

            if (TextUtils.isEmpty(this.EditTodd.getText().toString())) {
                AmountTodd = "0";
            } else {
                AmountTodd = EditTodd.getText().toString();
            }
            if (TextUtils.isEmpty(this.EditChild.getText().toString())) {
                AmountChild = "0";
            } else {
                AmountChild = EditChild.getText().toString();
            }
            if (TextUtils.isEmpty(this.EditAdult.getText().toString())) {
                AmountAdult = "0";
            } else {
                AmountAdult = EditAdult.getText().toString();
            }
            return true;
        }
    }

    private boolean CheckingText() {

        if ((!CheckingCBGroup()) || (!CheckingCBSupport()) || (!CheckingCBPromotor()) || (!CheckingCBTicket()) || (!CheckingTextHarga())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean ValidateQuotaChild() {
        int QuotaChild;
        int TotalChild;
        QuotaChild = G_MPopChild - G_RsvCQuota;
        QuotaChild = L_TChild - QuotaChild;

        if (QuotaChild < 0) {
            QuotaChild = 0;
        }
        if (!TextUtils.isEmpty(this.EditChild.getText().toString())) {
            TotalChild = Integer.valueOf(EditChild.getText().toString());
        } else TotalChild = 0;

        String message = getString(R.string.message_warning_invalid_quota) + ". \n" +
                getString(R.string.message_warning_invalid_quota_child_1) + String.valueOf(QuotaChild) + ". \n" +
                getString(R.string.message_warning_invalid_quota_child_2);

        if (QuotaChild < TotalChild) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean ValidateQuotaAdult() {
        int QuotaAdult;
        int TotalAdult;
        QuotaAdult = G_MAdult - G_RsvAQuota;
        QuotaAdult = L_TAdult - QuotaAdult;

        if (QuotaAdult < 0) {
            QuotaAdult = 0;
        }
        if (!TextUtils.isEmpty(this.EditAdult.getText().toString())) {
            TotalAdult = Integer.valueOf(EditAdult.getText().toString());
        } else TotalAdult = 0;

        String message = getString(R.string.message_warning_invalid_quota) + ". \n" +
                getString(R.string.message_warning_invalid_quota_adult_1) + String.valueOf(QuotaAdult) + ". \n" +
                getString(R.string.message_warning_invalid_quota_adult_2);

        if (QuotaAdult < TotalAdult) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
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
            getSpecialQuota.init(APIValueParams, APIParameters, URL_GET_DATA_SPECIAL_QUOTA, AddBooking.this, json_ValidateSpecialQuota, true);
        }
    }

    MultiParamGetDataJSON.JSONObjectResult json_ValidateSpecialQuota = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            L_RsvChild = Integer.valueOf(EditChild.getText().toString());
            L_RsvAdult = Integer.valueOf(EditAdult.getText().toString());
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
                    String message = getString(R.string.message_warning_invalid_quota) + ". \n" +
                            getString(R.string.message_warning_invalid_quota_child_1) + String.valueOf(L_RemCXQuota) + ". \n" +
                            getString(R.string.message_warning_invalid_quota_child_2);
                    Toast.makeText(AddBooking.this, message, Toast.LENGTH_LONG).show();
                    CheckingSpecialQuotaChild = false;
                }

                if (L_RemAXQuota < L_RsvAdult) {
                    String message = getString(R.string.message_warning_invalid_quota) + ". \n" +
                            getString(R.string.message_warning_invalid_quota_adult_1) + String.valueOf(L_RemAXQuota) + ". \n" +
                            getString(R.string.message_warning_invalid_quota_adult_2);
                    Toast.makeText(AddBooking.this, message, Toast.LENGTH_LONG).show();
                    CheckingSpecialQuotaAdult = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void SaveBooking() {
        get_ValidateSpecialQuota();
        PROMOTOR_CODE = ID_USER;
        NOTE = edtNote.getText().toString();

        if ((isValidDate(AddBooking.this))&&(CheckingText()) &&
                (ValidateQuotaChild()) &&
                (ValidateQuotaAdult()) &&
                (CheckingSpecialQuotaChild) &&
                (CheckingSpecialQuotaAdult)) {

            BEBE = AmountTodd;
            NINO = AmountChild;
            ADULTO = AmountAdult;

            insertBooking();
        }
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add("U_LOGIN");
        APIParameters.add("FECHA_VISITA");
        APIParameters.add("TURNO");
        APIParameters.add("ARR_TIME");
        APIParameters.add("HORA_SALIDA");
        APIParameters.add("USUARIO_ALTA");
        APIParameters.add("BEBE");
        APIParameters.add("NINO");
        APIParameters.add("ADULTO");
        APIParameters.add("PROMOTOR_CODE");
        APIParameters.add("NOTE");
        APIParameters.add("ID_NUM_ESC");
        APIParameters.add("ID_PAQ");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(U_LOGIN);
        APIValueParams.add(FECHA_VISITA);
        APIValueParams.add(String.valueOf(TURNO));
        APIValueParams.add(ARR_TIME);
        APIValueParams.add(HORA_SALIDA);
        APIValueParams.add(USUARIO_ALTA);
        APIValueParams.add(BEBE);
        APIValueParams.add(NINO);
        APIValueParams.add(ADULTO);
        APIValueParams.add(PROMOTOR_CODE);
        APIValueParams.add(NOTE);
        APIValueParams.add(ID_NUM_ESC);
        APIValueParams.add(ID_PAQ);
    }
    private void insertBooking(){
        AssignParam();
        AssignValueParam();
        MultiParamGetDataJSON InsertBooking = new MultiParamGetDataJSON();
        InsertBooking.init(APIValueParams, APIParameters, URL_SEND_DATA_BOOKING, AddBooking.this, json_status_save, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_status_save = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    ShowDialog(getString(R.string.message_dialog_Information), getString(R.string.message_success_saving_data));
                }else{
                    SingleDialodWithOutVoid(AddBooking.this, getString(R.string.message_dialog_warning), getString(R.string.message_failed_saving_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void ShowDialog(String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(AddBooking.this);
        View mView = LayoutInflater.from(AddBooking.this).inflate(R.layout.popup_message, null);
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
