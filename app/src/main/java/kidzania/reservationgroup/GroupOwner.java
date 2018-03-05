package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_SELECT_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GROUP_OWNER;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GROUP_OWNER;

/**
 * Created by mubarik on 27/12/2017.
 */

public class GroupOwner extends ParentListGroup {

    @Override
    public void initialization(){
        SENDER_CLASS = "GroupOwner";
        super.initialization();
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshData();
        registerReceiver(mMessageSelectGroup, new IntentFilter(GET_SELECT_GROUP));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageSelectGroup);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageSelectGroup = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onBackPressed();
        }
    };

    @Override
    public void refreshData(){
        SENDER_CLASS = "GroupOwner";
        super.refreshData();
    }

    @Override
    public void getGroup(){
        super.getGroup();
        HEADER_GROUP = HEAD_GROUP_OWNER;
        if(hasConnection(GroupOwner.this)) {
            MultiParamGetDataJSON getDataAvail = new MultiParamGetDataJSON();
            getDataAvail.init(APIValueParams, APIParameters, URL_GROUP_OWNER, GroupOwner.this, json_group, false);
        }else{
            OpenLostConnection(this);
        }
    }


}
