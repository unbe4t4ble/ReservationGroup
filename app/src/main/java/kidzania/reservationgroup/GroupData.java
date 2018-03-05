package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GROUP_DATA;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.IDUSR_OWN;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.POSITION_DATA;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.SEND_DRAFT;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GROUP_DATA;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_TO_DRAFT;

/**
 * Created by mubarik on 10/11/2017.
 */

public class GroupData extends ParentListGroup {

    final String PARAM_SEND_DRAFT="id_num_esc";

    @Override
    public void initialization(){
        SENDER_CLASS = "GroupData";
        super.initialization();
    }

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(mMessageGroupData, new IntentFilter(SEND_DRAFT));
        refreshData();
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageGroupData);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageGroupData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sendGroupToDraft();
        }
    };


    @Override
    public void refreshData(){
        SENDER_CLASS = "GroupData";
        super.refreshData();
    }

    @Override
    public void getGroup(){
        super.getGroup();
        HEADER_GROUP = HEAD_GROUP_DATA;
        if(hasConnection(GroupData.this)) {
            MultiParamGetDataJSON getGroupData = new MultiParamGetDataJSON();
            getGroupData.init(APIValueParams, APIParameters, URL_GROUP_DATA, GroupData.this, json_group, false);
        }else{
            OpenLostConnection(this);
        }
    }

    private void AssignParamSendDraft(){
        clearAPIParams();
        APIParameters.add(PARAM_SEND_DRAFT);
    }

    private void AssignValueParamSendDraft(){
        clearAPIValueParam();
        APIValueParams.add(ID_NUM_ESC);
    }

    public void sendGroupToDraft(){
        DialogSendToDraft();
    }

    MultiParamGetDataJSON.JSONObjectResult json_send_todraft = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_STATUS);
                String ID = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    ID = objData.getString("STATUS");
                }
                if (!TextUtils.isEmpty(ID)) {
                    data.remove(POSITION_DATA);
                    listGroupAdapter.notifyItemRemoved(POSITION_DATA);
                    listGroupAdapter.notifyItemRangeChanged(POSITION_DATA, data.size());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void DialogSendToDraft(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(GroupData.this);
        View mView = LayoutInflater.from(GroupData.this).inflate(R.layout.pop_two_button, null);
        final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
        final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button btnYes = (Button) mView.findViewById(R.id.btnYes);
        final Button btnNo = (Button) mView.findViewById(R.id.btnNo);
        txtJudul.setText(getString(R.string.message_dialog_confirmation));
        txtMessage.setText(getString(R.string.message_send_to_draft1)+
                " '"+GRPNAME+"' "+getString(R.string.message_send_to_draft2));
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                excuteSendDraft();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    private void excuteSendDraft(){
        if ((IDUSR_OWN.equals(ID_USER)) || IDUSR_OWN.equals("null")) {
            AssignParamSendDraft();
            AssignValueParamSendDraft();
            MultiParamGetDataJSON sendToDraft = new MultiParamGetDataJSON();
            sendToDraft.init(APIValueParams, APIParameters, URL_SEND_TO_DRAFT, GroupData.this, json_send_todraft, false);
        }else{
            SingleDialodWithOutVoid(GroupData.this,
                    getString(R.string.message_dialog_warning),
                    getString(R.string.message_warning_invalid_owner) );
        }
    }
}
