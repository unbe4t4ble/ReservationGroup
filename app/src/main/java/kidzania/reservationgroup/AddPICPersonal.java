package kidzania.reservationgroup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getInformationUser;
import static kidzania.reservationgroup.Misc.FuncGlobal.hideKeyboard;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.AREA;
import static kidzania.reservationgroup.Misc.VarGlobal.CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.DISTRICT;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.ZIPCODE;
import static kidzania.reservationgroup.Misc.VarGlobal.dbHelper;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_DATA_PIC;

public class AddPICPersonal extends AppCompatActivity {

    EditText edtMr, edtNamePIC, edtAddress, edtDistrikArea,
            edtDistrik, edtCity, edtProvince, edtZipCode,
            edtPhone, edtFax, edtEmail, edtIDCard;

    MenuItem saveGroup;

    String[] ArrayMr = new String[]{
            "Mr",
            "Mrs"
    };

    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picpersonal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DataSQLlite(AddPICPersonal.this);
        initialization();
        CheckConnection(this);
        hideKeyboard(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformationUser();
        setTextFromSearch();
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
                SaveDataPIC();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization() {
        edtMr = (EditText) findViewById(R.id.edtMr);
        edtNamePIC = (EditText) findViewById(R.id.edtNamePIC);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtDistrikArea = (EditText) findViewById(R.id.edtDistrikArea);
        edtDistrik = (EditText) findViewById(R.id.edtDistrik);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtProvince = (EditText) findViewById(R.id.edtProvince);
        edtZipCode = (EditText) findViewById(R.id.edtZipCode);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtFax = (EditText) findViewById(R.id.edtFax);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtIDCard = (EditText) findViewById(R.id.edtIDCard);

        setOnClick();
    }

    private void setTextFromSearch(){
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
    }

    private void setOnClick(){

        edtMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleChoice(AddPICPersonal.this, edtMr, ArrayMr);
            }
        });

        edtProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPICPersonal.this, SearchProvince.class));
            }
        });

        edtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtProvince.getText().toString())) {
                    startActivity(new Intent(AddPICPersonal.this, SearchCity.class));
                }else{
                    Toast.makeText(AddPICPersonal.this, getString(R.string.message_invalid_group_province), Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtDistrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtCity.getText().toString())) {
                    startActivity(new Intent(AddPICPersonal.this, SearchDistrik.class));
                }else{
                    Toast.makeText(AddPICPersonal.this, getString(R.string.message_invalid_group_city), Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtDistrikArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtDistrik.getText().toString())) {
                    startActivity(new Intent(AddPICPersonal.this, SearchDistrikArea.class));
                }else{
                    Toast.makeText(AddPICPersonal.this, getString(R.string.message_invalid_group_distrik), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SingleChoice(Context context, final EditText editText, final String[] ArrayList){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(ArrayList, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedItem = Arrays.asList(ArrayList).get(i);
                    }
                });
        //}
        builder.setPositiveButton(getString(R.string.btn_ok_message), null);
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
            }
        });
        alertDialog.show();
    }

    private boolean CheckingNamePIC() {
        if (TextUtils.isEmpty(edtNamePIC.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_pic_name));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingAddressPIC() {
        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_address));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingPhonePIC() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_phone));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupProvince() {
        if (TextUtils.isEmpty(edtProvince.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_province));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupCity() {
        if (TextUtils.isEmpty(edtCity.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_city));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupDistrik() {
        if (TextUtils.isEmpty(edtDistrik.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_distrik));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupDistrikArea() {
        if (TextUtils.isEmpty(edtDistrikArea.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_distrik_area));
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckingGroupZipCode() {
        if (TextUtils.isEmpty(edtZipCode.getText().toString())) {
            SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_invalid_group_zip_code));
            return false;
        } else {
            return true;
        }
    }

    private boolean isValid(){
        return !((!CheckingNamePIC()) || (!CheckingPhonePIC()) || (!CheckingAddressPIC()) ||
                (!CheckingGroupProvince()) || (!CheckingGroupCity()) || (!CheckingGroupDistrik()) ||
                (!CheckingGroupDistrikArea()) || (!CheckingGroupZipCode()));
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add("namePIC");
        APIParameters.add("phone");
        APIParameters.add("fax");
        APIParameters.add("email");
        APIParameters.add("addr");
        APIParameters.add("province");
        APIParameters.add("city");
        APIParameters.add("district");
        APIParameters.add("area");
        APIParameters.add("zipcode");
        APIParameters.add("idCard");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(edtMr.getText().toString()+" "+edtNamePIC.getText().toString());
        APIValueParams.add(edtPhone.getText().toString());
        APIValueParams.add(edtFax.getText().toString());
        APIValueParams.add(edtEmail.getText().toString());
        APIValueParams.add(edtAddress.getText().toString());
        APIValueParams.add(edtProvince.getText().toString());
        APIValueParams.add(edtCity.getText().toString());
        APIValueParams.add(edtDistrik.getText().toString());
        APIValueParams.add(edtDistrikArea.getText().toString());
        APIValueParams.add(edtZipCode.getText().toString());
        APIValueParams.add(edtIDCard.getText().toString());
    }

    private void SaveDataPIC(){
        if (isValid()){
            AssignParam();
            AssignValueParam();
            MultiParamGetDataJSON SaveGroup = new MultiParamGetDataJSON();
            SaveGroup.init(APIValueParams, APIParameters, URL_SEND_DATA_PIC, AddPICPersonal.this, json_save_pic, true);
        }
    }

    MultiParamGetDataJSON.JSONObjectResult json_save_pic = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    SingleDialodWithOutVoid(AddPICPersonal.this, getString(R.string.message_dialog_warning), getString(R.string.message_failed_saving_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void ShowDialog(String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(AddPICPersonal.this);
        View mView = LayoutInflater.from(AddPICPersonal.this).inflate(R.layout.popup_message, null);
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
