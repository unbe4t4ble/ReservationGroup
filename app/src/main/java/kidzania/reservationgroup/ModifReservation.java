package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Misc.CalcTicket;
import kidzania.reservationgroup.Misc.GetPackageReserv;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static kidzania.reservationgroup.Misc.FuncGlobal.CanEditShift;
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.FuncGlobal.getInformationUser;
import static kidzania.reservationgroup.Misc.FuncGlobal.getNormalNumber;
import static kidzania.reservationgroup.Misc.FuncGlobal.isValidDate;
import static kidzania.reservationgroup.Misc.FuncGlobal.showTimePicker;
import static kidzania.reservationgroup.Misc.VarDate.DateReservMM;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_A5;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_C5;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_T5;
import static kidzania.reservationgroup.Misc.VarGlobal.ADULTO;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.ARR_TIME;
import static kidzania.reservationgroup.Misc.VarGlobal.AllAmountGetPaket;
import static kidzania.reservationgroup.Misc.VarGlobal.AmountAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.AmountChild;
import static kidzania.reservationgroup.Misc.VarGlobal.AmountTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.BEBE;
import static kidzania.reservationgroup.Misc.VarGlobal.BusBefore;
import static kidzania.reservationgroup.Misc.VarGlobal.COMPLIMENT5;
import static kidzania.reservationgroup.Misc.VarGlobal.COMPLIMENT7;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.G_MPopChild;
import static kidzania.reservationgroup.Misc.VarGlobal.G_RsvAQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.G_RsvCQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.HANDICAP;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_SPECIAL_QUOTA;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.HORA_SALIDA;
import static kidzania.reservationgroup.Misc.VarGlobal.IDPACK_ADD;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_RESP_AGE;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.INSEN;
import static kidzania.reservationgroup.Misc.VarGlobal.L_AXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_CXQuota;
import static kidzania.reservationgroup.Misc.VarGlobal.L_TAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.L_TChild;
import static kidzania.reservationgroup.Misc.VarGlobal.LunchBefore;
import static kidzania.reservationgroup.Misc.VarGlobal.NINO;
import static kidzania.reservationgroup.Misc.VarGlobal.NOTIF_FINISH;
import static kidzania.reservationgroup.Misc.VarGlobal.PROMOTOR_CODE;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.SENIOR;
import static kidzania.reservationgroup.Misc.VarGlobal.STATUS_RESERV;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_IDPACK_ADD;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_AGE;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.SouvernirBefore;
import static kidzania.reservationgroup.Misc.VarGlobal.SumAmountAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.SumAmountChild;
import static kidzania.reservationgroup.Misc.VarGlobal.SumAmountTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.SumPrice;
import static kidzania.reservationgroup.Misc.VarGlobal.TAG_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.TOTAL_APAGAR;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAddAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAddChild;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAddTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.TempBaby;
import static kidzania.reservationgroup.Misc.VarGlobal.TempChild;
import static kidzania.reservationgroup.Misc.VarGlobal.TempHandyCap;
import static kidzania.reservationgroup.Misc.VarGlobal.TempSenior;
import static kidzania.reservationgroup.Misc.VarGlobal.TempTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.USUARIO_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.U_LOGIN;
import static kidzania.reservationgroup.Misc.VarGlobal.cursor;
import static kidzania.reservationgroup.Misc.VarGlobal.dbHelper;
import static kidzania.reservationgroup.Misc.VarGlobal.isCombineAll;
import static kidzania.reservationgroup.Misc.VarGlobal.isCombineLunch;
import static kidzania.reservationgroup.Misc.VarGlobal.isCombineSouvenir;
import static kidzania.reservationgroup.Misc.VarGlobal.isFromList;
import static kidzania.reservationgroup.Misc.VarGlobal.isModifReservation;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_SPECIAL_QUOTA;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_RESERVATION;

public class ModifReservation extends AppCompatActivity {

    EditText ModGroupName, ModArrival, ModLeaving, ModShift, ModMainTicket, ModCompliment;
    EditText EditTodd, EditChild, EditAdult, EditBaby, EditSenior, EditHandicap;
    EditText EditAddTodd, EditAddChild, EditAddAdult;
    EditText  ModResponsible, ModPromotor, cbAddTicket;
    TextView FieldTodd, FieldChild, FieldAdult;
    TextView ViewHargaTodd, ViewHargaChild, ViewHargaAdult;
    TextView ViewHargaBaby, ViewHargaSenior, ViewHargaHandicap;
    TextView ViewHargaAddTodd, ViewHargaAddChild, ViewHargaAddAdult;
    Button btnLunchPackage, btnMerchPackage, btnBusPackage, btnOthersPackage;

    String selectedItem;
    int Shift;

    int L_RemCXQuota;
    int L_RemAXQuota;
    int L_RsvChild;
    int L_RsvAdult;
    boolean CheckingSpecialQuotaChild = true;
    boolean CheckingSpecialQuotaAdult = true;

    GetPackageReserv getPackageReserv;

    String hargaAddTodd,hargaAddChild, hargaAddAdult;

    String ToddBefore, ChildBefore, AdultBefore, Com5Before, Com7Before, TickBefore, PromBefore;
    String Reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_reservation);
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
                SaveReservation();
                //getPackageReserv.excuteDeleteAndInsert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getInformationUser();
        setTextFromSearch();
        setBefore();
        registerReceiver(mMessageFinish, new IntentFilter(NOTIF_FINISH));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageFinish);
    }

    private BroadcastReceiver mMessageFinish = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ShowDialog(getString(R.string.message_dialog_Information), getString(R.string.message_success_saving_data));
        }
    };

    private void initialization(){
        Reason = "";
        ModGroupName = (EditText) findViewById(R.id.ModGroupName);
        ModResponsible = (EditText) findViewById(R.id.ModResponsible);
        ModPromotor = (EditText) findViewById(R.id.ModPromotor);
        ModArrival = (EditText) findViewById(R.id.ModArrival);
        ModLeaving =(EditText) findViewById(R.id.ModLeaving);
        ModShift = (EditText) findViewById(R.id.ModShift);
        ModCompliment = (EditText) findViewById(R.id.ModCompliment);
        ModMainTicket = (EditText) findViewById(R.id.ModMainTicket);
        FieldTodd = (TextView) findViewById(R.id.FieldTodd);
        FieldChild = (TextView) findViewById(R.id.FieldChild);
        FieldAdult = (TextView) findViewById(R.id.FieldAdult);
        EditTodd = (EditText) findViewById(R.id.EditTodd);
        EditChild = (EditText) findViewById(R.id.EditChild);
        EditAdult = (EditText) findViewById(R.id.EditAdult);
        ViewHargaTodd = (TextView) findViewById(R.id.ViewHargaTodd);
        ViewHargaChild = (TextView) findViewById(R.id.ViewHargaChild);
        ViewHargaAdult = (TextView) findViewById(R.id.ViewHargaAdult);
        EditBaby = (EditText) findViewById(R.id.EditBaby);
        EditSenior = (EditText) findViewById(R.id.EditSenior);
        EditHandicap = (EditText) findViewById(R.id.EditHandicap);
        ViewHargaBaby = (TextView) findViewById(R.id.ViewHargaBaby);
        ViewHargaSenior = (TextView) findViewById(R.id.ViewHargaSenior);
        ViewHargaHandicap = (TextView) findViewById(R.id.ViewHargaHandicap);
        cbAddTicket = (EditText) findViewById(R.id.cbAddTicket);
        EditAddTodd = (EditText) findViewById(R.id.EditAddTodd);
        EditAddChild = (EditText) findViewById(R.id.EditAddChild);
        EditAddAdult = (EditText) findViewById(R.id.EditAddAdult);
        ViewHargaAddTodd = (TextView) findViewById(R.id.ViewHargaAddTodd);
        ViewHargaAddChild = (TextView) findViewById(R.id.ViewHargaAddChild);
        ViewHargaAddAdult = (TextView) findViewById(R.id.ViewHargaAddAdult);
        btnLunchPackage = (Button) findViewById(R.id.btnLunchPackage);
        btnLunchPackage.setTag("RESERV_PLUNCH");
        btnBusPackage = (Button) findViewById(R.id.btnBusPackage);
        btnBusPackage.setTag("RESERV_PBUS");
        btnMerchPackage = (Button) findViewById(R.id.btnMerchPackage);
        btnMerchPackage.setTag("RESERV_PSOUV");
        btnOthersPackage = (Button) findViewById(R.id.btnOthersPackage);
        btnOthersPackage.setTag("RESERV_POTH");

        ModGroupName.setOnTouchListener(showGroupAvail);
        ModResponsible.setOnTouchListener(showSearchResponsible);
        ModPromotor.setOnTouchListener(showSearchPromotor);
        ModArrival.setOnTouchListener(showTimeArrival);
        ModLeaving.setOnTouchListener(showTimeLeaving);
        ModShift.setOnTouchListener(showShift);
        ModMainTicket.setOnTouchListener(showSearchMainTicketPack);
        cbAddTicket.setOnTouchListener(showSearchAddTicketPack);
        btnLunchPackage.setOnClickListener(showAddPackage);
        btnBusPackage.setOnClickListener(showAddPackage);
        btnMerchPackage.setOnClickListener(showAddPackage);
        btnOthersPackage.setOnClickListener(showAddPackage);
        ModCompliment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ModCompliment.getText().toString().equals("")){
                    COMPLIMENT5 = "0";
                }else{
                    COMPLIMENT5 = ModCompliment.getText().toString().trim();
                }
            }
        });

        clearEditTextMain();
        clearEditTextAdd();

        getPackageReserv = new GetPackageReserv(ModifReservation.this);
        getPackageReserv.excuteGet();
    }

    private void setTextFromSearch() {
        ModGroupName.setText(STR_ID_NUM_ESC);
        ModResponsible.setText(STR_ID_RESP_AGE);
        ModPromotor.setText(STR_ID_RESP_ESC);
        ModMainTicket.setText(STR_ID_PAQ);

        FieldTodd.setText(BEBE);
        FieldChild.setText(NINO);
        FieldAdult.setText(ADULTO);

        if (isModifReservation) {
            if (Integer.valueOf(BEBE) > Integer.parseInt(ADD_T5)) {
                EditTodd.setText(String.valueOf(Integer.valueOf(BEBE) - Integer.parseInt(ADD_T5)));
            } else {
                EditTodd.setText(BEBE);
            }

            if (Integer.valueOf(NINO) > Integer.parseInt(ADD_C5)) {
                EditChild.setText(String.valueOf(Integer.valueOf(NINO) - Integer.parseInt(ADD_C5)));
            } else {
                EditChild.setText(NINO);
            }

            if (Integer.valueOf(ADULTO) > Integer.parseInt(ADD_A5)) {
                EditAdult.setText(String.valueOf(Integer.valueOf(ADULTO) - Integer.parseInt(ADD_A5)));
            } else {
                EditAdult.setText(ADULTO);
            }
        }

        EditAddTodd.setText(ADD_T5);
        EditAddChild.setText(ADD_C5);
        EditAddAdult.setText(ADD_A5);
        String hargaTodd = String.valueOf(Integer.parseInt(BEBE)*Integer.parseInt(getNormalNumber(PriceTodd)));
        String hargaChild = String.valueOf(Integer.parseInt(NINO)*Integer.parseInt(getNormalNumber(PriceChild)));
        String hargaAdult = String.valueOf(Integer.parseInt(ADULTO)*Integer.parseInt(getNormalNumber(PriceAdult)));
        ViewHargaTodd.setText(getFormatedCurrency(this,hargaTodd));
        ViewHargaChild.setText(getFormatedCurrency(this,hargaChild));
        ViewHargaAdult.setText(getFormatedCurrency(this,hargaAdult));
        cbAddTicket.setText(STR_IDPACK_ADD);

        setHargaAddTicket();
        ViewHargaAddTodd.setText(getFormatedCurrency(this, hargaAddTodd));
        ViewHargaAddChild.setText(getFormatedCurrency(this, hargaAddChild));
        ViewHargaAddAdult.setText(getFormatedCurrency(this, hargaAddAdult));
        ModCompliment.setText(COMPLIMENT5);
        ModArrival.setText(ARR_TIME);
        ModLeaving.setText(HORA_SALIDA);
        if(TURNO > 0){
            ModShift.setText(getString(R.string.str_booking_afternoon));
        }else{
            ModShift.setText(getString(R.string.str_booking_morning));
        }

        setOnChangeEditTextMain();
        setOnChangeEditTextAdd();
        isModifReservation = false;
    }

    private void setHargaAddTicket(){
        String addToddler = EditAddTodd.getText().toString();
        String addChild = EditAddChild.getText().toString();
        String addAdult = EditAddAdult.getText().toString();

        if(!TextUtils.isEmpty(addToddler)){
            hargaAddTodd = String.valueOf(Integer.valueOf(addToddler) * Integer.parseInt(getNormalNumber(PriceAddTodd)));
        }
        if(!TextUtils.isEmpty(addChild)){
            hargaAddChild = String.valueOf(Integer.valueOf(addChild) * Integer.parseInt(getNormalNumber(PriceAddChild)));
        }
        if(!TextUtils.isEmpty(addAdult)){
            hargaAddAdult = String.valueOf(Integer.valueOf(addAdult) * Integer.parseInt(getNormalNumber(PriceAddAdult)));
        }

    }

    private void clearEditTextMain() {
        //EditText Main
        EditTodd.setText(BEBE);
        EditChild.setText(NINO);
        EditAdult.setText(ADULTO);
    }

    private void clearEditTextAdd() {
        //EditText Main
        EditAddTodd.setText(null);
        EditAddChild.setText(null);
        EditAddAdult.setText(null);
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

    private void setOnChangeEditTextAdd() {
        CalcTicket EditAddToddChange = new CalcTicket(this, EditAddTodd, ViewHargaAddTodd, PriceAddTodd);
        //EditAddToddChange.harga = PriceAddTodd;
        EditAddTodd.addTextChangedListener(EditAddToddChange);

        CalcTicket EditAddChildChange = new CalcTicket(this,EditAddChild, ViewHargaAddChild, PriceAddChild);
        //EditAddChildChange.harga = PriceAddChild;
        EditAddChild.addTextChangedListener(EditAddChildChange);

        CalcTicket EditAddAdultChange = new CalcTicket(this,EditAddAdult, ViewHargaAddAdult, PriceAddAdult);
        //EditAddAdultChange.harga = PriceAddAdult;
        EditAddAdult.addTextChangedListener(EditAddAdultChange);

        EditAddTodd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (EditAddTodd.getText().toString().equals("")){
                    ADD_T5 = "0";
                }else{
                    ADD_T5 = EditAddTodd.getText().toString().trim();
                }
            }
        });

        EditAddChild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (EditAddChild.getText().toString().equals("")){
                    ADD_C5 = "0";
                }else{
                    ADD_C5 = EditAddChild.getText().toString().trim();
                }
            }
        });

        EditAddAdult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (EditAddAdult.getText().toString().equals("")){
                    ADD_A5 = "0";
                }else{
                    ADD_A5 = EditAddAdult.getText().toString().trim();
                }
            }
        });
    }

    View.OnTouchListener showGroupAvail = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(ModifReservation.this, GroupOwner.class));
            return false;
        }
    };

    View.OnTouchListener showSearchPromotor = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(ModifReservation.this, SearchPromotor.class));
            return false;
        }
    };

    View.OnTouchListener showSearchResponsible = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(ModifReservation.this, SearchResponsible.class));
            return false;
        }
    };

    View.OnTouchListener showTimeArrival = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                showTimePicker(ModifReservation.this,ModArrival);
            }
            return false;
        }
    };

    View.OnTouchListener showTimeLeaving = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                showTimePicker(ModifReservation.this,ModLeaving);
            }
            return false;
        }
    };

    View.OnTouchListener showShift = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                if (CanEditShift(ModifReservation.this)) {
                    String[] ArrayShift = new String[]{
                            getString(R.string.str_booking_morning),
                            getString(R.string.str_booking_afternoon)
                    };
                    SingleChoice(ModifReservation.this, ModShift, getString(R.string.str_booking_shift), ArrayShift);
                }
            }
            return false;
        }
    };

    View.OnTouchListener showSearchMainTicketPack = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(ModifReservation.this, SearchMainTicketPack.class));
            return false;
        }
    };

    View.OnTouchListener showSearchAddTicketPack = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(ModifReservation.this, SearchAddTicketPack.class));
            return false;
        }
    };

    View.OnClickListener showAddPackage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = (String) v.getTag();
            showPackage(name);
        }
    };

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

    private void showPackage(String TAG){
        TAG_PACK = TAG;
        SENDER_CLASS = "ModifReservation";
        startActivity(new Intent(ModifReservation.this, AddPackage.class));
    }

    private boolean CheckingText() {
        boolean bResponsible = CheckingCBResponsible();
        boolean bPromotor = CheckingCBProm();
        boolean bMainTicket = CheckingCBTick();
        boolean bAddTicket = CheckingCBAddTick();
        boolean bCompliment = CheckingCompliment();

        return (bResponsible && bPromotor && bMainTicket && bAddTicket && bCompliment);

        //return !((!bResponsible) || (!bPromotor) || (!bMainTicket) || (!bAddTicket) || (!bCompliment));
    }

    private boolean CheckingCBResponsible() {
        boolean isValid = true;
        if (TextUtils.isEmpty(ModResponsible.getText().toString().trim())) {
            Toast.makeText(this, getText(R.string.message_invalid_modif_responsible), Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }

    private boolean CheckingCBProm() {
        boolean isValid = true;
        if (TextUtils.isEmpty(ModPromotor.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.message_invalid_modif_promotor), Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }

    private boolean CheckingCBTick() {
        boolean isValid = true;

        boolean   L_Vst = (Integer.valueOf(EditTodd.getText().toString().replace("","0")) > 0) ||
                          (Integer.valueOf(EditChild.getText().toString().replace("","0"))> 0) ||
                          (Integer.valueOf(EditAdult.getText().toString().replace("","0"))> 0);

        if ( TextUtils.isEmpty(ModMainTicket.getText().toString().trim()) && L_Vst ){
            Toast.makeText(this, getString(R.string.message_invalid_modif_ticket), Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }

private boolean CheckingCBAddTick() {
        boolean isValid = true;

        boolean   L_AddVst = (Integer.valueOf(EditAddTodd.getText().toString().replace("","0")) > 0) ||
                             (Integer.valueOf(EditAddChild.getText().toString().replace("","0"))> 0) ||
                             (Integer.valueOf(EditAddAdult.getText().toString().replace("","0"))> 0);

        if ( TextUtils.isEmpty(cbAddTicket.getText().toString().trim()) && L_AddVst ){
            Toast.makeText(this, getString(R.string.message_invalid_modif_add_ticket), Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }

    private boolean CheckingCompliment() {
        setCompliment();

        boolean isValid = false;
        int SumAdult = Integer.valueOf(TempAdult) + Integer.valueOf(TempAddAdult);

        boolean isLow = Integer.valueOf(COMPLIMENT5) < SumAdult;
        boolean isEqual = Integer.valueOf(COMPLIMENT5).equals(SumAdult);

        if (isLow  || isEqual){
            isValid = true;
        }else{
            Toast.makeText(this, getString(R.string.message_invalid_modif_adult), Toast.LENGTH_LONG).show();
        }

        return isValid;
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
            getSpecialQuota.init(APIValueParams, APIParameters, URL_GET_DATA_SPECIAL_QUOTA, ModifReservation.this, json_ValidateSpecialQuota, true);
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
                    Toast.makeText(ModifReservation.this, message, Toast.LENGTH_LONG).show();
                    CheckingSpecialQuotaChild = false;
                }

                if (L_RemAXQuota < L_RsvAdult) {
                    String message = getString(R.string.message_warning_invalid_quota) + ". \n" +
                            getString(R.string.message_warning_invalid_quota_adult_1) + String.valueOf(L_RemAXQuota) + ". \n" +
                            getString(R.string.message_warning_invalid_quota_adult_2);
                    Toast.makeText(ModifReservation.this, message, Toast.LENGTH_LONG).show();
                    CheckingSpecialQuotaAdult = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

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

    private void setCompliment(){
        if (TextUtils.isEmpty(ModCompliment.getText().toString().trim())){
            COMPLIMENT5 = "0";
        }else{
            COMPLIMENT5 = ModCompliment.getText().toString().trim();
        }
    }

    private void sumAllAmountVisitor(EditText edtTodd, EditText edtAddTodd,
                                     EditText edtChild, EditText edtAddChild,
                                     EditText edtAdult, EditText edtAddAdult){

        int hrgaTodd = 0, hrgaAddTodd = 0, hrgaChild = 0, hrgaAddChild = 0, hrgaAdult = 0, hrgaAddAdult = 0;

        //menghitung seluruh jumlah tiket
        if (edtTodd.getText().toString().equals("")){
            AmountTodd = "0";
        }else {
            AmountTodd = edtTodd.getText().toString();
        }

        if (edtAddTodd.getText().toString().equals("")){
            ADD_T5 = "0";
        }else {
            ADD_T5 = edtAddTodd.getText().toString();
        }

        if (edtChild.getText().toString().equals("")){
            AmountChild = "0";
        }else {
            AmountChild = edtChild.getText().toString();
        }

        if (edtAddChild.getText().toString().equals("")){
            ADD_C5 = "0";
        }else {
            ADD_C5 = edtAddChild.getText().toString();
        }

        if (edtAdult.getText().toString().equals("")){
            AmountAdult = "0";
        }else {
            AmountAdult = edtAdult.getText().toString();
        }

        if (edtAddAdult.getText().toString().equals("")){
            ADD_A5 = "0";
        }else {
            ADD_A5 = edtAddAdult.getText().toString();
        }
        SumAmountTodd = String.valueOf( Integer.valueOf(AmountTodd) + Integer.valueOf(ADD_T5));
        SumAmountChild = String.valueOf(Integer.valueOf(AmountChild) + Integer.valueOf(ADD_C5));
        SumAmountAdult = String.valueOf(Integer.valueOf(AmountAdult) + Integer.valueOf(ADD_A5));


        //mengitung seluruh jumlah harga tiket

        if (Integer.valueOf(AmountTodd) > 0 ) {
            hrgaTodd = Integer.valueOf(getNormalNumber(ViewHargaTodd.getText().toString()));
        }
        if (Integer.valueOf(ADD_T5) > 0 ) {
            hrgaAddTodd = Integer.valueOf(getNormalNumber(ViewHargaAddTodd.getText().toString()));
        }
        if (Integer.valueOf(AmountChild) > 0 ) {
            hrgaChild = Integer.valueOf(getNormalNumber(ViewHargaChild.getText().toString()));
        }
        if (Integer.valueOf(ADD_C5) > 0 ) {
            hrgaAddChild = Integer.valueOf(getNormalNumber(ViewHargaAddChild.getText().toString()));
        }
        if (Integer.valueOf(AmountAdult) > 0 ) {
            hrgaAdult = Integer.valueOf(getNormalNumber(ViewHargaAdult.getText().toString()));
        }
        if (Integer.valueOf(ADD_A5) > 0 ) {
            hrgaAddAdult = Integer.valueOf(getNormalNumber(ViewHargaAddAdult.getText().toString()));
        }
        SumPrice = String.valueOf(hrgaTodd+hrgaAddTodd+hrgaChild+hrgaAddChild+hrgaAdult+hrgaAddAdult);
    }

    private void SumAll(){
        int intHarga;
        SUMPackage();
        intHarga = Integer.valueOf(SumPrice);
        TOTAL_APAGAR = String.valueOf(intHarga + SUMPackage());

        if(Integer.valueOf(COMPLIMENT5)>0){
            TOTAL_APAGAR = String.valueOf(Integer.valueOf(TOTAL_APAGAR)-(Integer.valueOf(COMPLIMENT5)*Integer.valueOf(PriceAdult.replace(".",""))));
        }
    }

    private int SUMPackage(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT SUM(TOTAL) AS TOTAL FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER;
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    private void postEditVisitorToVar(){
        if (EditTodd.getText().toString().equals("")){
            TempTodd = "0";
        }else {
            TempTodd = EditTodd.getText().toString();
        }

        if (EditChild.getText().toString().equals("")){
            TempChild = "0";
        }else {
            TempChild = EditChild.getText().toString();
        }

        if (EditAdult.getText().toString().equals("")){
            TempAdult = "0";
        }else {
            TempAdult = EditAdult.getText().toString();
        }

        if (EditBaby.getText().toString().equals("")){
            TempBaby = "0";
        }else {
            TempBaby = EditBaby.getText().toString();
        }

        if (EditSenior.getText().toString().equals("")){
            TempSenior = "0";
        }else {
            TempSenior = EditSenior.getText().toString();
        }

        if (EditHandicap.getText().toString().equals("")){
            TempHandyCap = "0";
        }else {
            TempHandyCap = EditHandicap.getText().toString();
        }

        if (EditAddTodd.getText().toString().equals("")){
            TempAddTodd = "0";
        }else {
            TempAddTodd = EditAddTodd.getText().toString();
        }

        if (EditAddChild.getText().toString().equals("")){
            TempAddChild = "0";
        }else {
            TempAddChild = EditAddChild.getText().toString();
        }

        if (EditAddAdult.getText().toString().equals("")){
            TempAddAdult = "0";
        }else {
            TempAddAdult = EditAddAdult.getText().toString();
        }
    }

    private void SaveReservation(){
        get_ValidateSpecialQuota();

        postEditVisitorToVar();

        final CharSequence[] items = {
                "Show Report", "Save Data Booking"
        };


        if (isValidDate(ModifReservation.this) &&
                CheckingText() &&
                ValidateQuotaChild() &&
                ValidateQuotaAdult() &&
                CheckingSpecialQuotaChild &&
                CheckingSpecialQuotaAdult) {

            sumAllAmountVisitor(EditTodd, EditAddTodd, EditChild, EditAddChild, EditAdult, EditAddAdult);

            SumAll();

            AlertDialog.Builder builder = new AlertDialog.Builder(ModifReservation.this);
            builder.setTitle("");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                    if (item < 1) {
                        AllAmountGetPaket = (Integer.valueOf(TempAdult) - Integer.valueOf(COMPLIMENT5))+ Integer.valueOf(TempChild)+Integer.valueOf(TempTodd);
                        DialogCombine();
                    } else {
                        AmountTodd = TempTodd;
                        AmountChild = TempChild;
                        AmountAdult = TempAdult;
                        ADD_T5 = TempAddTodd;
                        ADD_C5 = TempAddChild;
                        ADD_A5 = TempAddAdult;
                        saveReservation();
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void DialogCombine(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(ModifReservation.this);
        View mView = getLayoutInflater().inflate(R.layout.confirm_reservation, null);
        final RadioButton BreakDown = (RadioButton) mView.findViewById(R.id.BreakDown);
        final RadioButton Combine = (RadioButton) mView.findViewById(R.id.Combine);
        final RadioGroup RGCombine = (RadioGroup) mView.findViewById(R.id.RGCombine);
        final RadioButton TickLunch = (RadioButton) mView.findViewById(R.id.TickLunch);
        final RadioButton TickLunchSouv = (RadioButton) mView.findViewById(R.id.TickLunchSouv);
        final RadioButton TickSouv = (RadioButton) mView.findViewById(R.id.TickSouv);
        final TextView AmountTickLunch = (TextView) mView.findViewById(R.id.AmountTickLunch);
        AmountTickLunch.setText("Toddler : "+TempTodd+", Child : "+TempChild+", Adult : "+TempAdult);
        final TextView AmountTickLunchSouv = (TextView) mView.findViewById(R.id.AmountTickLunchSouv);
        AmountTickLunchSouv.setText("Toddler : "+TempTodd+", Child : "+TempChild+", Adult : "+TempAdult);
        final TextView AmountTickSouv = (TextView) mView.findViewById(R.id.AmountTickSouv);
        AmountTickSouv.setText("Toddler : "+TempTodd+", Child : "+TempChild+", Adult : "+TempAdult);
        BreakDown.setChecked(true);
        BreakDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BreakDown.isChecked()){
                    RGCombine.setVisibility(View.GONE);
                }else {
                    RGCombine.setVisibility(View.VISIBLE);
                    TickLunch.setChecked(false);
                    TickLunchSouv.setChecked(false);
                    TickSouv.setChecked(false);
                }
            }
        });

        Combine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BreakDown.isChecked()){
                    RGCombine.setVisibility(View.GONE);
                    AmountTickLunch.setVisibility(View.GONE);
                    AmountTickLunchSouv.setVisibility(View.GONE);
                    AmountTickSouv.setVisibility(View.GONE);
                }else {
                    RGCombine.setVisibility(View.VISIBLE);
                    TickLunch.setChecked(false);
                    TickLunchSouv.setChecked(false);
                    TickSouv.setChecked(false);
                }
            }
        });

        TickLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountTickLunch.setVisibility(View.VISIBLE);
                AmountTickLunchSouv.setVisibility(View.GONE);
                AmountTickSouv.setVisibility(View.GONE);
            }
        });

        TickLunchSouv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountTickLunch.setVisibility(View.GONE);
                AmountTickLunchSouv.setVisibility(View.VISIBLE);
                AmountTickSouv.setVisibility(View.GONE);
            }
        });

        TickSouv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountTickLunch.setVisibility(View.GONE);
                AmountTickLunchSouv.setVisibility(View.GONE);
                AmountTickSouv.setVisibility(View.VISIBLE);
            }
        });

        sayWindows.setPositiveButton("ok", null);
        sayWindows.setNegativeButton("cancel", null);

        sayWindows.setView(mView);

        final AlertDialog mAlertDialog = sayWindows.create();
        mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|SOFT_INPUT_ADJUST_PAN);
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        int sumSouvenir = quantityPackage("RESERV_PSOUV");
                        int sumLunch = quantityPackage("RESERV_PLUNCH");

                        if(BreakDown.isChecked()){
                            isCombineAll = false;
                            isCombineSouvenir = false;
                            isCombineLunch = false;
                            Intent intent = new Intent(ModifReservation.this, ViewReport.class);
                            startActivity(intent);
                            mAlertDialog.dismiss();
                        }

                        if (TickLunch.isChecked()){
                            if (AllAmountGetPaket > sumLunch){
                                SingleDialodWithOutVoid(ModifReservation.this,
                                        getString(R.string.message_dialog_warning),
                                        "Lunch packages minimum quantity must be "+String.valueOf(AllAmountGetPaket));
                            }else{
                                isCombineAll = false;
                                isCombineSouvenir = false;
                                isCombineLunch = true;
                                Intent intent = new Intent(ModifReservation.this, ViewReport.class);
                                startActivity(intent);
                                mAlertDialog.dismiss();
                            }
                        }else
                        if (TickSouv.isChecked()){
                            if (AllAmountGetPaket > sumSouvenir){
                                SingleDialodWithOutVoid(ModifReservation.this,
                                        getString(R.string.message_dialog_warning),
                                        "Souvenir packages minimum quantity must be "+String.valueOf(AllAmountGetPaket));
                            }else{
                                isCombineAll = false;
                                isCombineSouvenir = true;
                                isCombineLunch = false;
                                Intent intent = new Intent(ModifReservation.this, ViewReport.class);
                                startActivity(intent);
                                mAlertDialog.dismiss();
                            }
                        }else
                        if (TickLunchSouv.isChecked()){
                            if (AllAmountGetPaket > sumLunch){
                                SingleDialodWithOutVoid(ModifReservation.this,
                                        getString(R.string.message_dialog_warning),
                                        "Lunch packages minimum quantity must be "+String.valueOf(AllAmountGetPaket));
                            }else
                            if (AllAmountGetPaket > sumSouvenir){
                                SingleDialodWithOutVoid(ModifReservation.this,
                                        getString(R.string.message_dialog_warning),
                                        "Souvenir packages minimum quantity must be "+String.valueOf(AllAmountGetPaket));
                            }else{
                                isCombineAll = true;
                                isCombineSouvenir = false;
                                isCombineLunch = false;
                                Intent intent = new Intent(ModifReservation.this, ViewReport.class);
                                startActivity(intent);
                                mAlertDialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private int quantityPackage(String TAG_PACK){
        int total = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT QUANTITY FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = '"+TAG_PACK+"' LIMIT 1";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            total = cursor.getInt(0);
        }
        return total;
    }

    private void AssignParamReservation(){
        clearAPIParams();
        APIParameters.add("U_LOGIN");
        APIParameters.add("TURNO");
        APIParameters.add("USUARIO_ALTA");
        APIParameters.add("ID_NUM_ESC");
        APIParameters.add("ID_RESP_AGE");
        APIParameters.add("ID_RESP_ESC");
        APIParameters.add("ID_PAQ");
        APIParameters.add("SumAmountTodd");
        APIParameters.add("SumAmountChild");
        APIParameters.add("SumAmountAdult");
        APIParameters.add("INSEN");
        APIParameters.add("SENIOR");
        APIParameters.add("HANDICAP");
        APIParameters.add("ARR_TIME");
        APIParameters.add("TOTAL_APAGAR");
        APIParameters.add("STATUS_RESERV");
        APIParameters.add("ADD_T5");
        APIParameters.add("ADD_C5");
        APIParameters.add("ADD_A5");
        APIParameters.add("COMPLIMENT5");
        APIParameters.add("HORA_SALIDA");
        APIParameters.add("PROMOTOR_CODE");
        APIParameters.add("IDPACK_ADD");
        APIParameters.add("ID_NUM_RESER");
        APIParameters.add("REASON");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(U_LOGIN);
        APIValueParams.add(String.valueOf(TURNO));
        APIValueParams.add(USUARIO_ALTA);
        APIValueParams.add(ID_NUM_ESC_RESERVATION);
        APIValueParams.add(ID_RESP_AGE);
        APIValueParams.add(ID_RESP_ESC);
        APIValueParams.add(ID_PAQ);
        APIValueParams.add(SumAmountTodd);
        APIValueParams.add(SumAmountChild);
        APIValueParams.add(SumAmountAdult);
        APIValueParams.add(INSEN);
        APIValueParams.add(SENIOR);
        APIValueParams.add(HANDICAP);
        APIValueParams.add(ARR_TIME);
        APIValueParams.add(TOTAL_APAGAR);
        APIValueParams.add(STATUS_RESERV);
        APIValueParams.add(ADD_T5);
        APIValueParams.add(ADD_C5);
        APIValueParams.add(ADD_A5);
        APIValueParams.add(COMPLIMENT5);
        APIValueParams.add(HORA_SALIDA);
        APIValueParams.add(PROMOTOR_CODE);
        APIValueParams.add(IDPACK_ADD);
        APIValueParams.add(ID_NUM_RESER);
        APIValueParams.add(Reason);
    }

    private void saveReservation(){
        CompareBeforeAfter();
        AssignParamReservation();
        AssignValueParam();
        MultiParamGetDataJSON dataReservation = new MultiParamGetDataJSON();
        dataReservation.init(APIValueParams, APIParameters, URL_SEND_RESERVATION, ModifReservation.this, json_insert_reservation, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_insert_reservation = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    getPackageReserv.excuteDeleteAndInsert();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void ShowDialog(String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(ModifReservation.this);
        View mView = LayoutInflater.from(ModifReservation.this).inflate(R.layout.popup_message, null);
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

    private void setBefore(){
        if (isFromList) {
            ToddBefore = BEBE;
            ChildBefore = NINO;
            AdultBefore = ADULTO;
            Com5Before = COMPLIMENT5;
            Com7Before = COMPLIMENT7;
            TickBefore = STR_ID_PAQ;
            PromBefore = STR_ID_RESP_ESC;
            isFromList = false;
        }
    }



    private void CompareBeforeAfter(){
        Reason = "Modif. ";
        String strTod = "",
                strChild = "",
                strAdult = "" ,
                strCom5 = "",
                strCom7 = "",
                strProm = "",
                strTick = "",
                strLunch = "",
                strSouv = "",
                strBus = "";

        if (!ToddBefore.equals(AmountTodd)){
            strTod = " |T: "+ToddBefore+" -> "+AmountTodd;
        }
        if (!ChildBefore.equals(AmountChild)){
            strChild = " |C: "+ChildBefore+" -> "+AmountChild;
        }
        if (!AdultBefore.equals(AmountAdult)){
            strAdult = " |A: "+AdultBefore+" -> "+AmountAdult;
        }
        if (!Com5Before.equals(COMPLIMENT5)){
            strCom5 = " |Cmp5: "+Com5Before+" -> "+COMPLIMENT5;
        }
        if (!Com7Before.equals(COMPLIMENT7)){
            strCom7 = " |Cmp7: "+Com7Before+" -> "+COMPLIMENT7;
        }
        if (!TickBefore.equals(STR_ID_PAQ)){
            strProm = " |Tick: "+TickBefore+" -> "+STR_ID_PAQ;
        }
        if (!PromBefore.equals(STR_ID_RESP_ESC)){
            strProm = " |Prom: "+PromBefore+" -> "+STR_ID_RESP_ESC;
        }
        if (!LunchBefore.equals(GetDataPackBefore("RESERV_PLUNCH"))){
            strLunch = " |L: "+LunchBefore+" -> "+GetDataPackBefore("RESERV_PLUNCH");
        }
        if (!SouvernirBefore.equals(GetDataPackBefore("RESERV_PSOUV"))){
            strSouv = " |S: "+SouvernirBefore+" -> "+GetDataPackBefore("RESERV_PSOUV");
        }
        if (!BusBefore.equals(GetDataPackBefore("RESERV_PBUS"))){
            strBus = " |B: "+BusBefore+" -> "+GetDataPackBefore("RESERV_PBUS");
        }
        Reason = Reason + strTod + strChild + strAdult + strCom5 + strCom7 + strProm + strTick + strLunch + strSouv + strBus;
    }

    private String GetDataPackBefore(String status){
        String NamePackage = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT PNAME FROM TRANSPACKAGE WHERE ID_NUM_RESER = "+ID_NUM_RESER+" AND STATUS='"+status+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            NamePackage = cursor.getString(cursor.getColumnIndex("PNAME"));
        }
        return NamePackage;
    }
}
