package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_RESPONSIBLE;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_RESP_AGE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_AGE;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_RESPONSIBLE;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_DATA_RESPONSIBLE;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 05/01/2018.
 */

public class SearchResponsible extends ParentSearchList {

    String ResponPanggilan, ResponNama, ResponPekerjaan, ResponPhone;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_add_plus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_add_plus:
                saveResponsible();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageResponsible, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageResponsible);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageResponsible = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ID_RESP_AGE = intent.getStringExtra(ID_TEXT_SEARCH);
            STR_ID_RESP_AGE = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchResponsible";
            onBackPressed();
        }
    };

    @Override
    public void getSearch() {
        super.getSearch();

        APIParameters.add("ID_NUM_ESC");
        APIValueParams.add(ID_NUM_ESC_RESERVATION);

        HEADER_SEARCH = HEAD_GET_DATA_RESPONSIBLE;
        if (hasConnection(SearchResponsible.this)) {
            MultiParamGetDataJSON getDataPromotor = new MultiParamGetDataJSON();
            getDataPromotor.init(APIValueParams, APIParameters, URL_GET_DATA_RESPONSIBLE, SearchResponsible.this, json_search, false);
        } else {
            OpenLostConnection(this);
        }
    }

    private void saveResponsible(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(SearchResponsible.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_responsible, null);
        final EditText edtPanggilan = (EditText) mView.findViewById(R.id.edtPanggilan);
        final EditText NamaRespon = (EditText) mView.findViewById(R.id.NamaRespon);
        final EditText edtOccupation = (EditText) mView.findViewById(R.id.edtOccupation);
        final EditText edtPhoneAlias = (EditText) mView.findViewById(R.id.edtPhone);
        sayWindows.setPositiveButton(getString(R.string.btn_dialog_save), null);
        sayWindows.setNegativeButton(getString(R.string.btn_dialog_cancel), null);

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
                        ResponPanggilan = edtPanggilan.getText().toString().trim();
                        ResponNama =  ResponPanggilan.trim() +" "+ NamaRespon.getText().toString().trim();
                        ResponPekerjaan = edtOccupation.getText().toString();
                        ResponPhone = edtPhoneAlias.getText().toString();

                        if(TextUtils.isEmpty(ResponNama)) {
                            SingleDialodWithOutVoid(SearchResponsible.this,
                                    getString(R.string.message_dialog_warning),
                                    getString(R.string.message_warning_empty_name_responsible));
                        }else
                        if(TextUtils.isEmpty(ResponPhone)){
                            SingleDialodWithOutVoid(SearchResponsible.this,
                                    getString(R.string.message_dialog_warning),
                                    getString(R.string.message_invalid_group_phone));
                        }else {
                            mAlertDialog.dismiss();
                            excuteResponsible();
                        }
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private void AssignParamResponsible(){
        clearAPIParams();
        APIParameters.add("nama");
        APIParameters.add("occupation");
        APIParameters.add("phone");
        APIParameters.add("id_num_esc");
    }

    private void AssignValueParamResponsible(){
        clearAPIValueParam();
        APIValueParams.add(ResponNama);
        APIValueParams.add(ResponPekerjaan);
        APIValueParams.add(ResponPhone);
        APIValueParams.add(ID_NUM_ESC_RESERVATION);
    }

    private void excuteResponsible(){
        AssignParamResponsible();
        AssignValueParamResponsible();
        MultiParamGetDataJSON SaveResponsible = new MultiParamGetDataJSON();
        SaveResponsible.init(APIValueParams, APIParameters, URL_SEND_DATA_RESPONSIBLE, SearchResponsible.this, json_responsible, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_responsible = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    ShowDialog(getString(R.string.message_dialog_warning), getString(R.string.message_failed_saving_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


}
