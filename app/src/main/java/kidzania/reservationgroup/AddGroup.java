package kidzania.reservationgroup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.util.Arrays;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.DateAndTimeNow;
import static kidzania.reservationgroup.Misc.FuncGlobal.DefaultVarGroup;
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.hideKeyboard;
import static kidzania.reservationgroup.Misc.VarGlobal.ADDR;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_A;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_C;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_FILTRP;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_T;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.AREA;
import static kidzania.reservationgroup.Misc.VarGlobal.BGT_TRIP;
import static kidzania.reservationgroup.Misc.VarGlobal.CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.DISTRICT;
import static kidzania.reservationgroup.Misc.VarGlobal.EMAIL;
import static kidzania.reservationgroup.Misc.VarGlobal.FAX;
import static kidzania.reservationgroup.Misc.VarGlobal.FECHA_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.FILTRP;
import static kidzania.reservationgroup.Misc.VarGlobal.GRADE;
import static kidzania.reservationgroup.Misc.VarGlobal.GRADE_TYP;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPRSVMOD;
import static kidzania.reservationgroup.Misc.VarGlobal.GTYPE;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.IDUSR_OWN;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.IS_EDIT;
import static kidzania.reservationgroup.Misc.VarGlobal.IS_PERSONAL;
import static kidzania.reservationgroup.Misc.VarGlobal.NO_HP;
import static kidzania.reservationgroup.Misc.VarGlobal.PHONE;
import static kidzania.reservationgroup.Misc.VarGlobal.PIC;
import static kidzania.reservationgroup.Misc.VarGlobal.PIC_ID;
import static kidzania.reservationgroup.Misc.VarGlobal.PLC_TRIP;
import static kidzania.reservationgroup.Misc.VarGlobal.PRINCIPAL;
import static kidzania.reservationgroup.Misc.VarGlobal.PRINC_HP;
import static kidzania.reservationgroup.Misc.VarGlobal.PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.SCH_TYPE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.ZIPCODE;
import static kidzania.reservationgroup.Misc.VarGlobal.dbHelper;
import static kidzania.reservationgroup.Misc.VarUrl.URL_EDIT_DATA_GROUP;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_DATA_GROUP;

public class AddGroup extends AppCompatActivity {

    EditText edtGroupName, edtGrade, edtGroupCatagory, edtAddress, edtDistrikArea,
            edtDistrik, edtCity, edtProvince, edtZipCode, edtPhone,
            edtFax, edtEmail, edtPrincipal, edtNoHpPrincipal, edtPIC,
            edtNoHpPIC, edtToddler, edtChild, edtAdult, edtTripBudget,
            edtTripFreq, edtTripPlace, edtFieldTrip;

     public EditText edtGroupType;

    TextInputLayout layGroupName, layGrade, layGroupType, layGroupCatagory, layAddress, layDistrikArea,
            layDistrik, layCity, layProvince, layZipCode, layPhone,
            layFax, layEmail, layPrincipal, layNoHpPrincipal, layPIC,
            layNoHpPIC, layToddler, layChild, layAdult, layTripBudget,
            layTripFreq, layTripPlace, layFieldTrip;

    MenuItem saveGroup;

    String[] ArrayGrade = new String[]{
            "TK",
            "SD",
            "SMP",
            "SMU"
    };

    String[] ArrayCatagory = new String[]{
            "Atas",
            "Menengah",
            "Bawah"
    };
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DataSQLlite(AddGroup.this);
        initialization();
        setDefaultText();
        CheckConnection(this);
        hideKeyboard(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextFromSearch();
    }

    @Override
    public void onPause(){
        super.onPause();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_save_group, menu);
        saveGroup = menu.findItem(R.id.btn_save_group);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_save_group:
                SaveDataGroup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization() {
        layGroupName = (TextInputLayout) findViewById(R.id.layGroupName);
        layGrade = (TextInputLayout) findViewById(R.id.layGrade);
        layGroupType = (TextInputLayout) findViewById(R.id.layGroupType);
        layGroupCatagory = (TextInputLayout) findViewById(R.id.layGroupCatagory);
        layAddress = (TextInputLayout) findViewById(R.id.layAddress);
        layDistrikArea = (TextInputLayout) findViewById(R.id.layDistrikArea);
        layDistrik = (TextInputLayout) findViewById(R.id.layDistrik);
        layCity = (TextInputLayout) findViewById(R.id.layCity);
        layProvince = (TextInputLayout) findViewById(R.id.layProvince);
        layZipCode = (TextInputLayout) findViewById(R.id.layZipCode);
        layPhone = (TextInputLayout) findViewById(R.id.layPhone);
        layFax = (TextInputLayout) findViewById(R.id.layFax);
        layEmail = (TextInputLayout) findViewById(R.id.layEmail);
        layPrincipal = (TextInputLayout) findViewById(R.id.layPrincipal);
        layNoHpPrincipal = (TextInputLayout) findViewById(R.id.layNoHpPrincipal);
        layPIC = (TextInputLayout) findViewById(R.id.layPIC);
        layNoHpPIC = (TextInputLayout) findViewById(R.id.layNoHpPIC);
        layToddler = (TextInputLayout) findViewById(R.id.layToddler);
        layChild = (TextInputLayout) findViewById(R.id.layChild);
        layAdult = (TextInputLayout) findViewById(R.id.layAdult);
        layTripBudget = (TextInputLayout) findViewById(R.id.layTripBudget);
        layTripFreq = (TextInputLayout) findViewById(R.id.layTripFreq);
        layTripPlace = (TextInputLayout) findViewById(R.id.layTripPlace);
        layFieldTrip = (TextInputLayout) findViewById(R.id.layFieldTrip);

        edtGroupName = (EditText) findViewById(R.id.edtGroupName);
        edtGrade = (EditText) findViewById(R.id.edtGrade);
        edtGroupType = (EditText) findViewById(R.id.edtGroupType);
        edtGroupCatagory = (EditText) findViewById(R.id.edtGroupCatagory);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtDistrikArea = (EditText) findViewById(R.id.edtDistrikArea);
        edtDistrik = (EditText) findViewById(R.id.edtDistrik);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtProvince = (EditText) findViewById(R.id.edtProvince);
        edtZipCode = (EditText) findViewById(R.id.edtZipCode);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtFax = (EditText) findViewById(R.id.edtFax);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPrincipal = (EditText) findViewById(R.id.edtPrincipal);
        edtNoHpPrincipal = (EditText) findViewById(R.id.edtNoHpPrincipal);
        edtPIC = (EditText) findViewById(R.id.edtPIC);
        edtNoHpPIC = (EditText) findViewById(R.id.edtNoHpPIC);
        edtToddler = (EditText) findViewById(R.id.edtToddler);
        edtChild = (EditText) findViewById(R.id.edtChild);
        edtAdult = (EditText) findViewById(R.id.edtAdult);
        edtTripBudget = (EditText) findViewById(R.id.edtTripBudget);
        edtTripFreq = (EditText) findViewById(R.id.edtTripFreq);
        edtTripPlace = (EditText) findViewById(R.id.edtTripPlace);
        edtFieldTrip = (EditText) findViewById(R.id.edtFieldTrip);

        setViewGTYPE();
        setOnClick();

    }

    private void setDefaultText(){
        if (!IS_EDIT && !IS_PERSONAL){
            DefaultVarGroup();
        }
        IS_PERSONAL = false;
    }

    private void setTextFromSearch(){
        if (SENDER_CLASS.equals("SearchGroupType")) {
            edtGroupType.setText(GRADE_TYP);
        }
        if (SENDER_CLASS.equals("SearchProvince")) {
            edtProvince.setText(PROVINCE);
            edtCity.setText("");
            edtDistrik.setText("");
            edtDistrikArea.setText("");
        }
        if (SENDER_CLASS.equals("SearchCity")) {
            edtCity.setText(CITY);
            edtDistrik.setText("");
            edtDistrikArea.setText("");
        }
        if (SENDER_CLASS.equals("SearchDistrik")) {
            edtDistrik.setText(DISTRICT);
            edtDistrikArea.setText("");
        }
        if (SENDER_CLASS.equals("SearchDistrikArea")) {
            edtDistrikArea.setText(AREA);
            edtZipCode.setText(ZIPCODE);
        }
        if (SENDER_CLASS.equals("GroupDraft")) {
            setTextFromList();
        }

    }

    private void setTextFromList(){
        edtGroupName.setText(GRPNAME); //Nama Sekolah/Group
        edtGrade.setText(GRADE); //Grade Sekolah/Group
        edtGroupType.setText(GRADE_TYP); //Grade Type Sekolah/Group
        edtAddress.setText(ADDR); //Alamat Sekolah/Group
        edtProvince.setText(PROVINCE); // Provinci
        edtCity.setText(CITY); // Kota/Kabupaten.
        edtDistrik.setText(DISTRICT); //Kecamatan
        edtDistrikArea.setText(AREA); //Kelurahan
        edtZipCode.setText(ZIPCODE); // Kodepos
        edtPhone.setText(PHONE); // telepon sekolah
        edtFax.setText(FAX); //Fax sekolah
        edtEmail.setText(EMAIL); //email sekolah
        edtPrincipal.setText(PRINCIPAL); // kepala sekolah/group
        edtNoHpPrincipal.setText(PRINC_HP); // no handphone kepala sekolah
        edtPIC.setText(PIC); // PIC
        edtNoHpPIC.setText(NO_HP); // no handphone PIC
        edtToddler.setText(AMNT_T); // jumlah anak dibawah 5 tahun
        edtChild.setText(AMNT_C); // jumlah anak anak
        edtAdult.setText(AMNT_A); // jumlah guru/dewasa.
        edtTripBudget.setText(BGT_TRIP); //Bugdet trip
        edtTripFreq.setText(AMNT_FILTRP); // frekuensi trip
        edtFieldTrip.setText(FILTRP); // field trip/jadwal wisata
        edtTripPlace.setText(PLC_TRIP); // place trip/tempat wisata
    }

    private void ViewCompany(){
        layGroupName.setVisibility(View.VISIBLE);
        layAddress.setVisibility(View.VISIBLE);
        layProvince.setVisibility(View.VISIBLE);
        layCity.setVisibility(View.VISIBLE);
        layDistrik.setVisibility(View.VISIBLE);
        layDistrikArea.setVisibility(View.VISIBLE);
        layPhone.setVisibility(View.VISIBLE);
        layFax.setVisibility(View.VISIBLE);
        layZipCode.setVisibility(View.VISIBLE);
        layEmail.setVisibility(View.VISIBLE);
        layPIC.setVisibility(View.VISIBLE);
        layNoHpPIC.setVisibility(View.VISIBLE);
    }

    private void ViewCourseAndFoundation(){
        layGroupName.setVisibility(View.VISIBLE);
        layAddress.setVisibility(View.VISIBLE);
        layProvince.setVisibility(View.VISIBLE);
        layCity.setVisibility(View.VISIBLE);
        layDistrik.setVisibility(View.VISIBLE);
        layDistrikArea.setVisibility(View.VISIBLE);
        layPhone.setVisibility(View.VISIBLE);
        layFax.setVisibility(View.VISIBLE);
        layZipCode.setVisibility(View.VISIBLE);
        layEmail.setVisibility(View.VISIBLE);
        layPrincipal.setVisibility(View.VISIBLE);
        layNoHpPrincipal.setVisibility(View.VISIBLE);
        layPIC.setVisibility(View.VISIBLE);
        layNoHpPIC.setVisibility(View.VISIBLE);
        layToddler.setVisibility(View.VISIBLE);
        layChild.setVisibility(View.VISIBLE);
        layAdult.setVisibility(View.VISIBLE);
        layTripBudget.setVisibility(View.VISIBLE);
        layTripFreq.setVisibility(View.VISIBLE);
        layFieldTrip.setVisibility(View.VISIBLE);
        layTripPlace.setVisibility(View.VISIBLE);
    }

    private void ViewSchool(){
        layGroupName.setVisibility(View.VISIBLE);
        layGrade.setVisibility(View.VISIBLE);
        layGroupType.setVisibility(View.VISIBLE);
        layGroupCatagory.setVisibility(View.VISIBLE);
        layAddress.setVisibility(View.VISIBLE);
        layProvince.setVisibility(View.VISIBLE);
        layCity.setVisibility(View.VISIBLE);
        layDistrik.setVisibility(View.VISIBLE);
        layDistrikArea.setVisibility(View.VISIBLE);
        layPhone.setVisibility(View.VISIBLE);
        layFax.setVisibility(View.VISIBLE);
        layZipCode.setVisibility(View.VISIBLE);
        layEmail.setVisibility(View.VISIBLE);
        layPrincipal.setVisibility(View.VISIBLE);
        layNoHpPrincipal.setVisibility(View.VISIBLE);
        layPIC.setVisibility(View.VISIBLE);
        layNoHpPIC.setVisibility(View.VISIBLE);
        layToddler.setVisibility(View.VISIBLE);
        layChild.setVisibility(View.VISIBLE);
        layAdult.setVisibility(View.VISIBLE);
        layTripBudget.setVisibility(View.VISIBLE);
        layTripFreq.setVisibility(View.VISIBLE);
        layFieldTrip.setVisibility(View.VISIBLE);
        layTripPlace.setVisibility(View.VISIBLE);
    }

    private void ViewPersonal(){
        layGroupName.setVisibility(View.VISIBLE);
        layAddress.setVisibility(View.VISIBLE);
        layProvince.setVisibility(View.VISIBLE);
        layCity.setVisibility(View.VISIBLE);
        layDistrik.setVisibility(View.VISIBLE);
        layDistrikArea.setVisibility(View.VISIBLE);
        layFax.setVisibility(View.VISIBLE);
        layZipCode.setVisibility(View.VISIBLE);
        layEmail.setVisibility(View.VISIBLE);
        layPIC.setVisibility(View.VISIBLE);
        layNoHpPIC.setVisibility(View.VISIBLE);
    }

    private void setViewGTYPE(){
        switch (GTYPE) {
            case "School":
                ViewSchool();
                break;
            case "Company":
                ViewCompany();
                break;
            case "Course":
            case "Foundation":
                ViewCourseAndFoundation();
                break;
            case "Personal":
                ViewPersonal();
                break;
        }
    }

    private void setOnClick(){
        edtGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleChoice(AddGroup.this, edtGrade, getString(R.string.str_group_grade), ArrayGrade);
            }
        });

        edtGroupCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleChoice(AddGroup.this, edtGroupCatagory, getString(R.string.str_group_catagory), ArrayCatagory);
            }
        });

        edtGroupType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtGrade.getText().toString())) {
                    startActivity(new Intent(AddGroup.this, SearchGroupType.class));
                }else{
                    Toast.makeText(AddGroup.this, getString(R.string.message_invalid_group_grade), Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddGroup.this, SearchProvince.class));
            }
        });

        edtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtProvince.getText().toString())) {
                    startActivity(new Intent(AddGroup.this, SearchCity.class));
                }else{
                    Toast.makeText(AddGroup.this, getString(R.string.message_invalid_group_province), Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtDistrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtCity.getText().toString())) {
                    startActivity(new Intent(AddGroup.this, SearchDistrik.class));
                }else{
                    Toast.makeText(AddGroup.this, getString(R.string.message_invalid_group_city), Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtDistrikArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtDistrik.getText().toString())) {
                    startActivity(new Intent(AddGroup.this, SearchDistrikArea.class));
                }else{
                    Toast.makeText(AddGroup.this, getString(R.string.message_invalid_group_distrik), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean CheckingGroupName() {
        if (TextUtils.isEmpty(edtGroupName.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_name));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGrade() {
        if (TextUtils.isEmpty(edtGrade.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_grade));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupType() {
        if (TextUtils.isEmpty(edtGroupType.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_type));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupCatagory() {
        if (TextUtils.isEmpty(edtGroupCatagory.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_catagory));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupAddress() {
        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_address));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupProvince() {
        if (TextUtils.isEmpty(edtProvince.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_province));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupCity() {
        if (TextUtils.isEmpty(edtCity.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_city));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupDistrik() {
        if (TextUtils.isEmpty(edtDistrik.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_distrik));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupDistrikArea() {
        if (TextUtils.isEmpty(edtDistrikArea.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_distrik_area));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupZipCode() {
        if (TextUtils.isEmpty(edtZipCode.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_zip_code));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupPhone() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_phone));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupPrincipal() {
        if (TextUtils.isEmpty(edtPrincipal.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_principal));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupPIC() {
        if (TextUtils.isEmpty(edtPIC.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_PIC));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupNoPIC() {
        if (TextUtils.isEmpty(edtNoHpPIC.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_HP_PIC));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupChild() {
        if (TextUtils.isEmpty(edtChild.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_child));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupAdult() {
        if (TextUtils.isEmpty(edtAdult.getText().toString())) {
            SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_adult));
            return false;
        } else {
            return true;
        }
    }

    private boolean isValid(){
        boolean isValid = true;
        if (GTYPE.equals("School")) {
            if ((!CheckingGroupName()) || (!CheckingGrade()) || (!CheckingGroupType()) || (!CheckingGroupCatagory()) || (!CheckingGroupAddress()) || (!CheckingGroupDistrikArea()) ||
                    (!CheckingGroupDistrik()) || (!CheckingGroupCity()) || (!CheckingGroupProvince()) ||
                    (!CheckingGroupZipCode()) || (!CheckingGroupPhone()) || (!CheckingGroupPrincipal()) || (!CheckingGroupPIC()) || (!CheckingGroupNoPIC()) ||
                    (!CheckingGroupChild()) || (!CheckingGroupAdult())) {
                isValid = false;
            }
        }else
        if (GTYPE.equals("Company")) {
            if ((!CheckingGroupName()) || (!CheckingGroupAddress()) || (!CheckingGroupDistrikArea()) ||
                    (!CheckingGroupDistrik()) || (!CheckingGroupCity()) || (!CheckingGroupProvince()) ||
                    (!CheckingGroupZipCode()) || (!CheckingGroupPhone()) || (!CheckingGroupPIC()) || (!CheckingGroupNoPIC())) {
                isValid = false;
            }
        }else
        if (GTYPE.equals("Course") || GTYPE.equals("Foundation")) {
            if ((!CheckingGroupName()) || (!CheckingGroupAddress()) || (!CheckingGroupDistrikArea()) ||
                    (!CheckingGroupDistrik()) || (!CheckingGroupCity()) || (!CheckingGroupProvince()) ||
                    (!CheckingGroupZipCode()) || (!CheckingGroupPhone()) || (!CheckingGroupPrincipal()) || (!CheckingGroupPIC()) || (!CheckingGroupNoPIC()) ||
                    (!CheckingGroupChild()) || (!CheckingGroupAdult())) {
                isValid = false;
            }
        }
        return isValid;
    }

    private void SaveDataGroup(){
        if (isValid()){
            if (!IS_EDIT) {
                sendDataGroup();
            }else{
                editDataGroup();
            }
        }
    }

    private void SingleChoice(Context context, final EditText editText, String Tag_Name, final String[] ArrayList){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Tag_Name);
        builder.setSingleChoiceItems(ArrayList, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedItem = Arrays.asList(ArrayList).get(i);
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
                        clearTextHeaderChoice(editText);
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

    private void clearTextHeaderChoice(EditText editText){
        String idEditText = getResources().getResourceName(editText.getId());
        String idGrade = getResources().getResourceName(edtGrade.getId());
        String idCatagory = getResources().getResourceName(edtGroupCatagory.getId());

        if (idEditText.equals(idGrade)){
            edtGroupType.setText("");
            GRADE = editText.getText().toString();
        }
        if (idEditText.equals(idCatagory)) {
            SCH_TYPE = editText.getText().toString();
        }
    }

    private void textToVal(){
        FECHA_ALTA = DateAndTimeNow();
        GRPNAME = edtGroupName.getText().toString();
        GRADE = edtGrade.getText().toString();
        GRADE_TYP = edtGroupType.getText().toString();
        SCH_TYPE = edtGroupCatagory.getText().toString();
        ADDR = edtAddress.getText().toString();
        AREA = edtDistrikArea.getText().toString();
        DISTRICT = edtDistrik.getText().toString();
        CITY = edtCity.getText().toString();
        PROVINCE = edtProvince.getText().toString();
        PHONE = edtPhone.getText().toString();
        EMAIL = edtEmail.getText().toString();
        FAX = edtFax.getText().toString();
        ZIPCODE = edtZipCode.getText().toString();
        PRINCIPAL = edtPrincipal.getText().toString();
        PRINC_HP = edtNoHpPrincipal.getText().toString();
        PIC = edtPIC.getText().toString();
        NO_HP = edtNoHpPIC.getText().toString();
        AMNT_T = edtToddler.getText().toString();
        AMNT_C = edtChild.getText().toString();
        AMNT_A = edtAdult.getText().toString();
        IDUSR_OWN = ID_USER;
        AMNT_FILTRP = edtTripFreq.getText().toString();
        FILTRP = edtFieldTrip.getText().toString();
        BGT_TRIP = edtTripBudget.getText().toString();
        PLC_TRIP = edtTripPlace.getText().toString();
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add("fecha_alta");
        APIParameters.add("grpname");
        APIParameters.add("grade");
        APIParameters.add("grade_typ");
        APIParameters.add("catagory");
        APIParameters.add("addr");
        APIParameters.add("area");
        APIParameters.add("district");
        APIParameters.add("city");
        APIParameters.add("province");
        APIParameters.add("phone");
        APIParameters.add("email");
        APIParameters.add("fax");
        APIParameters.add("zipcode");
        APIParameters.add("principal");
        APIParameters.add("princ_hp");
        APIParameters.add("pic");
        APIParameters.add("no_hp");
        APIParameters.add("amnt_t");
        APIParameters.add("amnt_c");
        APIParameters.add("amnt_a");
        APIParameters.add("id_user");
        APIParameters.add("amnt_filtrp");
        APIParameters.add("filtrp");
        APIParameters.add("bgt_trip");
        APIParameters.add("plc_trip");
        APIParameters.add("idusr_own");
        APIParameters.add("grprsvmod");
        APIParameters.add("gtype");
        APIParameters.add("pic_id");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(FECHA_ALTA);
        APIValueParams.add(GRPNAME);
        APIValueParams.add(GRADE);
        APIValueParams.add(GRADE_TYP);
        APIValueParams.add(SCH_TYPE);
        APIValueParams.add(ADDR);
        APIValueParams.add(AREA);
        APIValueParams.add(DISTRICT);
        APIValueParams.add(CITY);
        APIValueParams.add(PROVINCE);
        APIValueParams.add(PHONE);
        APIValueParams.add(EMAIL);
        APIValueParams.add(FAX);
        APIValueParams.add(ZIPCODE);
        APIValueParams.add(PRINCIPAL);
        APIValueParams.add(PRINC_HP);
        APIValueParams.add(PIC);
        APIValueParams.add(NO_HP);
        APIValueParams.add(AMNT_T);
        APIValueParams.add(AMNT_C);
        APIValueParams.add(AMNT_A);
        APIValueParams.add(ID_USER);
        APIValueParams.add(AMNT_FILTRP);
        APIValueParams.add(FILTRP);
        APIValueParams.add(BGT_TRIP);
        APIValueParams.add(PLC_TRIP);
        APIValueParams.add(ID_USER);
        APIValueParams.add(GRPRSVMOD);
        APIValueParams.add(GTYPE);
        if (GTYPE.equals("Personal")) {
            APIValueParams.add(PIC_ID);
        }else{
            APIValueParams.add("0");
        }
    }

    private void sendDataGroup(){
        textToVal();
        AssignParam();
        AssignValueParam();
        MultiParamGetDataJSON SaveGroup = new MultiParamGetDataJSON();
        SaveGroup.init(APIValueParams, APIParameters, URL_SEND_DATA_GROUP, AddGroup.this, json_save_group, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_save_group = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_failed_saving_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void editDataGroup(){
        textToVal();
        AssignParam();
        AssignValueParam();
        APIParameters.add("id_num_esc");
        APIValueParams.add(ID_NUM_ESC);
        MultiParamGetDataJSON SaveGroup = new MultiParamGetDataJSON();
        SaveGroup.init(APIValueParams, APIParameters, URL_EDIT_DATA_GROUP, AddGroup.this, json_edit_group, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_edit_group = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    SingleDialodWithOutVoid(AddGroup.this, getString(R.string.message_dialog_warning), getString(R.string.message_failed_edit_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void ShowDialog(String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(AddGroup.this);
        View mView = LayoutInflater.from(AddGroup.this).inflate(R.layout.popup_message, null);
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
