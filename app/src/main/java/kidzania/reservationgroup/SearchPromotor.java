package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
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
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_PROMOTOR;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_PROMOTOR;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_DATA_PROMOTOR;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 05/01/2018.
 */

public class SearchPromotor extends ParentSearchList {

    String PromNama, PromAlias;

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
                savePromotor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessagePromotor, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessagePromotor);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessagePromotor = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ID_RESP_ESC = intent.getStringExtra(ID_TEXT_SEARCH);
            STR_ID_RESP_ESC = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchPromotor";
            onBackPressed();
        }
    };

    @Override
    public void getSearch() {
        super.getSearch();

        APIParameters.add("ID_NUM_ESC");
        APIValueParams.add(ID_NUM_ESC_RESERVATION);

        HEADER_SEARCH = HEAD_GET_DATA_PROMOTOR;
        if (hasConnection(SearchPromotor.this)) {
            MultiParamGetDataJSON getDataPromotor = new MultiParamGetDataJSON();
            getDataPromotor.init(APIValueParams, APIParameters, URL_GET_DATA_PROMOTOR, SearchPromotor.this, json_search, false);
        } else {
            OpenLostConnection(this);
        }
    }

    private void savePromotor(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(SearchPromotor.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_promotor, null);
        final EditText NamaPromotor = (EditText) mView.findViewById(R.id.NamaPromotor);
        final EditText AliasPromotor = (EditText) mView.findViewById(R.id.AliasPromotor);
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
                        PromNama = NamaPromotor.getText().toString().trim();
                        PromAlias = AliasPromotor.getText().toString().trim();

                        if(PromNama.isEmpty()){
                            SingleDialodWithOutVoid(SearchPromotor.this,
                                    getString(R.string.message_dialog_warning),
                                    getString(R.string.message_warning_empty_name_responsible));
                        }else
                        if(PromAlias.isEmpty()){
                            SingleDialodWithOutVoid(SearchPromotor.this,
                                    getString(R.string.message_dialog_warning),
                                    getString(R.string.message_warning_empty_name_responsible));
                        }else {
                            mAlertDialog.dismiss();
                            excutePromotor();
                        }

                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private void AssignParamPromotor(){
        clearAPIParams();
        APIParameters.add("nama");
        APIParameters.add("alias");
        APIParameters.add("id_num_esc");
    }

    private void AssignValueParamPromotor(){
        clearAPIValueParam();
        APIValueParams.add(PromNama);
        APIValueParams.add(PromAlias);
        APIValueParams.add(ID_NUM_ESC_RESERVATION);
    }

    private void excutePromotor(){
        AssignParamPromotor();
        AssignValueParamPromotor();
        MultiParamGetDataJSON SavePromotor = new MultiParamGetDataJSON();
        SavePromotor.init(APIValueParams, APIParameters, URL_SEND_DATA_PROMOTOR, SearchPromotor.this, json_promotor, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_promotor = new MultiParamGetDataJSON.JSONObjectResult() {
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
